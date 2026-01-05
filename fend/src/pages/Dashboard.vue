<template>
  <div class="content-container">
    <h2>论坛管理控制台</h2>
    <div class="dashboard-stats content-row">
      <div class="stat-item">
        <h3>总帖子数</h3>
        <p>8,980</p>
      </div>
      <div class="stat-item">
        <h3>总用户数</h3>
        <p>12,560</p>
      </div>
      <div class="stat-item">
        <h3>今日新增帖子</h3>
        <p>128</p>
      </div>
      <div class="stat-item">
        <h3>今日新增用户</h3>
        <p>56</p>
      </div>
      <div class="stat-item">
        <h3>待审核内容</h3>
        <p>23</p>
      </div>
      <div class="stat-item">
        <h3>违规内容</h3>
        <p>8</p>
      </div>
    </div>

    <div class="dashboard-recent content-row">
      <h3>最近新增帖子</h3>
      <el-table :data="recentPosts" border style="width: 100%;">
        <el-table-column prop="title" label="帖子标题" min-width="300"></el-table-column>
        <el-table-column prop="author" label="作者"></el-table-column>
        <el-table-column prop="categoryName" label="所属板块"></el-table-column>
        <el-table-column prop="createTime" label="发布时间"></el-table-column>
        <el-table-column label="操作">
          <template #default="scope">
            <el-button size="small" type="primary" @click="goToDetail(scope.row.id)">查看</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getPosts } from '@/mock/posts'

const router = useRouter()
const recentPosts = ref([])

// 获取最近帖子
const getRecentPosts = async () => {
  const res = await getPosts({ pageNum: 1, pageSize: 5 })
  recentPosts.value = res.list
}

// 跳转到帖子详情
const goToDetail = (id) => {
  router.push(`/posts/detail/${id}`)
}

onMounted(() => {
  getRecentPosts()
})
</script>

<style scoped>
.dashboard-stats {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  margin: 20px 0;
}

.stat-item {
  background-color: #f8f9fa;
  padding: 20px;
  border-radius: 8px;
  width: calc(16.666% - 20px);
  text-align: center;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.stat-item h3 {
  font-size: 14px;
  color: #666;
  margin-bottom: 10px;
}

.stat-item p {
  font-size: 24px;
  font-weight: 600;
  color: #1e293b;
}

.dashboard-recent {
  width: 100%;
  margin-top: 20px;
}

.dashboard-recent h3 {
  margin-bottom: 10px;
  font-size: 16px;
}
</style>