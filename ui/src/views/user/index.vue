<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.userName" placeholder="姓名" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        查找
      </el-button>
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate">
        添加
      </el-button>
      <el-button v-waves :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-download" @click="handleDownload">
        导出
      </el-button>
    </div>

    <el-table
      ref="userTable"
      :key="tableKey"
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%;"
      @sort-change="sortChange"
      @current-change="handleCurrentChange"
    >
      <el-table-column label="ID" prop="id" sortable="custom" align="center" width="80">
        <template slot-scope="scope">
          <span>{{ scope.row.id }}</span>
        </template>
      </el-table-column>
      <el-table-column label="用户编号" align="center" prop="userCode" />
      <el-table-column label="用户名" width="150px" align="center">
        <template slot-scope="scope">
          <span class="link-type" @click="handleUpdate(scope.row)">{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="email" align="center" prop="email" />
      <el-table-column label="图标" align="center" prop="avatar" />
      <el-table-column label="状态" width="110px" align="center">
        <template slot-scope="scope">
          <span style="color:red;">{{ scope.row.status | statusFilter }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建时间">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="230" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button type="primary" size="mini" @click="handleUpdate(row)">
            修改
          </el-button>
          <el-button type="primary" size="mini" @click="handleUpdateRole(row)">
            分配角色
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="right" label-width="70px" style="width: 400px; margin-left:50px;">
        <el-form-item label="ID" prop="id">
          <el-input v-model="temp.id" :disabled="true" />
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input v-model="temp.name" />
        </el-form-item>
        <el-form-item label="用户编号" prop="userCode">
          <el-input v-model="temp.userCode" :readonly="dialogStatus!='create'" placeholder="不填则自动生成" />
        </el-form-item>
        <el-form-item v-if="dialogStatus=='create'" label="密码" prop="password">
          <el-input v-model="temp.password" :type="passwordType" />
          <span class="show-pwd" @click="showPwd">
            <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
          </span>
        </el-form-item>
        <el-form-item v-if="dialogStatus=='create'" label="确认密码" prop="password_confirm">
          <el-input v-model="temp.password1" :type="passwordType" />
        </el-form-item>
        <el-form-item label="email" prop="email">
          <el-input v-model="temp.email" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="temp.status" class="filter-item" placeholder="Please select">
            <el-option v-for="item in statusOptions" :key="item.key" :label="item.name" :value="item.key" />
          </el-select>
        </el-form-item>
        <el-form-item label="图标">
          <el-input v-model="temp.avatar" :max="3" style="margin-top:8px;" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          取消
        </el-button>
        <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">
          提交
        </el-button>
      </div>
    </el-dialog>

    <el-dialog ref="dataRoleForm" title="分配角色" :visible.sync="dialogRoleFormVisible">
      <el-form label-position="right" label-width="70px" style="width: 400px; margin-left:50px;">
        <el-form-item label="角色" prop="roles">
          <el-select v-model="roleValues" multiple filterable placeholder="请选择" style="width:90%">
            <el-option
              v-for="item in roleOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogRoleFormVisible = false">
          取消
        </el-button>
        <el-button type="primary" @click="updateRole()">
          提交
        </el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { userService } from '@/api/console/user'
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import roleService from '@/api/console/role'

export default {
  name: 'Module',
  components: { Pagination },
  directives: { waves },
  filters: {
    statusFilter(status) {
      const statusMap = {
        1: '有效',
        0: '无效'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      currentRow: null,
      roleValues: [],
      roleOptions: undefined,
      passwordType: 'password',
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 10,
        userName: undefined
      },
      temp: {
        id: undefined,
        userCode: '',
        name: '',
        email: '',
        avatar: '',
        status: 1,
        createTime: undefined,
        password: '',
        password1: ''
      },
      statusOptions: [{ key: 1, name: '有效' }, { key: 0, name: '无效' }],
      dialogFormVisible: false,
      dialogStatus: '',
      dialogRoleFormVisible: false,
      textMap: {
        update: '修改',
        create: '创建'
      },
      dialogPvVisible: false,
      rules: {
        name: [{ required: true, message: '名称为必填', trigger: 'change' }],
        status: [{ required: true, message: 'status为必选', trigger: 'blur' }],
        email: [{ required: true, type: 'email', message: '格式不对', trigger: 'blur' }],
        password: [{ required: true, message: '密码为必填', trigger: 'blur' }],
        password_confirm: [{ validator: this.validatePass, trigger: 'blur' }]
      },
      downloadLoading: false
    }
  },
  created() {
    this.getList()
    this.getRoleList()
  },
  methods: {
    handleCurrentChange(val) {
      this.currentRow = val
    },
    validatePass(rule, value, callback) {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (this.temp.password1 !== this.temp.password) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    },
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
    },
    getRoleList() {
      this.listLoading = true
      roleService.search().then(response => {
        this.roleOptions = response.data
        setTimeout(() => {
          this.listLoading = false
        }, 0.5 * 1000)
      })
    },
    getList() {
      this.listLoading = true
      userService.search(this.listQuery).then(response => {
        this.list = response.data
        this.total = response.total

        // Just to simulate the time of the request
        setTimeout(() => {
          this.listLoading = false
        }, 0.5 * 1000)
      })
    },
    sortChange(data) {
      const { prop, order } = data
      if (prop === 'id') {
        this.sortByID(order)
      }
    },
    sortByID(order) {
      if (order === 'ascending') {
        this.listQuery.sort = '+id'
      } else {
        this.listQuery.sort = '-id'
      }
      this.handleFilter()
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    resetTemp() {
      this.temp = {
        id: undefined,
        userCode: '',
        name: '',
        email: '',
        avatar: '',
        status: 1,
        createTime: undefined,
        password: '',
        password1: ''
      }
    },
    handleCreate() {
      this.resetTemp()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          userService.add(this.temp).then(response => {
            if (response.code === 1) {
              // this.list.unshift(this.temp)
              this.dialogFormVisible = false
              this.$notify({
                title: '成功',
                message: '创建成功',
                type: 'success',
                duration: 2000
              })
              this.getList()
            } else {
              this.$notify({
                title: '失败',
                message: response.msg,
                type: 'error',
                duration: 2000
              })
            }
          })
        }
      })
    },
    handleUpdate(row) {
      this.temp = Object.assign({}, row) // copy obj
      this.temp.timestamp = new Date(this.temp.timestamp)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    handleUpdateRole(row) {
      this.dialogRoleFormVisible = true
      this.roleValues = row.userRoles.map(m => { return m.id })
    },
    updateRole() {
      const userId = this.currentRow.id
      const userRoles = this.roleValues.map(m => { return { id: m } })
      const tempData = { id: userId, userRoles }
      userService.updateRole(tempData).then((response) => {
        if (response.code === 1) {
          for (const v of this.list) {
            if (v.id === userId) {
              v.userRoles = userRoles
              break
            }
          }
          this.$notify({
            title: '成功',
            message: '修改成功',
            type: 'success',
            duration: 2000
          })
          this.dialogRoleFormVisible = false
        }
      }).catch(error => {
        this.$notify({
          title: '失败',
          message: error,
          type: 'error',
          duration: 2000
        })
      })
    },
    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          const tempData = Object.assign({}, this.temp)
          userService.update(tempData.id, tempData).then((response) => {
            if (response.code === 1) {
              for (const v of this.list) {
                if (v.id === this.temp.id) {
                  const index = this.list.indexOf(v)
                  this.list.splice(index, 1, this.temp)
                  break
                }
              }
              this.$notify({
                title: '成功',
                message: '修改成功',
                type: 'success',
                duration: 2000
              })
              this.dialogFormVisible = false
            }
          }).catch(error => {
            this.$notify({
              title: '失败',
              message: error,
              type: 'error',
              duration: 2000
            })
          })
        }
      })
    },
    handleDownload() {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = ['id', 'name', 'email']
        const filterVal = ['id', 'name', 'email']
        const data = this.formatJson(filterVal, this.list)
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: 'table-list'
        })
        this.downloadLoading = false
      })
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(v => filterVal.map(j => {
        if (j === 'timestamp') {
          return parseTime(v[j])
        } else {
          return v[j]
        }
      }))
    }
  }
}
</script>
<style>

</style>
