import axios from 'axios';

// 配置 baseURL - 开发环境使用代理，生产环境使用完整URL
const baseURL = import.meta.env.DEV ? '/api' : 'http://localhost:8080';

// 创建 axios 实例
const request = axios.create({
  baseURL: baseURL,
  timeout: 5000,
  headers: {
    'Content-Type': 'application/json;charset=utf-8'
  }
});

// 请求拦截器（可选，用于添加 token 等）
request.interceptors.request.use(
  (config) => {
    // 可以在这里添加 token
    // const token = localStorage.getItem('gamebbs_admin_token');
    // if (token) {
    //   config.headers.Authorization = `Bearer ${token}`;
    // }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// 响应拦截器
request.interceptors.response.use(
  (response) => {
    const res = response.data;
    // 根据后端返回的数据结构判断
    // 如果后端直接返回 DataWrapper，检查 code 字段
    if (res.code !== undefined && res.code !== 200) {
      alert(res.msg || '请求失败');
      return Promise.reject(res);
    }
    return res;
  },
  (error) => {
    console.error('请求错误:', error);
    if (error.response) {
      // 服务器返回了错误状态码
      alert(`后端接口报错：${error.response.status} - ${error.response.statusText}`);
    } else if (error.request) {
      // 请求已发出但没有收到响应
      alert('无法连接到后端服务器，请检查后端是否运行在 http://localhost:8080');
    } else {
      // 其他错误
      alert('后端接口报错：' + error.message);
    }
    return Promise.reject(error);
  }
);

export default request;
