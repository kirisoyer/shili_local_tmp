<template>
  <div class="content-container" style="text-align: center;">
    <h1>403 - 无访问权限</h1>
    <p>你没有权限访问该页面，请联系管理员！</p>
    <button @click="goBack" style="padding: 8px 20px; cursor: pointer;">返回首页</button>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';

const router = useRouter();
const goBack = () => {
  router.push('/');
};
</script>