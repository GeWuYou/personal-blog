// 导入异步请求包
import axios from 'axios'
import { Message } from 'element-ui'
import { requestInterceptor } from '@/api/interceptor/interceptor'

// 默认的错误信息
const defaultErrorMessage = (error) => {
  console.log(error)
  Message.error('发生未知错误，请联系管理员或者稍后再访问!')
}
// 默认的失败信息
const defaultFailureMessage = (message) => Message.warning(message)
// 创建一个axios实例
const instance = axios.create({
  baseURL: 'http://localhost:8082/api/v1',  // 请求的基础路径
  timeout: 10000  // 超时，401
})
// // 添加请求拦截器
instance.interceptors.request.use(requestInterceptor)
// // 添加响应拦截器
// instance.interceptors.response.use(responseInterceptor)

/**
 * 异步post请求
 * @param url 请求url
 * @param data 提交的数据
 * @param success 成功响应回调
 * @param failure 失败响应回调
 * @param error 错误响应回调
 * @param callback 回调函数
 */
function _post(url, data, success, failure = defaultFailureMessage, error = defaultErrorMessage, callback = () => {
}) {
  // 发送post异步请求
  instance.post(url, JSON.stringify(data), {
    // 设置提交方式为Json提交
    headers: {
      'Content-Type': 'application/json'
    }
  }).
    // 设置回调函数
    then(({ data }) => {
      if (data.success) {
        success(data.data, data.message, data.code)
      } else {
        (failure ?? defaultFailureMessage)(data.message, data.code)
      }
      callback()
    }).catch(error)
}

/**
 * 异步get请求
 * @param url 请求url
 * @param data 请求参数
 * @param success 成功响应回调
 * @param failure 失败响应回调
 * @param error 错误响应回调
 * @param callback 回调函数
 */
function _get(url, data, success, failure = defaultFailureMessage, error = defaultErrorMessage, callback = () => {
}) {
  // 发送get异步请求
  instance.get(url, {
    params: data
  }).
    // 设置回调函数
    then(({ data }) => {
      if (data.success) {
        success(data.data, data.message, data.code)
      } else {
        (failure ?? defaultFailureMessage)(data.message, data.code)
      }
      callback()
    }).catch(error)
}

/**
 * 异步put请求
 * @param url 请求url
 * @param data 提交的数据
 * @param success 成功响应回调
 * @param failure 失败响应回调
 * @param error 错误响应回调
 * @param callback 回调函数
 */
function _put(url, data, success, failure = defaultFailureMessage, error = defaultErrorMessage, callback = () => {
}) {
  // 发送put异步请求
  instance.put(url, JSON.stringify(data), {
    // 设置提交方式为Json提交
    headers: {
      'Content-Type': 'application/json'
    }
  }).
    // 设置回调函数
    then(({ data }) => {
        if (data.success) {
          success(data.data, data.message, data.code)
        } else {
          (failure ?? defaultFailureMessage)(data.message, data.code)
        }
      callback()
      }
    ).catch(error)
}

/**
 * 异步delete请求
 * @param url 请求url
 * @param data 请求参数
 * @param success 成功响应回调
 * @param failure 失败响应回调
 * @param error 错误响应回调
 * @param callback 回调函数
 */
function _delete(url, data, success, failure = defaultFailureMessage, error = defaultErrorMessage, callback = () => {
}) {
  instance.delete(url, {
    params: data
  })
    .then(({ data }) => {
      if (data.success) {
        success(data.data, data.message, data.code)
      } else {
        (failure ?? defaultFailureMessage)(data.message, data.code)
      }
      callback()
    }).catch(error)
}

export { _get, _post, _put, _delete }