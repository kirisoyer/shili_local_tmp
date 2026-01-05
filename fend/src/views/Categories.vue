<template>
  <div class="content-container">
    <div class="categories-header">
      <h2>板块管理</h2>
      <button 
        style="padding: 8px 15px; background: #38bdf8; color: white; border: none; border-radius: 4px; cursor: pointer;"
      >
        新增板块
      </button>
    </div>

    <!-- 板块列表 -->
    <div class="categories-list">
      <table class="categories-table">
        <thead>
          <tr>
            <th width="50"><input type="checkbox" /></th>
            <th width="80">ID</th>
            <th width="80">图标</th>
            <th>板块名称</th>
            <th width="100">父板块</th>
            <th width="100">帖子数</th>
            <th width="100">排序值</th>
            <th width="80">状态</th>
            <th width="150">操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="category in categoriesList" :key="category.id">
            <td><input type="checkbox" :value="category.id" /></td>
            <td>{{ category.id }}</td>
            <td>
              <span style="font-size: 20px;">{{ category.icon }}</span>
            </td>
            <td>{{ category.name }}</td>
            <td>{{ category.parentName || '无' }}</td>
            <td>{{ category.postCount }}</td>
            <td>{{ category.sort }}</td>
            <td>
              <span 
                :style="{
                  color: category.status === 'show' ? '#10b981' : '#ef4444',
                  fontWeight: 600
                }"
              >
                {{ category.status === 'show' ? '显示' : '隐藏' }}
              </span>
            </td>
            <td class="categories-table__actions">
              <button 
                style="padding: 4px 8px; background: #38bdf8; color: white; border: none; border-radius: 2px; cursor: pointer; margin-right: 5px;"
              >
                编辑
              </button>
              <button 
                style="padding: 4px 8px; background: #f59e0b; color: white; border: none; border-radius: 2px; cursor: pointer; margin-right: 5px;"
              >
                排序
              </button>
              <button 
                style="padding: 4px 8px; background: #ef4444; color: white; border: none; border-radius: 2px; cursor: pointer;"
                @click="handleToggleStatus(category.id)"
              >
                {{ category.status === 'show' ? '隐藏' : '显示' }}
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';

// 模拟板块列表数据
const categoriesList = ref([
  {
    id: 1,
    icon: '🎮',
    name: '英雄联盟',
    parentName: '无',
    postCount: 328,
    sort: 1,
    status: 'show'
  },
  {
    id: 2,
    icon: '🏆',
    name: '王者荣耀',
    parentName: '无',
    postCount: 289,
    sort: 2,
    status: 'show'
  },
  {
    id: 3,
    icon: '✨',
    name: '原神',
    parentName: '无',
    postCount: 456,
    sort: 3,
    status: 'show'
  },
  {
    id: 4,
    icon: '🔫',
    name: 'CS2',
    parentName: '无',
    postCount: 189,
    sort: 4,
    status: 'hide'
  },
  {
    id: 5,
    icon: '📢',
    name: '赛事讨论',
    parentName: '英雄联盟',
    postCount: 128,
    sort: 1,
    status: 'show'
  }
]);

// 切换显示/隐藏状态
const handleToggleStatus = (id) => {
  const category = categoriesList.value.find(item => item.id === id);
  if (category) {
    category.status = category.status === 'show' ? 'hide' : 'show';
  }
};

onMounted(() => {
  console.log('板块管理页面加载完成');
});
</script>

<style scoped>
.categories-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.categories-header h2 {
  color: #1e293b;
}

.categories-table {
  width: 100%;
  border-collapse: collapse;
}

.categories-table th,
.categories-table td {
  padding: 12px 15px;
  text-align: left;
  border-bottom: 1px solid #e2e8f0;
}

.categories-table th {
  background: #f8fafc;
  color: #1e293b;
  font-weight: 600;
}

.categories-table tr:hover {
  background: #f8fafc;
}
</style>