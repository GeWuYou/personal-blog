// 添加请求拦截器
function requestInterceptor(config) {
  // 判断是否为预检请求
  if (config.method === 'options' || shouldExclude(config.url)) {
    // 不需要设置Authorization请求头，直接返回config
    console.log(config.url + '不需要设置Authorization请求头')
    return config
  }
  // 先从持久化的存储中尝试获取token
  let token = localStorage.getItem('token')
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

export { requestInterceptor }