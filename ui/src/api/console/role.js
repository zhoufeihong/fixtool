import serverConfig from '../setting'
import BaseService from '../baseService.js'

const controllerName = serverConfig.ConsleServerName + '/api/role/'

class RoleService extends BaseService {
  constructor() {
    super(controllerName)
  }
  grantAuthorization(data) {
    return this.send({
      urlMethod: 'grantAuthorization',
      method: 'post',
      data
    })
  }
  queryPermissionResource(roleId) {
    return this.send({
      urlMethod: 'queryPermissionResource',
      method: 'get',
      params: { roleId }
    })
  }
}

const roleService = new RoleService()

export default roleService
