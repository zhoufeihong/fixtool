import serverConfig from '../setting'
import BaseService from '../baseService.js'

const controllerName = serverConfig.ConsleServerName + '/api/role/'

class RoleService extends BaseService {
  constructor() {
    super(controllerName)
  }
  grantAuthorization(data) {
    return this.send({
      urlMethod: data.id + '/permission_resources',
      method: 'patch',
      data
    })
  }
  queryPermissionResource(roleId) {
    return this.send({
      urlMethod: roleId + '/permission_resources',
      method: 'get'
    })
  }
}

const roleService = new RoleService()

export default roleService
