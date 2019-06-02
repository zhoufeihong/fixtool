import request from '@/utils/request'
import serverConfig from '../setting'

const serverName = serverConfig.ConsleServerName

class ModuleService {
  listMenu() {
    return request({
      url: serverName + '/module/listMenu',
      method: 'get',
      params: { rootId: 0 }
    })
  }
  listModule(name, page, limit, sort) {
    return request({
      url: serverName + '/module/listModule',
      method: 'get',
      params: { name, page, limit, sort }
    })
  }
  listModuleByParentId(parentId) {
    return request({
      url: serverName + '/module/listModuleByParentId',
      method: 'get',
      params: { parentId }
    })
  }
  updateModule(data) {
    return request({
      url: serverName + '/module/updateModule',
      method: 'post',
      data
    })
  }
  addModule(data) {
    return request({
      url: serverName + '/module/addModule',
      method: 'post',
      data
    })
  }
  removeModule(data) {
    return request({
      url: serverName + '/module/removeModule',
      method: 'post',
      data
    })
  }
}

export const moduleService = new ModuleService()
