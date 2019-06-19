import request from '@/utils/request'
import serverConfig from '../setting'
import BaseService from '../baseService.js'

const controllerName = serverConfig.ConsleServerName + '/api/user/'

export function login(data) {
  return request({
    url: controllerName + 'getToken',
    method: 'post',
    data
  })
}

export function getUserInfo(accessToken) {
  return request({
    url: controllerName + 'getUserInfo',
    method: 'get',
    params: { accessToken }
  })
}

export function logout() {
  return request({
    url: controllerName + 'logout',
    method: 'get'
  })
}

export function refreshToken(accessToken) {
  return request({
    url: controllerName + 'refreshToken',
    method: 'post',
    params: { accessToken }
  })
}

class UserService extends BaseService {
  constructor() {
    super(controllerName)
  }

  updatePassword(data) {
    return this.send({
      urlMethod: 'updatePassword',
      method: 'put',
      data
    })
  }

  updateRole(data) {
    return this.send({
      urlMethod: 'updateRole',
      method: 'put',
      data
    })
  }
}

export const userService = new UserService()
