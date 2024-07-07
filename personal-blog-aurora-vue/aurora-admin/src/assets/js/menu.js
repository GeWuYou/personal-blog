import Layout from '@/layout/index.vue'
import router from '@/router'
import store from '@/store'
import Vue from 'vue'
import { _get } from '@/api/api'

export function generaMenu() {
  _get('/menu/user', {}, (data) => {
    let userMenus = data
    userMenus.forEach((item) => {
      if (item.icon != null) {
        item.icon = 'iconfont ' + item.icon
      }
      if (item.component === 'Layout') {
        item.component = Layout
      }
      if (item.children && item.children.length > 0) {
        item.children.forEach((route) => {
          route.icon = 'iconfont ' + route.icon
          route.component = loadView(route.component)
        })
      }
    })
    store.commit('saveUserMenus', userMenus)
    userMenus.forEach((item) => {
      router.addRoute(item)
    })
  }, (message) => {
    Vue.prototype.$message.warning(message)
    router.push({ path: '/login' }).then(() => {
    })
  })
}

export const loadView = (view) => {
  return (resolve) => require([`@/views${view}`], resolve)
}
