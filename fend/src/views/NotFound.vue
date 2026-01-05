<template>
  <div class="content-container" style="text-align: center; padding: 50px 0;">
    <h1 style="font-size: 80px; color: #ef4444; margin-bottom: 20px;">404</h1>
    <h2 style="font-size: 24px; color: #1e293b; margin-bottom: 20px;">页面不存在</h2>
    <p style="color: #666; margin-bottom: 30px;">你访问的页面不存在或已被删除，请检查URL是否正确</p>
    <button 
      @click="goBack"
      style="padding: 10px 30px; background: #38bdf8; color: white; border: none; border-radius: 4px; cursor: pointer;"
    >
      返回控制台
    </button>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';

const router = useRouter();
const goBack = () => {
  router.push('/dashboard');
};
</script>