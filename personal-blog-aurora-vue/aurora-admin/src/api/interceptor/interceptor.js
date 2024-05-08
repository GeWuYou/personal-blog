// 添加请求拦截器
function requestInterceptor(config) {
  // 判断是否为预检请求
  if (config.method === 'options' || shouldExclude(config.url)) {
    // 不需要设置Authorization请求头，直接返回config
    console.log(config.url + '不需要设置Authorization请求头')
    return config
  }
  // 先从持久化的存储中尝试获取token
  let token = localStorage.getItem('accessToken')
  // 如果token存在，则设置Authorization请求头
  if (token !== null && token !== undefined) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
}

// 判断请求的URL是否需要排除
function shouldExclude(url) {
  // 在这里添加需要排除的路径规则
  // 例如，排除以 "/public" 开头的请求
  // if (url.startsWith('/public')) {
  //     return true;
  // }
  // 添加其他需要排除的路径规则
  // ...
  const excludes = ['/api/v1/admin/users/login', '/api/v1/admin/report']
  return excludes.some(exclude => url === exclude)
}

// 添加响应拦截器
function responseInterceptor(response) {
  // 2xx 范围内的状态码都会触发该函数。
  let rememberMe = true
  // 获取本地token
  let localToken = localStorage.getItem('token')
  if (localToken === null) {
    rememberMe = false
  }
  // 获取响应头中的token
  let token = response.headers.get('Authorization')
  // 对响应数据做点什么
  if (token !== null && token !== undefined) {
    // 如果本地的token不为空且与响应中的token不同则更新token
    if (rememberMe && localToken !== token) {
      // 清理旧token
      localStorage.removeItem('token')
      // 重新设置token
      localStorage.setItem('token', token)
    }
    if (!rememberMe && sessionStorage !== token) {
      console.log('token是：' + token)
      // 清理旧token
      sessionStorage.removeItem('token')
      console.log('已设置')
      // 重新设置token
      sessionStorage.setItem('token', token)
    }
  }
  return response
}

// 导出拦截器
export { requestInterceptor, responseInterceptor }