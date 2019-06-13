<template>
  <el-cascader
    v-model="dataValue"
    :options="options"
    :props="{ checkStrictly: true }"
    style="width:100%"
    @change="handleChange"
  />
</template>

<script>
import { permissionResourceService } from '@/api/console/permissionResource'
export default {
  name: 'PermissionResourceCascader',
  props: {
    value: {
      required: false,
      default: '',
      type: String
    },
    isRoot: {
      required: false,
      default: false,
      type: Boolean
    }},
  data() {
    return {
      dataValue: [this.value],
      options: []
    }
  },
  watch: {
    value(val) {
      this.dataValue = val
    }
  },
  created() {
    this.listPermissionResource()
  },
  methods: {
    listPermissionResource() {
      permissionResourceService.listPermissionResourceByName('').then(
        response => {
          this.options = this.handleListPermissionResource(response.data)
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
    handleChange(val) {
      if (val && val instanceof Array) {
        if (val.length > 0) {
          this.$emit('input', val[val.length - 1])
          return
        }
      }
      this.$emit('input', '')
    }
  }
}
</script>
