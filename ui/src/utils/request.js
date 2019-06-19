import axios from 'axios'
import { MessageBox, Message, Loading } from 'element-ui'
import store from '@/store'
import serverConfig from '@/api/setting'

const excludes = [serverConfig.ConsleServerName + '/api/user/login', serverConfig.ConsleServerName + '/api/user/refreshToken']

// create an axios instance
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  withCredentials: true, // send cookies when cross-domain requests
  timeout: 60000 // request timeout
})

let loadingInstance
const startLoading = () => {
  loadingInstance = Loading.service({
    text: '请稍等'
  })
}

const reLogin = () => {
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
    startLoading()
    return config
  },
  error => {
    loadingInstance.close()
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
    loadingInstance.close()
    const res = response.data
    // if the custom code is not 1.
    if (res.code !== 1) {
      if (res.code === -1) {
        Message({
          message: res.msg || 'error',
          type: 'error',
          duration: 5 * 1000
        })
        return Promise.reject(res.msg || 'error')
      }
      // 50008: Illegal token; 50009: Token expired;
      if (res.specificCode === 50008 || res.specificCode === 50009) {
        reLogin()
      }
      return res
    } else {
      return res
    }
  },
  error => {
    loadingInstance.close()
    console.log('err' + error) // for debug
    if (error.response.status === 401) {
      reLogin()
    } else {
      Message({
        message: error.message,
        type: 'error',
        duration: 5 * 1000
      })
    }
    return Promise.reject(error)
  }
)

export default service
