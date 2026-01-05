import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';
import path from 'path';

export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, 'src'), // 配置@指向src目录
    },
  },
  server: {
    port: 3000, // 开发服务器端口（可选）
    open: true, // 启动后自动打开浏览器（可选）
    proxy: {
      // 代理所有 /api 开头的请求到后端服务器
      '/api': {
        target: 'http://localhost:8080', // 后端服务器地址
        changeOrigin: true, // 改变请求头中的 origin
        rewrite: (path) => path.replace(/^\/api/, ''), // 移除 /api 前缀
        // 如果需要 WebSocket 支持，可以添加以下配置
        // ws: true,
      },
    },
  },
});