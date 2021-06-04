/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50719
Source Host           : 127.0.0.1:3306
Source Database       : excel

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2021-06-04 17:05:21
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for el_field
-- ----------------------------
DROP TABLE IF EXISTS `el_field`;
CREATE TABLE `el_field` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `index` int(5) DEFAULT NULL COMMENT '字段序号',
  `name` varchar(100) DEFAULT NULL COMMENT '字段名称',
  `type` varchar(20) DEFAULT NULL COMMENT '字段类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for el_table
-- ----------------------------
DROP TABLE IF EXISTS `el_table`;
CREATE TABLE `el_table` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `name` varchar(100) DEFAULT NULL COMMENT '表名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for el_table_field
-- ----------------------------
DROP TABLE IF EXISTS `el_table_field`;
CREATE TABLE `el_table_field` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `tab_id` bigint(20) DEFAULT NULL COMMENT '表Id',
  `field_ids` varchar(100) DEFAULT NULL COMMENT '字段Ids(逗号分隔)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='表与字段关系表';

-- ----------------------------
-- Table structure for person
-- ----------------------------
DROP TABLE IF EXISTS `person`;
CREATE TABLE `person` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `age` int(2) DEFAULT NULL COMMENT '年龄',
  `sex` varchar(6) DEFAULT NULL COMMENT '性别',
  `birth` date DEFAULT NULL COMMENT '生日',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for story
-- ----------------------------
DROP TABLE IF EXISTS `story`;
CREATE TABLE `story` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL COMMENT '情节标题',
  `key_person` varchar(255) DEFAULT NULL COMMENT '关键人物',
  `pot` varchar(255) DEFAULT NULL COMMENT '剧情',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
