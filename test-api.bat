@echo off
REM 后端接口测试脚本 (Windows)
REM 使用方法: test-api.bat

set BASE_URL=http://localhost:8080
set POST_PATH=/post

echo ==========================================
echo 后端接口测试脚本
echo ==========================================
echo.

REM 检查后端是否运行
echo 1. 检查后端连接...
curl -s --connect-timeout 3 "%BASE_URL%%POST_PATH%?op=find" >nul 2>&1
if %errorlevel% equ 0 (
    echo [OK] 后端服务器连接正常
) else (
    echo [ERROR] 无法连接到后端服务器 %BASE_URL%
    echo         请确保后端服务器已启动并运行在 8080 端口
    pause
    exit /b 1
)
echo.

REM 测试 1: 分页查询
echo 2. 测试分页查询接口...
echo 请求: GET %BASE_URL%%POST_PATH%?op=find^&page=1^&rows=10
curl -s "%BASE_URL%%POST_PATH%?op=find&page=1&rows=10"
echo.
echo.

REM 测试 2: 获取帖子详情
echo 3. 测试获取帖子详情接口...
echo 请求: GET %BASE_URL%%POST_PATH%?op=detail^&id=1
curl -s "%BASE_URL%%POST_PATH%?op=detail&id=1"
echo.
echo.

REM 测试 3: 新增帖子
echo 4. 测试新增帖子接口...
echo 请求: POST %BASE_URL%%POST_PATH%?op=insert
curl -s -X POST "%BASE_URL%%POST_PATH%?op=insert" ^
  -H "Content-Type: application/json" ^
  -d "{\"title\": \"测试帖子标题\", \"content\": \"这是测试帖子的内容\", \"userId\": 1, \"boardId\": 1}"
echo.
echo.

REM 测试 4: 更新帖子
echo 5. 测试更新帖子接口...
echo 请求: POST %BASE_URL%%POST_PATH%?op=update
curl -s -X POST "%BASE_URL%%POST_PATH%?op=update" ^
  -H "Content-Type: application/json" ^
  -d "{\"id\": 1, \"title\": \"更新后的标题\", \"content\": \"更新后的内容\", \"boardId\": 1}"
echo.
echo.

REM 测试 5: 删除帖子
echo 6. 测试删除帖子接口...
echo 请求: GET %BASE_URL%%POST_PATH%?op=delete^&id=1
curl -s "%BASE_URL%%POST_PATH%?op=delete&id=1"
echo.
echo.

echo ==========================================
echo 测试完成！
echo ==========================================
pause

