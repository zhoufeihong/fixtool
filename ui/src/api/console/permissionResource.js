import request from '@/utils/request'
import serverConfig from '../setting'

const controllerName = serverConfig.ConsleServerName + '/permission_resource/'

class PermissionResourceService {
  listPermissionResource(listQuery) {
    const { name, page, limit } = listQuery
    return request({
      url: controllerName + 'listPermissionResource',
      method: 'get',
      params: { name, page, limit }
    })
  }
  listPermissionResourceByName(name) {
    return request({
      url: controllerName + 'listPermissionResourceByName',
      method: 'get',
      params: { name }
    })
  }
  addPermissionResource(data) {
    return request({
      url: controllerName + 'addPermissionResource',
      method: 'post',
      data
    })
  }
  removePermissionResource(data) {
    return request({
      url: controllerName + 'removePermissionResource',
      method: 'post',
      data
    })
  }
  updatePermissionResource(data) {
    return request({
      url: controllerName + 'updatePermissionResource',
      method: 'post',
      data
    })
  }
}

export const permissionResourceService = new PermissionResourceService()
