/*
 Navicat MySQL Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 80011
 Source Host           : localhost:3306
 Source Schema         : console

 Target Server Type    : MySQL
 Target Server Version : 80011
 File Encoding         : 65001

*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_module
-- ----------------------------
DROP TABLE IF EXISTS `t_module`;
CREATE TABLE `t_module`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '模块ID',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '模块名称',
  `url` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模块URL',
  `parent_id` bigint(11) NULL DEFAULT NULL COMMENT '父模块ID',
  `is_leaf` int(11) NULL DEFAULT NULL COMMENT '是否子模块',
  `status` int(2) NULL DEFAULT NULL COMMENT '状态',
  `options` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作项',
  `rank_index` int(11) NULL DEFAULT 99 COMMENT '排序',
  `version` int(11) NULL DEFAULT 0,
  `deleted` int(1) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统模块表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_module
-- ----------------------------
INSERT INTO `t_module` VALUES (1, '系统信息管理', '', 0, 0, 1, '', 100000, 6, 0);
INSERT INTO `t_module` VALUES (2, '模块信息管理', '/module/index', 1, 1, 1, 'all,add,update,del,query,export,auth', 100100, 0, 0);
INSERT INTO `t_module` VALUES (3, '用户信息管理', '/user/all', 1, 1, 1, 'all,query,add,update,del,auth', 100200, 0, 0);
INSERT INTO `t_module` VALUES (4, '角色信息管理', '/role/all', 1, 1, 1, 'all,query,add,update,del,auth', 100300, 0, 0);
INSERT INTO `t_module` VALUES (5, '微服务管理界面', '', 0, 0, 1, '', 200000, 2, 0);
INSERT INTO `t_module` VALUES (6, 'Eureka', 'http://localhost:7761/', 5, 0, 1, '', 200100, 4, 0);
INSERT INTO `t_module` VALUES (7, 'SpringBootAdmin', 'http://localhost:7751/', 5, 0, 1, '', 200200, 2, 0);
INSERT INTO `t_module` VALUES (8, 'Zipkin', 'http://localhost:9411', 5, 0, 1, '', 200300, 2, 0);
INSERT INTO `t_module` VALUES (9, 'SwaggerUi', 'http://localhost:7005/swagger-ui.html', 5, 0, 1, '', 200400, 3, 0);

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色代码',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  `status` int(2) NULL DEFAULT NULL,
  `deleted` int(1) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES (1, 'MANAGER', '管理员668523', 1, 0);
INSERT INTO `t_role` VALUES (2, 'TESTER', '测试', 1, 0);
INSERT INTO `t_role` VALUES (4, 'AGENT', '代理', 1, 0);
INSERT INTO `t_role` VALUES (5, 'SERVICE', '客服', 1, 0);
INSERT INTO `t_role` VALUES (6, 'ACCOUNTING', '财务', 1, 0);
INSERT INTO `t_role` VALUES (7, 'RISK', '风控', 1, 0);
INSERT INTO `t_role` VALUES (8, 'TELEMARKETING', '电销', 1, 0);
INSERT INTO `t_role` VALUES (9, 'aa', 'a', 1, 1);

-- ----------------------------
-- Table structure for t_role_auth
-- ----------------------------
DROP TABLE IF EXISTS `t_role_auth`;
CREATE TABLE `t_role_auth`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `role_id` bigint(11) NULL DEFAULT NULL,
  `module_id` bigint(11) NULL DEFAULT NULL,
  `permission` varchar(500) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 86 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role_auth
-- ----------------------------
INSERT INTO `t_role_auth` VALUES (75, 1, 2, 'all,add,update,del,query,export,auth');
INSERT INTO `t_role_auth` VALUES (76, 1, 3, 'all,query,add,update,del,auth');
INSERT INTO `t_role_auth` VALUES (77, 1, 4, 'all,query,add,update,del,auth');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '账号',
  `password` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '密码',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `email` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电子邮件地址',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '入职时间',
  `avatar` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `status` int(2) NULL DEFAULT NULL COMMENT '状态0不可用1可用',
  `mfa_secret` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密钥',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, 'admin', '3f1b2c65b6fb8d3105f129c38937ef37', 'admin', 'zhoufh@zhongan.com.cn', '2019-04-02 17:12:47', NULL, 1, '4cf29c582a634ddbb8e524c1375e81c7');

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) NULL DEFAULT NULL,
  `role_id` bigint(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_userId_roleId`(`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 46 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES (45, 1, 1);
INSERT INTO `t_user_role` VALUES (1, 1, 9);
INSERT INTO `t_user_role` VALUES (36, 7, 2);

SET FOREIGN_KEY_CHECKS = 1;
