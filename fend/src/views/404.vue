<template>
  <div class="content-container" style="text-align: center;">
    <h1>404 - 页面不存在</h1>
    <p>你访问的页面不存在，请检查路径！</p>
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