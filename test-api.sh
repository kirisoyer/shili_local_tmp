#!/bin/bash

# 后端接口测试脚本
# 使用方法: bash test-api.sh

BASE_URL="http://localhost:8080"
POST_PATH="/post"

echo "=========================================="
echo "后端接口测试脚本"
echo "=========================================="
echo ""

# 检查后端是否运行
echo "1. 检查后端连接..."
if curl -s --connect-timeout 3 "${BASE_URL}${POST_PATH}?op=find" > /dev/null 2>&1; then
    echo "✅ 后端服务器连接正常"
else
    echo "❌ 无法连接到后端服务器 ${BASE_URL}"
    echo "   请确保后端服务器已启动并运行在 8080 端口"
    exit 1
fi
echo ""

# 测试 1: 分页查询
echo "2. 测试分页查询接口..."
echo "请求: GET ${BASE_URL}${POST_PATH}?op=find&page=1&rows=10"
response=$(curl -s "${BASE_URL}${POST_PATH}?op=find&page=1&rows=10")
echo "响应: $response"
echo ""

# 测试 2: 获取帖子详情（需要有效的 ID）
echo "3. 测试获取帖子详情接口..."
echo "请求: GET ${BASE_URL}${POST_PATH}?op=detail&id=1"
response=$(curl -s "${BASE_URL}${POST_PATH}?op=detail&id=1")
echo "响应: $response"
echo ""

# 测试 3: 新增帖子
echo "4. 测试新增帖子接口..."
echo "请求: POST ${BASE_URL}${POST_PATH}?op=insert"
response=$(curl -s -X POST "${BASE_URL}${POST_PATH}?op=insert" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "测试帖子标题",
    "content": "这是测试帖子的内容",
    "userId": 1,
    "boardId": 1
  }')
echo "响应: $response"
echo ""

# 测试 4: 更新帖子（需要有效的 ID）
echo "5. 测试更新帖子接口..."
echo "请求: POST ${BASE_URL}${POST_PATH}?op=update"
response=$(curl -s -X POST "${BASE_URL}${POST_PATH}?op=update" \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "title": "更新后的标题",
    "content": "更新后的内容",
    "boardId": 1
  }')
echo "响应: $response"
echo ""

# 测试 5: 删除帖子（需要有效的 ID）
echo "6. 测试删除帖子接口..."
echo "请求: GET ${BASE_URL}${POST_PATH}?op=delete&id=1"
response=$(curl -s "${BASE_URL}${POST_PATH}?op=delete&id=1")
echo "响应: $response"
echo ""

echo "=========================================="
echo "测试完成！"
echo "=========================================="

