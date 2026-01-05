<template>
  <div class="content-container">
    <h1>首页</h1>
    <div class="content-row">
      <p>当前用户：{{ userName }}</p>
      <p>是否拥有首页权限：{{ hasHomePermission }}</p>
      <button @click="logout">退出登录</button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { getUserInfo, checkPermission } from '@/tools/permission';
import { useRouter } from 'vue-router';

const router = useRouter();
const userName = ref('');
const hasHomePermission = ref(false);

onMounted(() => {
  // 初始化用户信息和权限
  const userInfo = getUserInfo();
  userName.value = userInfo.name || '未命名';
  hasHomePermission.value = checkPermission('home:view');
});

// 退出登录
const logout = () => {
  localStorage.removeItem('userInfo');
  router.push('/login');
};
</script>