<template>
  <div class="search-bar content-row">
    <label class="input-tip">{{ label }}：</label>
    <el-input
      v-model="searchValue"
      class="input-field"
      :placeholder="placeholder"
      @keyup.enter="handleSearch"
      clearable
    ></el-input>
    <el-button type="primary" @click="handleSearch">搜索</el-button>
    <el-button type="default" @click="handleReset">重置</el-button>
  </div>
</template>

<script setup>
import { defineProps, defineEmits, ref, watch } from 'vue'

// 定义属性
const props = defineProps({
  label: {
    type: String,
    default: '搜索'
  },
  placeholder: {
    type: String,
    default: '请输入搜索内容'
  },
  initValue: {
    type: String,
    default: ''
  }
})

// 定义事件
const emit = defineEmits(['search', 'reset'])

// 搜索值
const searchValue = ref(props.initValue)

// 监听初始值变化
watch(() => props.initValue, (val) => {
  searchValue.value = val
}, { immediate: true })

// 处理搜索
const handleSearch = () => {
  emit('search', searchValue.value.trim())
}

// 处理重置
const handleReset = () => {
  searchValue.value = ''
  emit('reset')
}
</script>

<style scoped>
.search-bar {
  gap: 10px;
}

.el-input {
  width: 300px;
}
</style>