# 后端接口测试指南

## 项目结构分析

### 前端配置
- **前端端口**: 3000 (Vite 开发服务器)
- **前端地址**: http://localhost:3000
- **API 代理**: `/api` → `http://localhost:8080`

### 后端配置
- **后端端口**: 8080
- **后端地址**: http://localhost:8080
- **Servlet 路径**: `/post`
- **CORS**: 已配置，允许所有来源

## 问题分析

### 发现的问题
1. ✅ **已修复**: `fend/src/utils/request.js` 存在循环导入问题
2. ✅ **已修复**: `vite.config.js` 缺少代理配置
3. ✅ **已修复**: baseURL 配置不正确

### 修复内容
1. 修复了 `utils/request.js`，移除了循环导入，正确导入 axios
2. 配置了 Vite 代理，将 `/api` 请求代理到 `http://localhost:8080`
3. 优化了错误处理，提供更详细的错误信息

## 后端接口说明

### 接口地址
- **基础路径**: `http://localhost:8080/post`
- **请求方式**: GET 或 POST（后端 doPost 方法会调用 doGet）

### 接口参数说明

所有接口都需要通过 `op` 参数指定操作类型：

| 操作类型 | op 值 | 请求方法 | 说明 |
|---------|-------|---------|------|
| 分页查询 | `find` | GET | 查询帖子列表 |
| 帖子详情 | `detail` | GET | 获取单个帖子详情 |
| 新增帖子 | `insert` | POST | 创建新帖子 |
| 更新帖子 | `update` | POST | 更新帖子信息 |
| 删除帖子 | `delete` | GET | 删除帖子 |

## 接口测试方法

### 方法一：使用 curl 命令（推荐用于快速测试）

#### 1. 分页查询帖子
```bash
# 基本查询（默认第1页，每页10条）
curl "http://localhost:8080/post?op=find&page=1&rows=10"

# 按板块查询
curl "http://localhost:8080/post?op=find&page=1&rows=10&boardId=1"

# 按标题关键词查询
curl "http://localhost:8080/post?op=find&page=1&rows=10&title=测试"
```

#### 2. 获取帖子详情
```bash
curl "http://localhost:8080/post?op=detail&id=1"
```

#### 3. 新增帖子
```bash
curl -X POST "http://localhost:8080/post?op=insert" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "测试帖子标题",
    "content": "这是测试帖子的内容",
    "userId": 1,
    "boardId": 1
  }'
```

#### 4. 更新帖子
```bash
curl -X POST "http://localhost:8080/post?op=update" \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "title": "更新后的标题",
    "content": "更新后的内容",
    "boardId": 1
  }'
```

#### 5. 删除帖子
```bash
curl "http://localhost:8080/post?op=delete&id=1"
```

### 方法二：使用 Postman

1. **导入测试集合**（见下方 Postman 配置）
2. **设置环境变量**:
   - `base_url`: `http://localhost:8080`
   - `post_path`: `/post`

### 方法三：使用浏览器测试（仅限 GET 请求）

直接在浏览器地址栏输入：
```
http://localhost:8080/post?op=find&page=1&rows=10
```

### 方法四：使用前端页面测试

1. 确保后端运行在 `http://localhost:8080`
2. 启动前端开发服务器：
   ```bash
   cd fend
   npm run dev
   ```
3. 前端会自动打开 `http://localhost:3000`
4. 前端请求会自动通过代理转发到后端

## 测试步骤

### 步骤 1: 检查后端是否运行
```bash
# 检查 8080 端口是否被占用
netstat -ano | findstr :8080  # Windows
# 或
lsof -i :8080  # Mac/Linux
```

### 步骤 2: 测试 CORS 配置
使用浏览器开发者工具（F12）检查：
- Network 标签页查看请求是否成功
- Console 标签页查看是否有 CORS 错误

### 步骤 3: 测试基本连接
```bash
# 最简单的测试
curl "http://localhost:8080/post?op=find"
```

### 步骤 4: 测试各个接口
按照上面的 curl 命令依次测试每个接口

## 常见问题排查

### 问题 1: 连接被拒绝 (Connection refused)
**原因**: 后端服务器未启动
**解决**: 启动后端服务器

### 问题 2: CORS 错误
**原因**: 跨域配置问题
**解决**: 检查 `CorsFilter.java` 是否正确配置并生效

### 问题 3: 404 Not Found
**原因**: Servlet 路径不正确
**解决**: 确认后端 Servlet 映射路径为 `/post`

### 问题 4: 500 Internal Server Error
**原因**: 后端代码错误或数据库连接问题
**解决**: 
- 查看后端日志
- 检查数据库连接配置
- 检查 MyBatis 配置

### 问题 5: 前端无法连接后端
**原因**: 代理配置未生效
**解决**: 
1. 确认 `vite.config.js` 中代理配置正确
2. 重启前端开发服务器
3. 确认前端请求路径以 `/api` 开头

## 响应数据格式

### 成功响应示例
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    // 具体数据
  }
}
```

### 错误响应示例
```json
{
  "code": 500,
  "msg": "操作失败：具体错误信息"
}
```

## 数据库连接检查

如果后端无法连接数据库，检查以下配置：
1. `mybatis-config.xml` 中的数据库连接配置
2. MySQL 服务是否运行
3. 数据库用户名、密码是否正确
4. 数据库是否存在

## 下一步操作

1. ✅ 修复前端请求配置
2. ✅ 配置 Vite 代理
3. ✅ 创建测试文档
4. ⏳ 使用上述方法测试后端接口
5. ⏳ 根据测试结果调整配置

