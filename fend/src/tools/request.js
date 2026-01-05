import { ElMessage } from 'element-plus'

// 模拟请求配置
const requestConfig = {
  baseURL: '/api',
  timeout: 10000
}

/**
 * 通用请求函数
 * @param {String} url 请求地址
 * @param {String} method 请求方法 GET/POST/PUT/DELETE
 * @param {Object} data 请求参数
 * @returns {Promise} 请求结果
 */
export const request = async (url, method = 'GET', data = {}) => {
  try {
    // 模拟请求头
    const headers = {
      'Content-Type': 'application/json',
      'Authorization': localStorage.getItem('gamebbs_admin_token') || ''
    }

    // 不同请求方法处理
    let options = {
      method,
      headers
    }

    if (method.toUpperCase() === 'GET') {
      // GET请求拼接参数
      const params = new URLSearchParams(data)
      url = `${url}?${params.toString()}`
    } else {
      // POST/PUT/DELETE 请求体
      options.body = JSON.stringify(data)
    }

    // 模拟请求（实际项目替换为真实fetch/axios）
    const response = await fetch(requestConfig.baseURL + url, options)
    const result = await response.json()

    // 统一错误处理
    if (result.code !== 200) {
      ElMessage.error(result.msg || '请求失败')
      return Promise.reject(result)
    }

    return result
  } catch (error) {
    ElMessage.error('网络异常，请稍后重试')
    return Promise.reject(error)
  }
}

// 快捷请求方法
export const get = (url, data) => request(url, 'GET', data)
export const post = (url, data) => request(url, 'POST', data)
export const put = (url, data) => request(url, 'PUT', data)
export const del = (url, data) => request(url, 'DELETE', data)