<template>
  <div class="content-container">
    <div class="dashboard-header">
      <h2>控制台概览</h2>
      <p>GameBBS 论坛数据总览</p>
    </div>

    <!-- 数据统计卡片 -->
    <div class="dashboard-stats">
      <div class="stats-card">
        <div class="stats-card__icon">📝</div>
        <div class="stats-card__info">
          <h3>总帖子数</h3>
          <p class="stats-card__value">{{ stats.posts }}</p>
        </div>
      </div>
      <div class="stats-card">
        <div class="stats-card__icon">👥</div>
        <div class="stats-card__info">
          <h3>总用户数</h3>
          <p class="stats-card__value">{{ stats.users }}</p>
        </div>
      </div>
      <div class="stats-card">
        <div class="stats-card__icon">📂</div>
        <div class="stats-card__info">
          <h3>板块数量</h3>
          <p class="stats-card__value">{{ stats.categories }}</p>
        </div>
      </div>
      <div class="stats-card">
        <div class="stats-card__icon">💬</div>
        <div class="stats-card__info">
          <h3>总评论数</h3>
          <p class="stats-card__value">{{ stats.comments }}</p>
        </div>
      </div>
    </div>

    <!-- 近期数据 -->
    <div class="dashboard-recent" style="margin-top: 30px;">
      <h3>近期新增（7天）</h3>
      <table class="dashboard-table">
        <thead>
          <tr>
            <th>类型</th>
            <th>新增数量</th>
            <th>环比增长</th>
            <th>操作建议</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>新帖子</td>
            <td>{{ recent.posts }}</td>
            <td style="color: green;">+{{ recent.postsRate }}%</td>
            <td>内容活跃度提升，建议加强审核</td>
          </tr>
          <tr>
            <td>新用户</td>
            <td>{{ recent.users }}</td>
            <td style="color: green;">+{{ recent.usersRate }}%</td>
            <td>用户增长良好，可推新活动</td>
          </tr>
          <tr>
            <td>新评论</td>
            <td>{{ recent.comments }}</td>
            <td style="color: red;">-{{ recent.commentsRate }}%</td>
            <td>评论数下降，建议优化互动机制</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';

// 模拟统计数据
const stats = ref({
  posts: 1289,
  users: 568,
  categories: 18,
  comments: 4521
});

// 模拟近期数据
const recent = ref({
  posts: 156,
  postsRate: 8.5,
  users: 89,
  usersRate: 12.3,
  comments: 328,
  commentsRate: 5.2
});

onMounted(() => {
  // 实际项目中可从接口获取数据
  console.log('控制台数据加载完成');
});
</script>

<style scoped>
.dashboard-header h2 {
  color: #1e293b;
  margin-bottom: 8px;
}

.dashboard-header p {
  color: #666;
  font-size: 14px;
  margin-bottom: 20px;
}

.dashboard-stats {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
}

.stats-card {
  background: #f8fafc;
  padding: 20px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  gap: 15px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.stats-card__icon {
  font-size: 32px;
  width: 60px;
  height: 60px;
  background: #eff6ff;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.stats-card__info h3 {
  font-size: 14px;
  color: #64748b;
  margin-bottom: 4px;
}

.stats-card__value {
  font-size: 24px;
  font-weight: 600;
  color: #1e293b;
}

.dashboard-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 10px;
}

.dashboard-table th,
.dashboard-table td {
  padding: 12px 15px;
  text-align: left;
  border-bottom: 1px solid #e2e8f0;
}

.dashboard-table th {
  background: #f8fafc;
  color: #1e293b;
  font-weight: 600;
}

.dashboard-table tr:hover {
  background: #f8fafc;
}
</style>