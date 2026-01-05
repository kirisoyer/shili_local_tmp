import { requestDelay, handlePagination } from './index'

// 模拟用户列表
const mockUsers = [
  {
    id: 1001,
    username: '原神玩家123',
    nickname: '提瓦特探险者',
    avatar: '/avatar/1001.png',
    phone: '13800138001',
    email: 'player123@gamebbs.com',
    registerTime: '2024-10-01 08:30:22',
    lastLoginTime: '2025-01-10 15:40:10',
    status: 1, // 1-正常 2-封禁
    role: 2 // 1-超级管理员 2-普通用户 3-版主
  },
  {
    id: 1002,
    username: '王者老玩家',
    nickname: '最强王者',
    avatar: '/avatar/1002.png',
    phone: '13900139002',
    email: 'kingplayer@gamebbs.com',
    registerTime: '2024-09-15 14:20:10',
    lastLoginTime: '2025-01-09 10:15:30',
    status: 1,
    role: 2
  },
  {
    id: 1003,
    username: 'LOL卖号',
    nickname: '卖号换奶茶',
    avatar: '/avatar/1003.png',
    phone: '13700137003',
    email: 'sellaccount@gamebbs.com',
    registerTime: '2024-11-05 11:10:05',
    lastLoginTime: '2025-01-08 17:20:45',
    status: 2, // 封禁
    role: 2
  },
  {
    id: 9999,
    username: 'admin',
    nickname: '超级管理员',
    avatar: '/avatar/admin.png',
    phone: '13600136000',
    email: 'admin@gamebbs.com',
    registerTime: '2024-08-01 00:00:00',
    lastLoginTime: '2025-01-10 16:30:20',
    status: 1,
    role: 1 // 超级管理员
  }
]

// 获取用户列表
export const getUsers = async (params = { pageNum: 1, pageSize: 10, keyword: '' }) => {
  await requestDelay()
  let filterList = mockUsers
  if (params.keyword) {
    filterList = mockUsers.filter(item => 
      item.username.includes(params.keyword) || item.nickname.includes(params.keyword) || item.phone.includes(params.keyword)
    )
  }
  return handlePagination(filterList, params.pageNum, params.pageSize)
}

// 修改用户状态
export const updateUserStatus = async (id, status) => {
  await requestDelay()
  const index = mockUsers.findIndex(item => item.id === id)
  if (index > -1) {
    mockUsers[index].status = status
    return true
  }
  return false
}

// 修改用户角色
export const updateUserRole = async (id, role) => {
  await requestDelay()
  const index = mockUsers.findIndex(item => item.id === id)
  if (index > -1) {
    mockUsers[index].role = role
    return true
  }
  return false
}