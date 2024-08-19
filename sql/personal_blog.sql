/*
 Navicat Premium Data Transfer

 Source Server         : 虚拟机MySQL数据库
 Source Server Type    : MySQL
 Source Server Version : 80032
 Source Host           : 192.168.200.129:3306
 Source Schema         : personal_blog

 Target Server Type    : MySQL
 Target Server Version : 80032
 File Encoding         : 65001

 Date: 18/08/2024 11:48:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_about
-- ----------------------------
DROP TABLE IF EXISTS `tb_about`;
CREATE TABLE `tb_about`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '内容',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '关于表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_about
-- ----------------------------
INSERT INTO `tb_about` VALUES (1, '\"# 你好\\n这是关于我\"', '2022-07-24 17:22:13', '2024-07-13 23:16:33');

-- ----------------------------
-- Table structure for tb_article
-- ----------------------------
DROP TABLE IF EXISTS `tb_article`;
CREATE TABLE `tb_article`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '文章id',
  `article_cover` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文章缩略图',
  `article_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文章标题',
  `article_abstract` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文章摘要，如果该字段为空，默认取文章前500个字符作为摘要',
  `article_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文章内容',
  `is_top` tinyint NOT NULL DEFAULT 0 COMMENT '是否置顶 0 否 1 是',
  `is_featured` tinyint NOT NULL DEFAULT 0 COMMENT '是否推荐 0 否 1 是',
  `is_delete` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除 0 否 1 是',
  `user_id` bigint NOT NULL COMMENT '用户Id',
  `category_id` bigint NULL DEFAULT NULL COMMENT '分类id',
  `status` tinyint NOT NULL DEFAULT 3 COMMENT '文章状态 1公开 2  私密 3 草稿',
  `type` tinyint NOT NULL DEFAULT 1 COMMENT '文章类型  1原创 2 转载 3 翻译',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '访问密码',
  `original_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '原文链接',
  `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文章表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_article
-- ----------------------------
INSERT INTO `tb_article` VALUES (2, 'http://localhost:8082/api/v1/admin/blog/articles/526875b3af7439b62d70a52ec60f08c4.jpg', '测试置顶文章', '这是测试文章摘要', '# 这是篇测试置顶文章\n测试文本', 1, 1, 0, 1, 1, 1, 1, NULL, NULL, '2024-08-08 22:41:48', '2024-08-08 22:41:47');
INSERT INTO `tb_article` VALUES (3, 'http://localhost:8082/api/v1/admin/blog/articles/be6eed297734ab1e853a9bdc65e05548.jpg', '文章2', '', '# 这是篇测试推荐文章\n测试文本', 0, 1, 0, 1, 1, 1, 1, NULL, NULL, '2024-07-22 12:54:58', '2024-07-22 12:54:58');
INSERT INTO `tb_article` VALUES (4, 'http://localhost:8082/api/v1/admin/blog/articles/68abd59cf4a5019dd2ddbf3deef19d0e.jpg', '测试文章3', '', '这是第三篇文章', 0, 1, 0, 1, 1, 1, 1, NULL, NULL, '2024-07-22 12:54:46', '2024-07-22 12:54:46');
INSERT INTO `tb_article` VALUES (5, 'http://localhost:8082/api/v1/admin/blog/articles/4d6192e538467d825d4425b0d8f5c64e-2024-08-18T11-40-22.880400500.jpg', '项目中哪些场景可以使用异步', '', '在项目中使用异步编程可以显著提高系统的性能和响应速度，特别是在处理大量 I/O 操作或需要长时间处理的任务时。以下是一些常见的场景，在这些场景中使用异步编程可能会带来显著的优势：\n\n1. 网络请求\n场景: 发起外部 API 调用、HTTP 请求、数据库查询等。\n优势: 异步处理网络请求可以避免线程被阻塞，提高系统并发处理能力。\n2. 文件操作\n场景: 读取或写入大文件、文件上传和下载等。\n优势: 文件操作通常是 I/O 密集型任务，异步处理可以释放主线程去处理其他任务。\n3. 数据库查询\n场景: 复杂的数据库查询、批量数据处理等。\n优势: 异步数据库操作可以避免长时间等待数据库响应，减少应用程序的延迟。\n4. 消息队列处理\n场景: 处理来自 RabbitMQ、Kafka 等消息队列的消息。\n优势: 异步处理消息队列中的消息，能够快速响应和处理高吞吐量的消息流。\n5. 任务调度\n场景: 定时任务、批处理任务、长时间运行的任务。\n优势: 通过异步任务调度，可以将长时间执行的任务放到后台处理，不影响系统的实时响应能力。\n6. 数据处理和计算\n场景: 大数据分析、数据转换和清洗等复杂计算任务。\n优势: 异步处理可以将计算密集型任务放在后台执行，防止阻塞主线程。\n7. 用户交互操作\n场景: Web 应用中的表单提交、按钮点击触发的后台任务等。\n优势: 异步处理用户操作后的后台任务，能够让用户界面保持响应性。\n8. 远程服务调用\n场景: 调用微服务、RPC 调用、远程数据库操作等。\n优势: 异步调用远程服务，避免因为网络延迟或服务不可用导致主线程被阻塞。\n9. 缓存更新\n场景: 更新 Redis、Memcached 等缓存数据。\n优势: 异步更新缓存，保证缓存的及时性，同时不影响主流程的执行。\n10. 日志记录\n场景: 大量日志写入、日志上传到远程服务器。\n优势: 异步写入日志，减少 I/O 操作对主线程的影响。\n通过在这些场景中使用异步编程，可以有效提升应用程序的性能和用户体验，避免因为同步操作导致的性能瓶颈和系统阻塞。\n![20180701 1236 ユウキ 5.jpeg](http://localhost:8082/api/v1/admin/blog/articles/8d9a59bca2c06fa528d29e1474b9ae08-2024-08-18T11-39-15.694900800.jpeg)', 0, 0, 0, 1, 1, 1, 1, NULL, NULL, '2024-08-18 11:40:42', '2024-08-18 11:40:43');

-- ----------------------------
-- Table structure for tb_article_tag
-- ----------------------------
DROP TABLE IF EXISTS `tb_article_tag`;
CREATE TABLE `tb_article_tag`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '文章标签中间表id',
  `article_id` bigint NOT NULL COMMENT '文章Id',
  `tag_id` bigint NOT NULL COMMENT '标签id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `article_id`(`article_id` ASC) USING BTREE,
  INDEX `tag_id`(`tag_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文章标签中间表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_article_tag
-- ----------------------------
INSERT INTO `tb_article_tag` VALUES (21, 4, 1);
INSERT INTO `tb_article_tag` VALUES (22, 3, 1);
INSERT INTO `tb_article_tag` VALUES (30, 2, 1);
INSERT INTO `tb_article_tag` VALUES (32, 5, 1);

-- ----------------------------
-- Table structure for tb_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_category`;
CREATE TABLE `tb_category`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '分类Id',
  `category_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类名',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_category
-- ----------------------------
INSERT INTO `tb_category` VALUES (1, '测试分类', '2024-07-15 23:06:06', NULL);

-- ----------------------------
-- Table structure for tb_comment
-- ----------------------------
DROP TABLE IF EXISTS `tb_comment`;
CREATE TABLE `tb_comment`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '评论Id',
  `parent_id` bigint NULL DEFAULT NULL COMMENT '父评论id',
  `user_id` bigint NOT NULL COMMENT '用户Id',
  `topic_id` bigint NULL DEFAULT NULL COMMENT '评论主题id',
  `comment_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论内容',
  `reply_user_id` bigint NULL DEFAULT NULL COMMENT '回复用户id',
  `type` tinyint NOT NULL COMMENT '评论类型 1.文章 2.留言 3.关于我 4.友链 5.说说',
  `is_delete` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除  0否 1是',
  `is_review` tinyint NOT NULL DEFAULT 1 COMMENT '是否审核',
  `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '评论时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_comment_user`(`user_id` ASC) USING BTREE,
  INDEX `fk_comment_parent`(`parent_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '评论表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_comment
-- ----------------------------
INSERT INTO `tb_comment` VALUES (1, NULL, 4, 68, '测试评论', NULL, 5, 0, 1, '2024-07-22 12:21:00', '2024-07-22 12:21:00');
INSERT INTO `tb_comment` VALUES (2, NULL, 4, NULL, '测试评论', NULL, 3, 0, 1, '2024-07-22 12:39:29', '2024-07-22 12:39:29');
INSERT INTO `tb_comment` VALUES (3, NULL, 4, NULL, '测试留言', NULL, 2, 0, 1, '2024-07-22 12:40:02', '2024-07-22 12:40:02');
INSERT INTO `tb_comment` VALUES (4, NULL, 4, 3, '测试评论', NULL, 1, 0, 0, '2024-08-06 13:53:18', NULL);

-- ----------------------------
-- Table structure for tb_exception_log
-- ----------------------------
DROP TABLE IF EXISTS `tb_exception_log`;
CREATE TABLE `tb_exception_log`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `opt_uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求接口',
  `opt_method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求方式',
  `request_method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求方式',
  `request_param` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求参数',
  `opt_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作描述',
  `exception_info` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '异常信息',
  `ip_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip',
  `ip_source` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip属地',
  `create_time` datetime NOT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 418 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '异常日志表\r\n' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_exception_log
-- ----------------------------

-- ----------------------------
-- Table structure for tb_friend_link
-- ----------------------------
DROP TABLE IF EXISTS `tb_friend_link`;
CREATE TABLE `tb_friend_link`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `link_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '链接名',
  `link_avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '链接头像',
  `link_address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '链接地址',
  `link_intro` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '链接介绍',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_friend_link_user`(`link_name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 48 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '友链表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_friend_link
-- ----------------------------
INSERT INTO `tb_friend_link` VALUES (47, '测试链接', 'null', 'www.baidu.com', '测试', '2024-07-15 21:02:51', NULL);

-- ----------------------------
-- Table structure for tb_image_reference
-- ----------------------------
DROP TABLE IF EXISTS `tb_image_reference`;
CREATE TABLE `tb_image_reference`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `image_url` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '图片路径',
  `is_delete` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '图片引用表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_image_reference
-- ----------------------------
INSERT INTO `tb_image_reference` VALUES (3, 'http://localhost:8082/api/v1/admin/blog/photos/8d9a59bca2c06fa528d29e1474b9ae08-2024-08-11T14-36-11.856403400.jpeg', 0);
INSERT INTO `tb_image_reference` VALUES (7, 'http://localhost:8082/api/v1/admin/blog/photos/526875b3af7439b62d70a52ec60f08c4-2024-08-11T14-56-35.195027600.jpg', 0);
INSERT INTO `tb_image_reference` VALUES (8, 'http://localhost:8082/api/v1/admin/blog/photos/e88a5e47354a6708d62756806679659c-2024-08-11T15-50-22.018152900.jpg', 0);
INSERT INTO `tb_image_reference` VALUES (9, '', 1);
INSERT INTO `tb_image_reference` VALUES (10, 'http://localhost:8082/api/v1/admin/blog/articles/4d6192e538467d825d4425b0d8f5c64e-2024-08-18T11-40-22.880400500.jpg', 0);

-- ----------------------------
-- Table structure for tb_job
-- ----------------------------
DROP TABLE IF EXISTS `tb_job`;
CREATE TABLE `tb_job`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `job_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调用目标字符串',
  `cron_expression` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'cron执行表达式',
  `misfire_policy` tinyint(1) NULL DEFAULT 3 COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  `concurrent` tinyint(1) NULL DEFAULT 1 COMMENT '是否并发执行（0禁止 1允许）',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '状态（0暂停 1正常）',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注信息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 92 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '定时任务调度表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_job
-- ----------------------------
INSERT INTO `tb_job` VALUES (87, '清理临时图片', '文件清理', 'blogQuartz.clearTempImage()', '0 0/15 * * * ?', 0, 1, 1, '2024-08-08 11:21:37', '2024-08-15 10:14:31', '清理图片');
INSERT INTO `tb_job` VALUES (88, '清理未引用图片', '文件清理', 'blogQuartz.clearNotReferenceImage()', '0 5,20,35,50 * * * ?', 0, 1, 1, '2024-08-11 15:48:55', '2024-08-15 10:14:51', '清理未引用图片');
INSERT INTO `tb_job` VALUES (89, '保存每日访问量', '统计数据', 'blogQuartz.saveUniqueView()', '0 0 4 * * ?', 0, 1, 1, '2024-08-15 10:14:08', '2024-08-15 11:52:08', '保存每日访问量');
INSERT INTO `tb_job` VALUES (90, '统计用户地域分布', '统计数据', 'blogQuartz.statisticalUserArea()', '0 0 4 * * ?', 0, 1, 1, '2024-08-15 10:18:21', '2024-08-15 11:51:56', '统计用户地域分布');
INSERT INTO `tb_job` VALUES (91, '刷新全部服务配置', '服务配置', 'blogQuartz.refreshAllConfig()', '* * * * * ?', 0, 0, 0, '2024-08-15 10:25:04', '2024-08-15 11:51:34', '请手动执行本方法，而不是定时执行');

-- ----------------------------
-- Table structure for tb_job_log
-- ----------------------------
DROP TABLE IF EXISTS `tb_job_log`;
CREATE TABLE `tb_job_log`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
  `job_id` int NOT NULL COMMENT '任务ID',
  `job_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调用目标字符串',
  `job_message` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '日志信息',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '执行状态（0正常 1失败）',
  `exception_info` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '异常信息',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `start_time` datetime NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime NULL DEFAULT NULL COMMENT '结束时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6559 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '定时任务调度日志表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_job_log
-- ----------------------------

-- ----------------------------
-- Table structure for tb_menu
-- ----------------------------
DROP TABLE IF EXISTS `tb_menu`;
CREATE TABLE `tb_menu`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '菜单id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单名',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单路径',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单组件',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `order_num` int NOT NULL COMMENT '排序号',
  `parent_id` int NULL DEFAULT NULL COMMENT '父菜单id(默认为0，表示顶级菜单)',
  `is_hidden` tinyint NOT NULL DEFAULT 0 COMMENT '是否隐藏 0表示否。1表示是',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `parent_id`(`parent_id` ASC, `is_hidden` ASC) USING BTREE COMMENT '父菜单id与菜单状态的组合索引',
  INDEX `parent_id_2`(`parent_id` ASC) USING BTREE COMMENT '父菜单id索引',
  INDEX `status`(`is_hidden` ASC) USING BTREE COMMENT '菜单状态索引'
) ENGINE = InnoDB AUTO_INCREMENT = 226 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_menu
-- ----------------------------
INSERT INTO `tb_menu` VALUES (1, '首页', '/', '/home/Home.vue', 'el-icon-myshouye', '2021-01-26 17:06:51', '2022-07-27 16:33:11', 1, NULL, 0);
INSERT INTO `tb_menu` VALUES (2, '文章管理', '/article-submenu', 'Layout', 'el-icon-mywenzhang-copy', '2021-01-25 20:43:07', '2022-07-27 16:32:55', 2, NULL, 0);
INSERT INTO `tb_menu` VALUES (3, '消息管理', '/message-submenu', 'Layout', 'el-icon-myxiaoxi', '2021-01-25 20:44:17', '2022-07-27 16:32:57', 3, NULL, 0);
INSERT INTO `tb_menu` VALUES (4, '系统管理', '/system-submenu', 'Layout', 'el-icon-myshezhi', '2021-01-25 20:45:57', '2021-01-25 20:45:59', 5, NULL, 0);
INSERT INTO `tb_menu` VALUES (5, '个人中心', '/setting', '/setting/Setting.vue', 'el-icon-myuser', '2021-01-26 17:22:38', '2021-01-26 17:22:41', 7, NULL, 0);
INSERT INTO `tb_menu` VALUES (6, '发布文章', '/articles', '/article/Article.vue', 'el-icon-myfabiaowenzhang', '2021-01-26 14:30:48', '2021-01-26 14:30:51', 1, 2, 0);
INSERT INTO `tb_menu` VALUES (7, '修改文章', '/articles/*', '/article/Article.vue', 'el-icon-myfabiaowenzhang', '2024-08-11 14:57:26', '2024-08-11 14:57:26', 2, 2, 0);
INSERT INTO `tb_menu` VALUES (8, '文章列表', '/article-list', '/article/ArticleList.vue', 'el-icon-mywenzhangliebiao', '2021-01-26 14:32:13', '2021-01-26 14:32:16', 3, 2, 0);
INSERT INTO `tb_menu` VALUES (9, '分类管理', '/categories', '/category/Category.vue', 'el-icon-myfenlei', '2021-01-26 14:33:42', '2021-01-26 14:33:43', 4, 2, 0);
INSERT INTO `tb_menu` VALUES (10, '标签管理', '/tags', '/tag/Tag.vue', 'el-icon-myicontag', '2021-01-26 14:34:33', '2021-01-26 14:34:36', 5, 2, 0);
INSERT INTO `tb_menu` VALUES (11, '评论管理', '/comments', '/comment/Comment.vue', 'el-icon-mypinglunzu', '2021-01-26 14:35:31', '2021-01-26 14:35:34', 1, 3, 0);
INSERT INTO `tb_menu` VALUES (13, '用户列表', '/users', '/user/User.vue', 'el-icon-myyonghuliebiao', '2021-01-26 14:38:09', '2021-01-26 14:38:12', 1, 202, 0);
INSERT INTO `tb_menu` VALUES (14, '角色管理', '/roles', '/role/Role.vue', 'el-icon-myjiaoseliebiao', '2021-01-26 14:39:01', '2021-01-26 14:39:03', 2, 213, 0);
INSERT INTO `tb_menu` VALUES (15, '接口管理', '/resources', '/resource/Resource.vue', 'el-icon-myjiekouguanli', '2021-01-26 14:40:14', '2021-08-07 20:00:28', 2, 213, 0);
INSERT INTO `tb_menu` VALUES (16, '菜单管理', '/menus', '/menu/Menu.vue', 'el-icon-mycaidan', '2021-01-26 14:40:54', '2021-08-07 10:18:49', 2, 213, 0);
INSERT INTO `tb_menu` VALUES (17, '友链管理', '/links', '/friendLink/FriendLink.vue', 'el-icon-mydashujukeshihuaico-', '2021-01-26 14:41:35', '2021-01-26 14:41:37', 3, 4, 0);
INSERT INTO `tb_menu` VALUES (18, '关于我', '/about', '/about/About.vue', 'el-icon-myguanyuwo', '2021-01-26 14:42:05', '2021-01-26 14:42:10', 4, 4, 0);
INSERT INTO `tb_menu` VALUES (19, '日志管理', '/log-submenu', 'Layout', 'el-icon-myguanyuwo', '2021-01-31 21:33:56', '2021-01-31 21:33:59', 6, NULL, 0);
INSERT INTO `tb_menu` VALUES (20, '操作日志', '/operation/log', '/log/OperationLog.vue', 'el-icon-myguanyuwo', '2021-01-31 15:53:21', '2022-07-28 10:51:28', 1, 19, 0);
INSERT INTO `tb_menu` VALUES (201, '在线用户', '/online/users', '/user/Online.vue', 'el-icon-myyonghuliebiao', '2021-02-05 14:59:51', '2021-02-05 14:59:53', 7, 202, 0);
INSERT INTO `tb_menu` VALUES (202, '用户管理', '/users-submenu', 'Layout', 'el-icon-myyonghuliebiao', '2021-02-06 23:44:59', '2022-07-27 16:32:59', 4, NULL, 0);
INSERT INTO `tb_menu` VALUES (205, '相册管理', '/album-submenu', 'Layout', 'el-icon-myimage-fill', '2021-08-03 15:10:54', '2021-08-07 20:02:06', 5, NULL, 0);
INSERT INTO `tb_menu` VALUES (206, '相册列表', '/albums', '/album/Album.vue', 'el-icon-myzhaopian', '2021-08-03 20:29:19', '2021-08-04 11:45:47', 1, 205, 0);
INSERT INTO `tb_menu` VALUES (208, '照片管理', '/albums/:albumId', '/album/Photo.vue', 'el-icon-myzhaopian', '2024-08-11 14:59:04', '2024-08-11 14:59:04', 1, 205, 1);
INSERT INTO `tb_menu` VALUES (209, '定时任务', '/quartz', '/quartz/Quartz.vue', 'el-icon-myyemianpeizhi', '2021-08-04 11:36:27', '2021-08-07 20:01:26', 2, 4, 0);
INSERT INTO `tb_menu` VALUES (210, '照片回收站', '/photos/delete', '/album/Delete.vue', 'el-icon-myhuishouzhan', '2024-08-11 14:57:19', '2024-08-11 14:57:19', 3, 205, 0);
INSERT INTO `tb_menu` VALUES (213, '权限管理', '/permission-submenu', 'Layout', 'el-icon-mydaohanglantubiao_quanxianguanli', '2021-08-07 19:56:55', '2021-08-07 19:59:40', 4, NULL, 0);
INSERT INTO `tb_menu` VALUES (214, '网站管理', '/website', '/website/Website.vue', 'el-icon-myxitong', '2021-08-07 20:06:41', NULL, 1, 4, 0);
INSERT INTO `tb_menu` VALUES (220, '定时任务日志', '/quartz/log/*', '/log/QuartzLog.vue', 'el-icon-myguanyuwo', '2024-08-15 12:37:53', '2024-08-15 12:37:53', 2, 19, 0);
INSERT INTO `tb_menu` VALUES (221, '说说管理', '/talk-submenu', 'Layout', 'el-icon-mypinglun', '2022-08-15 17:27:10', '2022-08-15 17:27:39', 3, NULL, 0);
INSERT INTO `tb_menu` VALUES (222, '说说列表', '/talk-list', '/talk/TalkList.vue', 'el-icon-myiconfontdongtaidianji', '2022-08-15 17:29:05', NULL, 1, 221, 0);
INSERT INTO `tb_menu` VALUES (223, '发布说说', '/talks', '/talk/Talk.vue', 'el-icon-myfabusekuai', '2022-08-15 17:34:26', '2022-08-16 16:06:04', 2, 221, 0);
INSERT INTO `tb_menu` VALUES (224, '修改说说', '/talks/:talkId', '/talk/Talk.vue', 'el-icon-myfabusekuai', '2024-07-14 14:56:13', '2024-07-14 14:56:14', 3, 221, 0);
INSERT INTO `tb_menu` VALUES (225, '异常日志', '/exception/log', '/log/ExceptionLog.vue', 'el-icon-myguanyuwo', '2022-08-25 13:40:08', '2022-08-25 13:40:31', 1, 19, 0);

-- ----------------------------
-- Table structure for tb_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `tb_operation_log`;
CREATE TABLE `tb_operation_log`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `opt_module` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作模块',
  `opt_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作路径',
  `opt_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作类型',
  `opt_method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作方法',
  `opt_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作描述',
  `request_method` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '请求方法',
  `request_param` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求参数',
  `response_data` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '响应数据',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
  `ip_address` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户登录ip',
  `ip_source` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'ip属地',
  `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '发生时间',
  `duration` bigint NULL DEFAULT NULL COMMENT '耗时',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 527 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '操作日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_operation_log
-- ----------------------------
INSERT INTO `tb_operation_log` VALUES (524, '操作日志表 前端控制器', '/admin/operation/log/clean', 'DELETE', 'com.gewuyou.blog.admin.controller.OperationLogController.cleanOperationLogs', '清空操作日志', 'DELETE', '[]', NULL, 1, 'admin', '0:0:0:0:0:0:0:1', '', '2024-08-18 11:48:24', NULL, '2024-08-18T11:48:24.209695800', 5);
INSERT INTO `tb_operation_log` VALUES (525, '异常日志表	 前端控制器', '/admin/exception/log/clean', 'DELETE', 'com.gewuyou.blog.admin.controller.ExceptionLogController.cleanExceptionLogs', '清空异常日志', 'DELETE', '[]', NULL, 1, 'admin', '0:0:0:0:0:0:0:1', '', '2024-08-18 11:48:27', NULL, '2024-08-18T11:48:27.293949400', 25);
INSERT INTO `tb_operation_log` VALUES (526, '定时任务调度日志表 前端控制器', '/admin/jobLog/clean', 'DELETE', 'com.gewuyou.blog.admin.controller.JobLogController.cleanJobLogs', '清空定时任务日志', 'DELETE', '[]', NULL, 1, 'admin', '0:0:0:0:0:0:0:1', '', '2024-08-18 11:48:31', NULL, '2024-08-18T11:48:30.585971400', 4);

-- ----------------------------
-- Table structure for tb_photo
-- ----------------------------
DROP TABLE IF EXISTS `tb_photo`;
CREATE TABLE `tb_photo`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `album_id` int NOT NULL COMMENT '相册id',
  `photo_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '照片名',
  `photo_desc` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '照片描述',
  `photo_src` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '照片地址',
  `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 80 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '照片' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_photo
-- ----------------------------
INSERT INTO `tb_photo` VALUES (78, 14, '1822527520636481538', NULL, 'http://localhost:8082/api/v1/admin/blog/photos/526875b3af7439b62d70a52ec60f08c4-2024-08-11T14-56-35.195027600.jpg', 0, '2024-08-11 14:56:37', NULL);
INSERT INTO `tb_photo` VALUES (79, 14, '1822541053432508417', NULL, 'http://localhost:8082/api/v1/admin/blog/photos/e88a5e47354a6708d62756806679659c-2024-08-11T15-50-22.018152900.jpg', 0, '2024-08-11 15:50:23', NULL);

-- ----------------------------
-- Table structure for tb_photo_album
-- ----------------------------
DROP TABLE IF EXISTS `tb_photo_album`;
CREATE TABLE `tb_photo_album`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `album_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '相册名',
  `album_desc` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '相册描述',
  `album_cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '相册封面',
  `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态值 1公开 2私密',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '相册' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_photo_album
-- ----------------------------
INSERT INTO `tb_photo_album` VALUES (14, '测试相册', '测试', 'http://localhost:8082/api/v1/admin/blog/photos/8d9a59bca2c06fa528d29e1474b9ae08-2024-08-11T14-36-11.856403400.jpeg', 0, 1, '2024-08-11 14:36:14', '2024-08-11 14:55:50');

-- ----------------------------
-- Table structure for tb_resource
-- ----------------------------
DROP TABLE IF EXISTS `tb_resource`;
CREATE TABLE `tb_resource`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '资源id',
  `resource_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '资源名称',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限路径',
  `request_method` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求方法：GET, POST, PUT, DELETE等',
  `parent_id` int NULL DEFAULT NULL COMMENT '父模块id',
  `is_anonymous` tinyint NOT NULL DEFAULT 0 COMMENT '是否匿名访问 0表示否 1表示是',
  `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `name`(`resource_name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1230 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '资源表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_resource
-- ----------------------------
INSERT INTO `tb_resource` VALUES (1050, '博客信息', NULL, NULL, NULL, 0, '2024-06-02 22:53:54', NULL);
INSERT INTO `tb_resource` VALUES (1051, '分类模块', NULL, NULL, NULL, 0, '2022-08-19 22:26:21', NULL);
INSERT INTO `tb_resource` VALUES (1052, '友链模块', NULL, NULL, NULL, 0, '2022-08-19 22:26:21', NULL);
INSERT INTO `tb_resource` VALUES (1053, '定时任务日志模块', NULL, NULL, NULL, 0, '2022-08-19 22:26:21', NULL);
INSERT INTO `tb_resource` VALUES (1054, '定时任务模块', NULL, NULL, NULL, 0, '2022-08-19 22:26:21', NULL);
INSERT INTO `tb_resource` VALUES (1055, '异常处理模块', NULL, NULL, NULL, 0, '2022-08-19 22:26:21', NULL);
INSERT INTO `tb_resource` VALUES (1056, '操作日志模块', NULL, NULL, NULL, 0, '2022-08-19 22:26:21', NULL);
INSERT INTO `tb_resource` VALUES (1057, '文章模块', NULL, NULL, NULL, 0, '2022-08-19 22:26:21', NULL);
INSERT INTO `tb_resource` VALUES (1058, '标签模块', NULL, NULL, NULL, 0, '2022-08-19 22:26:21', NULL);
INSERT INTO `tb_resource` VALUES (1059, '照片模块', NULL, NULL, NULL, 0, '2022-08-19 22:26:21', NULL);
INSERT INTO `tb_resource` VALUES (1060, '用户信息模块', NULL, NULL, NULL, 0, '2022-08-19 22:26:21', NULL);
INSERT INTO `tb_resource` VALUES (1061, '用户账号模块', NULL, NULL, NULL, 0, '2022-08-19 22:26:21', NULL);
INSERT INTO `tb_resource` VALUES (1062, '相册模块', NULL, NULL, NULL, 0, '2022-08-19 22:26:21', NULL);
INSERT INTO `tb_resource` VALUES (1063, '菜单模块', NULL, NULL, NULL, 0, '2022-08-19 22:26:21', NULL);
INSERT INTO `tb_resource` VALUES (1064, '角色模块', NULL, NULL, NULL, 0, '2022-08-19 22:26:21', NULL);
INSERT INTO `tb_resource` VALUES (1065, '评论模块', NULL, NULL, NULL, 0, '2022-08-19 22:26:21', NULL);
INSERT INTO `tb_resource` VALUES (1066, '说说模块', NULL, NULL, NULL, 0, '2022-08-19 22:26:21', NULL);
INSERT INTO `tb_resource` VALUES (1067, '资源模块', NULL, NULL, NULL, 0, '2022-08-19 22:26:21', NULL);
INSERT INTO `tb_resource` VALUES (1068, '获取系统信息', '/admin/system', 'GET', 1050, 1, '2024-07-18 15:52:45', '2024-07-18 15:52:45');
INSERT INTO `tb_resource` VALUES (1069, '查看关于我信息', '/server/about', 'GET', 1050, 1, '2024-06-02 23:03:53', '2022-08-19 22:26:57');
INSERT INTO `tb_resource` VALUES (1070, '获取系统后台信息', '/admin', 'GET', 1050, 0, '2022-08-19 22:26:22', NULL);
INSERT INTO `tb_resource` VALUES (1071, '修改关于我信息', '/admin/about', 'PUT', 1050, 0, '2022-08-19 22:26:22', NULL);
INSERT INTO `tb_resource` VALUES (1072, '获取后台文章', '/admin/article/list', 'GET', 1057, 0, '2024-06-10 22:32:51', NULL);
INSERT INTO `tb_resource` VALUES (1073, '保存和修改文章', '/admin/article', 'POST', 1057, 0, '2024-06-10 22:32:58', NULL);
INSERT INTO `tb_resource` VALUES (1074, '删除或者恢复文章', '/admin/article', 'PUT', 1057, 0, '2024-06-10 22:33:28', NULL);
INSERT INTO `tb_resource` VALUES (1075, '物理删除文章', '/admin/article', 'DELETE', 1057, 0, '2024-06-10 22:33:44', NULL);
INSERT INTO `tb_resource` VALUES (1076, '导出文章', '/admin/article/export', 'POST', 1057, 0, '2024-06-10 22:33:47', NULL);
INSERT INTO `tb_resource` VALUES (1077, '上传文章图片', '/admin/article/images', 'POST', 1057, 0, '2024-06-10 22:34:04', NULL);
INSERT INTO `tb_resource` VALUES (1078, '导入文章', '/admin/article/import', 'POST', 1057, 0, '2024-06-10 22:34:32', NULL);
INSERT INTO `tb_resource` VALUES (1079, '修改文章是否置顶和推荐', '/admin/articles/top-and-featured', 'PUT', 1057, 0, '2024-06-10 22:34:38', NULL);
INSERT INTO `tb_resource` VALUES (1080, '根据id查看后台文章', '/admin/article/*', 'GET', 1057, 0, '2024-06-10 22:34:46', NULL);
INSERT INTO `tb_resource` VALUES (1081, '查看后台分类列表', '/admin/category/list', 'GET', 1051, 0, '2024-06-10 22:35:41', NULL);
INSERT INTO `tb_resource` VALUES (1082, '添加或修改分类', '/admin/category', 'POST', 1051, 0, '2024-06-10 22:35:44', NULL);
INSERT INTO `tb_resource` VALUES (1083, '删除分类', '/admin/category', 'DELETE', 1051, 0, '2024-06-10 22:35:47', NULL);
INSERT INTO `tb_resource` VALUES (1084, '搜索文章分类', '/admin/category/search', 'GET', 1051, 0, '2024-06-10 22:35:51', NULL);
INSERT INTO `tb_resource` VALUES (1085, '查询后台评论', '/admin/comment/list', 'GET', 1065, 0, '2024-06-10 22:36:25', NULL);
INSERT INTO `tb_resource` VALUES (1086, '删除评论', '/admin/comment', 'DELETE', 1065, 0, '2024-06-10 22:36:18', NULL);
INSERT INTO `tb_resource` VALUES (1087, '审核评论', '/admin/comment/review', 'PUT', 1065, 0, '2024-07-22 12:19:11', '2024-07-22 12:19:11');
INSERT INTO `tb_resource` VALUES (1088, '上传博客配置图片', '/admin/config/images', 'POST', 1050, 0, '2024-06-10 22:38:36', NULL);
INSERT INTO `tb_resource` VALUES (1089, '获取定时任务的日志列表', '/admin/jobLog/list', 'GET', 1053, 0, '2024-08-08 12:21:37', '2024-08-08 12:21:37');
INSERT INTO `tb_resource` VALUES (1090, '删除定时任务的日志', '/admin/jobLog', 'DELETE', 1053, 0, '2024-08-08 12:21:48', '2024-08-08 12:21:47');
INSERT INTO `tb_resource` VALUES (1091, '清除定时任务的日志', '/admin/jobLog/clean', 'DELETE', 1053, 0, '2024-08-08 12:21:53', '2024-08-08 12:21:52');
INSERT INTO `tb_resource` VALUES (1092, '获取定时任务日志的所有组名', '/admin/jobLog/groups', 'GET', 1053, 0, '2024-08-08 12:21:58', '2024-08-08 12:21:58');
INSERT INTO `tb_resource` VALUES (1093, '获取任务列表', '/admin/job/list', 'GET', 1054, 0, '2024-07-07 16:38:02', NULL);
INSERT INTO `tb_resource` VALUES (1094, '添加定时任务', '/admin/job', 'POST', 1054, 0, '2024-06-10 22:39:21', NULL);
INSERT INTO `tb_resource` VALUES (1095, '修改定时任务', '/admin/job', 'PUT', 1054, 0, '2024-06-10 22:39:21', NULL);
INSERT INTO `tb_resource` VALUES (1096, '删除定时任务', '/admin/job', 'DELETE', 1054, 0, '2024-06-10 22:39:24', NULL);
INSERT INTO `tb_resource` VALUES (1097, '获取所有job分组', '/admin/job/groups', 'GET', 1054, 0, '2024-06-10 22:39:39', NULL);
INSERT INTO `tb_resource` VALUES (1098, '执行某个任务', '/admin/job/run', 'PUT', 1054, 0, '2024-06-10 22:40:00', NULL);
INSERT INTO `tb_resource` VALUES (1099, '更改任务的状态', '/admin/job/status', 'PUT', 1054, 0, '2024-06-10 22:40:04', NULL);
INSERT INTO `tb_resource` VALUES (1100, '根据id获取任务', '/admin/job/*', 'GET', 1054, 0, '2024-06-10 22:40:08', NULL);
INSERT INTO `tb_resource` VALUES (1101, '查看后台友链列表', '/admin/link/list', 'GET', 1052, 0, '2024-06-10 22:49:30', NULL);
INSERT INTO `tb_resource` VALUES (1102, '保存或修改友链', '/admin/link', 'POST', 1052, 0, '2024-06-10 22:49:02', NULL);
INSERT INTO `tb_resource` VALUES (1103, '删除友链', '/admin/link', 'DELETE', 1052, 0, '2024-06-10 22:49:25', NULL);
INSERT INTO `tb_resource` VALUES (1104, '查看菜单列表', '/admin/menu', 'GET', 1063, 0, '2024-06-10 22:48:06', NULL);
INSERT INTO `tb_resource` VALUES (1105, '新增或修改菜单', '/admin/menu', 'POST', 1063, 0, '2024-06-10 22:48:07', NULL);
INSERT INTO `tb_resource` VALUES (1106, '修改目录是否隐藏', '/admin/menu/isHidden', 'PUT', 1063, 0, '2024-06-10 22:48:09', NULL);
INSERT INTO `tb_resource` VALUES (1107, '删除菜单', '/admin/menu/*', 'DELETE', 1063, 0, '2024-06-10 22:48:11', NULL);
INSERT INTO `tb_resource` VALUES (1108, '查看操作日志', '/admin/operation/log', 'GET', 1056, 0, '2024-06-10 22:50:19', NULL);
INSERT INTO `tb_resource` VALUES (1109, '删除操作日志', '/admin/operation/log', 'DELETE', 1056, 0, '2024-06-10 22:50:21', NULL);
INSERT INTO `tb_resource` VALUES (1110, '根据相册id获取照片列表', '/admin/photo/list', 'GET', 1059, 0, '2024-07-15 15:28:23', '2024-07-15 15:28:22');
INSERT INTO `tb_resource` VALUES (1111, '保存照片', '/admin/photo', 'POST', 1059, 0, '2024-06-10 22:50:24', NULL);
INSERT INTO `tb_resource` VALUES (1112, '更新照片信息', '/admin/photo', 'PUT', 1059, 0, '2024-06-10 22:50:24', NULL);
INSERT INTO `tb_resource` VALUES (1113, '删除照片', '/admin/photo', 'DELETE', 1059, 0, '2024-06-10 22:51:51', NULL);
INSERT INTO `tb_resource` VALUES (1114, '移动照片相册', '/admin/photo/album', 'PUT', 1059, 0, '2024-06-10 22:52:45', NULL);
INSERT INTO `tb_resource` VALUES (1115, '查看后台相册列表', '/admin/photo/album', 'GET', 1062, 0, '2024-07-18 14:59:10', '2024-07-18 14:59:11');
INSERT INTO `tb_resource` VALUES (1116, '保存或更新相册', '/admin/photo/album', 'POST', 1062, 0, '2024-06-10 22:53:22', NULL);
INSERT INTO `tb_resource` VALUES (1117, '上传相册封面', '/admin/photo/album/upload', 'POST', 1062, 0, '2024-07-15 12:15:26', '2024-07-15 12:15:26');
INSERT INTO `tb_resource` VALUES (1118, '获取后台相册列表信息', '/admin/photo/album/info', 'GET', 1062, 0, '2024-06-10 22:53:27', NULL);
INSERT INTO `tb_resource` VALUES (1119, '根据id删除相册', '/admin/photo/album/*', 'DELETE', 1062, 0, '2024-06-10 22:53:29', NULL);
INSERT INTO `tb_resource` VALUES (1120, '根据id获取后台相册信息', '/admin/photo/album/info/*', 'GET', 1062, 0, '2024-06-10 22:56:16', NULL);
INSERT INTO `tb_resource` VALUES (1121, '更新照片删除状态', '/admin/photo/delete', 'PUT', 1059, 0, '2024-06-10 22:54:02', NULL);
INSERT INTO `tb_resource` VALUES (1122, '查看资源列表', '/admin/resource/list', 'GET', 1067, 0, '2024-06-10 22:56:48', NULL);
INSERT INTO `tb_resource` VALUES (1123, '新增或修改资源', '/admin/resource', 'POST', 1067, 0, '2024-06-10 22:56:43', NULL);
INSERT INTO `tb_resource` VALUES (1124, '删除资源', '/admin/resource/*', 'DELETE', 1067, 0, '2024-06-10 22:56:52', NULL);
INSERT INTO `tb_resource` VALUES (1125, '保存或更新角色', '/admin/role', 'POST', 1064, 0, '2022-08-19 22:26:22', NULL);
INSERT INTO `tb_resource` VALUES (1126, '查看角色菜单选项', '/admin/menu/role', 'GET', 1063, 0, '2024-06-10 23:00:07', NULL);
INSERT INTO `tb_resource` VALUES (1127, '查看角色资源选项', '/admin/resource/role', 'GET', 1067, 0, '2024-06-10 23:00:20', NULL);
INSERT INTO `tb_resource` VALUES (1128, '查询角色列表', '/admin/role/list', 'GET', 1064, 0, '2024-06-10 23:01:01', NULL);
INSERT INTO `tb_resource` VALUES (1129, '删除角色', '/admin/role', 'DELETE', 1064, 0, '2024-06-10 23:00:38', NULL);
INSERT INTO `tb_resource` VALUES (1130, '查询后台标签列表', '/admin/tag/list', 'GET', 1058, 0, '2024-06-10 23:01:54', NULL);
INSERT INTO `tb_resource` VALUES (1131, '添加或修改标签', '/admin/tag', 'POST', 1058, 0, '2024-06-10 23:01:18', NULL);
INSERT INTO `tb_resource` VALUES (1132, '删除标签', '/admin/tag', 'DELETE', 1058, 0, '2024-06-10 23:01:19', NULL);
INSERT INTO `tb_resource` VALUES (1133, '搜索文章标签', '/admin/tag/search', 'GET', 1058, 0, '2024-06-10 23:01:21', NULL);
INSERT INTO `tb_resource` VALUES (1134, '查看后台说说', '/admin/talk/list', 'GET', 1066, 0, '2024-06-23 21:40:20', NULL);
INSERT INTO `tb_resource` VALUES (1135, '保存或修改说说', '/admin/talk', 'POST', 1066, 0, '2024-06-23 21:40:36', NULL);
INSERT INTO `tb_resource` VALUES (1136, '删除说说', '/admin/talk', 'DELETE', 1066, 0, '2024-06-23 21:40:41', NULL);
INSERT INTO `tb_resource` VALUES (1137, '上传说说图片', '/admin/talk/images', 'POST', 1066, 0, '2024-06-23 21:40:47', NULL);
INSERT INTO `tb_resource` VALUES (1138, '根据id查看后台说说', '/admin/talk/*', 'GET', 1066, 1, '2024-06-23 21:40:51', '2022-08-19 22:33:52');
INSERT INTO `tb_resource` VALUES (1139, '查看当前用户菜单', '/admin/menu/user', 'GET', 1063, 0, '2024-06-23 21:44:57', NULL);
INSERT INTO `tb_resource` VALUES (1140, '查询后台用户列表', '/admin/user', 'GET', 1061, 0, '2024-06-23 21:44:59', NULL);
INSERT INTO `tb_resource` VALUES (1141, '获取用户区域分布', '/admin/user/area', 'GET', 1061, 0, '2024-06-23 21:45:20', NULL);
INSERT INTO `tb_resource` VALUES (1142, '修改用户禁用状态', '/server/user/disable', 'PUT', 1060, 0, '2024-07-06 20:34:30', NULL);
INSERT INTO `tb_resource` VALUES (1143, '查看在线用户', '/server/user/online', 'GET', 1060, 0, '2024-07-06 20:34:33', NULL);
INSERT INTO `tb_resource` VALUES (1144, '修改管理员密码', '/admin/user/password', 'PUT', 1061, 0, '2024-06-23 22:30:44', NULL);
INSERT INTO `tb_resource` VALUES (1145, '查询用户角色选项', '/admin/role/user/list', 'GET', 1064, 0, '2024-06-23 22:32:09', NULL);
INSERT INTO `tb_resource` VALUES (1146, '修改用户角色', '/admin/user/role', 'PUT', 1060, 0, '2024-07-06 20:17:45', NULL);
INSERT INTO `tb_resource` VALUES (1147, '下线用户', '/server/user/*/online', 'DELETE', 1060, 0, '2024-07-06 20:34:37', NULL);
INSERT INTO `tb_resource` VALUES (1148, '获取网站配置', '/server/website/config', 'GET', 1050, 1, '2024-07-18 15:25:41', '2024-07-18 15:25:41');
INSERT INTO `tb_resource` VALUES (1149, '更新网站配置', '/admin/website/config', 'PUT', 1050, 0, '2022-08-19 22:26:22', NULL);
INSERT INTO `tb_resource` VALUES (1150, '根据相册id查看照片列表', '/server/photo/*', 'GET', 1059, 1, '2024-07-06 20:34:44', '2022-08-19 22:27:54');
INSERT INTO `tb_resource` VALUES (1151, '获取所有文章归档', '/server/article/list/archives/', 'GET', 1057, 1, '2024-07-06 20:35:35', '2022-08-19 22:27:35');
INSERT INTO `tb_resource` VALUES (1152, '获取所有文章', '/server/article/list', 'GET', 1057, 1, '2024-07-06 20:41:48', '2022-08-19 22:27:37');
INSERT INTO `tb_resource` VALUES (1153, '根据分类id获取文章', '/server/article/list/categoryId', 'GET', 1057, 1, '2024-07-19 12:51:42', '2024-07-19 12:51:43');
INSERT INTO `tb_resource` VALUES (1154, '搜索文章', '/server/articles/search', 'GET', 1057, 1, '2024-07-06 20:41:58', '2022-08-19 22:27:40');
INSERT INTO `tb_resource` VALUES (1155, '根据标签id获取文章', '/server/article/list/tagId', 'GET', 1057, 1, '2024-08-02 11:34:58', '2024-08-02 11:34:58');
INSERT INTO `tb_resource` VALUES (1156, '获取置顶和推荐文章', '/server/article/top-and-featured', 'GET', 1057, 1, '2024-07-18 15:24:20', '2024-07-18 15:24:20');
INSERT INTO `tb_resource` VALUES (1157, '根据id获取文章', '/server/article/*', 'GET', 1057, 1, '2024-07-19 15:07:14', '2024-07-19 15:07:14');
INSERT INTO `tb_resource` VALUES (1158, '/处理GlobalException', '/global-exception', 'GET', 1055, 0, '2024-07-06 20:43:30', NULL);
INSERT INTO `tb_resource` VALUES (1159, '/处理GlobalException', '/global-exception', 'HEAD', 1055, 0, '2024-07-06 20:44:01', NULL);
INSERT INTO `tb_resource` VALUES (1160, '/处理GlobalException', '/global-exception', 'POST', 1055, 0, '2024-07-06 20:43:59', NULL);
INSERT INTO `tb_resource` VALUES (1161, '/处理GlobalException', '/global-exception', 'PUT', 1055, 0, '2024-07-06 20:44:03', NULL);
INSERT INTO `tb_resource` VALUES (1162, '/处理GlobalException', '/global-exception', 'DELETE', 1055, 0, '2024-07-06 20:44:05', NULL);
INSERT INTO `tb_resource` VALUES (1163, '/处理GlobalException', '/global-exception', 'OPTIONS', 1055, 0, '2024-07-06 20:44:06', NULL);
INSERT INTO `tb_resource` VALUES (1164, '/处理GlobalException', '/global-exception', 'PATCH', 1055, 0, '2024-07-06 20:44:08', NULL);
INSERT INTO `tb_resource` VALUES (1165, '获取所有分类', '/server/category/list', 'GET', 1051, 1, '2024-07-06 20:50:14', '2022-08-19 22:27:05');
INSERT INTO `tb_resource` VALUES (1166, '获取评论', '/server/comment/list', 'GET', 1065, 1, '2024-07-06 20:53:02', '2022-08-19 22:33:50');
INSERT INTO `tb_resource` VALUES (1167, '添加评论', '/server/comment', 'POST', 1065, 0, '2024-07-22 11:35:33', '2024-07-22 11:35:33');
INSERT INTO `tb_resource` VALUES (1168, '获取前七个评论', '/server/comment/topSix', 'GET', 1065, 1, '2024-07-06 20:53:59', '2022-08-19 22:33:44');
INSERT INTO `tb_resource` VALUES (1169, '查看友链列表', '/server/link/list', 'GET', 1052, 1, '2024-07-21 09:59:51', '2024-07-21 09:59:52');
INSERT INTO `tb_resource` VALUES (1170, '获取相册列表', '/admin/photo/album/list', 'GET', 1062, 1, '2024-07-06 21:02:32', '2022-08-19 22:28:25');
INSERT INTO `tb_resource` VALUES (1171, '上报访问信息', '/admin/report', 'POST', 1050, 1, '2024-07-13 13:18:12', '2024-07-13 13:18:12');
INSERT INTO `tb_resource` VALUES (1172, '获取所有标签', '/server/tag/list', 'GET', 1058, 1, '2024-07-06 21:04:24', '2022-08-19 22:31:23');
INSERT INTO `tb_resource` VALUES (1173, '获取前十个标签', '/server/tag/topTen', 'GET', 1058, 1, '2024-07-06 21:05:25', '2022-08-19 22:31:27');
INSERT INTO `tb_resource` VALUES (1174, '查看说说列表', '/server/talk', 'GET', 1066, 1, '2024-07-06 21:05:49', '2022-08-19 22:28:38');
INSERT INTO `tb_resource` VALUES (1175, '根据id查看说说', '/server/talk/*', 'GET', 1066, 1, '2024-07-06 21:05:55', '2022-08-19 22:28:38');
INSERT INTO `tb_resource` VALUES (1176, '更新用户头像', '/admin/user-info/avatar', 'POST', 1060, 0, '2024-07-22 11:00:39', '2024-07-22 11:00:39');
INSERT INTO `tb_resource` VALUES (1177, '发送邮箱验证码', '/admin/user/code', 'POST', 1061, 1, '2024-07-20 14:05:18', '2024-07-20 14:05:18');
INSERT INTO `tb_resource` VALUES (1178, '绑定用户邮箱', '/server/user-info/email', 'PUT', 1060, 1, '2024-07-06 21:09:23', '2022-08-19 22:28:06');
INSERT INTO `tb_resource` VALUES (1179, '更新用户信息', '/server/user-info', 'PUT', 1060, 0, '2024-07-22 11:02:09', '2024-07-22 11:02:09');
INSERT INTO `tb_resource` VALUES (1180, '根据id获取用户信息', '/server/user-info/*', 'GET', 1060, 1, '2024-07-06 21:09:40', '2022-08-19 22:28:07');
INSERT INTO `tb_resource` VALUES (1181, '用户登出', '/admin/user/logout', 'POST', 1061, 0, '2024-07-20 22:57:48', '2024-07-20 22:57:49');
INSERT INTO `tb_resource` VALUES (1182, 'qq登录', '/users/oauth/qq', 'POST', 1061, 1, '2022-08-19 22:26:22', '2022-08-19 22:28:16');
INSERT INTO `tb_resource` VALUES (1183, '修改密码', '/admin/user/password', 'PUT', 1061, 1, '2024-07-06 21:09:49', '2022-08-19 22:28:18');
INSERT INTO `tb_resource` VALUES (1184, '用户注册', '/admin/user/register', 'POST', 1061, 1, '2024-07-06 21:10:00', '2022-08-19 22:28:17');
INSERT INTO `tb_resource` VALUES (1185, '修改用户的订阅状态', '/admin/user-info/subscribe', 'PUT', 1060, 1, '2024-07-06 21:10:15', '2022-08-19 22:28:08');
INSERT INTO `tb_resource` VALUES (1189, '获取用户列表', '/admin/user/list', 'GET', 1061, 1, '2024-07-12 12:28:37', NULL);
INSERT INTO `tb_resource` VALUES (1190, '获取在线用户', '/admin/user-info/online', 'GET', 1060, 1, '2024-07-12 17:45:51', NULL);
INSERT INTO `tb_resource` VALUES (1191, '禁用用户', '/admin/user-info/disable', 'PUT', 1060, 1, '2024-07-12 17:15:40', NULL);
INSERT INTO `tb_resource` VALUES (1192, '修改用户角色', '/admin/user-info/role', 'PUT', 1060, 0, '2024-07-12 21:51:45', NULL);
INSERT INTO `tb_resource` VALUES (1195, '异常日志模块', NULL, NULL, NULL, 0, '2024-07-13 13:55:24', NULL);
INSERT INTO `tb_resource` VALUES (1196, '获取异常日志', '/admin/exception/log/list', 'GET', 1195, 0, '2024-07-14 14:34:20', '2024-07-14 14:34:21');
INSERT INTO `tb_resource` VALUES (1197, '删除异常日志', '/admin/exception/log', 'DELETE', 1195, 0, '2024-07-14 14:35:08', '2024-07-14 14:35:09');
INSERT INTO `tb_resource` VALUES (1199, '根据类型获取评论数量', '/server/comment/count/type/*', 'GET', 1065, 0, '2024-07-16 17:25:49', NULL);
INSERT INTO `tb_resource` VALUES (1201, '获取文章总数(不包括删除的文章)', '/server/article/count/not-deleted', 'GET', 1057, 1, '2024-07-19 12:42:17', '2024-07-19 12:42:17');
INSERT INTO `tb_resource` VALUES (1202, '获取文章归档统计数据', '/server/article/list/statistics', 'GET', 1057, 0, '2024-07-16 17:43:43', NULL);
INSERT INTO `tb_resource` VALUES (1203, '获取所有标签', '/server/tag/all', 'GET', 1058, 0, '2024-07-16 18:42:04', NULL);
INSERT INTO `tb_resource` VALUES (1204, '内部接口模块', NULL, NULL, NULL, 0, '2024-07-16 19:12:25', NULL);
INSERT INTO `tb_resource` VALUES (1207, '清理动态元数据', '/server/dynamic-security-metadata-source/clear', 'DELETE', 1204, 0, '2024-07-16 19:21:20', '2024-07-16 19:21:21');
INSERT INTO `tb_resource` VALUES (1208, '上传照片', '/admin/photo/upload', 'POST', 1059, 0, '2024-07-17 13:05:48', '2024-07-17 13:05:49');
INSERT INTO `tb_resource` VALUES (1209, '根据相册Id查看照片列表', '/server/photo/*', 'GET', 1062, 1, '2024-07-18 15:01:21', '2024-07-18 15:01:21');
INSERT INTO `tb_resource` VALUES (1212, '获取相册列表', '/server/photo/album/list', 'GET', 1062, 1, '2024-07-18 15:11:09', '2024-07-18 15:11:10');
INSERT INTO `tb_resource` VALUES (1213, '获取分类数量', '/server/category/count', 'GET', 1051, 0, '2024-07-18 23:53:55', NULL);
INSERT INTO `tb_resource` VALUES (1214, '获取标签数量', '/server/tag/count', 'GET', 1058, 0, '2024-07-18 23:55:45', NULL);
INSERT INTO `tb_resource` VALUES (1215, '获取文章排行', '/server/article/rank', 'POST', 1057, 1, '2024-07-20 08:26:13', '2024-07-20 08:26:12');
INSERT INTO `tb_resource` VALUES (1223, '添加用户', '/server/user-info/insert', 'POST', 1060, 1, '2024-07-20 22:19:09', '2024-07-20 22:19:10');
INSERT INTO `tb_resource` VALUES (1224, '查看评论列表', '/server/comment/list', 'GET', 1065, 1, '2024-07-21 09:58:56', '2024-07-21 09:58:56');
INSERT INTO `tb_resource` VALUES (1225, '测试模块', NULL, NULL, NULL, 0, '2024-08-08 23:04:57', NULL);
INSERT INTO `tb_resource` VALUES (1226, '测试读接口', '/admin/test/read', 'GET', 1225, 1, '2024-08-08 23:06:32', '2024-08-08 23:06:32');
INSERT INTO `tb_resource` VALUES (1227, '测试写接口', '/admin/test/write', 'POST', 1225, 1, '2024-08-08 23:06:33', '2024-08-08 23:06:33');
INSERT INTO `tb_resource` VALUES (1228, '清空操作日志', '/admin/operation/log/clean', 'DELETE', 1056, 0, '2024-08-11 15:32:14', NULL);
INSERT INTO `tb_resource` VALUES (1229, '清空异常日志', '/admin/exception/log/clean', 'DELETE', 1195, 0, '2024-08-11 15:44:54', NULL);

-- ----------------------------
-- Table structure for tb_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '角色Id',
  `role_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  `is_disable` tinyint NOT NULL DEFAULT 0 COMMENT '是否禁用 0否 ：1是',
  `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `name`(`role_name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_role
-- ----------------------------
INSERT INTO `tb_role` VALUES (1, 'admin', 0, '2024-08-11 15:45:07', '2024-08-11 15:45:07');
INSERT INTO `tb_role` VALUES (19, 'user', 0, '2024-07-22 12:19:19', '2024-07-22 12:19:19');

-- ----------------------------
-- Table structure for tb_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_menu`;
CREATE TABLE `tb_role_menu`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '角色菜单中间表id',
  `role_id` int NOT NULL COMMENT '角色id',
  `menu_id` int NOT NULL COMMENT '菜单id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `role_id`(`role_id` ASC) USING BTREE,
  INDEX `menu_id`(`menu_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3828 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色菜单中间表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_role_menu
-- ----------------------------
INSERT INTO `tb_role_menu` VALUES (3776, 1, 1);
INSERT INTO `tb_role_menu` VALUES (3777, 1, 2);
INSERT INTO `tb_role_menu` VALUES (3778, 1, 6);
INSERT INTO `tb_role_menu` VALUES (3779, 1, 7);
INSERT INTO `tb_role_menu` VALUES (3780, 1, 8);
INSERT INTO `tb_role_menu` VALUES (3781, 1, 9);
INSERT INTO `tb_role_menu` VALUES (3782, 1, 10);
INSERT INTO `tb_role_menu` VALUES (3783, 1, 3);
INSERT INTO `tb_role_menu` VALUES (3784, 1, 11);
INSERT INTO `tb_role_menu` VALUES (3785, 1, 221);
INSERT INTO `tb_role_menu` VALUES (3786, 1, 222);
INSERT INTO `tb_role_menu` VALUES (3787, 1, 223);
INSERT INTO `tb_role_menu` VALUES (3788, 1, 224);
INSERT INTO `tb_role_menu` VALUES (3789, 1, 202);
INSERT INTO `tb_role_menu` VALUES (3790, 1, 13);
INSERT INTO `tb_role_menu` VALUES (3791, 1, 201);
INSERT INTO `tb_role_menu` VALUES (3792, 1, 213);
INSERT INTO `tb_role_menu` VALUES (3793, 1, 14);
INSERT INTO `tb_role_menu` VALUES (3794, 1, 15);
INSERT INTO `tb_role_menu` VALUES (3795, 1, 16);
INSERT INTO `tb_role_menu` VALUES (3796, 1, 4);
INSERT INTO `tb_role_menu` VALUES (3797, 1, 214);
INSERT INTO `tb_role_menu` VALUES (3798, 1, 209);
INSERT INTO `tb_role_menu` VALUES (3799, 1, 17);
INSERT INTO `tb_role_menu` VALUES (3800, 1, 18);
INSERT INTO `tb_role_menu` VALUES (3801, 1, 205);
INSERT INTO `tb_role_menu` VALUES (3802, 1, 206);
INSERT INTO `tb_role_menu` VALUES (3803, 1, 208);
INSERT INTO `tb_role_menu` VALUES (3804, 1, 210);
INSERT INTO `tb_role_menu` VALUES (3805, 1, 19);
INSERT INTO `tb_role_menu` VALUES (3806, 1, 20);
INSERT INTO `tb_role_menu` VALUES (3807, 1, 225);
INSERT INTO `tb_role_menu` VALUES (3808, 1, 220);
INSERT INTO `tb_role_menu` VALUES (3809, 1, 5);
INSERT INTO `tb_role_menu` VALUES (3810, 19, 1);
INSERT INTO `tb_role_menu` VALUES (3811, 19, 2);
INSERT INTO `tb_role_menu` VALUES (3812, 19, 6);
INSERT INTO `tb_role_menu` VALUES (3813, 19, 7);
INSERT INTO `tb_role_menu` VALUES (3814, 19, 8);
INSERT INTO `tb_role_menu` VALUES (3815, 19, 9);
INSERT INTO `tb_role_menu` VALUES (3816, 19, 10);
INSERT INTO `tb_role_menu` VALUES (3817, 19, 3);
INSERT INTO `tb_role_menu` VALUES (3818, 19, 11);
INSERT INTO `tb_role_menu` VALUES (3819, 19, 221);
INSERT INTO `tb_role_menu` VALUES (3820, 19, 222);
INSERT INTO `tb_role_menu` VALUES (3821, 19, 223);
INSERT INTO `tb_role_menu` VALUES (3822, 19, 224);
INSERT INTO `tb_role_menu` VALUES (3823, 19, 205);
INSERT INTO `tb_role_menu` VALUES (3824, 19, 206);
INSERT INTO `tb_role_menu` VALUES (3825, 19, 208);
INSERT INTO `tb_role_menu` VALUES (3826, 19, 210);
INSERT INTO `tb_role_menu` VALUES (3827, 19, 5);

-- ----------------------------
-- Table structure for tb_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_resource`;
CREATE TABLE `tb_role_resource`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '角色资源中间表id',
  `role_id` int NOT NULL COMMENT '角色id',
  `resource_id` int NOT NULL COMMENT '资源id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `role_id`(`role_id` ASC) USING BTREE,
  INDEX `resource_id`(`resource_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9692 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色资源中间表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_role_resource
-- ----------------------------
INSERT INTO `tb_role_resource` VALUES (9251, 19, 1050);
INSERT INTO `tb_role_resource` VALUES (9252, 19, 1070);
INSERT INTO `tb_role_resource` VALUES (9253, 19, 1071);
INSERT INTO `tb_role_resource` VALUES (9254, 19, 1088);
INSERT INTO `tb_role_resource` VALUES (9255, 19, 1149);
INSERT INTO `tb_role_resource` VALUES (9256, 19, 1051);
INSERT INTO `tb_role_resource` VALUES (9257, 19, 1081);
INSERT INTO `tb_role_resource` VALUES (9258, 19, 1082);
INSERT INTO `tb_role_resource` VALUES (9259, 19, 1083);
INSERT INTO `tb_role_resource` VALUES (9260, 19, 1084);
INSERT INTO `tb_role_resource` VALUES (9261, 19, 1213);
INSERT INTO `tb_role_resource` VALUES (9262, 19, 1052);
INSERT INTO `tb_role_resource` VALUES (9263, 19, 1101);
INSERT INTO `tb_role_resource` VALUES (9264, 19, 1102);
INSERT INTO `tb_role_resource` VALUES (9265, 19, 1103);
INSERT INTO `tb_role_resource` VALUES (9266, 19, 1057);
INSERT INTO `tb_role_resource` VALUES (9267, 19, 1072);
INSERT INTO `tb_role_resource` VALUES (9268, 19, 1073);
INSERT INTO `tb_role_resource` VALUES (9269, 19, 1074);
INSERT INTO `tb_role_resource` VALUES (9270, 19, 1075);
INSERT INTO `tb_role_resource` VALUES (9271, 19, 1076);
INSERT INTO `tb_role_resource` VALUES (9272, 19, 1077);
INSERT INTO `tb_role_resource` VALUES (9273, 19, 1078);
INSERT INTO `tb_role_resource` VALUES (9274, 19, 1079);
INSERT INTO `tb_role_resource` VALUES (9275, 19, 1080);
INSERT INTO `tb_role_resource` VALUES (9276, 19, 1202);
INSERT INTO `tb_role_resource` VALUES (9277, 19, 1058);
INSERT INTO `tb_role_resource` VALUES (9278, 19, 1130);
INSERT INTO `tb_role_resource` VALUES (9279, 19, 1131);
INSERT INTO `tb_role_resource` VALUES (9280, 19, 1132);
INSERT INTO `tb_role_resource` VALUES (9281, 19, 1133);
INSERT INTO `tb_role_resource` VALUES (9282, 19, 1203);
INSERT INTO `tb_role_resource` VALUES (9283, 19, 1214);
INSERT INTO `tb_role_resource` VALUES (9284, 19, 1059);
INSERT INTO `tb_role_resource` VALUES (9285, 19, 1110);
INSERT INTO `tb_role_resource` VALUES (9286, 19, 1111);
INSERT INTO `tb_role_resource` VALUES (9287, 19, 1112);
INSERT INTO `tb_role_resource` VALUES (9288, 19, 1113);
INSERT INTO `tb_role_resource` VALUES (9289, 19, 1114);
INSERT INTO `tb_role_resource` VALUES (9290, 19, 1121);
INSERT INTO `tb_role_resource` VALUES (9291, 19, 1208);
INSERT INTO `tb_role_resource` VALUES (9292, 19, 1060);
INSERT INTO `tb_role_resource` VALUES (9293, 19, 1142);
INSERT INTO `tb_role_resource` VALUES (9294, 19, 1143);
INSERT INTO `tb_role_resource` VALUES (9295, 19, 1146);
INSERT INTO `tb_role_resource` VALUES (9296, 19, 1147);
INSERT INTO `tb_role_resource` VALUES (9297, 19, 1176);
INSERT INTO `tb_role_resource` VALUES (9298, 19, 1179);
INSERT INTO `tb_role_resource` VALUES (9299, 19, 1192);
INSERT INTO `tb_role_resource` VALUES (9300, 19, 1061);
INSERT INTO `tb_role_resource` VALUES (9301, 19, 1140);
INSERT INTO `tb_role_resource` VALUES (9302, 19, 1141);
INSERT INTO `tb_role_resource` VALUES (9303, 19, 1144);
INSERT INTO `tb_role_resource` VALUES (9304, 19, 1181);
INSERT INTO `tb_role_resource` VALUES (9305, 19, 1062);
INSERT INTO `tb_role_resource` VALUES (9306, 19, 1115);
INSERT INTO `tb_role_resource` VALUES (9307, 19, 1116);
INSERT INTO `tb_role_resource` VALUES (9308, 19, 1117);
INSERT INTO `tb_role_resource` VALUES (9309, 19, 1118);
INSERT INTO `tb_role_resource` VALUES (9310, 19, 1119);
INSERT INTO `tb_role_resource` VALUES (9311, 19, 1120);
INSERT INTO `tb_role_resource` VALUES (9312, 19, 1063);
INSERT INTO `tb_role_resource` VALUES (9313, 19, 1104);
INSERT INTO `tb_role_resource` VALUES (9314, 19, 1105);
INSERT INTO `tb_role_resource` VALUES (9315, 19, 1106);
INSERT INTO `tb_role_resource` VALUES (9316, 19, 1107);
INSERT INTO `tb_role_resource` VALUES (9317, 19, 1126);
INSERT INTO `tb_role_resource` VALUES (9318, 19, 1139);
INSERT INTO `tb_role_resource` VALUES (9319, 19, 1065);
INSERT INTO `tb_role_resource` VALUES (9320, 19, 1085);
INSERT INTO `tb_role_resource` VALUES (9321, 19, 1086);
INSERT INTO `tb_role_resource` VALUES (9322, 19, 1087);
INSERT INTO `tb_role_resource` VALUES (9323, 19, 1167);
INSERT INTO `tb_role_resource` VALUES (9324, 19, 1199);
INSERT INTO `tb_role_resource` VALUES (9325, 19, 1066);
INSERT INTO `tb_role_resource` VALUES (9326, 19, 1134);
INSERT INTO `tb_role_resource` VALUES (9327, 19, 1135);
INSERT INTO `tb_role_resource` VALUES (9328, 19, 1136);
INSERT INTO `tb_role_resource` VALUES (9329, 19, 1137);
INSERT INTO `tb_role_resource` VALUES (9330, 19, 1204);
INSERT INTO `tb_role_resource` VALUES (9331, 19, 1207);
INSERT INTO `tb_role_resource` VALUES (9571, 1, 1050);
INSERT INTO `tb_role_resource` VALUES (9572, 1, 1070);
INSERT INTO `tb_role_resource` VALUES (9573, 1, 1071);
INSERT INTO `tb_role_resource` VALUES (9574, 1, 1088);
INSERT INTO `tb_role_resource` VALUES (9575, 1, 1149);
INSERT INTO `tb_role_resource` VALUES (9576, 1, 1051);
INSERT INTO `tb_role_resource` VALUES (9577, 1, 1081);
INSERT INTO `tb_role_resource` VALUES (9578, 1, 1082);
INSERT INTO `tb_role_resource` VALUES (9579, 1, 1083);
INSERT INTO `tb_role_resource` VALUES (9580, 1, 1084);
INSERT INTO `tb_role_resource` VALUES (9581, 1, 1213);
INSERT INTO `tb_role_resource` VALUES (9582, 1, 1052);
INSERT INTO `tb_role_resource` VALUES (9583, 1, 1101);
INSERT INTO `tb_role_resource` VALUES (9584, 1, 1102);
INSERT INTO `tb_role_resource` VALUES (9585, 1, 1103);
INSERT INTO `tb_role_resource` VALUES (9586, 1, 1053);
INSERT INTO `tb_role_resource` VALUES (9587, 1, 1089);
INSERT INTO `tb_role_resource` VALUES (9588, 1, 1090);
INSERT INTO `tb_role_resource` VALUES (9589, 1, 1091);
INSERT INTO `tb_role_resource` VALUES (9590, 1, 1092);
INSERT INTO `tb_role_resource` VALUES (9591, 1, 1054);
INSERT INTO `tb_role_resource` VALUES (9592, 1, 1093);
INSERT INTO `tb_role_resource` VALUES (9593, 1, 1094);
INSERT INTO `tb_role_resource` VALUES (9594, 1, 1095);
INSERT INTO `tb_role_resource` VALUES (9595, 1, 1096);
INSERT INTO `tb_role_resource` VALUES (9596, 1, 1097);
INSERT INTO `tb_role_resource` VALUES (9597, 1, 1098);
INSERT INTO `tb_role_resource` VALUES (9598, 1, 1099);
INSERT INTO `tb_role_resource` VALUES (9599, 1, 1100);
INSERT INTO `tb_role_resource` VALUES (9600, 1, 1055);
INSERT INTO `tb_role_resource` VALUES (9601, 1, 1158);
INSERT INTO `tb_role_resource` VALUES (9602, 1, 1159);
INSERT INTO `tb_role_resource` VALUES (9603, 1, 1160);
INSERT INTO `tb_role_resource` VALUES (9604, 1, 1161);
INSERT INTO `tb_role_resource` VALUES (9605, 1, 1162);
INSERT INTO `tb_role_resource` VALUES (9606, 1, 1163);
INSERT INTO `tb_role_resource` VALUES (9607, 1, 1164);
INSERT INTO `tb_role_resource` VALUES (9608, 1, 1056);
INSERT INTO `tb_role_resource` VALUES (9609, 1, 1108);
INSERT INTO `tb_role_resource` VALUES (9610, 1, 1109);
INSERT INTO `tb_role_resource` VALUES (9611, 1, 1228);
INSERT INTO `tb_role_resource` VALUES (9612, 1, 1057);
INSERT INTO `tb_role_resource` VALUES (9613, 1, 1072);
INSERT INTO `tb_role_resource` VALUES (9614, 1, 1073);
INSERT INTO `tb_role_resource` VALUES (9615, 1, 1074);
INSERT INTO `tb_role_resource` VALUES (9616, 1, 1075);
INSERT INTO `tb_role_resource` VALUES (9617, 1, 1076);
INSERT INTO `tb_role_resource` VALUES (9618, 1, 1077);
INSERT INTO `tb_role_resource` VALUES (9619, 1, 1078);
INSERT INTO `tb_role_resource` VALUES (9620, 1, 1079);
INSERT INTO `tb_role_resource` VALUES (9621, 1, 1080);
INSERT INTO `tb_role_resource` VALUES (9622, 1, 1202);
INSERT INTO `tb_role_resource` VALUES (9623, 1, 1058);
INSERT INTO `tb_role_resource` VALUES (9624, 1, 1130);
INSERT INTO `tb_role_resource` VALUES (9625, 1, 1131);
INSERT INTO `tb_role_resource` VALUES (9626, 1, 1132);
INSERT INTO `tb_role_resource` VALUES (9627, 1, 1133);
INSERT INTO `tb_role_resource` VALUES (9628, 1, 1203);
INSERT INTO `tb_role_resource` VALUES (9629, 1, 1214);
INSERT INTO `tb_role_resource` VALUES (9630, 1, 1059);
INSERT INTO `tb_role_resource` VALUES (9631, 1, 1110);
INSERT INTO `tb_role_resource` VALUES (9632, 1, 1111);
INSERT INTO `tb_role_resource` VALUES (9633, 1, 1112);
INSERT INTO `tb_role_resource` VALUES (9634, 1, 1113);
INSERT INTO `tb_role_resource` VALUES (9635, 1, 1114);
INSERT INTO `tb_role_resource` VALUES (9636, 1, 1121);
INSERT INTO `tb_role_resource` VALUES (9637, 1, 1208);
INSERT INTO `tb_role_resource` VALUES (9638, 1, 1060);
INSERT INTO `tb_role_resource` VALUES (9639, 1, 1142);
INSERT INTO `tb_role_resource` VALUES (9640, 1, 1143);
INSERT INTO `tb_role_resource` VALUES (9641, 1, 1146);
INSERT INTO `tb_role_resource` VALUES (9642, 1, 1147);
INSERT INTO `tb_role_resource` VALUES (9643, 1, 1176);
INSERT INTO `tb_role_resource` VALUES (9644, 1, 1179);
INSERT INTO `tb_role_resource` VALUES (9645, 1, 1192);
INSERT INTO `tb_role_resource` VALUES (9646, 1, 1061);
INSERT INTO `tb_role_resource` VALUES (9647, 1, 1140);
INSERT INTO `tb_role_resource` VALUES (9648, 1, 1141);
INSERT INTO `tb_role_resource` VALUES (9649, 1, 1144);
INSERT INTO `tb_role_resource` VALUES (9650, 1, 1181);
INSERT INTO `tb_role_resource` VALUES (9651, 1, 1062);
INSERT INTO `tb_role_resource` VALUES (9652, 1, 1115);
INSERT INTO `tb_role_resource` VALUES (9653, 1, 1116);
INSERT INTO `tb_role_resource` VALUES (9654, 1, 1117);
INSERT INTO `tb_role_resource` VALUES (9655, 1, 1118);
INSERT INTO `tb_role_resource` VALUES (9656, 1, 1119);
INSERT INTO `tb_role_resource` VALUES (9657, 1, 1120);
INSERT INTO `tb_role_resource` VALUES (9658, 1, 1063);
INSERT INTO `tb_role_resource` VALUES (9659, 1, 1104);
INSERT INTO `tb_role_resource` VALUES (9660, 1, 1105);
INSERT INTO `tb_role_resource` VALUES (9661, 1, 1106);
INSERT INTO `tb_role_resource` VALUES (9662, 1, 1107);
INSERT INTO `tb_role_resource` VALUES (9663, 1, 1126);
INSERT INTO `tb_role_resource` VALUES (9664, 1, 1139);
INSERT INTO `tb_role_resource` VALUES (9665, 1, 1064);
INSERT INTO `tb_role_resource` VALUES (9666, 1, 1125);
INSERT INTO `tb_role_resource` VALUES (9667, 1, 1128);
INSERT INTO `tb_role_resource` VALUES (9668, 1, 1129);
INSERT INTO `tb_role_resource` VALUES (9669, 1, 1145);
INSERT INTO `tb_role_resource` VALUES (9670, 1, 1065);
INSERT INTO `tb_role_resource` VALUES (9671, 1, 1085);
INSERT INTO `tb_role_resource` VALUES (9672, 1, 1086);
INSERT INTO `tb_role_resource` VALUES (9673, 1, 1087);
INSERT INTO `tb_role_resource` VALUES (9674, 1, 1167);
INSERT INTO `tb_role_resource` VALUES (9675, 1, 1199);
INSERT INTO `tb_role_resource` VALUES (9676, 1, 1066);
INSERT INTO `tb_role_resource` VALUES (9677, 1, 1134);
INSERT INTO `tb_role_resource` VALUES (9678, 1, 1135);
INSERT INTO `tb_role_resource` VALUES (9679, 1, 1136);
INSERT INTO `tb_role_resource` VALUES (9680, 1, 1137);
INSERT INTO `tb_role_resource` VALUES (9681, 1, 1067);
INSERT INTO `tb_role_resource` VALUES (9682, 1, 1122);
INSERT INTO `tb_role_resource` VALUES (9683, 1, 1123);
INSERT INTO `tb_role_resource` VALUES (9684, 1, 1124);
INSERT INTO `tb_role_resource` VALUES (9685, 1, 1127);
INSERT INTO `tb_role_resource` VALUES (9686, 1, 1195);
INSERT INTO `tb_role_resource` VALUES (9687, 1, 1196);
INSERT INTO `tb_role_resource` VALUES (9688, 1, 1197);
INSERT INTO `tb_role_resource` VALUES (9689, 1, 1229);
INSERT INTO `tb_role_resource` VALUES (9690, 1, 1204);
INSERT INTO `tb_role_resource` VALUES (9691, 1, 1207);

-- ----------------------------
-- Table structure for tb_tag
-- ----------------------------
DROP TABLE IF EXISTS `tb_tag`;
CREATE TABLE `tb_tag`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '标签Id',
  `tag_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标签名',
  `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `tag_id`(`id` ASC) USING BTREE,
  UNIQUE INDEX `tag_name`(`tag_name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '标签表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_tag
-- ----------------------------
INSERT INTO `tb_tag` VALUES (1, '测试标签', '2024-07-15 23:35:22', NULL);

-- ----------------------------
-- Table structure for tb_talk
-- ----------------------------
DROP TABLE IF EXISTS `tb_talk`;
CREATE TABLE `tb_talk`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '说说id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `content` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '说说内容',
  `images` varchar(2500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图片',
  `is_top` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否置顶',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态 1.公开 2.私密',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 70 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '说说表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_talk
-- ----------------------------
INSERT INTO `tb_talk` VALUES (68, 1, '人麻了', '', 0, 1, '2024-07-11 15:42:27', NULL);
INSERT INTO `tb_talk` VALUES (69, 1, '你好，世界', '[\"http://localhost:8082/api/v1/admin/blog/talks/6955f629a5bc04fb6a4f3e3961c3abf8.jpg\",\"http://localhost:8082/api/v1/admin/blog/talks/2684eb8772061fffe15fadf037ae897a.jpg\",\"http://localhost:8082/api/v1/admin/blog/talks/6955f629a5bc04fb6a4f3e3961c3abf8.jpg\"]', 1, 2, '2024-07-11 15:43:52', '2024-08-10 22:18:11');

-- ----------------------------
-- Table structure for tb_unique_view
-- ----------------------------
DROP TABLE IF EXISTS `tb_unique_view`;
CREATE TABLE `tb_unique_view`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `views_count` int NOT NULL COMMENT '访问量',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1541 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '访问统计表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_unique_view
-- ----------------------------
INSERT INTO `tb_unique_view` VALUES (1539, 3, '2024-08-14 10:14:55', NULL);
INSERT INTO `tb_unique_view` VALUES (1540, 3, '2024-08-14 11:52:11', NULL);

-- ----------------------------
-- Table structure for tb_user_auth
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_auth`;
CREATE TABLE `tb_user_auth`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户认证id',
  `user_info_id` bigint NOT NULL COMMENT '用户信息id',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `login_type` tinyint(1) NOT NULL DEFAULT 1 COMMENT '登录类型  0表示密码登录 1表示邮箱登录 2表示微信登录 3表示QQ登录',
  `ip_address` varchar(49) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户登录ip',
  `ip_source` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'ip属地',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `last_login_time` datetime NULL DEFAULT NULL COMMENT '上次登录时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1015 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户认证信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_user_auth
-- ----------------------------
INSERT INTO `tb_user_auth` VALUES (1, 1, 'admin', '$2a$10$/Z90STxVyGOIfNhTfvzbEuJ9t1yHjrkN6pBMRAqd5g5SdNIrdt5Da', 1, '0:0:0:0:0:0:0:1', '', 'admin@qq.com', '2022-08-19 21:43:46', '2024-08-18 10:59:30', '2024-08-18 10:59:30');
INSERT INTO `tb_user_auth` VALUES (1014, 4, 'user', '$2a$10$3GP1cdsdHzeiIkSkQk4hNOIWHfvpE2mEvFemkJWfMjKR4d3V49Bm6', 1, '0:0:0:0:0:0:0:1', '', '1063891901@qq.com', '2024-07-20 22:42:34', '2024-08-06 13:52:06', '2024-08-06 13:52:06');

-- ----------------------------
-- Table structure for tb_user_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_info`;
CREATE TABLE `tb_user_info`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户Id',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `nick_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户头像',
  `intro` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户简介',
  `website` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '个人网站',
  `is_subscribe` tinyint NULL DEFAULT 0 COMMENT '是否订阅',
  `is_disable` tinyint NOT NULL DEFAULT 0 COMMENT '是否禁用 0 表示否 1表示是',
  `create_time` datetime NOT NULL COMMENT '注册时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user_info
-- ----------------------------
INSERT INTO `tb_user_info` VALUES (1, 'admin@qq.com', '演示账号', 'http://localhost:8082/api/v1/admin/blog/avatar/2515cce78903bf1edd5e2e771f044de4.jpg', '这是演示账号', 'https://www.linhaojun.top', 0, 0, '2022-08-19 21:42:04', '2024-07-31 14:43:01');
INSERT INTO `tb_user_info` VALUES (4, '1063891901@qq.com', '用户1814672250002567169', 'http://localhost:8082/api/v1/admin/blog/avatar/cf97cdc63c2d31b8ce46363e3c67f13b.jpeg', NULL, NULL, 0, 0, '2024-07-20 22:42:34', '2024-07-22 11:05:39');

-- ----------------------------
-- Table structure for tb_user_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_role`;
CREATE TABLE `tb_user_role`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户角色中间表id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `role_id` int NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id` ASC) USING BTREE,
  INDEX `role_id`(`role_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户角色中间表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user_role
-- ----------------------------
INSERT INTO `tb_user_role` VALUES (9, 1, 1);
INSERT INTO `tb_user_role` VALUES (11, 4, 19);

-- ----------------------------
-- Table structure for tb_website_config
-- ----------------------------
DROP TABLE IF EXISTS `tb_website_config`;
CREATE TABLE `tb_website_config`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '配置表Id',
  `config` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '配置信息',
  `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '网站配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_website_config
-- ----------------------------
INSERT INTO `tb_website_config` VALUES (1, '{\"name\":\"个人博客\",\"englishName\":\"personal-blog\",\"author\":\"gewuyou\",\"authorAvatar\":\"http://localhost:8082/api/v1/admin/blog/config/7ecff5655747bc3a2797f5a1e4dc0728.jpg\",\"authorIntro\":\"格物窥天理，守仁致良知\",\"logo\":\"http://localhost:8082/api/v1/admin/blog/config/74e164e040fb67078eb58de16840a31b.png\",\"multiLanguage\":1,\"notice\":\"芝士公告\",\"websiteCreateTime\":\"2024-07-15\",\"beianNumber\":\"114515\",\"qqLogin\":0,\"isCommentReview\":0,\"isEmailNotice\":1,\"isReward\":0,\"favicon\":\"http://localhost:8082/api/v1/admin/blog/config/a96ad39529298daef9bb193efe1718bf.png\",\"websiteTitle\":\"gewuyou的个人博客\",\"gonganBeianNumber\":\"1919\"}', '2024-07-31 14:18:19', '2024-07-31 14:18:19');

SET FOREIGN_KEY_CHECKS = 1;
