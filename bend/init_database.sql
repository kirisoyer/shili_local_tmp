-- BBS 论坛数据库初始化脚本
-- 使用方法: mysql -u root -p < init_database.sql

-- 创建数据库
CREATE DATABASE IF NOT EXISTS bbs_forum 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

USE bbs_forum;

-- 用户表
CREATE TABLE IF NOT EXISTS bbs_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(255) NOT NULL COMMENT '密码',
    email VARCHAR(100) COMMENT '邮箱',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 板块表
CREATE TABLE IF NOT EXISTS bbs_board (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    board_name VARCHAR(100) NOT NULL COMMENT '板块名称',
    board_desc VARCHAR(500) COMMENT '板块描述',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='板块表';

-- 帖子表
CREATE TABLE IF NOT EXISTS bbs_post (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL COMMENT '帖子标题',
    content TEXT NOT NULL COMMENT '帖子内容',
    user_id BIGINT NOT NULL COMMENT '发帖用户ID',
    board_id BIGINT NOT NULL COMMENT '所属板块ID',
    view_count INT DEFAULT 0 COMMENT '浏览量',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_board_id (board_id),
    INDEX idx_user_id (user_id),
    INDEX idx_create_time (create_time),
    FOREIGN KEY (user_id) REFERENCES bbs_user(id) ON DELETE CASCADE,
    FOREIGN KEY (board_id) REFERENCES bbs_board(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='帖子表';

-- 评论表
CREATE TABLE IF NOT EXISTS bbs_comment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    post_id BIGINT NOT NULL COMMENT '帖子ID',
    user_id BIGINT NOT NULL COMMENT '评论用户ID',
    content TEXT NOT NULL COMMENT '评论内容',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_post_id (post_id),
    INDEX idx_user_id (user_id),
    FOREIGN KEY (post_id) REFERENCES bbs_post(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES bbs_user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评论表';

-- 插入测试数据
-- 测试用户
INSERT INTO bbs_user (username, password, email) VALUES
('admin', '123456', 'admin@example.com'),
('user1', '123456', 'user1@example.com'),
('user2', '123456', 'user2@example.com')
ON DUPLICATE KEY UPDATE username=username;

-- 测试板块
INSERT INTO bbs_board (board_name, board_desc) VALUES
('技术交流', '技术讨论和分享'),
('灌水区', '闲聊灌水'),
('问题求助', '遇到问题来这里求助'),
('资源分享', '分享学习资源和工具')
ON DUPLICATE KEY UPDATE board_name=board_name;

-- 测试帖子
INSERT INTO bbs_post (title, content, user_id, board_id) VALUES
('欢迎来到论坛', '这是第一个测试帖子，欢迎大家来交流！', 1, 1),
('Java 学习心得', '分享一些 Java 学习经验和技巧，希望对大家有帮助。', 1, 1),
('Spring Boot 入门教程', '详细介绍 Spring Boot 的基本使用方法。', 2, 1),
('今天天气真好', '今天天气不错，适合出去走走。', 2, 2),
('如何学习编程？', '新手求教，如何开始学习编程？', 3, 3)
ON DUPLICATE KEY UPDATE title=title;

-- 查询验证
SELECT '数据库初始化完成！' AS message;
SELECT COUNT(*) AS user_count FROM bbs_user;
SELECT COUNT(*) AS board_count FROM bbs_board;
SELECT COUNT(*) AS post_count FROM bbs_post;

