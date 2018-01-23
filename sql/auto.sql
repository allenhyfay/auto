/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50543
Source Host           : 127.0.0.1:3306
Source Database       : auto

Target Server Type    : MYSQL
Target Server Version : 50543
File Encoding         : 65001

Date: 2018-01-23 14:09:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for uniquebo
-- ----------------------------
DROP TABLE IF EXISTS `uniquebo`;
CREATE TABLE `uniquebo` (
  `uuid` varchar(32) NOT NULL,
  `devices` varchar(500) DEFAULT NULL,
  `title` varchar(500) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of uniquebo
-- ----------------------------
