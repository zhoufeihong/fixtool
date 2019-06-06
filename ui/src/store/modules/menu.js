import { startBaseRoutes, endBaseRoutes, constantRoutes, resetRouter } from '@/router'
import Layout from '@/layout'
import { moduleService } from '@/api/console/module'
import { getMenus, setMenus } from '@/utils/auth'

const state = {
  routes: null
}

const mutations = {
  setRoutes: (state, routes) => {
    state.routes = routes
  }
}

const actions = {
  createRoutes: async({ commit }) => {
    let menusTemp = getMenus()
    if (!menusTemp || Object.keys(menusTemp).length === 0) {
      const { data } = await moduleService.listMenu()
      menusTemp = data
      setMenus(menusTemp)
    }
    const routes = listRouter(menusTemp)
    commit('setRoutes', startBaseRoutes.concat(routes))
    constantRoutes.length = 0
    constantRoutes.push(...startBaseRoutes)
    constantRoutes.push(...routes)
    constantRoutes.push(...endBaseRoutes)
    resetRouter()
  }
}

const listRouter = (menus) => {
  let mTemp = menus
  // 子节点
  if (mTemp.id === 0) {
    mTemp = mTemp.childMenus
  }
  // 转换菜单
  const getRoute = (menu) => {
    if (menu.url == null || menu.url.length === 0) {
      return {
        path: '/' + menu.name,
        name: menu.name,
        component: Layout,
        alwaysShow: true,
        meta: { title: menu.name, icon: 'dashboard' },
        children: []
      }
    } else {
      if (menu.url != null && (!menu.url.startsWith('http') && !menu.url.startsWith('https'))) {
        return {
          path: menu.url,
          name: menu.name,
          component: (resolve) => {
            require(['@/views' + menu.url + '.vue'], resolve)
          },
          meta: { title: menu.name, icon: 'dashboard' }
        }
      }
      return {
        path: menu.url,
        name: menu.name,
        meta: { title: menu.name, icon: 'dashboard' }
      }
    }
  }
  const mapRoute = (menus) => {
    const tempRoutes = menus.map(m => {
      const tempRoute = getRoute(m)
      if (m.childMenus != null && m.childMenus.length > 0) {
        tempRoute.children = mapRoute(m.childMenus)
        tempRoute.redirect = tempRoute.children[0].path
      }
      return tempRoute
    })
    return tempRoutes
  }
  return mapRoute(mTemp)
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

