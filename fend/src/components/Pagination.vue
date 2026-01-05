<template>
  <div class="pagination content-row">
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pageNum"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      background
    >
    </el-pagination>
  </div>
</template>

<script setup>
import { defineProps, defineEmits, watch } from 'vue'

const props = defineProps({
  pageNum: {
    type: Number,
    default: 1
  },
  pageSize: {
    type: Number,
    default: 10
  },
  total: {
    type: Number,
    default: 0
  }
})

const emit = defineEmits(['page-change'])

// 每页条数改变
const handleSizeChange = (val) => {
  emit('page-change', { pageNum: 1, pageSize: val })
}

// 当前页改变
const handleCurrentChange = (val) => {
  emit('page-change', { pageNum: val, pageSize: props.pageSize })
}
</script>

<style scoped>
.pagination {
  justify-content: center;
  margin-top: 20px;
}
</style>