<template>
  <div class="content-container">
    <h2>帖子管理</h2>
    
    <!-- 搜索栏 -->
    <SearchBar 
      label="帖子标题/作者"
      placeholder="请输入帖子标题或作者名"
      @search="handleSearch"
      @reset="handleReset"
    ></SearchBar>

    <!-- 操作按钮 -->
    <ActionButtonGroup 
      :buttons="actionButtons"
      @btn-click="handleBtnClick"
    ></ActionButtonGroup>

    <!-- 帖子列表 -->
    <el-table 
      :data="postList" 
      border 
      style="width: 100%;"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <el-table-column prop="title" label="帖子标题" min-width="300"></el-table-column>
      <el-table-column prop="author" label="作者" width="120"></el-table-column>
      <el-table-column prop="categoryName" label="所属板块" width="150"></el-table-column>
      <el-table-column prop="viewCount" label="浏览量" width="100"></el-table-column>
      <el-table-column prop="commentCount" label="评论数" width="100"></el-table-column>
      <el-table-column prop="createTime" label="发布时间" width="180"></el-table-column>
      <el-table-column 
        prop="status" 
        label="状态" 
        width="100"
        :formatter="formatPostStatus"
      ></el-table-column>
      <el-table-column label="操作" width="200">
        <template #default="scope">
          <el-button size="small" type="primary" @click="viewPost(scope.row.id)">查看</el-button>
          <el-button 
            size="small" 
            type="success" 
            v-if="scope.row.status !== 1"
            @click="updateStatus(scope.row.id, 1)"
          >
            解封
          </el-button>
          <el-button 
            size="small" 
            type="danger" 
            v-if="scope.row.status === 1"
            @click="updateStatus(scope.row.id, 3)"
          >
            封禁
          </el-button>
          <el-button size="small" type="warning" @click="deletePost(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <Pagination 
      :page-num="pageNum"
      :page-size="pageSize"
      :total="total"
      @page-change="handlePageChange"
    ></Pagination>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import SearchBar from '@/components/SearchBar.vue'
import Pagination from '@/components/Pagination.vue'
import ActionButtonGroup from '@/components/ActionButtonGroup.vue'
import { getPosts, updatePostStatus, deletePost } from '@/mock/posts'
import { getStatusText } from '@/tools/common'

const router = useRouter()

// 分页参数
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)
// 搜索关键词
const keyword = ref('')
// 帖子列表
const postList = ref([])
// 选中的帖子
const selectedPosts = ref([])

// 操作按钮配置
const actionButtons = ref([
  { key: 'batchDelete', label: '批量删除', type: 'danger', icon: 'Delete' },
  { key: 'batchBan', label: '批量封禁', type: 'warning', icon: 'Lock' },
  { key: 'batchUnban', label: '批量解封', type: 'success', icon: 'Unlock' },
  { key: 'refresh', label: '刷新列表', type: 'default', icon: 'Refresh' }
])

// 状态格式化
const formatPostStatus = (row) => {
  const statusOptions = [
    { value: 1, label: '正常' },
    { value: 2, label: '审核中' },
    { value: 3, label: '已封禁' }
  ]
  return getStatusText(row.status, statusOptions)
}

// 获取帖子列表
const getPostList = async () => {
  const res = await getPosts({
    pageNum: pageNum.value,
    pageSize: pageSize.value,
    keyword: keyword.value
  })
  postList.value = res.list
  total.value = res.total
}

// 搜索
const handleSearch = (val) => {
  keyword.value = val
  pageNum.value = 1
  getPostList()
}

// 重置搜索
const handleReset = () => {
  keyword.value = ''
  pageNum.value = 1
  getPostList()
}

// 分页改变
const handlePageChange = (params) => {
  pageNum.value = params.pageNum
  pageSize.value = params.pageSize
  getPostList()
}

// 选择行改变
const handleSelectionChange = (val) => {
  selectedPosts.value = val
}

// 操作按钮点击
const handleBtnClick = (key) => {
  if (key === 'refresh') {
    getPostList()
    ElMessage.success('列表已刷新')
    return
  }

  if (selectedPosts.value.length === 0) {
    ElMessage.warning('请选择要操作的帖子')
    return
  }

  const ids = selectedPosts.value.map(item => item.id)
  
  switch (key) {
    case 'batchDelete':
      ElMessageBox.confirm(
        '确定要批量删除选中的帖子吗？此操作不可恢复！',
        '批量删除',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }
      ).then(async () => {
        // 模拟批量删除
        let successCount = 0
        for (const id of ids) {
          const res = await deletePost(id)
          if (res) successCount++
        }
        ElMessage.success(`成功删除 ${successCount} 条帖子`)
        getPostList()
      })
      break
    case 'batchBan':
      ElMessageBox.confirm(
        '确定要批量封禁选中的帖子吗？',
        '批量封禁',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }
      ).then(async () => {
        let successCount = 0
        for (const id of ids) {
          const res = await updatePostStatus(id, 3)
          if (res) successCount++
        }
        ElMessage.success(`成功封禁 ${successCount} 条帖子`)
        getPostList()
      })
      break
    case 'batchUnban':
      ElMessageBox.confirm(
        '确定要批量解封选中的帖子吗？',
        '批量解封',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'success'
        }
      ).then(async () => {
        let successCount = 0
        for (const id of ids) {
          const res = await updatePostStatus(id, 1)
          if (res) successCount++
        }
        ElMessage.success(`成功解封 ${successCount} 条帖子`)
        getPostList()
      })
      break
  }
}

// 查看帖子
const viewPost = (id) => {
  router.push(`/posts/detail/${id}`)
}

// 修改帖子状态
const updateStatus = async (id, status) => {
  const statusText = status === 1 ? '解封' : '封禁'
  ElMessageBox.confirm(
    `确定要${statusText}该帖子吗？`,
    statusText,
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: status === 1 ? 'success' : 'warning'
    }
  ).then(async () => {
    const res = await updatePostStatus(id, status)
    if (res) {
      ElMessage.success(`帖子${statusText}成功`)
      getPostList()
    } else {
      ElMessage.error(`帖子${statusText}失败`)
    }
  })
}

// 删除帖子
const deletePost = async (id) => {
  ElMessageBox.confirm(
    '确定要删除该帖子吗？此操作不可恢复！',
    '删除帖子',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'danger'
    }
  ).then(async () => {
    const res = await deletePost(id)
    if (res) {
      ElMessage.success('帖子删除成功')
      getPostList()
    } else {
      ElMessage.error('帖子删除失败')
    }
  })
}

onMounted(() => {
  getPostList()
})
</script>

<style scoped>
.content-container h2 {
  margin-bottom: 20px;
  font-size: 18px;
  color: #1e293b;
}
</style>