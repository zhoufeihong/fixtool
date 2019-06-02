import axios from 'axios'
import { MessageBox, Message } from 'element-ui'
import store from '@/store'
import serverConfig from '@/api/setting'

const excludes = [serverConfig.ConsleServerName + '/user/login', serverConfig.ConsleServerName + '/user/refreshToken']

// create an axios instance
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  withCredentials: true, // send cookies when cross-domain requests
  timeout: 30000 // request timeout
})

// request interceptor
service.interceptors.request.use(
  config => {
    // do something before request is sent
    if (excludes.includes(config.url)) {
      return config
    }

    if (store.getters.token) {
      // 判断token是否快过期
      const expireTime = store.state.user.expireTime || 0
      const leftTime = expireTime - new Date().getTime()
      if (leftTime < 5 * 60 * 1000 && leftTime > 0) {
        store.dispatch('user/refreshToken')
      }
      // let each request carry token
      // ['X-Token'] is a custom headers key
      // please modify it according to the actual situation
      config.headers['Authorization'] = store.getters.token
    }
    return config
  },
  error => {
    // do something with request error
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(
  /**
   * If you want to get http information such as headers or status
   * Please return  response => response
  */

  /**
   * Determine the request status by custom code
   * Here is just an example
   * You can also judge the status by HTTP Status Code
   */
  response => {
    const res = response.data
    // if the custom code is not 20000, it is judged as an error.
    if (res.code !== 1) {
      if (res.code === -1) {
        Message({
          message: res.msg || 'error',
          type: 'error',
          duration: 5 * 1000
        })
      }
      // 50008: Illegal token; 50009: Token expired;
      if (res.specificCode === 50008 || res.specificCode === 50009) {
        // to re-login
        MessageBox.confirm('You have been logged out, you can cancel to stay on this page, or log in again', 'Confirm logout', {
          confirmButtonText: 'Re-Login',
          cancelButtonText: 'Cancel',
          type: 'warning'
        }).then(() => {
          store.dispatch('user/resetToken').then(() => {
            location.reload()
          })
        }).catch(() => {})
      }
      return Promise.reject(res.message || 'error')
    } else {
      return res
    }
  },
  error => {
    console.log('err' + error) // for debug
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service
