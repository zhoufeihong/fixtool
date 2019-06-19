import serverConfig from '../setting'
import BaseService from '../baseService.js'

const controllerName = serverConfig.ConsleServerName + '/api/permission_resource/'

class PermissionResourceService extends BaseService {
  constructor() {
    super(controllerName)
  }
}

export const permissionResourceService = new PermissionResourceService()
