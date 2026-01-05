import { requestDelay, handlePagination } from './index'

// 模拟板块列表
const mockCategories = [
  {
    id: 1,
    name: '原神攻略',
    desc: '原神游戏攻略、玩法、角色养成讨论',
    sort: 1,
    postCount: 1250,
    status: 1 // 1-启用 2-禁用
  },
  {
    id: 2,
    name: '王者荣耀讨论',
    desc: '王者荣耀英雄、皮肤、赛事讨论',
    sort: 2,
    postCount: 3420,
    status: 1
  },
  {
    id: 3,
    name: '账号交易',
    desc: '游戏账号、道具交易（注意防骗）',
    sort: 3,
    postCount: 890,
    status: 1
  },
  {
    id: 4,
    name: '游戏杂谈',
    desc: '各类游戏闲聊、吐槽、推荐',
    sort: 4,
    postCount: 5680,
    status: 1
  },
  {
    id: 5,
    name: '违规举报',
    desc: '违规帖子、用户举报专区',
    sort: 5,
    postCount: 320,
    status: 1
  }
]

// 获取板块列表
export const getCategories = async (params = { pageNum: 1, pageSize: 10, keyword: '' }) => {
  await requestDelay()
  let filterList = mockCategories
  if (params.keyword) {
    filterList = mockCategories.filter(item => item.name.includes(params.keyword) || item.desc.includes(params.keyword))
  }
  return handlePagination(filterList, params.pageNum, params.pageSize)
}

// 添加板块
export const addCategory = async (category) => {
  await requestDelay()
  const newId = mockCategories.length + 1
  mockCategories.push({
    id: newId,
    ...category,
    postCount: 0,
    status: 1
  })
  return newId
}

// 编辑板块
export const editCategory = async (id, category) => {
  await requestDelay()
  const index = mockCategories.findIndex(item => item.id === id)
  if (index > -1) {
    mockCategories[index] = { ...mockCategories[index], ...category }
    return true
  }
  return false
}

// 修改板块状态
export const updateCategoryStatus = async (id, status) => {
  await requestDelay()
  const index = mockCategories.findIndex(item => item.id === id)
  if (index > -1) {
    mockCategories[index].status = status
    return true
  }
  return false
}