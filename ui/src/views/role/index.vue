<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.name" placeholder="角色名称" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
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
      <el-table-column label="编号" align="center" prop="code" />
      <el-table-column label="名称" width="150px" align="center">
        <template slot-scope="scope">
          <span class="link-type" @click="handleUpdate(scope.row)">{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" width="110px" align="center">
        <template slot-scope="scope">
          <span style="color:red;">{{ scope.row.status | statusFilter }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
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
          <el-button type="primary" size="mini" @click="handleUpdatePermission(row)">
            分配权限
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="right" label-width="70px" style="width: 400px; margin-left:50px;">
        <el-form-item label="ID" prop="id">
          <el-input v-model="temp.id" :disabled="true" />
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input v-model="temp.name" />
        </el-form-item>
        <el-form-item label="编号" prop="code">
          <el-input v-model="temp.code" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="temp.status" class="filter-item" placeholder="Please select">
            <el-option v-for="item in statusOptions" :key="item.key" :label="item.name" :value="item.key" />
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="temp.remark" />
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

    <el-dialog ref="dataRoleForm" title="分配权限" :visible.sync="dialogPermissionFormVisible">
      <el-form label-position="right" label-width="70px" style="width: 400px; margin-left:50px;">
        <el-form-item label="权限" prop="permissions">
          <permission-resource-tree v-model="permissionValues" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogPermissionFormVisible = false">
          取消
        </el-button>
        <el-button type="primary" @click="updatePermission()">
          提交
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import roleService from '@/api/console/role'
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import PermissionResourceTree from '@/views/components/PermissionResourceTree'

export default {
  name: 'Role',
  directives: { waves },
  components: { PermissionResourceTree },
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
      permissionValues: [],
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 10,
        name: undefined
      },
      temp: {
        id: undefined,
        name: '',
        code: '',
        status: 1,
        remark: ''
      },
      statusOptions: [{ key: 1, name: '有效' }, { key: 0, name: '无效' }],
      dialogFormVisible: false,
      dialogStatus: '',
      dialogPermissionFormVisible: false,
      textMap: {
        update: '编辑',
        create: '创建'
      },
      dialogPvVisible: false,
      rules: {
        name: [{ required: true, message: '名称为必填', trigger: 'change' }],
        status: [{ required: true, message: '状态为必选', trigger: 'blur' }],
        code: [{ required: true, message: 'code为必选', trigger: 'blur' }]
      },
      downloadLoading: false
    }
  },
  created() {
    this.getList()
  },
  methods: {
    handleCurrentChange(val) {
      this.currentRow = val
    },
    getList() {
      this.listLoading = true
      roleService.search(this.listQuery.name).then(response => {
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
        name: '',
        code: '',
        status: 1,
        remark: ''
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
          roleService.add(this.temp).then(response => {
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
    handleUpdatePermission(row) {
      roleService.queryPermissionResource(row.id).then(response => {
        this.permissionValues = response.data.map(m => m.code)
        this.dialogPermissionFormVisible = true
      })
    },
    updatePermission() {
      const id = this.currentRow.id
      const permissionResources = this.permissionValues.map(m => { return { code: m } })
      const tempData = { id: id, permissionResources }
      roleService.grantAuthorization(tempData).then((response) => {
        if (response.code === 1) {
          for (const v of this.list) {
            if (v.id === id) {
              v.permissionResources = permissionResources
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
        } else {
          this.$notify({
            title: '失败',
            message: response.msg,
            type: 'error',
            duration: 2000
          })
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
          roleService.update(tempData.id, tempData).then((response) => {
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
        const tHeader = ['id', 'name', 'code']
        const filterVal = ['id', 'name', 'code']
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
