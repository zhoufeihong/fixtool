<template>
  <el-cascader
    v-model="value"
    :options="options"
    @change="handleChange" />
</template>

<script>
import { Cascader } from 'element-ui'
export default {
  name: 'PermissionResourceCascader',
  component: { Cascader },
  props: {
    value: {
      required: true,
      type: Array
    }},
  data() {
    return {
      options: []
    }
  },
  methods: {
    handleChange(value) {
      console.log(value)
    }
  }
}  
</script>