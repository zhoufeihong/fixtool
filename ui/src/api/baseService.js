import request from '@/utils/request'

export default class BaseService {
  constructor(controllerName) {
    this.controllerName = controllerName
  }

  // 查询
  search(params) {
    return request({
      url: this.controllerName + 'search',
      method: 'get',
      params: params
    })
  }

  // 分页查询
  searchPageList(params) {
    return request({
      url: this.controllerName + 'searchPageList',
      method: 'get',
      params: params
    })
  }

  // 获取
  get(id) {
    return request({
      url: this.controllerName + id,
      method: 'get'
    })
  }

  // 新增
  add(data) {
    return request({
      url: this.controllerName,
      method: 'post',
      data
    })
  }

  // 更新
  update(id, data) {
    return request({
      url: this.controllerName + id,
      method: 'put',
      data
    })
  }

  // 删除
  delete(id) {
    return request({
      url: this.controllerName + id,
      method: 'delete'
    })
  }

  // 发送请求
  send(requestData) {
    // url = 请求控制器 + 请求方法
    requestData.url = this.controllerName + requestData.urlMethod
    return request(requestData)
  }
}
