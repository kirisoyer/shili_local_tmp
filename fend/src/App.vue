<template>
  <div class="bbs-root">
    <!-- 论坛管理系统头部导航 -->
    <header class="bbs-header">
      <div class="bbs-header__logo">
        <h1>GameBBS 管理后台</h1>
        <p>游戏论坛管理系统</p>
      </div>
      <nav class="bbs-header__nav">
        <router-link to="/dashboard">控制台</router-link>
        <router-link to="/posts">帖子管理</router-link>
        <router-link to="/users">用户管理</router-link>
        <router-link to="/categories">板块管理</router-link>
        <router-link to="/comments">评论管理</router-link>
        <router-link to="/settings">系统设置</router-link>
      </nav>
    </header>

    <!-- 核心路由视图 -->
    <main class="bbs-main">
      <router-view></router-view>
    </main>

    <!-- 底部信息 -->
    <footer class="bbs-footer">
      <div class="bbs-footer__inner">
        <p>© 2025 GameBBS 游戏论坛管理系统 - 保留所有权利</p>
        <p class="bbs-footer__tips">技术支持：GameBBS 开发团队</p>
      </div>
    </footer>
  </div>
</template>

<script setup>
// 全局通用逻辑（可扩展）
import { onMounted } from 'vue'

// 示例：全局登录状态检测（可根据实际需求扩展）
onMounted(() => {
  const token = localStorage.getItem('gamebbs_admin_token')
  if (!token && !window.location.pathname.includes('/login')) {
    window.location.href = '/login'
  }
})
</script>

<style>
/* 全局基础样式 - 保留原有所有类名，新增游戏论坛风格 */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  height: 100%;
  width: 100%;
  position: absolute;
  margin: 0;
  padding: 0;
  background-color: #f1f1f1; /* 保留原有背景色 */
  font-family: "Microsoft YaHei", "PingFang SC", "Helvetica Neue", sans-serif;
  color: #333;
}

/* 原有样式完全保留（确保子组件兼容） */
.content-container {
  background-color: white;
  padding: 20px;
  margin: 20px;
  border-radius: 10px;
  min-width: 1200px;
  display: inline-block;
  width: 90%;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05); /* 新增轻微阴影提升质感 */
}

.input-tip {
  margin: 0 10px;
  line-height: 40px;
  color: #666; /* 新增文字色提升可读性 */
  font-size: 14px;
}

.input-field {
  margin-right: 40px;
}

.content-row {
  margin-bottom: 20px;
  display: flex; /* 新增flex布局，适配表单行 */
  align-items: center;
  flex-wrap: wrap; /* 适配小屏幕换行 */
}

/* 新增游戏论坛管理系统样式 */
.bbs-root {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

/* 头部导航 */
.bbs-header {
  background: linear-gradient(135deg, #1e293b 0%, #0f172a 100%);
  color: #fff;
  padding: 0 30px;
  height: 70px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
  position: sticky;
  top: 0;
  z-index: 999;
}

.bbs-header__logo h1 {
  font-size: 20px;
  font-weight: 600;
  color: #38bdf8; /* 游戏风格高亮蓝 */
  margin-bottom: 2px;
}

.bbs-header__logo p {
  font-size: 12px;
  color: #94a3b8;
}

.bbs-header__nav {
  display: flex;
  gap: 30px;
}

.bbs-header__nav a {
  color: #e2e8f0;
  text-decoration: none;
  font-size: 14px;
  padding: 8px 0;
  border-bottom: 2px solid transparent;
  transition: all 0.3s ease;
}

.bbs-header__nav a:hover,
.bbs-header__nav a.router-link-active {
  color: #38bdf8;
  border-bottom: 2px solid #38bdf8;
}

/* 主内容区 */
.bbs-main {
  flex: 1;
  padding: 10px 20px;
  max-width: 1920px;
  margin: 0 auto;
  width: 100%;
}

/* 底部 */
.bbs-footer {
  background-color: #1e293b;
  color: #94a3b8;
  padding: 15px 0;
  margin-top: auto;
}

.bbs-footer__inner {
  text-align: center;
  font-size: 12px;
}

.bbs-footer__tips {
  margin-top: 5px;
}
</style>