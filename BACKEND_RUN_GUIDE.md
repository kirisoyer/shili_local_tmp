# 后端服务运行指南

## 项目信息

- **项目类型**: Java Web 应用 (Servlet)
- **Java 版本**: Java SE 17
- **Web 服务器**: Apache Tomcat 10.1
- **数据库**: MySQL 8.x
- **框架**: MyBatis 3.5.2
- **Servlet 路径**: `/post`

## 前置要求

### 1. 安装 Java JDK 17
- 下载地址: https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html
- 验证安装:
  ```bash
  java -version
  ```
  应显示 `java version "17.x.x"`

### 2. 安装 Apache Tomcat 10.1
- 下载地址: https://tomcat.apache.org/download-10.cgi
- 解压到任意目录，例如: `C:\apache-tomcat-10.1.x`
- 设置环境变量 `CATALINA_HOME` 指向 Tomcat 目录（可选）

### 3. 安装 MySQL 8.x
- 下载地址: https://dev.mysql.com/downloads/mysql/
- 确保 MySQL 服务正在运行
- 验证安装:
  ```bash
  mysql --version
  ```

## 数据库配置

### 步骤 1: 创建数据库

使用 MySQL 客户端（命令行或 MySQL Workbench）执行：

```sql
CREATE DATABASE IF NOT EXISTS bbs_forum 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;
```

### 步骤 2: 创建数据表

执行以下 SQL 创建必要的表：

```sql
USE bbs_forum;

-- 用户表（如果不存在）
CREATE TABLE IF NOT EXISTS bbs_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 板块表（如果不存在）
CREATE TABLE IF NOT EXISTS bbs_board (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    board_name VARCHAR(100) NOT NULL,
    board_desc VARCHAR(500),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 帖子表
CREATE TABLE IF NOT EXISTS bbs_post (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    user_id BIGINT NOT NULL,
    board_id BIGINT NOT NULL,
    view_count INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES bbs_user(id),
    FOREIGN KEY (board_id) REFERENCES bbs_board(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 评论表（如果不存在）
CREATE TABLE IF NOT EXISTS bbs_comment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    post_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (post_id) REFERENCES bbs_post(id),
    FOREIGN KEY (user_id) REFERENCES bbs_user(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

### 步骤 3: 插入测试数据（可选）

```sql
USE bbs_forum;

-- 插入测试用户
INSERT INTO bbs_user (username, password, email) VALUES
('admin', '123456', 'admin@example.com'),
('user1', '123456', 'user1@example.com');

-- 插入测试板块
INSERT INTO bbs_board (board_name, board_desc) VALUES
('技术交流', '技术讨论和分享'),
('灌水区', '闲聊灌水'),
('问题求助', '遇到问题来这里求助');

-- 插入测试帖子
INSERT INTO bbs_post (title, content, user_id, board_id) VALUES
('欢迎来到论坛', '这是第一个测试帖子', 1, 1),
('Java 学习心得', '分享一些 Java 学习经验', 1, 1);
```

### 步骤 4: 检查数据库配置

打开 `bend/src/main/java/mybatis-config.xml`，确认数据库连接信息：

```xml
<property name="url" value="jdbc:mysql://localhost:3306/bbs_forum?useUnicode=true&amp;characterEncoding=UTF-8&amp;serverTimezone=Asia/Shanghai" />
<property name="username" value="root" />
<property name="password" value="123456" />
```

**如果您的 MySQL 配置不同，请修改：**
- `localhost:3306` - 数据库地址和端口
- `root` - 数据库用户名
- `123456` - 数据库密码

## 运行后端服务

### 方法一：使用 Eclipse/IntelliJ IDEA（推荐）

#### Eclipse 配置步骤：

1. **导入项目**
   - File → Import → Existing Projects into Workspace
   - 选择 `bend` 目录
   - 点击 Finish

2. **配置 Tomcat 服务器**
   - Window → Preferences → Server → Runtime Environments
   - Add → Apache Tomcat v10.1
   - 选择 Tomcat 安装目录
   - 点击 Finish

3. **添加项目到服务器**
   - 右键项目 → Properties → Project Facets
   - 勾选 "Dynamic Web Module" 和 "Java"
   - 右键项目 → Run As → Run on Server
   - 选择 Tomcat 10.1
   - 点击 Finish

4. **启动服务器**
   - 服务器会自动启动
   - 查看 Console 输出，确认没有错误

#### IntelliJ IDEA 配置步骤：

1. **导入项目**
   - File → Open → 选择 `bend` 目录

2. **配置 Tomcat**
   - Run → Edit Configurations
   - 点击 "+" → Tomcat Server → Local
   - 配置 Tomcat 安装目录
   - Deployment 标签页 → 添加 Artifact
   - 选择项目的 war 包或 exploded 版本

3. **启动服务器**
   - 点击 Run 按钮或按 Shift+F10

### 方法二：使用 VSCode

VSCode 启动 Java Web 项目有多种方式，推荐使用 Tomcat for Java 扩展。

#### 方式一：使用 Tomcat for Java 扩展（推荐）

1. **安装必要的扩展**
   
   打开 VSCode，按 `Ctrl+Shift+X` 打开扩展市场，安装以下扩展：
   - **Extension Pack for Java** (Microsoft)
     - 包含 Java 开发所需的核心扩展
   - **Tomcat for Java** (Wei Shen)
     - 提供 Tomcat 服务器集成功能

2. **打开项目**
   - File → Open Folder → 选择 `bend` 目录
   - 等待 Java 扩展初始化项目（首次打开可能需要一些时间）

3. **配置 Tomcat 服务器**
   
   - 按 `Ctrl+Shift+P` 打开命令面板
   - 输入 `Tomcat: Add Tomcat Server`
   - 选择已安装的 Tomcat 目录（例如: `C:\apache-tomcat-10.1.x`）
   - 确认后，VSCode 会在 `.vscode` 目录下创建配置文件

4. **编译项目**
   
   由于项目没有 Maven/Gradle，需要手动编译：
   
   **方法 A：使用 VSCode 终端编译**
   
   打开终端（`Ctrl+`` 或 Terminal → New Terminal），执行：
   
   ```bash
   cd bend
   
   # Windows PowerShell
   $libs = "src\main\webapp\WEB-INF\lib\*"
   javac -cp "$libs" -d build\classes -encoding UTF-8 `
         src\main\java\com\example\ordermanager\**\*.java
   
   # Windows CMD
   javac -cp "src\main\webapp\WEB-INF\lib\*" -d build\classes -encoding UTF-8 ^
         src\main\java\com\example\ordermanager\**\*.java
   
   # Linux/Mac
   javac -cp "src/main/webapp/WEB-INF/lib/*" -d build/classes -encoding UTF-8 \
         src/main/java/com/example/ordermanager/**/*.java
   
   # 复制 mybatis-config.xml 到编译输出目录
   # Windows
   copy src\main\java\mybatis-config.xml build\classes\
   # Linux/Mac
   cp src/main/java/mybatis-config.xml build/classes/
   ```
   
   **方法 B：使用 tasks.json 自动编译**
   
   创建 `.vscode/tasks.json` 文件：
   
   ```json
   {
     "version": "2.0.0",
     "tasks": [
       {
         "label": "compile-java",
         "type": "shell",
         "command": "javac",
         "args": [
           "-cp",
           "src/main/webapp/WEB-INF/lib/*",
           "-d",
           "build/classes",
           "-encoding",
           "UTF-8",
           "src/main/java/com/example/ordermanager/**/*.java"
         ],
         "group": {
           "kind": "build",
           "isDefault": true
         },
         "problemMatcher": ["$tsc"]
       }
     ]
   }
   ```
   
   然后按 `Ctrl+Shift+B` 执行编译任务。

5. **创建 WAR 包（可选）**
   
   如果需要部署 WAR 包，可以创建脚本或使用以下命令：
   
   ```bash
   # Windows PowerShell
   New-Item -ItemType Directory -Force -Path temp\WEB-INF\classes, temp\WEB-INF\lib
   Copy-Item -Recurse build\classes\* temp\WEB-INF\classes\
   Copy-Item src\main\webapp\WEB-INF\lib\* temp\WEB-INF\lib\
   cd temp
   jar cvf ..\bbs-forum.war *
   cd ..
   Remove-Item -Recurse -Force temp
   ```

6. **部署到 Tomcat**
   
   - 在 VSCode 左侧资源管理器中，找到 "TOMCAT SERVERS" 面板
   - 右键点击已添加的 Tomcat 服务器
   - 选择 "Add War" 或 "Add War Package"
   - 选择项目目录或 WAR 文件
   - 输入应用名称（例如: `bbs-forum`）

7. **启动 Tomcat**
   
   - 在 "TOMCAT SERVERS" 面板中，右键点击服务器
   - 选择 "Start" 或点击服务器旁边的启动按钮
   - 查看 OUTPUT 面板（View → Output → 选择 "Tomcat for Java"）确认启动成功

8. **验证部署**
   - 打开浏览器访问: `http://localhost:8080/bbs-forum/post?op=find&page=1&rows=10`
   - 应该能看到 JSON 响应

#### 方式二：使用 Community Server Connectors 扩展

1. **安装扩展**
   - **Community Server Connectors** (Red Hat)
     - 提供多种服务器（包括 Tomcat）的集成

2. **配置服务器**
   - 按 `Ctrl+Shift+P` → `Server: Add Server`
   - 选择 "Tomcat"
   - 配置 Tomcat 路径和端口

3. **部署和启动**
   - 右键项目 → "Add to Server"
   - 选择配置的 Tomcat 服务器
   - 启动服务器

#### 方式三：手动配置 launch.json（高级）

如果上述扩展不适用，可以手动配置：

1. **创建 `.vscode/launch.json`**
   
   ```json
   {
     "version": "0.2.0",
     "configurations": [
       {
         "type": "java",
         "name": "Launch Tomcat",
         "request": "launch",
         "mainClass": "org.apache.catalina.startup.Bootstrap",
         "projectName": "bend",
         "args": [
           "start"
         ],
         "vmArgs": [
           "-Dcatalina.home=C:/apache-tomcat-10.1.x",
           "-Dcatalina.base=C:/apache-tomcat-10.1.x",
           "-Djava.util.logging.manager=org.apache.juli.ClassLoaderLogManager"
         ],
         "classPaths": [
           "${workspaceFolder}/bend/src/main/webapp/WEB-INF/lib/*",
           "C:/apache-tomcat-10.1.x/bin/*",
           "C:/apache-tomcat-10.1.x/lib/*"
         ]
       }
     ]
   }
   ```
   
   **注意**: 需要将路径替换为您的实际 Tomcat 安装路径。

2. **创建 `.vscode/settings.json`**
   
   ```json
   {
     "java.configuration.runtimes": [
       {
         "name": "JavaSE-17",
         "path": "C:/Program Files/Java/jdk-17",
         "default": true
       }
     ],
     "java.project.sourcePaths": [
       "src/main/java"
     ],
     "java.project.outputPath": "build/classes",
     "java.project.referencedLibraries": [
       "src/main/webapp/WEB-INF/lib/**/*.jar"
     ]
   }
   ```

3. **启动调试**
   - 按 `F5` 或点击调试按钮
   - 选择 "Launch Tomcat" 配置

#### VSCode 使用提示

- **查看日志**: View → Output → 选择对应的输出通道
- **调试**: 在代码中设置断点，使用 F5 启动调试模式
- **重新编译**: 修改代码后，按 `Ctrl+Shift+B` 重新编译
- **重新部署**: 修改代码后，需要重新编译并重启 Tomcat

### 方法三：手动部署到 Tomcat

#### 步骤 1: 编译项目

如果项目未编译，需要先编译：

```bash
cd bend

# 创建编译输出目录
mkdir -p build/classes

# 编译 Java 文件（需要设置 classpath）
javac -cp "src/main/webapp/WEB-INF/lib/*" \
      -d build/classes \
      src/main/java/com/example/ordermanager/**/*.java \
      src/main/java/mybatis-config.xml
```

**注意**: 手动编译比较复杂，建议使用 IDE 或 Maven/Gradle。

#### 步骤 2: 创建 WAR 包

```bash
cd bend

# 创建临时目录结构
mkdir -p temp/WEB-INF/classes
mkdir -p temp/WEB-INF/lib

# 复制编译后的类文件
cp -r build/classes/* temp/WEB-INF/classes/

# 复制依赖库
cp src/main/webapp/WEB-INF/lib/* temp/WEB-INF/lib/

# 创建 web.xml（如果需要）
# 由于使用了 @WebServlet 注解，可能不需要 web.xml

# 打包成 WAR
cd temp
jar cvf ../bbs-forum.war *
cd ..
```

#### 步骤 3: 部署到 Tomcat

1. **复制 WAR 文件**
   - 将 `bbs-forum.war` 复制到 Tomcat 的 `webapps` 目录
   - 例如: `C:\apache-tomcat-10.1.x\webapps\bbs-forum.war`

2. **启动 Tomcat**
   ```bash
   # Windows
   C:\apache-tomcat-10.1.x\bin\startup.bat
   
   # Linux/Mac
   /path/to/tomcat/bin/startup.sh
   ```

3. **验证部署**
   - 打开浏览器访问: `http://localhost:8080/bbs-forum/post?op=find`
   - 应该能看到 JSON 响应

### 方法四：使用 Maven（如果项目有 pom.xml）

如果项目有 `pom.xml` 文件：

```bash
cd bend
mvn clean package
# 生成的 WAR 文件在 target 目录
# 复制到 Tomcat webapps 目录
```

## 验证服务运行

### 1. 检查端口占用

```bash
# Windows
netstat -ano | findstr :8080

# Linux/Mac
lsof -i :8080
```

### 2. 测试接口

使用浏览器或 curl 测试：

```bash
# 测试分页查询
curl "http://localhost:8080/post?op=find&page=1&rows=10"

# 或直接在浏览器打开
http://localhost:8080/post?op=find&page=1&rows=10
```

### 3. 查看日志

- **Tomcat 日志**: `$CATALINA_HOME/logs/catalina.out`
- **IDE Console**: 查看 IDE 的控制台输出
- **应用日志**: 查看是否有异常堆栈信息

## 常见问题

### 问题 1: 端口 8080 被占用

**解决方案**:
1. 修改 Tomcat 端口:
   - 编辑 `$CATALINA_HOME/conf/server.xml`
   - 找到 `<Connector port="8080"` 改为其他端口，如 `8081`
   - 重启 Tomcat

2. 或停止占用 8080 端口的进程:
   ```bash
   # Windows
   netstat -ano | findstr :8080
   taskkill /PID <进程ID> /F
   
   # Linux/Mac
   lsof -ti:8080 | xargs kill -9
   ```

### 问题 2: 数据库连接失败

**错误信息**: `Communications link failure` 或 `Access denied`

**解决方案**:
1. 确认 MySQL 服务正在运行
2. 检查 `mybatis-config.xml` 中的数据库配置
3. 确认数据库 `bbs_forum` 已创建
4. 确认用户名和密码正确
5. 检查 MySQL 是否允许本地连接

### 问题 3: 找不到类文件 (ClassNotFoundException)

**解决方案**:
1. 确认项目已正确编译
2. 检查 `WEB-INF/lib` 目录下是否有所有依赖 JAR 包
3. 确认 `mybatis-config.xml` 在 classpath 中

### 问题 4: Servlet 映射失败 (404)

**解决方案**:
1. 确认使用了 `@WebServlet("/post")` 注解
2. 如果使用 web.xml，检查 servlet 映射配置
3. 确认项目已正确部署到 Tomcat
4. 检查访问路径是否正确（注意上下文路径）

### 问题 5: CORS 跨域错误

**解决方案**:
- 项目已配置 `CorsFilter`，应该自动处理跨域
- 如果仍有问题，检查 `CorsFilter.java` 是否正确配置
- 确认 Filter 已正确注册

## 项目结构说明

```
bend/
├── src/
│   └── main/
│       ├── java/
│       │   ├── com/example/ordermanager/
│       │   │   ├── dao/          # 数据访问层
│       │   │   ├── entity/       # 实体类
│       │   │   ├── filter/       # 过滤器（CORS）
│       │   │   ├── service/      # 业务逻辑层
│       │   │   ├── servlet/      # Servlet 控制器
│       │   │   └── utils/        # 工具类
│       │   └── mybatis-config.xml  # MyBatis 配置
│       └── webapp/
│           └── WEB-INF/
│               └── lib/          # 依赖 JAR 包
└── build/                        # 编译输出目录
```

## 下一步

1. ✅ 配置数据库并创建表
2. ✅ 启动后端服务
3. ✅ 测试接口是否正常
4. ⏳ 启动前端服务（见前端 README）
5. ⏳ 测试前后端联调

## 快速启动检查清单

- [ ] Java 17 已安装
- [ ] Tomcat 10.1 已安装
- [ ] MySQL 8.x 已安装并运行
- [ ] 数据库 `bbs_forum` 已创建
- [ ] 数据表已创建（bbs_user, bbs_board, bbs_post, bbs_comment）
- [ ] 数据库连接配置已检查（mybatis-config.xml）
- [ ] 项目已导入 IDE 或已编译
- [ ] Tomcat 服务器已配置
- [ ] 项目已部署到 Tomcat
- [ ] 服务已启动（端口 8080）
- [ ] 接口测试通过

完成以上步骤后，后端服务应该可以正常运行了！

