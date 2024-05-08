import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)
// 解决报错
const originalPush = Router.prototype.push
const originalReplace = Router.prototype.replace

const routes = [
  {
    path: '/login',
    name: '登录',
    hidden: true,
    component: () => import('../views/login/Login.vue')
  }
]
const createRouter = () =>
  new Router({
    mode: 'history',
    routes: routes
  })
const router = createRouter()

export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher
}

// push
Router.prototype.push = function push(location, onResolve, onReject) {
  if (onResolve || onReject) return originalPush.call(this, location, onResolve, onReject)
  return originalPush.call(this, location).catch(err => err)
}
// replace
Router.prototype.replace = function push(location, onResolve, onReject) {
  if (onResolve || onReject) return originalReplace.call(this, location, onResolve, onReject)
  return originalReplace.call(this, location).catch(err => err)
}
export default router
