<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.name" placeholder="名称" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
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
      <el-table-column label="名称" width="150px" align="center">
        <template slot-scope="scope">
          <span class="link-type" @click="handleUpdate(scope.row)">{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="url地址" align="center" prop="url" />
      <el-table-column label="状态" width="110px" align="center">
        <template slot-scope="scope">
          <span style="color:red;">{{ scope.row.status | statusFilter }}</span>
        </template>
      </el-table-column>
      <el-table-column label="权限">
        <template slot-scope="scope">
          <span>{{ scope.row.permissionResourcesCode }}</span>
        </template>
      </el-table-column>
      <el-table-column label="排序" prop="rankIndex" width="110px" align="center" />
      <el-table-column label="操作" align="center" width="230" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button type="primary" size="mini" @click="handleUpdate(row)">
            修改
          </el-button>
          <el-button v-if="row.status!='deleted'" size="mini" type="danger" @click="handleDelete(row)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="70px" style="width: 400px; margin-left:50px;">
        <el-form-item label="ID" prop="id">
          <el-input v-model="temp.id" :disabled="true" />
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input v-model="temp.name" />
        </el-form-item>
        <el-form-item label="url" prop="url">
          <el-input v-model="temp.url" />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="temp.status" class="filter-item" placeholder="Please select">
            <el-option v-for="item in statusOptions" :key="item.key" :label="item.name" :value="item.key" />
          </el-select>
        </el-form-item>
        <el-form-item label="父菜单">
          <el-select v-model="temp.parentId" class="filter-item" placeholder="Please select">
            <el-option v-for="item in listParentOptions" :key="item.id" :label="item.name" :value="item.id" :disabled="item.id==temp.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="权限">
          <permission-resource-cascader v-model="temp.permissionResourcesCode" />
        </el-form-item>
        <el-form-item label="排序">
          <el-input v-model="temp.rankIndex" :max="3" style="margin-top:8px;" type="number" />
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
  </div>
</template>

<script>
import { moduleService } from '@/api/console/module'
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import { MessageBox } from 'element-ui'
import PermissionResourceCascader from '@/views/components/PermissionResourceCascader'

export default {
  name: 'Module',
  components: { Pagination, PermissionResourceCascader },
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
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 10,
        name: undefined
      },
      listParentOptions: undefined,
      temp: {
        id: undefined,
        name: '',
        url: '',
        parentId: 0,
        isLeaf: '',
        status: 1,
        permissionResourcesCode: '',
        rankIndex: 0
      },
      statusOptions: [{ key: 1, name: '有效' }, { key: 0, name: '无效' }],
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑',
        create: '新增'
      },
      dialogPvVisible: false,
      rules: {
        name: [{ required: true, message: '名称为必填', trigger: 'change' }],
        rankIndex: [{ required: true, message: '排序为必填', trigger: 'blur' }]
      },
      downloadLoading: false
    }
  },
  created() {
    this.getList()
    this.getListParentOptions()
  },
  methods: {
    handleCurrentChange(val) {
      this.currentRow = val
    },
    getList() {
      this.listLoading = true
      moduleService.searchPageList(this.listQuery).then(response => {
        this.list = response.data
        this.total = response.total

        // Just to simulate the time of the request
        setTimeout(() => {
          this.listLoading = false
        }, 0.5 * 1000)
      })
    },
    getListParentOptions() {
      moduleService.listModuleByParentId(0).then(response => {
        this.listParentOptions = [{ 'id': 0, name: '第一级菜单' }].concat(response.data)
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
        url: '',
        parentId: 0,
        isLeaf: '',
        status: 1,
        permissionResourcesCode: '',
        rankIndex: 0
      }
    },
    handleCreate() {
      this.resetTemp()
      this.getListParentOptions()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      if (this.currentRow && this.currentRow.parentId === 0) {
        this.temp.parentId = this.currentRow.id
      }
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          moduleService.add(this.temp).then(response => {
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
            }
          })
        }
      })
    },
    handleUpdate(row) {
      this.temp = Object.assign({}, row) // copy obj
      this.temp.timestamp = new Date(this.temp.timestamp)
      this.getListParentOptions()
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          const tempData = Object.assign({}, this.temp)
          moduleService.update(tempData.id, tempData).then((response) => {
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
    handleDelete(row) {
      MessageBox.confirm('确认删除？', '信息', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        moduleService.delete(row.id).then(response => {
          if (response.code === 1) {
            const index = this.list.indexOf(row)
            this.list.splice(index, 1)
          }
        })
      }).catch(() => {})
    },
    handleDownload() {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = ['id', 'name', 'url']
        const filterVal = ['id', 'name', 'url']
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
