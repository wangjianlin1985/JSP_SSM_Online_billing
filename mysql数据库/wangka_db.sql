/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50051
Source Host           : localhost:3306
Source Database       : wangka_db

Target Server Type    : MYSQL
Target Server Version : 50051
File Encoding         : 65001

Date: 2019-05-27 15:17:21
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `username` varchar(20) NOT NULL default '',
  `password` varchar(32) default NULL,
  PRIMARY KEY  (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('a', 'a');

-- ----------------------------
-- Table structure for `t_callinfo`
-- ----------------------------
DROP TABLE IF EXISTS `t_callinfo`;
CREATE TABLE `t_callinfo` (
  `callId` int(11) NOT NULL auto_increment COMMENT '记录id',
  `userObj` varchar(30) NOT NULL COMMENT '呼叫会员',
  `callTime` varchar(20) default NULL COMMENT '呼叫时间',
  `handFlag` varchar(20) NOT NULL COMMENT '处理状态',
  `handMemo` varchar(500) default NULL COMMENT '处理备注',
  PRIMARY KEY  (`callId`),
  KEY `userObj` (`userObj`),
  CONSTRAINT `t_callinfo_ibfk_1` FOREIGN KEY (`userObj`) REFERENCES `t_userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_callinfo
-- ----------------------------
INSERT INTO `t_callinfo` VALUES ('1', '1001', '2019-05-24 17:20:25', '已处理', '--');
INSERT INTO `t_callinfo` VALUES ('2', '1002', '2019-05-26 16:37:14', '已处理', '我电脑卡死了！');
INSERT INTO `t_callinfo` VALUES ('3', '1002', '2019-05-27 15:10:22', '已处理', '我肚子饿死了，网管先给我来点饼干！马上来！');

-- ----------------------------
-- Table structure for `t_charging`
-- ----------------------------
DROP TABLE IF EXISTS `t_charging`;
CREATE TABLE `t_charging` (
  `chargingId` int(11) NOT NULL auto_increment COMMENT '计费id',
  `chargingName` varchar(20) NOT NULL COMMENT '计费名称',
  `chargingMoney` float NOT NULL COMMENT '计费金额',
  `moneyWay` varchar(20) NOT NULL COMMENT '缴费算法',
  `chargingMemo` varchar(800) NOT NULL COMMENT '计费说明',
  PRIMARY KEY  (`chargingId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_charging
-- ----------------------------
INSERT INTO `t_charging` VALUES ('1', '小时计费', '5', '按小时数算', '不足半个小时算半个小时');
INSERT INTO `t_charging` VALUES ('2', '包夜', '20', '一次买断', '从晚上10点到第2天早上6点，一共8个小时');

-- ----------------------------
-- Table structure for `t_computer`
-- ----------------------------
DROP TABLE IF EXISTS `t_computer`;
CREATE TABLE `t_computer` (
  `computerNo` varchar(20) NOT NULL COMMENT 'computerNo',
  `computerName` varchar(20) NOT NULL COMMENT '计算机名称',
  `area` varchar(20) NOT NULL COMMENT '所在区域',
  `computerPhoto` varchar(60) NOT NULL COMMENT '计算机照片',
  `computerDesc` varchar(8000) NOT NULL COMMENT '计算机描述',
  `computerState` varchar(20) NOT NULL COMMENT '电脑状态',
  `addTime` varchar(20) default NULL COMMENT '添加时间',
  PRIMARY KEY  (`computerNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_computer
-- ----------------------------
INSERT INTO `t_computer` VALUES ('CP001', '游戏娱乐电脑1', 'A区', 'upload/cfccf9bd-bdaf-4aaa-b954-28c470b86208.jpg', '<p>很好用的电脑，配置高，速度快！</p>', '空闲中', '2019-05-24 17:17:58');
INSERT INTO `t_computer` VALUES ('CP002', '游戏娱乐电脑12', 'A区', 'upload/cfccf9bd-bdaf-4aaa-b954-28c470b86208.jpg', '<p>好电脑</p>', '空闲中', '2019-05-26 13:31:32');
INSERT INTO `t_computer` VALUES ('CP003', '游戏娱乐电脑13', 'A区', 'upload/cfccf9bd-bdaf-4aaa-b954-28c470b86208.jpg', '<p>好电脑</p>', '空闲中', '2019-05-26 13:32:01');
INSERT INTO `t_computer` VALUES ('CP004', '游戏娱乐电脑14', 'B区', 'upload/cfccf9bd-bdaf-4aaa-b954-28c470b86208.jpg', '<p>好电脑</p>', '使用中', '2019-05-26 13:36:39');

-- ----------------------------
-- Table structure for `t_computeruse`
-- ----------------------------
DROP TABLE IF EXISTS `t_computeruse`;
CREATE TABLE `t_computeruse` (
  `cuId` int(11) NOT NULL auto_increment COMMENT '记录id',
  `userObj` varchar(30) NOT NULL COMMENT '上机用户',
  `computerObj` varchar(20) NOT NULL COMMENT '使用电脑',
  `chargingObj` int(11) NOT NULL COMMENT '计费方式',
  `startTime` varchar(20) default NULL COMMENT '上机时间',
  `endTime` varchar(20) default NULL COMMENT '下机时间',
  `jiezhangFlag` varchar(20) NOT NULL COMMENT '是否结账',
  `useMoney` float NOT NULL COMMENT '结账金额',
  `memo` varchar(500) default NULL COMMENT '备注信息',
  PRIMARY KEY  (`cuId`),
  KEY `userObj` (`userObj`),
  KEY `computerObj` (`computerObj`),
  KEY `chargingObj` (`chargingObj`),
  CONSTRAINT `t_computeruse_ibfk_1` FOREIGN KEY (`userObj`) REFERENCES `t_userinfo` (`user_name`),
  CONSTRAINT `t_computeruse_ibfk_2` FOREIGN KEY (`computerObj`) REFERENCES `t_computer` (`computerNo`),
  CONSTRAINT `t_computeruse_ibfk_3` FOREIGN KEY (`chargingObj`) REFERENCES `t_charging` (`chargingId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_computeruse
-- ----------------------------
INSERT INTO `t_computeruse` VALUES ('1', '1001', 'CP001', '1', '2019-05-26 14:18:06', '2019-05-26 15:50:08', '是', '10', '测试');
INSERT INTO `t_computeruse` VALUES ('2', '1001', 'CP002', '2', '2019-05-19 10:55:16', '2019-05-20 07:57:54', '是', '20', '测试');
INSERT INTO `t_computeruse` VALUES ('3', '1002', 'CP001', '1', '2019-05-27 14:00:02', '2019-05-27 15:15:07', '是', '7.5', '下机结账测试，上机不足半小时算半小时价格');

-- ----------------------------
-- Table structure for `t_dish`
-- ----------------------------
DROP TABLE IF EXISTS `t_dish`;
CREATE TABLE `t_dish` (
  `dishNo` varchar(20) NOT NULL COMMENT 'dishNo',
  `dishClassObj` int(11) NOT NULL COMMENT '菜品类别',
  `dishName` varchar(20) NOT NULL COMMENT '菜品名称',
  `dishPhoto` varchar(60) NOT NULL COMMENT '菜品图片',
  `dishPrice` float NOT NULL COMMENT '菜品单价',
  `tuijianFlag` varchar(20) NOT NULL COMMENT '是否推荐',
  `upState` varchar(20) NOT NULL COMMENT '上架状态',
  `viewNum` int(11) NOT NULL COMMENT '浏览量',
  `dishDesc` varchar(8000) NOT NULL COMMENT '菜品描述',
  `addTime` varchar(20) default NULL COMMENT '添加时间',
  PRIMARY KEY  (`dishNo`),
  KEY `dishClassObj` (`dishClassObj`),
  CONSTRAINT `t_dish_ibfk_1` FOREIGN KEY (`dishClassObj`) REFERENCES `t_dishclass` (`dishClassId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_dish
-- ----------------------------
INSERT INTO `t_dish` VALUES ('CP001', '1', '青椒肉丝', 'upload/025e80ba-2013-4b23-859d-0dbb1eefc06a.jpg', '15', '是', '上架中', '2', '<p>美味佳肴，值得推荐</p>', '2019-05-24 17:19:46');
INSERT INTO `t_dish` VALUES ('CP002', '1', '炝炒莲白', 'upload/cf3e5faf-01d6-4278-8129-2ccf9d43c590.jpg', '12', '是', '上架中', '2', '<p>很好吃的白菜</p>', '2019-05-26 13:10:30');

-- ----------------------------
-- Table structure for `t_dishclass`
-- ----------------------------
DROP TABLE IF EXISTS `t_dishclass`;
CREATE TABLE `t_dishclass` (
  `dishClassId` int(11) NOT NULL auto_increment COMMENT '菜品分类id',
  `dishClassName` varchar(20) NOT NULL COMMENT '菜品分类名称',
  PRIMARY KEY  (`dishClassId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_dishclass
-- ----------------------------
INSERT INTO `t_dishclass` VALUES ('1', '炒菜类');
INSERT INTO `t_dishclass` VALUES ('2', '炖菜类');

-- ----------------------------
-- Table structure for `t_dishorder`
-- ----------------------------
DROP TABLE IF EXISTS `t_dishorder`;
CREATE TABLE `t_dishorder` (
  `orderId` int(11) NOT NULL auto_increment COMMENT '点餐id',
  `dishObj` varchar(20) NOT NULL COMMENT '菜品名称',
  `dishNum` int(11) NOT NULL COMMENT '订餐数量',
  `dishMoney` float NOT NULL COMMENT '菜品金额',
  `orderMemo` varchar(500) default NULL COMMENT '订单备注',
  `userObj` varchar(30) NOT NULL COMMENT '点餐用户',
  `orderTime` varchar(20) default NULL COMMENT '点餐时间',
  `orderState` varchar(20) NOT NULL COMMENT '订单状态',
  PRIMARY KEY  (`orderId`),
  KEY `dishObj` (`dishObj`),
  KEY `userObj` (`userObj`),
  CONSTRAINT `t_dishorder_ibfk_1` FOREIGN KEY (`dishObj`) REFERENCES `t_dish` (`dishNo`),
  CONSTRAINT `t_dishorder_ibfk_2` FOREIGN KEY (`userObj`) REFERENCES `t_userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_dishorder
-- ----------------------------
INSERT INTO `t_dishorder` VALUES ('1', 'CP001', '2', '30', '快点，饿死了', '1001', '2019-05-24 17:20:04', '已送餐');
INSERT INTO `t_dishorder` VALUES ('2', 'CP002', '1', '12', '快点送来吃', '1001', '2019-05-26 13:22:36', '已付款');
INSERT INTO `t_dishorder` VALUES ('3', 'CP002', '2', '24', '有钱了，快送餐来吧！', '1002', '2019-05-27 15:08:23', '已送餐');

-- ----------------------------
-- Table structure for `t_leaveword`
-- ----------------------------
DROP TABLE IF EXISTS `t_leaveword`;
CREATE TABLE `t_leaveword` (
  `leaveWordId` int(11) NOT NULL auto_increment COMMENT '留言id',
  `leaveTitle` varchar(80) NOT NULL COMMENT '留言标题',
  `leaveContent` varchar(2000) NOT NULL COMMENT '留言内容',
  `userObj` varchar(30) NOT NULL COMMENT '留言人',
  `leaveTime` varchar(20) default NULL COMMENT '留言时间',
  `replyContent` varchar(1000) default NULL COMMENT '管理回复',
  `replyTime` varchar(20) default NULL COMMENT '回复时间',
  PRIMARY KEY  (`leaveWordId`),
  KEY `userObj` (`userObj`),
  CONSTRAINT `t_leaveword_ibfk_1` FOREIGN KEY (`userObj`) REFERENCES `t_userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_leaveword
-- ----------------------------
INSERT INTO `t_leaveword` VALUES ('1', '1111', '222', '1001', '2019-05-24 17:20:17', '3333', '2019-05-24 17:20:21');
INSERT INTO `t_leaveword` VALUES ('2', 'cccc', 'dddd', '1001', '2019-05-25 16:57:01', '--', '--');

-- ----------------------------
-- Table structure for `t_notice`
-- ----------------------------
DROP TABLE IF EXISTS `t_notice`;
CREATE TABLE `t_notice` (
  `noticeId` int(11) NOT NULL auto_increment COMMENT '公告id',
  `title` varchar(80) NOT NULL COMMENT '标题',
  `content` varchar(5000) NOT NULL COMMENT '公告内容',
  `publishDate` varchar(20) default NULL COMMENT '发布时间',
  PRIMARY KEY  (`noticeId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_notice
-- ----------------------------
INSERT INTO `t_notice` VALUES ('1', '网咖管理系统开通', '<p>要上机的朋友们来这里，可以进行各种服务哦！</p>', '2019-05-24 17:20:40');

-- ----------------------------
-- Table structure for `t_recharge`
-- ----------------------------
DROP TABLE IF EXISTS `t_recharge`;
CREATE TABLE `t_recharge` (
  `rechargeId` int(11) NOT NULL auto_increment COMMENT '充值id',
  `userObj` varchar(30) NOT NULL COMMENT '充值用户',
  `chargeMoney` float NOT NULL COMMENT '充值金额',
  `chargeTime` varchar(20) default NULL COMMENT '充值时间',
  `chargeMemo` varchar(500) default NULL COMMENT '备注信息',
  PRIMARY KEY  (`rechargeId`),
  KEY `userObj` (`userObj`),
  CONSTRAINT `t_recharge_ibfk_1` FOREIGN KEY (`userObj`) REFERENCES `t_userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_recharge
-- ----------------------------
INSERT INTO `t_recharge` VALUES ('1', '1001', '50', '2019-05-24 17:18:31', '测试');
INSERT INTO `t_recharge` VALUES ('2', '1002', '100', '2019-05-27 15:04:24', '测试');

-- ----------------------------
-- Table structure for `t_userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `t_userinfo`;
CREATE TABLE `t_userinfo` (
  `user_name` varchar(30) NOT NULL COMMENT 'user_name',
  `password` varchar(30) NOT NULL COMMENT '登录密码',
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `gender` varchar(4) NOT NULL COMMENT '性别',
  `birthDate` varchar(20) default NULL COMMENT '出生日期',
  `userPhoto` varchar(60) NOT NULL COMMENT '会员照片',
  `telephone` varchar(20) NOT NULL COMMENT '联系电话',
  `email` varchar(50) NOT NULL COMMENT '邮箱',
  `address` varchar(80) default NULL COMMENT '家庭地址',
  `userMoney` float NOT NULL COMMENT '会员余额',
  `regTime` varchar(20) default NULL COMMENT '注册时间',
  PRIMARY KEY  (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_userinfo
-- ----------------------------
INSERT INTO `t_userinfo` VALUES ('1001', '123', '张晓丽', '女', '2019-05-13', 'upload/224d7bed-9395-4530-b551-ca0172cfb433.jpg', '13958342342', 'xiaoli@163.com', '四川成都红星路13号aa', '300', '2019-05-24 17:16:14');
INSERT INTO `t_userinfo` VALUES ('1002', '123', '王晓芬', '女', '2019-05-15', 'upload/d0ca21e4-ef7b-435a-a143-19d9d2cb29e6.jpg', '13573598343', 'xiaofen@163.com', '四川南充滨江路', '76', '2019-05-25 16:49:16');
