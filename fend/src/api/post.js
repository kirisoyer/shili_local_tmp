// 导入封装的axios请求工具
import request from '@/utils/request';

// 1. 分页查询帖子（适配后端接口参数）
export function getPostList(params) {
  return request({
    url: '/post',
    method: 'get',
    params: {
      op: 'find',
      page: params.page || 1,
      rows: params.rows || 10,
      boardId: params.boardId || '', // 板块ID（对应前端筛选的板块）
      title: params.title || ''      // 标题关键词（对应前端搜索框）
    }
  });
}

// 2. 删除帖子
export function deletePost(id) {
  return request({
    url: '/post',
    method: 'get',
    params: {
      op: 'delete',
      id: id
    }
  });
}

// 3. 封禁/解封帖子（如果后端有这个接口，没有则先加）
export function forbidPost(id, status) {
  return request({
    url: '/post',
    method: 'post',
    params: { op: 'update' },
    data: {
      id: id,
      status: status // normal/forbid
    }
  });
}