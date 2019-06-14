import request from '@/utils/request'
import serverConfig from '../setting'

const controllerName = serverConfig.ConsleServerName + '/user/'

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

class UserService {
  listUser(listQuery) {
    return request({
      url: controllerName + 'listUser',
      method: 'get',
      params: listQuery
    })
  }
  addUser(data) {
    return request({
      url: controllerName + 'addUser',
      method: 'post',
      data
    })
  }
  updateUser(data) {
    return request({
      url: controllerName + 'updateUser',
      method: 'post',
      data
    })
  }
  updatePassword(data) {
    return request({
      url: controllerName + 'updatePassword',
      method: 'post',
      data
    })
  }
  updateRole(data) {
    return request({
      url: controllerName + 'updateRole',
      method: 'post',
      data
    })
  }
}

export const userService = new UserService()
