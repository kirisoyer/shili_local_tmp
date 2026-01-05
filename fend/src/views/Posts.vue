<template>
  <!-- 模板部分不变，保留原有结构 -->
  <div class="content-container">
    <div class="posts-header">
      <h2>帖子管理</h2>
      <div class="posts-header__actions">
        <button style="padding: 8px 15px; background: #38bdf8; color: white; border: none; border-radius: 4px; cursor: pointer; margin-right: 10px;">
          新增帖子
        </button>
        <button style="padding: 8px 15px; background: #ef4444; color: white; border: none; border-radius: 4px; cursor: pointer;">
          批量删除
        </button>
      </div>
    </div>

    <!-- 搜索筛选 -->
    <div class="posts-filter content-row">
      <label class="input-tip">关键词：</label>
      <input 
        v-model="searchKey" 
        class="input-field" 
        type="text" 
        placeholder="帖子标题/内容/作者"
        style="padding: 8px; width: 200px; border: 1px solid #ddd; border-radius: 4px;"
      />
      <label class="input-tip">板块：</label>
      <select 
        v-model="filterCategory" 
        class="input-field" 
        style="padding: 8px; width: 150px; border: 1px solid #ddd; border-radius: 4px;"
      >
        <option value="">全部板块</option>
        <option value="1">英雄联盟</option>
        <option value="2">王者荣耀</option>
        <option value="3">原神</option>
        <option value="4">CS2</option>
      </select>
      <label class="input-tip">状态：</label>
      <select 
        v-model="filterStatus" 
        class="input-field" 
        style="padding: 8px; width: 120px; border: 1px solid #ddd; border-radius: 4px;"
      >
        <option value="">全部状态</option>
        <option value="normal">正常</option>
        <option value="audit">审核中</option>
        <option value="forbid">已封禁</option>
      </select>
      <button 
        @click="handleSearch"
        style="padding: 8px 15px; background: #1e293b; color: white; border: none; border-radius: 4px; cursor: pointer;"
      >
        搜索
      </button>
    </div>

    <!-- 帖子列表 -->
    <div class="posts-list">
      <table class="posts-table">
        <thead>
          <tr>
            <th width="50"><input type="checkbox" /></th>
            <th width="80">ID</th>
            <th>标题</th>
            <th width="120">作者</th>
            <th width="100">板块</th>
            <th width="150">发布时间</th>
            <th width="80">状态</th>
            <th width="120">操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="post in postsList" :key="post.id">
            <td><input type="checkbox" :value="post.id" /></td>
            <td>{{ post.id }}</td>
            <td class="posts-table__title">{{ post.title }}</td>
            <td>{{ post.author || '未知' }}</td>
            <td>{{ getCategoryName(post.boardId) }}</td>
            <td>{{ formatTime(post.createTime) }}</td>
            <td>
              <span 
                :style="{
                  color: post.status === 'normal' ? '#10b981' : post.status === 'audit' ? '#f59e0b' : '#ef4444',
                  fontWeight: 600
                }"
              >
                {{ post.status === 'normal' ? '正常' : post.status === 'audit' ? '审核中' : '已封禁' }}
              </span>
            </td>
            <td class="posts-table__actions">
              <button 
                style="padding: 4px 8px; background: #38bdf8; color: white; border: none; border-radius: 2px; cursor: pointer; margin-right: 5px;"
              >
                编辑
              </button>
              <button 
                style="padding: 4px 8px; background: #ef4444; color: white; border: none; border-radius: 2px; cursor: pointer;"
                @click="handleForbid(post.id)"
              >
                {{ post.status === 'forbid' ? '解封' : '封禁' }}
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- 分页 -->
    <div class="posts-pagination" style="margin-top: 20px; text-align: right;">
      <button 
        @click="changePage(currentPage - 1)"
        :disabled="currentPage === 1"
        style="padding: 8px 12px; border: 1px solid #ddd; border-radius: 4px; cursor: pointer; margin-right: 5px;"
      >
        上一页
      </button>
      <span style="margin: 0 10px;">第 {{ currentPage }} / {{ totalPage }} 页</span>
      <button 
        @click="changePage(currentPage + 1)"
        :disabled="currentPage === totalPage"
        style="padding: 8px 12px; border: 1px solid #ddd; border-radius: 4px; cursor: pointer; margin-left: 5px;"
      >
        下一页
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
// 导入真实接口请求函数
import { getPostList, deletePost, forbidPost } from '@/api/post';

// 筛选条件
const searchKey = ref('');
const filterCategory = ref('');
const filterStatus = ref('');

// 分页参数
const currentPage = ref(1);
const pageSize = ref(10);
const totalPage = ref(0);

// 真实帖子列表（从后端获取）
const postsList = ref([]);

// 板块名称映射（匹配后端boardId）
const categoryMap = ref({
  '1': '英雄联盟',
  '2': '王者荣耀',
  '3': '原神',
  '4': 'CS2'
});

// 初始化加载数据
onMounted(() => {
  loadPostList();
});

// 加载帖子列表（核心：调用后端接口）
const loadPostList = async () => {
  try {
    // 构造请求参数
    const params = {
      page: currentPage.value,
      rows: pageSize.value,
      boardId: filterCategory.value,
      title: searchKey.value
    };
    // 调用后端接口
    const res = await getPostList(params);
    // 把后端返回的真实数据赋值给列表
    postsList.value = res.data.data || [];
    // 赋值总页数（后端返回的totalPage）
    totalPage.value = res.data.totalPage || 0;
  } catch (error) {
    console.error('加载帖子失败：', error);
    alert('加载帖子列表失败，请检查后端服务！');
  }
};

// 搜索事件（重新加载列表）
const handleSearch = () => {
  currentPage.value = 1; // 搜索后回到第一页
  loadPostList();
};

// 分页切换
const changePage = (page) => {
  if (page < 1 || page > totalPage.value) return;
  currentPage.value = page;
  loadPostList();
};

// 封禁/解封帖子
const handleForbid = async (id) => {
  try {
    const post = postsList.value.find(item => item.id === id);
    if (!post) return;
    // 调用后端封禁/解封接口
    const newStatus = post.status === 'forbid' ? 'normal' : 'forbid';
    await forbidPost(id, newStatus);
    // 刷新列表
    loadPostList();
    alert(post.status === 'forbid' ? '解封成功' : '封禁成功');
  } catch (error) {
    console.error('操作失败：', error);
    alert('操作失败，请检查后端服务！');
  }
};

// 辅助函数：根据boardId获取板块名称
const getCategoryName = (boardId) => {
  return categoryMap.value[boardId] || '未知板块';
};

// 辅助函数：格式化时间
const formatTime = (time) => {
  if (!time) return '';
  // 简单格式化时间（根据后端返回的时间格式调整）
  return new Date(time).toLocaleString();
};
</script>

<style scoped>
/* 样式部分不变 */
.posts-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.posts-header h2 {
  color: #1e293b;
}

.posts-table {
  width: 100%;
  border-collapse: collapse;
}

.posts-table th,
.posts-table td {
  padding: 12px 15px;
  text-align: left;
  border-bottom: 1px solid #e2e8f0;
}

.posts-table th {
  background: #f8fafc;
  color: #1e293b;
  font-weight: 600;
}

.posts-table tr:hover {
  background: #f8fafc;
}

.posts-table__title {
  max-width: 400px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>