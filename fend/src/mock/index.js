// 模拟接口请求延迟
export const requestDelay = (time = 1000) => {
  return new Promise(resolve => setTimeout(resolve, time))
}

// 通用分页处理
export const handlePagination = (list, pageNum, pageSize) => {
  const start = (pageNum - 1) * pageSize
  const end = start + pageSize
  return {
    list: list.slice(start, end),
    total: list.length,
    pageNum,
    pageSize
  }
}