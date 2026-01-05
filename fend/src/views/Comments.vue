<template>
  <div class="content-container">
    <div class="comments-header">
      <h2>评论管理</h2>
      <button 
        style="padding: 8px 15px; background: #ef4444; color: white; border: none; border-radius: 4px; cursor: pointer;"
      >
        批量删除
      </button>
    </div>

    <!-- 搜索筛选 -->
    <div class="comments-filter content-row">
      <label class="input-tip">关键词：</label>
      <input 
        v-model="searchKey" 
        class="input-field" 
        type="text" 
        placeholder="评论内容/用户名/帖子标题"
        style="padding: 8px; width: 200px; border: 1px solid #ddd; border-radius: 4px;"
      />
      <label class="input-tip">状态：</label>
      <select 
        v-model="filterStatus" 
        class="input-field" 
        style="padding: 8px; width: 120px; border: 1px solid #ddd; border-radius: 4px;"
      >
        <option value="">全部状态</option>
        <option value="normal">正常</option>
        <option value="forbid">已删除</option>
      </select>
      <button 
        @click="handleSearch"
        style="padding: 8px 15px; background: #1e293b; color: white; border: none; border-radius: 4px; cursor: pointer;"
      >
        搜索
      </button>
    </div>

    <!-- 评论列表 -->
    <div class="comments-list">
      <table class="comments-table">
        <thead>
          <tr>
            <th width="50"><input type="checkbox" /></th>
            <th width="80">ID</th>
            <th>评论内容</th>
            <th width="100">所属帖子</th>
            <th width="100">评论用户</th>
            <th width="150">评论时间</th>
            <th width="80">状态</th>
            <th width="100">操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="comment in commentsList" :key="comment.id">
            <td><input type="checkbox" :value="comment.id" /></td>
            <td>{{ comment.id }}</td>
            <td class="comments-table__content">{{ comment.content }}</td>
            <td>{{ comment.postTitle }}</td>
            <td>{{ comment.author }}</td>
            <td>{{ comment.createTime }}</td>
            <td>
              <span 
                :style="{
                  color: comment.status === 'normal' ? '#10b981' : '#ef4444',
                  fontWeight: 600
                }"
              >
                {{ comment.status === 'normal' ? '正常' : '已删除' }}
              </span>
            </td>
            <td class="comments-table__actions">
              <button 
                style="padding: 4px 8px; background: #ef4444; color: white; border: none; border-radius: 2px; cursor: pointer;"
                @click="handleDelete(comment.id)"
              >
                {{ comment.status === 'normal' ? '删除' : '恢复' }}
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- 分页 -->
    <div class="comments-pagination" style="margin-top: 20px; text-align: right;">
      <button 
        style="padding: 8px 12px; border: 1px solid #ddd; border-radius: 4px; cursor: pointer; margin-right: 5px;"
      >
        上一页
      </button>
      <span style="margin: 0 10px;">第 1 / 15 页</span>
      <button 
        style="padding: 8px 12px; border: 1px solid #ddd; border-radius: 4px; cursor: pointer; margin-left: 5px;"
      >
        下一页
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';

// 筛选条件
const searchKey = ref('');
const filterStatus = ref('');

// 模拟评论列表数据
const commentsList = ref([
  {
    id: 5001,
    content: 'S14总决赛LPL战队加油！希望能夺冠！',
    postTitle: '英雄联盟S14全球总决赛赛程汇总',
    author: '电竞迷123',
    createTime: '2025-01-15 10:30',
    status: 'normal'
  },
  {
    id: 5002,
    content: '原神4.5版本的新角色强度一般，不建议抽',
    postTitle: '原神4.5版本新角色强度分析',
    author: '提瓦特旅行者',
    createTime: '2025-01-14 16:50',
    status: 'normal'
  },
  {
    id: 5003,
    content: '违规评论内容，包含广告信息',
    postTitle: '王者荣耀新赛季打野思路分享',
    author: '广告用户001',
    createTime: '2025-01-13 09:20',
    status: 'forbid'
  }
]);

// 搜索事件
const handleSearch = () => {
  console.log('评论搜索条件：', {
    searchKey: searchKey.value,
    status: filterStatus.value
  });
};

// 删除/恢复评论
const handleDelete = (id) => {
  const comment = commentsList.value.find(item => item.id === id);
  if (comment) {
    comment.status = comment.status === 'normal' ? 'forbid' : 'normal';
  }
};

onMounted(() => {
  console.log('评论管理页面加载完成');
});
</script>

<style scoped>
.comments-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.comments-header h2 {
  color: #1e293b;
}

.comments-table {
  width: 100%;
  border-collapse: collapse;
}

.comments-table th,
.comments-table td {
  padding: 12px 15px;
  text-align: left;
  border-bottom: 1px solid #e2e8f0;
}

.comments-table th {
  background: #f8fafc;
  color: #1e293b;
  font-weight: 600;
}

.comments-table tr:hover {
  background: #f8fafc;
}

.comments-table__content {
  max-width: 400px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>