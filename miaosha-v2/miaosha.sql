/*
Navicat MySQL Data Transfer

Source Server         : miaosha
Source Server Version : 50562
Source Host           : localhost:3306
Source Database       : miaosha

Target Server Type    : MYSQL
Target Server Version : 50562
File Encoding         : 65001

Date: 2019-02-13 19:05:07
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `goods`
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `goods_name` varchar(16) DEFAULT NULL COMMENT '商品名称',
  `goods_title` varchar(64) DEFAULT NULL COMMENT '商品标题',
  `goods_img` varchar(64) DEFAULT NULL COMMENT '商品的图片',
  `goods_detail` longtext COMMENT '商品的详情介绍',
  `goods_price` decimal(10,2) DEFAULT '0.00' COMMENT '商品单价',
  `goods_stock` int(11) DEFAULT '0' COMMENT '商品库存，-1表示没有限制',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES ('1', 'iphoneX', 'Apple iPhone X (A1865) 64GB 银色 移动联通电信4G手机', '/img/iphonex.png', 'Apple iPhone X (A1865) 64GB 银色 移动联通电信4G手机', '8765.00', '10000');
INSERT INTO `goods` VALUES ('2', '华为Meta9', '华为 Mate 9 4GB+32GB版 月光银 移动联通电信4G手机 双卡双待', '/img/meta10.png', '华为 Mate 9 4GB+32GB版 月光银 移动联通电信4G手机 双卡双待', '3212.00', '-1');
INSERT INTO `goods` VALUES ('3', 'iphone8', 'Apple iPhone 8 (A1865) 64GB 银色 移动联通电信4G手机', '/img/iphone8.png', 'Apple iPhone 8 (A1865) 64GB 银色 移动联通电信4G手机', '5589.00', '10000');
INSERT INTO `goods` VALUES ('4', '小米6', '小米6 4GB+32GB版 月光银 移动联通电信4G手机 双卡双待', '/img/mi6.png', '小米6 4GB+32GB版 月光银 移动联通电信4G手机 双卡双待', '3212.00', '10000');

-- ----------------------------
-- Table structure for `iplog`
-- ----------------------------
DROP TABLE IF EXISTS `iplog`;
CREATE TABLE `iplog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ip` varchar(50) NOT NULL,
  `loginState` tinyint(4) NOT NULL,
  `username` varchar(50) DEFAULT NULL,
  `loginInfoId` bigint(20) DEFAULT NULL,
  `loginType` tinyint(4) NOT NULL,
  `loginTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=180 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of iplog
-- ----------------------------
INSERT INTO `iplog` VALUES ('1', '0:0:0:0:0:0:0:1', '0', 'stef', null, '0', '2015-12-11 15:30:12');
INSERT INTO `iplog` VALUES ('2', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-11 15:30:34');
INSERT INTO `iplog` VALUES ('3', '192.168.101.237', '0', 'stef', null, '0', '2015-12-11 15:35:29');
INSERT INTO `iplog` VALUES ('4', '192.168.101.237', '0', 'stef', null, '0', '2015-12-11 15:35:36');
INSERT INTO `iplog` VALUES ('5', '192.168.101.237', '1', 'stef', '4', '0', '2015-12-11 15:35:55');
INSERT INTO `iplog` VALUES ('6', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-11 15:52:12');
INSERT INTO `iplog` VALUES ('7', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-11 16:12:26');
INSERT INTO `iplog` VALUES ('8', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-11 16:16:44');
INSERT INTO `iplog` VALUES ('9', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-11 16:19:33');
INSERT INTO `iplog` VALUES ('10', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-11 17:10:35');
INSERT INTO `iplog` VALUES ('11', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-11 17:11:11');
INSERT INTO `iplog` VALUES ('12', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-11 17:21:12');
INSERT INTO `iplog` VALUES ('13', '0:0:0:0:0:0:0:1', '0', 'admin', null, '1', '2015-12-11 17:31:51');
INSERT INTO `iplog` VALUES ('14', '0:0:0:0:0:0:0:1', '1', 'admin', '5', '1', '2015-12-11 17:31:54');
INSERT INTO `iplog` VALUES ('15', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-11 21:41:10');
INSERT INTO `iplog` VALUES ('16', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-11 22:25:31');
INSERT INTO `iplog` VALUES ('17', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-12 00:06:15');
INSERT INTO `iplog` VALUES ('18', '127.0.0.1', '1', 'stef', '4', '0', '2015-12-12 00:06:41');
INSERT INTO `iplog` VALUES ('19', '127.0.0.1', '1', 'stef', '4', '0', '2015-12-12 00:07:19');
INSERT INTO `iplog` VALUES ('20', '127.0.0.1', '1', 'stef', '4', '0', '2015-12-12 00:07:55');
INSERT INTO `iplog` VALUES ('21', '127.0.0.1', '1', 'stef', '4', '0', '2015-12-12 00:08:41');
INSERT INTO `iplog` VALUES ('22', '127.0.0.1', '1', 'stef', '4', '0', '2015-12-12 00:09:31');
INSERT INTO `iplog` VALUES ('23', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-12 00:10:24');
INSERT INTO `iplog` VALUES ('24', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-12 00:27:10');
INSERT INTO `iplog` VALUES ('25', '0:0:0:0:0:0:0:1', '0', 'admin', null, '1', '2015-12-12 00:28:01');
INSERT INTO `iplog` VALUES ('26', '0:0:0:0:0:0:0:1', '0', 'admin', null, '1', '2015-12-12 00:28:05');
INSERT INTO `iplog` VALUES ('27', '0:0:0:0:0:0:0:1', '1', 'admin', '5', '1', '2015-12-12 00:28:28');
INSERT INTO `iplog` VALUES ('28', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-12 00:58:26');
INSERT INTO `iplog` VALUES ('29', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-12 09:42:40');
INSERT INTO `iplog` VALUES ('30', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-12 10:06:31');
INSERT INTO `iplog` VALUES ('31', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-12 10:27:44');
INSERT INTO `iplog` VALUES ('32', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-12 11:32:17');
INSERT INTO `iplog` VALUES ('33', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-12 11:44:50');
INSERT INTO `iplog` VALUES ('34', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-12 11:50:13');
INSERT INTO `iplog` VALUES ('35', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-12 12:04:17');
INSERT INTO `iplog` VALUES ('36', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-12 14:33:56');
INSERT INTO `iplog` VALUES ('37', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-12 14:49:33');
INSERT INTO `iplog` VALUES ('38', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-12 15:41:37');
INSERT INTO `iplog` VALUES ('39', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-12 17:12:22');
INSERT INTO `iplog` VALUES ('40', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-12 17:15:17');
INSERT INTO `iplog` VALUES ('41', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-12 17:28:57');
INSERT INTO `iplog` VALUES ('42', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-13 00:48:59');
INSERT INTO `iplog` VALUES ('43', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-13 19:26:18');
INSERT INTO `iplog` VALUES ('44', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-13 19:29:07');
INSERT INTO `iplog` VALUES ('45', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-13 19:31:44');
INSERT INTO `iplog` VALUES ('46', '0:0:0:0:0:0:0:1', '1', 'admin', '6', '0', '2015-12-13 19:49:49');
INSERT INTO `iplog` VALUES ('47', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-14 16:22:22');
INSERT INTO `iplog` VALUES ('48', '0:0:0:0:0:0:0:1', '0', 'admin', null, '1', '2015-12-14 16:47:25');
INSERT INTO `iplog` VALUES ('49', '0:0:0:0:0:0:0:1', '1', 'admin', '5', '1', '2015-12-14 16:47:28');
INSERT INTO `iplog` VALUES ('50', '0:0:0:0:0:0:0:1', '0', 'admin', null, '1', '2015-12-14 17:17:45');
INSERT INTO `iplog` VALUES ('51', '0:0:0:0:0:0:0:1', '1', 'admin', '5', '1', '2015-12-14 17:17:48');
INSERT INTO `iplog` VALUES ('52', '0:0:0:0:0:0:0:1', '1', 'admin', '5', '1', '2015-12-14 17:20:32');
INSERT INTO `iplog` VALUES ('53', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-14 17:57:08');
INSERT INTO `iplog` VALUES ('54', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-14 18:08:30');
INSERT INTO `iplog` VALUES ('55', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-14 18:10:10');
INSERT INTO `iplog` VALUES ('56', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-14 18:20:43');
INSERT INTO `iplog` VALUES ('57', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-14 18:29:47');
INSERT INTO `iplog` VALUES ('58', '0:0:0:0:0:0:0:1', '1', 'admin', '5', '1', '2015-12-14 20:30:18');
INSERT INTO `iplog` VALUES ('59', '0:0:0:0:0:0:0:1', '1', 'admin', '5', '1', '2015-12-15 00:07:51');
INSERT INTO `iplog` VALUES ('60', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-15 09:59:39');
INSERT INTO `iplog` VALUES ('61', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-15 10:33:24');
INSERT INTO `iplog` VALUES ('62', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-15 10:48:11');
INSERT INTO `iplog` VALUES ('63', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-15 11:11:00');
INSERT INTO `iplog` VALUES ('64', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-15 11:11:45');
INSERT INTO `iplog` VALUES ('65', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-15 11:29:05');
INSERT INTO `iplog` VALUES ('66', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-15 11:41:23');
INSERT INTO `iplog` VALUES ('67', '0:0:0:0:0:0:0:1', '1', 'admin', '5', '1', '2015-12-15 12:03:31');
INSERT INTO `iplog` VALUES ('68', '0:0:0:0:0:0:0:1', '1', 'admin', '5', '1', '2015-12-15 12:05:50');
INSERT INTO `iplog` VALUES ('69', '127.0.0.1', '1', 'admin', '5', '1', '2015-12-15 14:30:18');
INSERT INTO `iplog` VALUES ('70', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-15 14:40:54');
INSERT INTO `iplog` VALUES ('71', '127.0.0.1', '1', 'admin', '5', '1', '2015-12-15 14:41:07');
INSERT INTO `iplog` VALUES ('72', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-15 14:42:38');
INSERT INTO `iplog` VALUES ('73', '127.0.0.1', '1', 'admin', '5', '1', '2015-12-15 14:42:48');
INSERT INTO `iplog` VALUES ('74', '127.0.0.1', '1', 'admin', '5', '1', '2015-12-15 14:44:39');
INSERT INTO `iplog` VALUES ('75', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-15 14:45:26');
INSERT INTO `iplog` VALUES ('76', '127.0.0.1', '1', 'admin', '5', '1', '2015-12-15 14:46:28');
INSERT INTO `iplog` VALUES ('77', '127.0.0.1', '1', 'admin', '5', '1', '2015-12-15 15:16:02');
INSERT INTO `iplog` VALUES ('78', '0:0:0:0:0:0:0:1', '1', 'admin', '5', '1', '2015-12-15 15:27:56');
INSERT INTO `iplog` VALUES ('79', '0:0:0:0:0:0:0:1', '1', 'admin', '5', '1', '2015-12-15 16:03:09');
INSERT INTO `iplog` VALUES ('80', '0:0:0:0:0:0:0:1', '1', 'admin', '5', '1', '2015-12-15 16:08:52');
INSERT INTO `iplog` VALUES ('81', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-15 16:12:39');
INSERT INTO `iplog` VALUES ('82', '0:0:0:0:0:0:0:1', '1', 'stea', '7', '0', '2015-12-15 16:22:11');
INSERT INTO `iplog` VALUES ('83', '0:0:0:0:0:0:0:1', '1', 'admin', '5', '1', '2015-12-15 16:26:30');
INSERT INTO `iplog` VALUES ('84', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-15 17:10:57');
INSERT INTO `iplog` VALUES ('85', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-15 17:22:46');
INSERT INTO `iplog` VALUES ('86', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-15 17:32:21');
INSERT INTO `iplog` VALUES ('87', '0:0:0:0:0:0:0:1', '1', 'admin', '5', '1', '2015-12-15 17:33:53');
INSERT INTO `iplog` VALUES ('88', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-15 17:42:23');
INSERT INTO `iplog` VALUES ('89', '0:0:0:0:0:0:0:1', '1', 'admin', '5', '1', '2015-12-17 09:23:00');
INSERT INTO `iplog` VALUES ('90', '0:0:0:0:0:0:0:1', '1', 'admin', '5', '1', '2015-12-17 09:32:27');
INSERT INTO `iplog` VALUES ('91', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-17 09:34:16');
INSERT INTO `iplog` VALUES ('92', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-17 11:37:12');
INSERT INTO `iplog` VALUES ('93', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-17 14:52:20');
INSERT INTO `iplog` VALUES ('94', '0:0:0:0:0:0:0:1', '1', 'admin', '5', '1', '2015-12-17 15:30:32');
INSERT INTO `iplog` VALUES ('95', '0:0:0:0:0:0:0:1', '1', 'admin', '5', '1', '2015-12-17 15:47:41');
INSERT INTO `iplog` VALUES ('96', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-17 15:48:22');
INSERT INTO `iplog` VALUES ('97', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-17 20:01:49');
INSERT INTO `iplog` VALUES ('98', '0:0:0:0:0:0:0:1', '1', 'stea', '7', '0', '2015-12-17 20:55:13');
INSERT INTO `iplog` VALUES ('99', '0:0:0:0:0:0:0:1', '1', 'admin', '5', '1', '2015-12-17 22:04:01');
INSERT INTO `iplog` VALUES ('100', '0:0:0:0:0:0:0:1', '1', 'admin', '5', '1', '2015-12-17 22:44:39');
INSERT INTO `iplog` VALUES ('101', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-17 22:45:13');
INSERT INTO `iplog` VALUES ('102', '0:0:0:0:0:0:0:1', '1', 'admin', '5', '1', '2015-12-18 09:37:40');
INSERT INTO `iplog` VALUES ('103', '0:0:0:0:0:0:0:1', '1', 'admin', '5', '1', '2015-12-18 09:39:34');
INSERT INTO `iplog` VALUES ('104', '0:0:0:0:0:0:0:1', '1', 'admin', '5', '1', '2015-12-18 09:41:53');
INSERT INTO `iplog` VALUES ('105', '0:0:0:0:0:0:0:1', '1', 'admin', '5', '1', '2015-12-18 09:45:00');
INSERT INTO `iplog` VALUES ('106', '0:0:0:0:0:0:0:1', '1', 'admin', '5', '1', '2015-12-18 09:46:45');
INSERT INTO `iplog` VALUES ('107', '0:0:0:0:0:0:0:1', '1', 'admin', '5', '1', '2015-12-18 11:44:19');
INSERT INTO `iplog` VALUES ('108', '0:0:0:0:0:0:0:1', '1', 'admin', '5', '1', '2015-12-18 11:45:11');
INSERT INTO `iplog` VALUES ('109', '0:0:0:0:0:0:0:1', '1', 'admin', '5', '1', '2015-12-18 14:43:59');
INSERT INTO `iplog` VALUES ('110', '0:0:0:0:0:0:0:1', '1', 'admin', '5', '1', '2015-12-18 15:14:19');
INSERT INTO `iplog` VALUES ('111', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-18 16:33:32');
INSERT INTO `iplog` VALUES ('112', '0:0:0:0:0:0:0:1', '0', 'sta', null, '0', '2015-12-18 16:35:42');
INSERT INTO `iplog` VALUES ('113', '0:0:0:0:0:0:0:1', '1', 'stea', '7', '0', '2015-12-18 16:35:47');
INSERT INTO `iplog` VALUES ('114', '0:0:0:0:0:0:0:1', '1', 'stea', '7', '0', '2015-12-18 16:36:42');
INSERT INTO `iplog` VALUES ('115', '0:0:0:0:0:0:0:1', '1', 'admin', '5', '1', '2015-12-18 17:09:46');
INSERT INTO `iplog` VALUES ('116', '0:0:0:0:0:0:0:1', '1', 'admin', '5', '1', '2015-12-18 17:13:29');
INSERT INTO `iplog` VALUES ('117', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-18 17:16:08');
INSERT INTO `iplog` VALUES ('118', '0:0:0:0:0:0:0:1', '1', 'admin', '5', '1', '2015-12-19 12:08:57');
INSERT INTO `iplog` VALUES ('119', '0:0:0:0:0:0:0:1', '1', 'admin', '5', '1', '2015-12-19 15:34:05');
INSERT INTO `iplog` VALUES ('120', '0:0:0:0:0:0:0:1', '1', 'admin', '5', '1', '2015-12-19 15:38:20');
INSERT INTO `iplog` VALUES ('121', '0:0:0:0:0:0:0:1', '1', 'admin', '5', '1', '2015-12-19 17:19:40');
INSERT INTO `iplog` VALUES ('122', '0:0:0:0:0:0:0:1', '1', 'admin', '5', '1', '2015-12-19 17:56:38');
INSERT INTO `iplog` VALUES ('123', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-19 18:15:18');
INSERT INTO `iplog` VALUES ('124', '0:0:0:0:0:0:0:1', '1', 'admin', '5', '1', '2015-12-19 21:13:01');
INSERT INTO `iplog` VALUES ('125', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-19 23:08:42');
INSERT INTO `iplog` VALUES ('126', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-21 10:16:43');
INSERT INTO `iplog` VALUES ('127', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-23 10:07:24');
INSERT INTO `iplog` VALUES ('128', '0:0:0:0:0:0:0:1', '1', 'stea', '7', '0', '2015-12-23 10:07:31');
INSERT INTO `iplog` VALUES ('129', '0:0:0:0:0:0:0:1', '1', 'stea', '7', '0', '2015-12-23 10:09:16');
INSERT INTO `iplog` VALUES ('130', '127.0.0.1', '1', 'stea', '7', '0', '2015-12-23 10:15:04');
INSERT INTO `iplog` VALUES ('131', '0:0:0:0:0:0:0:1', '1', 'stea', '7', '0', '2015-12-23 10:44:25');
INSERT INTO `iplog` VALUES ('132', '0:0:0:0:0:0:0:1', '1', 'admin', '5', '1', '2015-12-23 10:58:06');
INSERT INTO `iplog` VALUES ('133', '0:0:0:0:0:0:0:1', '1', 'admin', '5', '1', '2015-12-23 10:59:53');
INSERT INTO `iplog` VALUES ('134', '0:0:0:0:0:0:0:1', '1', 'admin', '5', '1', '2015-12-23 11:34:17');
INSERT INTO `iplog` VALUES ('135', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-23 11:35:05');
INSERT INTO `iplog` VALUES ('136', '0:0:0:0:0:0:0:1', '1', 'stea', '7', '0', '2015-12-23 11:35:08');
INSERT INTO `iplog` VALUES ('137', '0:0:0:0:0:0:0:1', '1', 'stea', '7', '0', '2015-12-23 15:26:33');
INSERT INTO `iplog` VALUES ('138', '0:0:0:0:0:0:0:1', '1', 'stea', '7', '0', '2015-12-23 16:17:49');
INSERT INTO `iplog` VALUES ('139', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-23 16:20:33');
INSERT INTO `iplog` VALUES ('140', '0:0:0:0:0:0:0:1', '1', 'stee', '10', '0', '2015-12-23 16:20:36');
INSERT INTO `iplog` VALUES ('141', '0:0:0:0:0:0:0:1', '1', 'admin', '5', '1', '2015-12-23 16:21:18');
INSERT INTO `iplog` VALUES ('142', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-23 16:35:28');
INSERT INTO `iplog` VALUES ('143', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-23 16:47:57');
INSERT INTO `iplog` VALUES ('144', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-23 17:01:50');
INSERT INTO `iplog` VALUES ('145', '0:0:0:0:0:0:0:1', '1', 'stee', '10', '0', '2015-12-23 17:03:30');
INSERT INTO `iplog` VALUES ('146', '0:0:0:0:0:0:0:1', '1', 'stee', '10', '0', '2015-12-23 17:05:10');
INSERT INTO `iplog` VALUES ('147', '0:0:0:0:0:0:0:1', '1', 'stee', '10', '0', '2015-12-23 17:09:05');
INSERT INTO `iplog` VALUES ('148', '0:0:0:0:0:0:0:1', '1', 'admin', '5', '1', '2015-12-24 09:07:42');
INSERT INTO `iplog` VALUES ('149', '0:0:0:0:0:0:0:1', '1', 'admin', '5', '1', '2015-12-24 10:03:21');
INSERT INTO `iplog` VALUES ('150', '0:0:0:0:0:0:0:1', '1', 'admin', '5', '1', '2015-12-24 10:06:12');
INSERT INTO `iplog` VALUES ('151', '0:0:0:0:0:0:0:1', '1', 'admin', '5', '1', '2015-12-24 10:30:35');
INSERT INTO `iplog` VALUES ('152', '0:0:0:0:0:0:0:1', '1', 'admin', '5', '1', '2015-12-24 15:58:57');
INSERT INTO `iplog` VALUES ('153', '0:0:0:0:0:0:0:1', '1', 'stef', '4', '0', '2015-12-24 16:00:12');
INSERT INTO `iplog` VALUES ('154', '0:0:0:0:0:0:0:1', '1', 'stee', '10', '0', '2015-12-24 16:01:33');
INSERT INTO `iplog` VALUES ('155', '0:0:0:0:0:0:0:1', '1', 'stea', '7', '0', '2015-12-24 16:01:47');
INSERT INTO `iplog` VALUES ('156', '127.0.0.1', '1', 'stef', '4', '0', '2019-01-15 19:33:27');
INSERT INTO `iplog` VALUES ('157', '127.0.0.1', '1', 'stef', '4', '0', '2019-01-15 19:35:12');
INSERT INTO `iplog` VALUES ('158', '127.0.0.1', '1', 'stef', '4', '0', '2019-01-15 19:37:57');
INSERT INTO `iplog` VALUES ('159', '127.0.0.1', '1', 'stea', '7', '0', '2019-01-15 19:38:52');
INSERT INTO `iplog` VALUES ('160', '127.0.0.1', '1', 'stef', '4', '0', '2019-01-16 10:03:20');
INSERT INTO `iplog` VALUES ('161', '127.0.0.1', '1', 'qiurunze', '16', '0', '2019-01-17 10:59:16');
INSERT INTO `iplog` VALUES ('162', '127.0.0.1', '1', 'qiurunze', '16', '0', '2019-01-17 10:56:56');
INSERT INTO `iplog` VALUES ('163', '127.0.0.1', '1', 'qiurunze', '12', '0', '2019-01-17 11:02:46');
INSERT INTO `iplog` VALUES ('164', '127.0.0.1', '1', 'qiurunze', '13', '0', '2019-01-17 11:07:13');
INSERT INTO `iplog` VALUES ('165', '127.0.0.1', '1', 'stef', '4', '0', '2019-01-18 18:57:49');
INSERT INTO `iplog` VALUES ('166', '127.0.0.1', '1', 'stef', '4', '0', '2019-01-23 19:11:34');
INSERT INTO `iplog` VALUES ('167', '127.0.0.1', '1', 'stef', '4', '0', '2019-01-23 19:23:59');
INSERT INTO `iplog` VALUES ('168', '127.0.0.1', '1', 'stef', '4', '0', '2019-01-23 19:24:19');
INSERT INTO `iplog` VALUES ('169', '127.0.0.1', '0', 'stef', null, '0', '2019-01-25 17:51:50');
INSERT INTO `iplog` VALUES ('170', '127.0.0.1', '0', 'stef', null, '0', '2019-01-25 17:51:51');
INSERT INTO `iplog` VALUES ('171', '127.0.0.1', '0', 'qiurunze', null, '0', '2019-01-25 18:00:30');
INSERT INTO `iplog` VALUES ('172', '127.0.0.1', '1', 'qiurunze11', '37', '0', '2019-01-25 18:04:56');
INSERT INTO `iplog` VALUES ('173', '127.0.0.1', '1', 'qiurunze11', '37', '0', '2019-01-25 18:05:58');
INSERT INTO `iplog` VALUES ('174', '127.0.0.1', '1', 'qiurunze11', '37', '0', '2019-01-25 18:06:45');
INSERT INTO `iplog` VALUES ('175', '127.0.0.1', '1', 'qiurunze11', '37', '0', '2019-01-25 18:06:58');
INSERT INTO `iplog` VALUES ('176', '127.0.0.1', '1', 'qiurunze11', '37', '0', '2019-01-25 18:09:12');
INSERT INTO `iplog` VALUES ('177', '127.0.0.1', '0', 'stef', null, '0', '2019-01-25 19:16:31');
INSERT INTO `iplog` VALUES ('178', '127.0.0.1', '0', 'stef', null, '0', '2019-01-25 19:32:31');
INSERT INTO `iplog` VALUES ('179', '127.0.0.1', '1', 'qiurunze', '38', '0', '2019-01-31 10:39:20');

-- ----------------------------
-- Table structure for `miaosha_goods`
-- ----------------------------
DROP TABLE IF EXISTS `miaosha_goods`;
CREATE TABLE `miaosha_goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '秒杀的商品表',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品Id',
  `miaosha_price` decimal(10,2) DEFAULT '0.00' COMMENT '秒杀价',
  `stock_count` int(11) DEFAULT NULL COMMENT '库存数量',
  `start_date` datetime DEFAULT NULL COMMENT '秒杀开始时间',
  `end_date` datetime DEFAULT NULL COMMENT '秒杀结束时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of miaosha_goods
-- ----------------------------
INSERT INTO `miaosha_goods` VALUES ('1', '1', '0.01', '0', '2019-02-01 21:51:23', '2019-09-01 21:51:27');
INSERT INTO `miaosha_goods` VALUES ('2', '2', '0.01', '7', '2017-12-04 21:40:14', '2017-12-31 14:00:24');
INSERT INTO `miaosha_goods` VALUES ('3', '3', '0.01', '9', '2017-12-04 21:40:14', '2017-12-31 14:00:24');
INSERT INTO `miaosha_goods` VALUES ('4', '4', '0.01', '9', '2017-12-04 21:40:14', '2017-12-31 14:00:24');

-- ----------------------------
-- Table structure for `miaosha_message`
-- ----------------------------
DROP TABLE IF EXISTS `miaosha_message`;
CREATE TABLE `miaosha_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '消息主键',
  `messageid` bigint(20) NOT NULL COMMENT '分布式id',
  `content` text COMMENT '消息内容',
  `create_time` date DEFAULT NULL COMMENT '创建时间',
  `status` int(1) NOT NULL COMMENT '1 有效 2 失效 ',
  `over_time` datetime DEFAULT NULL COMMENT '结束时间',
  `message_type` int(1) DEFAULT '3' COMMENT '0 秒杀消息 1 购买消息 2 推送消息',
  `send_type` int(1) DEFAULT '3' COMMENT '发送类型 0 app 1 pc 2 ios',
  `good_name` varchar(50) DEFAULT '' COMMENT '商品名称',
  `price` decimal(10,2) DEFAULT '0.00' COMMENT '商品价格',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of miaosha_message
-- ----------------------------
INSERT INTO `miaosha_message` VALUES ('1', '533324506110885888', '尊敬的用户你好，你已经成功注册！', null, '0', null, null, '0', null, null);
INSERT INTO `miaosha_message` VALUES ('2', '533324506110885888', '尊敬的用户你好，你已经成功注册！', null, '0', null, null, '0', null, null);
INSERT INTO `miaosha_message` VALUES ('3', '533324506110885888', '尊敬的用户你好，你已经成功注册！', '2019-01-11', '0', null, null, '0', null, null);
INSERT INTO `miaosha_message` VALUES ('4', '533324506110885888', '尊敬的用户你好，你已经成功注册！', '2019-01-11', '0', null, null, '0', null, null);

-- ----------------------------
-- Table structure for `miaosha_message_user`
-- ----------------------------
DROP TABLE IF EXISTS `miaosha_message_user`;
CREATE TABLE `miaosha_message_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userid` bigint(20) NOT NULL,
  `messageid` bigint(50) NOT NULL,
  `goodid` int(20) DEFAULT NULL,
  `orderid` int(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of miaosha_message_user
-- ----------------------------
INSERT INTO `miaosha_message_user` VALUES ('1', '1', '222', '22', '2');
INSERT INTO `miaosha_message_user` VALUES ('11', '22', '533324506110885888', null, null);
INSERT INTO `miaosha_message_user` VALUES ('12', '22', '533324506110885888', null, null);
INSERT INTO `miaosha_message_user` VALUES ('13', '22', '533324506110885888', null, null);
INSERT INTO `miaosha_message_user` VALUES ('14', '22', '533324506110885888', null, null);

-- ----------------------------
-- Table structure for `miaosha_order`
-- ----------------------------
DROP TABLE IF EXISTS `miaosha_order`;
CREATE TABLE `miaosha_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `order_id` bigint(20) DEFAULT NULL COMMENT '订单ID',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_uid_gid` (`user_id`,`goods_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1562 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of miaosha_order
-- ----------------------------
INSERT INTO `miaosha_order` VALUES ('1556', '18513103817', '1570', '1');
INSERT INTO `miaosha_order` VALUES ('1557', '18513103817', '1571', '2');
INSERT INTO `miaosha_order` VALUES ('1558', '18911456390', '1572', '1');
INSERT INTO `miaosha_order` VALUES ('1559', '17612505625', '1573', '1');
INSERT INTO `miaosha_order` VALUES ('1560', '18612766138', '1574', '1');
INSERT INTO `miaosha_order` VALUES ('1561', '15779573071', '1575', '1');

-- ----------------------------
-- Table structure for `miaosha_user`
-- ----------------------------
DROP TABLE IF EXISTS `miaosha_user`;
CREATE TABLE `miaosha_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID，手机号码',
  `nickname` varchar(255) NOT NULL,
  `password` varchar(32) DEFAULT NULL COMMENT 'MD5(MD5(pass明文+固定salt) + salt)',
  `salt` varchar(20) DEFAULT NULL,
  `head` varchar(128) DEFAULT NULL COMMENT '头像，云存储的ID',
  `register_date` datetime DEFAULT NULL COMMENT '注册时间',
  `last_login_date` datetime DEFAULT NULL COMMENT '上蔟登录时间',
  `login_count` int(11) DEFAULT '0' COMMENT '登录次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18912341258 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of miaosha_user
-- ----------------------------
INSERT INTO `miaosha_user` VALUES ('18912341238', '18612766138', 'b7797cce01b4b131b433b6acf4add449', '1a2b3c4d', null, '2019-01-09 17:08:16', null, '0');
INSERT INTO `miaosha_user` VALUES ('18912341248', '18612766122', 'fac9465b740927c2022bec2c9d34ff65', '1a2b3c4d', null, '2019-02-03 13:29:39', null, '0');
INSERT INTO `miaosha_user` VALUES ('18912341253', 'qiurunze1234', '3b73de3931d4e763036c7d1c57f8da06', 'rfHWSXBSgGak+692sE3J', null, '2019-02-07 09:52:59', null, '0');
INSERT INTO `miaosha_user` VALUES ('18912341254', 'qiurunze12345', '5d50fdef255f945bfd716c11bf1c7395', 'S1/dD1b1SHxCFmatEk53', null, '2019-02-07 09:53:14', null, '0');
INSERT INTO `miaosha_user` VALUES ('18912341255', 'qiurunze111', '8afabe7ab668033b1a165ab02ac0563a', '3dugTaLzrFcATKRsiqd5', null, '2019-02-07 10:00:32', null, '0');
INSERT INTO `miaosha_user` VALUES ('18912341256', 'qiurunze123', 'ab4322e6432970fb983118c14d05b577', 'hQmJaNOKYw6e2TzANDEj', null, '2019-02-10 01:14:23', null, '0');
INSERT INTO `miaosha_user` VALUES ('18912341257', 'qq111111', 'c4cf5cfe364c46812f59437360e0fb4b', 'FhIDGaeblUQQFMGzS+Tp', null, '2019-02-10 01:22:55', null, '0');

-- ----------------------------
-- Table structure for `order_info`
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品ID',
  `delivery_addr_id` bigint(20) DEFAULT NULL COMMENT '收获地址ID',
  `goods_name` varchar(16) DEFAULT NULL COMMENT '冗余过来的商品名称',
  `goods_count` int(11) DEFAULT '0' COMMENT '商品数量',
  `goods_price` decimal(10,2) DEFAULT '0.00' COMMENT '商品单价',
  `order_channel` tinyint(4) DEFAULT '0' COMMENT '1pc，2android，3ios',
  `status` tinyint(4) DEFAULT '0' COMMENT '订单状态，0新建未支付，1已支付，2已发货，3已收货，4已退款，5已完成',
  `create_date` datetime DEFAULT NULL COMMENT '订单的创建时间',
  `pay_date` datetime DEFAULT NULL COMMENT '支付时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1576 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of order_info
-- ----------------------------
INSERT INTO `order_info` VALUES ('1570', '18513103817', '1', null, 'iphoneX', '1', '0.01', '1', '0', '2019-02-03 20:26:18', null);
INSERT INTO `order_info` VALUES ('1571', '18513103817', '2', null, '华为Meta9', '1', '0.01', '1', '0', '2019-02-03 20:26:18', null);
INSERT INTO `order_info` VALUES ('1572', '18911456390', '1', null, 'iphoneX', '1', '0.01', '1', '0', '2019-02-03 20:26:18', null);
INSERT INTO `order_info` VALUES ('1573', '17612505625', '1', null, 'iphoneX', '1', '0.01', '1', '0', '2019-02-03 20:26:18', null);
INSERT INTO `order_info` VALUES ('1574', '18612766138', '1', null, 'iphoneX', '1', '0.01', '1', '0', '2019-02-03 20:26:18', null);
INSERT INTO `order_info` VALUES ('1575', '15779573071', '1', null, 'iphoneX', '1', '0.01', '1', '0', '2019-02-13 03:24:07', null);

-- ----------------------------
-- Table structure for `teacher`
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `t_id` int(11) NOT NULL AUTO_INCREMENT,
  `t_name` varchar(20) DEFAULT NULL,
  `u_id` int(20) DEFAULT NULL,
  PRIMARY KEY (`t_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES ('1', 'wwww', '2');
INSERT INTO `teacher` VALUES ('2', '223e232', '1');

-- ----------------------------
-- Table structure for `users`
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) DEFAULT NULL,
  `age` int(100) DEFAULT NULL,
  `_address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ss` (`name`,`age`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', 'Joshua', '22', 'qwer');
INSERT INTO `users` VALUES ('2', '2e2', '23', 'xcsdc');
