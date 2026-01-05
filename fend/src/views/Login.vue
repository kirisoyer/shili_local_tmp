<template>
  <div class="login-container">
    <div class="login-card">
      <div class="login-card__header">
        <h2>GameBBS 管理后台</h2>
        <p>游戏论坛管理系统 - 管理员登录</p>
      </div>
      <div class="login-card__form">
        <div class="content-row">
          <label class="input-tip">账号：</label>
          <input 
            v-model="username" 
            class="input-field" 
            type="text" 
            placeholder="请输入管理员账号"
            style="padding: 8px; width: 280px; border: 1px solid #ddd; border-radius: 4px;"
          />
        </div>
        <div class="content-row">
          <label class="input-tip">密码：</label>
          <input 
            v-model="password" 
            class="input-field" 
            type="password" 
            placeholder="请输入管理员密码"
            style="padding: 8px; width: 280px; border: 1px solid #ddd; border-radius: 4px;"
          />
        </div>
        <div class="content-row" style="justify-content: center; margin-top: 20px;">
          <button 
            @click="handleLogin"
            style="padding: 10px 40px; background: #38bdf8; color: white; border: none; border-radius: 4px; cursor: pointer;"
          >
            登录
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const username = ref('');
const password = ref('');

// 模拟登录逻辑
const handleLogin = () => {
  if (!username.value || !password.value) {
    alert('请输入账号和密码！');
    return;
  }
  // 模拟验证（实际项目替换为接口请求）
  if (username.value === 'admin' && password.value === 'admin123') {
    localStorage.setItem('gamebbs_admin_token', 'gamebbs_admin_123456');
    router.push('/dashboard');
  } else {
    alert('账号或密码错误！');
  }
};
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color: #f1f1f1;
}

.login-card {
  background: white;
  padding: 40px;
  border-radius: 10px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  width: 400px;
  text-align: center;
}

.login-card__header h2 {
  color: #1e293b;
  margin-bottom: 8px;
}

.login-card__header p {
  color: #666;
  font-size: 14px;
  margin-bottom: 20px;
}
</style>