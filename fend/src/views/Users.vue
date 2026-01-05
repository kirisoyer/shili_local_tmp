<template>
  <div class="content-container">
    <div class="users-header">
      <h2>用户管理</h2>
      <div class="users-header__actions">
        <button 
          style="padding: 8px 15px; background: #38bdf8; color: white; border: none; border-radius: 4px; cursor: pointer;"
        >
          新增用户
        </button>
      </div>
    </div>

    <!-- 搜索筛选 -->
    <div class="users-filter content-row">
      <label class="input-tip">用户名：</label>
      <input 
        v-model="searchKey" 
        class="input-field" 
        type="text" 
        placeholder="请输入用户名/邮箱/手机号"
        style="padding: 8px; width: 200px; border: 1px solid #ddd; border-radius: 4px;"
      />
      <label class="input-tip">用户组：</label>
      <select 
        v-model="filterGroup" 
        class="input-field" 
        style="padding: 8px; width: 120px; border: 1px solid #ddd; border-radius: 4px;"
      >
        <option value="">全部用户组</option>
        <option value="admin">管理员</option>
        <option value="normal">普通用户</option>
        <option value="vip">VIP用户</option>
        <option value="forbid">封禁用户</option>
      </select>
      <button 
        @click="handleSearch"
        style="padding: 8px 15px; background: #1e293b; color: white; border: none; border-radius: 4px; cursor: pointer;"
      >
        搜索
      </button>
    </div>

    <!-- 用户列表 -->
    <div class="users-list">
      <table class="users-table">
        <thead>
          <tr>
            <th width="50"><input type="checkbox" /></th>
            <th width="80">UID</th>
            <th width="80">头像</th>
            <th>用户名</th>
            <th width="150">邮箱</th>
            <th width="120">手机号</th>
            <th width="100">用户组</th>
            <th width="150">注册时间</th>
            <th width="120">操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="user in usersList" :key="user.uid">
            <td><input type="checkbox" :value="user.uid" /></td>
            <td>{{ user.uid }}</td>
            <td>
              <img 
                :src="user.avatar" 
                alt="头像" 
                style="width: 40px; height: 40px; border-radius: 50%;"
              />
            </td>
            <td>{{ user.username }}</td>
            <td>{{ user.email }}</td>
            <td>{{ user.phone }}</td>
            <td>
              <span 
                :style="{
                  color: user.group === 'admin' ? '#38bdf8' : user.group === 'normal' ? '#10b981' : user.group === 'vip' ? '#f59e0b' : '#ef4444',
                  fontWeight: 600
                }"
              >
                {{ user.group === 'admin' ? '管理员' : user.group === 'normal' ? '普通用户' : user.group === 'vip' ? 'VIP用户' : '封禁用户' }}
              </span>
            </td>
            <td>{{ user.regTime }}</td>
            <td class="users-table__actions">
              <button 
                style="padding: 4px 8px; background: #38bdf8; color: white; border: none; border-radius: 2px; cursor: pointer; margin-right: 5px;"
              >
                编辑
              </button>
              <button 
                style="padding: 4px 8px; background: #ef4444; color: white; border: none; border-radius: 2px; cursor: pointer;"
                @click="handleForbid(user.uid)"
              >
                {{ user.group === 'forbid' ? '解封' : '封禁' }}
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- 分页 -->
    <div class="users-pagination" style="margin-top: 20px; text-align: right;">
      <button 
        style="padding: 8px 12px; border: 1px solid #ddd; border-radius: 4px; cursor: pointer; margin-right: 5px;"
      >
        上一页
      </button>
      <span style="margin: 0 10px;">第 1 / 8 页</span>
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
const filterGroup = ref('');

// 模拟用户列表数据
const usersList = ref([
  {
    uid: 10001,
    avatar: 'https://picsum.photos/40/40?random=1',
    username: 'admin',
    email: 'admin@gamebbs.com',
    phone: '13800138000',
    group: 'admin',
    regTime: '2025-01-01 00:00'
  },
  {
    uid: 10002,
    avatar: 'https://picsum.photos/40/40?random=2',
    username: '电竞迷123',
    email: 'dj迷123@gamebbs.com',
    phone: '13800138001',
    group: 'normal',
    regTime: '2025-01-02 10:15'
  },
  {
    uid: 10003,
    avatar: 'https://picsum.photos/40/40?random=3',
    username: '提瓦特旅行者',
    email: 'traveler@gamebbs.com',
    phone: '13800138002',
    group: 'vip',
    regTime: '2025-01-03 14:20'
  },
  {
    uid: 10004,
    avatar: 'https://picsum.photos/40/40?random=4',
    username: '违规用户001',
    email: 'forbid001@gamebbs.com',
    phone: '13800138003',
    group: 'forbid',
    regTime: '2025-01-04 09:30'
  }
]);

// 搜索事件
const handleSearch = () => {
  console.log('用户搜索条件：', {
    searchKey: searchKey.value,
    group: filterGroup.value
  });
};

// 封禁/解封事件
const handleForbid = (uid) => {
  const user = usersList.value.find(item => item.uid === uid);
  if (user) {
    user.group = user.group === 'forbid' ? 'normal' : 'forbid';
  }
};

onMounted(() => {
  console.log('用户管理页面加载完成');
});
</script>

<style scoped>
.users-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.users-header h2 {
  color: #1e293b;
}

.users-table {
  width: 100%;
  border-collapse: collapse;
}

.users-table th,
.users-table td {
  padding: 12px 15px;
  text-align: left;
  border-bottom: 1px solid #e2e8f0;
}

.users-table th {
  background: #f8fafc;
  color: #1e293b;
  font-weight: 600;
}

.users-table tr:hover {
  background: #f8fafc;
}
</style>