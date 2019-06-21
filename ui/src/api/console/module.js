import serverConfig from '../setting'
import BaseService from '../baseService.js'

const controllerName = serverConfig.ConsleServerName + '/api/module/'

class ModuleService extends BaseService {
  constructor() {
    super(controllerName)
  }
  listMenu() {
    return this.send({
      urlMethod: 'menus',
      method: 'get'
    })
  }
  listModuleByParentId(parentId) {
    return this.send({
      urlMethod: parentId + '/Submodule',
      method: 'get'
    })
  }
}

export const moduleService = new ModuleService()
