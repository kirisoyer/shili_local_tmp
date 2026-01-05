import { requestDelay, handlePagination } from './index'

// 模拟帖子列表
const mockPosts = [
  {
    id: 1,
    title: '[攻略] 原神枫丹水下探索全技巧',
    content: '枫丹水下探索需要注意体力值，携带特定角色可延长水下时间...',
    author: '原神玩家123',
    authorId: 1001,
    categoryId: 1,
    categoryName: '原神攻略',
    createTime: '2025-01-10 14:20:30',
    viewCount: 1250,
    commentCount: 89,
    status: 1 // 1-正常 2-审核中 3-已封禁
  },
  {
    id: 2,
    title: '吐槽：王者荣耀新皮肤定价太离谱',
    content: '新出的典藏皮肤要2000+，完全不值这个价...',
    author: '王者老玩家',
    authorId: 1002,
    categoryId: 2,
    categoryName: '王者荣耀讨论',
    createTime: '2025-01-09 09:15:22',
    viewCount: 3420,
    commentCount: 210,
    status: 1
  },
  {
    id: 3,
    title: '出售英雄联盟账号，全英雄全皮肤',
    content: '账号等级180，全英雄，皮肤300+，价格可谈...',
    author: 'LOL卖号',
    authorId: 1003,
    categoryId: 3,
    categoryName: '账号交易',
    createTime: '2025-01-08 16:40:15',
    viewCount: 890,
    commentCount: 45,
    status: 3 // 已封禁
  }
]

// 获取帖子列表（模拟分页）
export const getPosts = async (params = { pageNum: 1, pageSize: 10, keyword: '' }) => {
  await requestDelay()
  let filterList = mockPosts
  // 关键词搜索
  if (params.keyword) {
    filterList = mockPosts.filter(item => 
      item.title.includes(params.keyword) || item.content.includes(params.keyword) || item.author.includes(params.keyword)
    )
  }
  // 分页处理
  return handlePagination(filterList, params.pageNum, params.pageSize)
}

// 获取帖子详情
export const getPostDetail = async (id) => {
  await requestDelay()
  return mockPosts.find(item => item.id === id) || null
}

// 修改帖子状态
export const updatePostStatus = async (id, status) => {
  await requestDelay()
  const index = mockPosts.findIndex(item => item.id === id)
  if (index > -1) {
    mockPosts[index].status = status
    return true
  }
  return false
}

// 删除帖子
export const deletePost = async (id) => {
  await requestDelay()
  const index = mockPosts.findIndex(item => item.id === id)
  if (index > -1) {
    mockPosts.splice(index, 1)
    return true
  }
  return false
}