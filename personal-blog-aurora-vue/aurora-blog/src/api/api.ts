// 导入异步请求包
import axios, { AxiosInstance, AxiosResponse } from 'axios'

// 默认的错误信息
const defaultErrorMessage = (error: any): void => {
  console.log(error)
}

// 默认的失败信息
const defaultFailureMessage = (message: string): void => console.log(message)

// 创建一个axios实例
const instance: AxiosInstance = axios.create({
  baseURL: 'http://localhost:8082/api/v1/server',  // 请求的基础路径
  timeout: 10000  // 超时，401
})

// 定义响应数据类型
interface ResponseData<T = any> {
  success: boolean;
  data: T;
  message: string;
  code: number;
}

/**
 * 异步post请求
 * @param url 请求url
 * @param data 提交的数据
 * @param success 成功响应回调
 * @param failure 失败响应回调
 * @param error 错误响应回调
 */
function _post<T>(url: string, data: any, success: (data: T, message: string, code: number) => void, failure: (message: string, code: number) => void = defaultFailureMessage, error: (error: any) => void = defaultErrorMessage): void {
  instance.post(url, JSON.stringify(data), {
    headers: {
      'Content-Type': 'application/json'
    }
  }).then((response: AxiosResponse<ResponseData<T>>) => {
    const { data } = response
    if (data.success) {
      success(data.data, data.message, data.code)
    } else {
      failure(data.message, data.code)
    }
  }).catch(error)
}

/**
 * 异步get请求
 * @param url 请求url
 * @param data 请求参数
 * @param success 成功响应回调
 * @param failure 失败响应回调
 * @param error 错误响应回调
 */
function _get<T>(url: string, data: any, success: (data: T, message: string, code: number) => void, failure: (message: string, code: number) => void = defaultFailureMessage, error: (error: any) => void = defaultErrorMessage): void {
  instance.get(url, {
    params: data
  }).then((response: AxiosResponse<ResponseData<T>>) => {
    const { data } = response
    if (data.success) {
      success(data.data, data.message, data.code)
    } else {
      failure(data.message, data.code)
    }
  }).catch(error)
}

/**
 * 异步put请求
 * @param url 请求url
 * @param data 提交的数据
 * @param success 成功响应回调
 * @param failure 失败响应回调
 * @param error 错误响应回调
 */
function _put<T>(url: string, data: any, success: (data: T, message: string, code: number) => void, failure: (message: string, code: number) => void = defaultFailureMessage, error: (error: any) => void = defaultErrorMessage): void {
  instance.put(url, JSON.stringify(data), {
    headers: {
      'Content-Type': 'application/json'
    }
  }).then((response: AxiosResponse<ResponseData<T>>) => {
    const { data } = response
    if (data.success) {
      success(data.data, data.message, data.code)
    } else {
      failure(data.message, data.code)
    }
  }).catch(error)
}

/**
 * 异步delete请求
 * @param url 请求url
 * @param data 提交的数据
 * @param success 成功响应回调
 * @param failure 失败响应回调
 * @param error 错误响应回调
 */
function _delete<T>(url: string, data: any, success: (data: T, message: string, code: number) => void, failure: (message: string, code: number) => void = defaultFailureMessage, error: (error: any) => void = defaultErrorMessage): void {
  instance.delete(url, {
    params: data
  })
    .then((response: AxiosResponse<ResponseData<T>>) => {
      const { data } = response
      if (data.success) {
        success(data.data, data.message, data.code)
      } else {
        failure(data.message, data.code)
      }
    }).catch(error)
}

export { _get, _post, _put, _delete }
