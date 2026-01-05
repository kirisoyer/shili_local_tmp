import { createApp } from 'vue';
import App from './App.vue';
import router from './router'; // 导入路由配置

// 创建Vue实例并挂载
const app = createApp(App);
app.use(router); // 注册路由
app.mount('#app');