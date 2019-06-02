import request from '@/utils/request'
import serverConfig from '../setting'

const serverName = serverConfig.ConsleServerName

export function login(data) {
  return request({
    url: serverName + '/user/getToken',
    method: 'post',
    data
  })
}

export function getUserInfo(accessToken) {
  return request({
    url: serverName + '/user/getUserInfo',
    method: 'get',
    params: { accessToken }
  })
}

export function logout() {
  return request({
    url: serverName + '/user/logout',
    method: 'get'
  })
}

export function refreshToken(accessToken) {
  return request({
    url: serverName + '/user/refreshToken',
    method: 'post',
    params: { accessToken }
  })
}
