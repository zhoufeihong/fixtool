const TokenKey = 'fixtool_token'

const MenuKey = 'fixtool_menu'

export function getToken() {
  return JSON.parse(sessionStorage.getItem(TokenKey) || '{}')
}

export function setToken(tokenData) {
  return sessionStorage.setItem(TokenKey, JSON.stringify(tokenData))
}

export function removeTokenData() {
  return sessionStorage.removeItem(TokenKey)
}

export function getMenus() {
  return JSON.parse(sessionStorage.getItem(MenuKey) || '{}')
}

export function setMenus(menuData) {
  return sessionStorage.setItem(MenuKey, JSON.stringify(menuData))
}

export function removeMenus() {
  return sessionStorage.removeItem(MenuKey)
}
