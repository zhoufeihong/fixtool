import serverConfig from '../setting'
import BaseService from '../baseService.js'

const controllerName = serverConfig.ConsleServerName + '/api/module/'

class ModuleService extends BaseService {
  constructor() {
    super(controllerName)
  }
  listMenu() {
    return this.send({
      urlMethod: 'listMenu',
      method: 'get',
      params: { rootId: 0 }
    })
  }
  listModuleByParentId(parentId) {
    return this.send({
      urlMethod: 'listModuleByParentId',
      method: 'get',
      params: { parentId }
    })
  }
}

export const moduleService = new ModuleService()
