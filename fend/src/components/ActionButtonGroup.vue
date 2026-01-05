<template>
  <div class="action-buttons content-row">
    <el-button 
      v-for="btn in buttons" 
      :key="btn.key"
      :type="btn.type || 'default'"
      :icon="btn.icon"
      @click="handleClick(btn.key)"
      :disabled="btn.disabled"
    >
      {{ btn.label }}
    </el-button>
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue'

const props = defineProps({
  buttons: {
    type: Array,
    default: () => []
  }
})

const emit = defineEmits(['btn-click'])

const handleClick = (key) => {
  emit('btn-click', key)
}
</script>

<style scoped>
.action-buttons {
  gap: 10px;
}
</style>