<template>
  <div class="content-container">
    <div class="settings-header">
      <h2>系统设置</h2>
      <button 
        @click="handleSave"
        style="padding: 8px 15px; background: #38bdf8; color: white; border: none; border-radius: 4px; cursor: pointer;"
      >
        保存设置
      </button>
    </div>

    <!-- 基础设置 -->
    <div class="settings-section">
      <h3 style="margin-bottom: 20px; color: #1e293b; border-bottom: 1px solid #e2e8f0; padding-bottom: 10px;">
        基础设置
      </h3>
      <div class="content-row">
        <label class="input-tip">网站名称：</label>
        <input 
          v-model="settings.siteName" 
          class="input-field" 
          type="text" 
          style="padding: 8px; width: 300px; border: 1px solid #ddd; border-radius: 4px;"
        />
      </div>
      <div class="content-row">
        <label class="input-tip">网站域名：</label>
        <input 
          v-model="settings.siteDomain" 
          class="input-field" 
          type="text" 
          style="padding: 8px; width: 300px; border: 1px solid #ddd; border-radius: 4px;"
        />
      </div>
      <div class="content-row">
        <label class="input-tip">网站描述：</label>
        <textarea 
          v-model="settings.siteDesc" 
          class="input-field" 
          rows="3" 
          style="padding: 8px; width: 300px; border: 1px solid #ddd; border-radius: 4px; resize: none;"
        ></textarea>
      </div>
      <div class="content-row">
        <label class="input-tip">是否开启注册：</label>
        <label style="margin-right: 20px;">
          <input 
            v-model="settings.allowRegister" 
            type="radio" 
            value="1" 
          /> 开启
        </label>
        <label>
          <input 
            v-model="settings.allowRegister" 
            type="radio" 
            value="0" 
          /> 关闭
        </label>
      </div>
    </div>

    <!-- 安全设置 -->
    <div class="settings-section" style="margin-top: 30px;">
      <h3 style="margin-bottom: 20px; color: #1e293b; border-bottom: 1px solid #e2e8f0; padding-bottom: 10px;">
        安全设置
      </h3>
      <div class="content-row">
        <label class="input-tip">密码最小长度：</label>
        <input 
          v-model="settings.pwdMinLength" 
          class="input-field" 
          type="number" 
          style="padding: 8px; width: 100px; border: 1px solid #ddd; border-radius: 4px;"
        />
      </div>
      <div class="content-row">
        <label class="input-tip">登录失败锁定次数：</label>
        <input 
          v-model="settings.loginLockCount" 
          class="input-field" 
          type="number" 
          style="padding: 8px; width: 100px; border: 1px solid #ddd; border-radius: 4px;"
        />
      </div>
      <div class="content-row">
        <label class="input-tip">锁定时长（分钟）：</label>
        <input 
          v-model="settings.loginLockTime" 
          class="input-field" 
          type="number" 
          style="padding: 8px; width: 100px; border: 1px solid #ddd; border-radius: 4px;"
        />
      </div>
    </div>

    <!-- 邮件设置 -->
    <div class="settings-section" style="margin-top: 30px;">
      <h3 style="margin-bottom: 20px; color: #1e293b; border-bottom: 1px solid #e2e8f0; padding-bottom: 10px;">
        邮件设置
      </h3>
      <div class="content-row">
        <label class="input-tip">SMTP服务器：</label>
        <input 
          v-model="settings.smtpHost" 
          class="input-field" 
          type="text" 
          style="padding: 8px; width: 300px; border: 1px solid #ddd; border-radius: 4px;"
        />
      </div>
      <div class="content-row">
        <label class="input-tip">SMTP端口：</label>
        <input 
          v-model="settings.smtpPort" 
          class="input-field" 
          type="number" 
          style="padding: 8px; width: 100px; border: 1px solid #ddd; border-radius: 4px;"
        />
      </div>
      <div class="content-row">
        <label class="input-tip">发件人邮箱：</label>
        <input 
          v-model="settings.smtpUser" 
          class="input-field" 
          type="text" 
          style="padding: 8px; width: 300px; border: 1px solid #ddd; border-radius: 4px;"
        />
      </div>
      <div class="content-row">
        <label class="input-tip">发件人密码：</label>
        <input 
          v-model="settings.smtpPass" 
          class="input-field" 
          type="password" 
          style="padding: 8px; width: 300px; border: 1px solid #ddd; border-radius: 4px;"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';

// 系统设置数据
const settings = ref({
  // 基础设置
  siteName: 'GameBBS 游戏论坛',
  siteDomain: 'https://gamebbs.com',
  siteDesc: '专业的游戏论坛交流平台，汇聚各类游戏玩家！',
  allowRegister: '1',
  // 安全设置
  pwdMinLength: 6,
  loginLockCount: 5,
  loginLockTime: 15,
  // 邮件设置
  smtpHost: 'smtp.qq.com',
  smtpPort: 465,
  smtpUser: 'admin@gamebbs.com',
  smtpPass: '********'
});

// 保存设置
const handleSave = () => {
  // 实际项目中提交到后端接口
  console.log('保存系统设置：', settings.value);
  alert('设置保存成功！');
};

onMounted(() => {
  console.log('系统设置页面加载完成');
});
</script>

<style scoped>
.settings-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
}

.settings-header h2 {
  color: #1e293b;
}

.settings-section {
  background: #f8fafc;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}
</style>