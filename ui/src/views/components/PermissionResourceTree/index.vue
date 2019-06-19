<template>
  <div>
    <el-input v-if="showSearch" v-model="filterText" placeholder="输入关键字进行过滤" />
    <el-tree ref="tree" :data="treeData" show-checkbox default-expand-all node-key="value" highlight-current :props="defaultProps" :filter-node-method="filterNode" @check="handleChange" />
  </div>
</template>

<script>
import { permissionResourceService } from '@/api/console/permissionResource'
export default {
  name: 'PermissionResourceTree',
  props: {
    value: {
      required: true,
      type: Array
    },
    isRoot: {
      required: false,
      default: false,
      type: Boolean
    },
    showSearch: {
      required: false,
      default: true,
      type: Boolean
    }},
  data() {
    return {
      filterText: '',
      treeData: [],
      defaultProps: {
        children: 'children',
        label: 'label'
      }
    }
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val)
    },
    value(val) {
      this.$refs.tree.setCheckedKeys(val)
    }
  },
  created() {
    this.listPermissionResource()
  },
  methods: {
    filterNode(value, treeData) {
      if (!value) return true
      return treeData.label.indexOf(value) !== -1
    },
    listPermissionResource() {
      permissionResourceService.search('').then(
        response => {
          this.treeData = this.handleListPermissionResource(response.data)
          this.$refs.tree.setCheckedKeys(this.value)
        }
      )
    },
    handleListPermissionResource(data) {
      let rootValue = '0'
      if (this.isRoot) {
        rootValue = '-1'
      }
      const dataRoot = {
        value: rootValue,
        children: []
      }
      const findP = (dataOption) => {
        data.forEach(element => {
          if (element.parentCode === dataOption.value) {
            const tempOption = {
              value: element.code,
              label: element.name
            }
            findP(tempOption, element.code)
            if (!dataOption.children) {
              dataOption.children = []
            }
            dataOption.children.push(tempOption)
          }
        })
      }
      findP(dataRoot)
      return dataRoot.children
    },
    handleChange(data) {
      const val = this.$refs.tree.getCheckedKeys()
      if (val && val instanceof Array) {
        if (val.length > 0) {
          this.$emit('input', val)
          return
        }
      }
      this.$emit('input', [])
    }
  }
}
</script>
