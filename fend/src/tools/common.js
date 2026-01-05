/**
 * 格式化时间
 * @param {Date|String|Number} time 时间
 * @param {String} format 格式 YYYY-MM-DD HH:mm:ss
 * @returns {String} 格式化后的时间
 */
export const formatTime = (time, format = 'YYYY-MM-DD HH:mm:ss') => {
  if (!time) return ''
  const date = new Date(time)
  const year = date.getFullYear()
  const month = String(date.getMonth() + 1).padStart(2, '0')
  const day = String(date.getDate()).padStart(2, '0')
  const hour = String(date.getHours()).padStart(2, '0')
  const minute = String(date.getMinutes()).padStart(2, '0')
  const second = String(date.getSeconds()).padStart(2, '0')
  
  return format.replace('YYYY', year)
    .replace('MM', month)
    .replace('DD', day)
    .replace('HH', hour)
    .replace('mm', minute)
    .replace('ss', second)
}

/**
 * 状态文本转换
 * @param {Number} status 状态值
 * @param {Array} options 状态配置 [ { value: 1, label: '正常' }, ... ]
 * @returns {String} 状态文本
 */
export const getStatusText = (status, options) => {
  const option = options.find(item => item.value === status)
  return option ? option.label : '未知状态'
}

/**
 * 防抖函数
 * @param {Function} fn 执行函数
 * @param {Number} delay 延迟时间
 * @returns {Function} 防抖后的函数
 */
export const debounce = (fn, delay = 500) => {
  let timer = null
  return (...args) => {
    if (timer) clearTimeout(timer)
    timer = setTimeout(() => {
      fn.apply(this, args)
    }, delay)
  }
}

/**
 * 节流函数
 * @param {Function} fn 执行函数
 * @param {Number} interval 间隔时间
 * @returns {Function} 节流后的函数
 */
export const throttle = (fn, interval = 500) => {
  let lastTime = 0
  return (...args) => {
    const now = Date.now()
    if (now - lastTime >= interval) {
      fn.apply(this, args)
      lastTime = now
    }
  }
}

/**
 * 深拷贝
 * @param {Any} obj 拷贝对象
 * @returns {Any} 拷贝后的对象
 */
export const deepClone = (obj) => {
  if (obj === null || typeof obj !== 'object') return obj
  if (obj instanceof Date) return new Date(obj)
  if (obj instanceof Array) return obj.map(item => deepClone(item))
  if (obj instanceof Object) {
    const newObj = {}
    for (const key in obj) {
      if (obj.hasOwnProperty(key)) {
        newObj[key] = deepClone(obj[key])
      }
    }
    return newObj
  }
  return obj
}