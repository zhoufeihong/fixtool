﻿/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50716
Source Host           : localhost:3306
Source Database       : console

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2019-06-14 16:38:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_module`
-- ----------------------------
DROP TABLE IF EXISTS `t_module`;
CREATE TABLE `t_module` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '模块ID',
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '模块名称',
  `url` varchar(64) DEFAULT NULL COMMENT '模块URL',
  `parent_id` bigint(11) DEFAULT NULL COMMENT '父模块ID',
  `is_leaf` int(11) DEFAULT NULL COMMENT '是否子模块',
  `status` int(2) DEFAULT NULL COMMENT '状态',
  `permission_resources_code` varchar(100) DEFAULT NULL,
  `options` varchar(50) DEFAULT NULL COMMENT '操作项',
  `rank_index` int(11) DEFAULT '99' COMMENT '排序',
  `version` int(11) DEFAULT '0',
  `deleted` int(1) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统模块表';

-- ----------------------------
-- Records of t_module
-- ----------------------------
INSERT INTO `t_module` VALUES ('1', '系统信息管理', '', '0', '0', '1', '001001002', '', '100000', '19', '0', '2019-06-11 11:01:08', '1', '2019-06-13 12:08:10', '1');
INSERT INTO `t_module` VALUES ('2', '模块信息管理', '/module/index', '1', '1', '1', '001001001', 'all,add,update,del,query,export,auth', '100100', '0', '0', '2019-06-11 11:01:08', '1', '2019-06-11 11:01:08', '1');
INSERT INTO `t_module` VALUES ('3', '用户信息管理', '/user/all', '1', '1', '1', '001001002', 'all,query,add,update,del,auth', '100200', '1', '0', '2019-06-11 11:01:08', '1', '2019-06-13 08:36:33', '1');
INSERT INTO `t_module` VALUES ('4', '角色信息管理', '/role/all', '1', '1', '1', '001001002', 'all,query,add,update,del,auth', '100300', '1', '0', '2019-06-11 11:01:08', '1', '2019-06-13 11:15:37', '1');
INSERT INTO `t_module` VALUES ('5', '微服务管理界面', '', '0', '0', '1', null, '', '200000', '2', '0', '2019-06-11 11:01:08', '1', '2019-06-11 11:01:08', '1');
INSERT INTO `t_module` VALUES ('6', 'Eureka', 'http://localhost:7761/', '5', '1', '1', null, '', '200100', '6', '0', '2019-06-11 11:01:08', '1', '2019-06-11 10:10:48', '1');
INSERT INTO `t_module` VALUES ('7', 'SpringBootAdmin', 'http://localhost:7751/', '5', '0', '1', null, '', '200200', '2', '0', '2019-06-11 11:01:08', '1', '2019-06-11 11:01:08', '1');
INSERT INTO `t_module` VALUES ('8', 'Zipkin', 'http://localhost:9411', '5', '1', '1', null, '', '200300', '3', '0', '2019-06-11 11:01:08', '1', '2019-06-11 11:01:08', '1');
INSERT INTO `t_module` VALUES ('9', 'SwaggerUi', 'http://localhost:7005/swagger-ui.html', '5', '1', '1', null, '', '200400', '4', '0', '2019-06-11 11:01:08', '1', '2019-06-11 11:01:08', '1');
INSERT INTO `t_module` VALUES ('58', '权限资源项管理', '/permission-resource/index', '1', '1', '1', '', null, '100400', '0', '0', '2019-06-13 07:46:53', '1', '2019-06-13 07:46:53', '1');
INSERT INTO `t_module` VALUES ('60', 'druid监控', 'http://localhost:7005/druid/index.html', '5', '1', '1', '', null, '200500', '2', '0', '2019-06-14 08:33:02', '1', '2019-06-14 08:34:09', '1');

-- ----------------------------
-- Table structure for `t_permission_resource`
-- ----------------------------
DROP TABLE IF EXISTS `t_permission_resource`;
CREATE TABLE `t_permission_resource` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(100) NOT NULL COMMENT '编码',
  `parent_code` varchar(100) NOT NULL,
  `name` varchar(100) DEFAULT NULL COMMENT '名称',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `status` int(1) DEFAULT '1' COMMENT '状态0不可用1可用',
  `version` int(11) DEFAULT '0' COMMENT '版本',
  `deleted` int(1) DEFAULT '0' COMMENT '是否已经删除(1:已经删除,0:未删除)',
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Idx_PermissionResources_1` (`code`) USING BTREE,
  KEY `Idx_PermissionResources_2` (`parent_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_permission_resource
-- ----------------------------
INSERT INTO `t_permission_resource` VALUES ('1', '001001001', '001001000', '模块信息查询', '', '1', '2', '0', '2019-06-11 11:01:24', '1', '2019-06-13 12:27:59', '1');
INSERT INTO `t_permission_resource` VALUES ('4', '001001002', '001001000', '模块信息修改', '', '1', '0', '0', '2019-06-11 17:38:18', '1', '2019-06-11 17:38:22', '1');
INSERT INTO `t_permission_resource` VALUES ('5', '001001000', '0', '模块信息管理', null, '1', '0', '0', '2019-06-12 16:47:24', '1', '2019-06-12 16:47:29', '1');
INSERT INTO `t_permission_resource` VALUES ('6', '0', '-1', '权限基础节点', null, '1', '0', '0', '2019-06-13 20:11:36', '1', '2019-06-13 20:11:39', '1');

-- ----------------------------
-- Table structure for `t_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `code` varchar(20) DEFAULT NULL COMMENT '角色代码',
  `name` varchar(50) DEFAULT NULL COMMENT '角色描述',
  `remark` varchar(500) DEFAULT NULL,
  `status` int(2) DEFAULT NULL,
  `deleted` int(1) DEFAULT '0',
  `version` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色表';

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('1', 'MANAGER', '管理员', null, '1', '0', '0', '2019-06-11 11:01:29', '1', '2019-06-11 11:01:29', '1');
INSERT INTO `t_role` VALUES ('2', 'TESTER', '测试', null, '1', '0', '0', '2019-06-11 11:01:29', '1', '2019-06-11 11:01:29', '1');
INSERT INTO `t_role` VALUES ('4', 'AGENT', '代理4012644576793025639718', null, '1', '0', '8', '2019-06-11 11:01:29', '1', '2019-06-12 05:46:08', 'emptyUser');
INSERT INTO `t_role` VALUES ('5', 'SERVICE', '客服', null, '1', '0', '0', '2019-06-11 11:01:29', '1', '2019-06-11 11:01:29', '1');
INSERT INTO `t_role` VALUES ('6', 'ACCOUNTING', '财务', null, '1', '0', '0', '2019-06-11 11:01:29', '1', '2019-06-11 11:01:29', '1');
INSERT INTO `t_role` VALUES ('7', 'RISK', '风控', null, '1', '0', '0', '2019-06-11 11:01:29', '1', '2019-06-11 11:01:29', '1');
INSERT INTO `t_role` VALUES ('8', 'TELEMARKETING', '电销', null, '1', '0', '0', '2019-06-11 11:01:29', '1', '2019-06-11 11:01:29', '1');

-- ----------------------------
-- Table structure for `t_role_auth`
-- ----------------------------
DROP TABLE IF EXISTS `t_role_auth`;
CREATE TABLE `t_role_auth` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `role_id` bigint(11) DEFAULT NULL,
  `permission_resources_code` varchar(100) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` varchar(50) DEFAULT NULL,
  `deleted` int(1) DEFAULT '0',
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `IDX_RoleAuth1` (`role_id`,`deleted`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_role_auth
-- ----------------------------
INSERT INTO `t_role_auth` VALUES ('86', '1', '001001001', '2019-06-11 09:51:52', 'emptyUser', '2019-06-11 09:51:52', 'emptyUser', '1', '0');
INSERT INTO `t_role_auth` VALUES ('87', '1', '001001002', '2019-06-11 09:51:53', 'emptyUser', '2019-06-11 09:51:53', 'emptyUser', '1', '0');
INSERT INTO `t_role_auth` VALUES ('88', '1', '001001001', '2019-06-11 09:53:17', 'emptyUser', '2019-06-11 09:53:17', 'emptyUser', '0', '0');
INSERT INTO `t_role_auth` VALUES ('89', '1', '001001002', '2019-06-11 09:53:17', 'emptyUser', '2019-06-11 09:53:17', 'emptyUser', '0', '0');

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(64) NOT NULL DEFAULT '' COMMENT '账号',
  `password` varchar(256) NOT NULL DEFAULT '' COMMENT '密码',
  `name` varchar(64) DEFAULT NULL COMMENT '姓名',
  `email` varchar(64) DEFAULT NULL COMMENT '电子邮件地址',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '入职时间',
  `avatar` varchar(100) DEFAULT NULL COMMENT '头像',
  `status` int(2) DEFAULT NULL COMMENT '状态0不可用1可用',
  `mfa_secret` varchar(32) DEFAULT NULL COMMENT '密钥',
  `create_by` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` varchar(50) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `deleted` int(1) DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统用户表';

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'admin', '3f1b2c65b6fb8d3105f129c38937ef37', 'admin', 'zhoufh@zhongan.com.cn', '2019-06-11 11:01:40', null, '1', '4cf29c582a634ddbb8e524c1375e81c7', '1', '2019-06-12 05:46:08', 'emptyUser', '18', '0');

-- ----------------------------
-- Table structure for `t_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) DEFAULT NULL,
  `role_id` bigint(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` varchar(50) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `deleted` int(1) DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `unique_userId_roleId` (`user_id`,`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES ('36', '7', '2', '2019-06-11 11:01:46', '1', '2019-06-11 11:01:46', '1', '0', '0');
INSERT INTO `t_user_role` VALUES ('79', '1', '2', null, null, null, null, null, '0');
INSERT INTO `t_user_role` VALUES ('80', '1', '1', null, null, null, null, null, '0');
