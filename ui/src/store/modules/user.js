import { login, logout, getUserInfo as getInfo, refreshToken as refreshTokenApi } from '@/api/console/user'
import { getToken, setToken, removeTokenData as removeToken, removeMenus } from '@/utils/auth'
import { resetRouter } from '@/router'
import { Message } from 'element-ui'

const state = {
  token: getToken().token,
  expireTime: getToken().expireTime,
  name: '',
  avatar: ''
}

const mutations = {
  SET_TOKEN: (state, tokenData) => {
    state.token = tokenData.token
    state.expireTime = tokenData.expireTime
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  }
}

const actions = {
  // user login
  login({ commit }, userInfo) {
    const { userName, password } = userInfo
    return new Promise((resolve, reject) => {
      login({ userName: userName.trim(), password: password }).then(response => {
        if (response.code === 0) {
          Message({
            message: response.msg || 'error',
            type: 'error',
            duration: 5 * 1000
          })
          resolve()
          return
        }
        const { data } = response
        const tokenData = { token: data.accessToken, expireTime: data.expireTime }
        commit('SET_TOKEN', tokenData)
        setToken(tokenData)
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // refresh token
  refreshToken({ commit, state }) {
    const accessToken = state.token
    return new Promise((resolve, reject) => {
      refreshTokenApi(accessToken).then(response => {
        if (response.code === 0) {
          Message({
            message: response.msg || 'error',
            type: 'error',
            duration: 5 * 1000
          })
          resolve()
          return
        }
        const { data } = response
        const tokenData = { token: data.accessToken, expireTime: data.expireTime }
        commit('SET_TOKEN', tokenData)
        setToken(tokenData)
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // get user info
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      getInfo(state.token).then(response => {
        const { data } = response

        if (!data) {
          reject('Verification failed, please Login again.')
        }

        const { name, avatar } = data

        commit('SET_NAME', name)
        commit('SET_AVATAR', avatar)
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // user logout
  logout({ commit, state }) {
    return new Promise((resolve, reject) => {
      logout(state.token).then(() => {
        commit('SET_TOKEN', '')
        removeToken()
        resetRouter()
        removeMenus()
        resolve()
        location.reload()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      commit('SET_TOKEN', '')
      removeToken()
      removeMenus()
      resolve()
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

