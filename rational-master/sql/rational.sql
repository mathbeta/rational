/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50150
Source Host           : localhost:3306
Source Database       : rational

Target Server Type    : MYSQL
Target Server Version : 50150
File Encoding         : 65001

Date: 2017-04-16 15:43:29
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `ra_app`
-- ----------------------------
DROP TABLE IF EXISTS `ra_app`;
CREATE TABLE `ra_app` (
  `id` varchar(50) NOT NULL,
  `name` varchar(256) NOT NULL COMMENT '应用名称',
  `image` varchar(512) NOT NULL COMMENT '应用使用的镜像',
  `replicas` int(11) DEFAULT '1' COMMENT '应用副本数',
  `cluster_id` varchar(50) NOT NULL COMMENT '部署app的集群id',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
);

-- ----------------------------
-- Records of ra_app
-- ----------------------------
INSERT INTO ra_app VALUES ('1', 'test', 'test', '3', '1', '2017-04-16 08:16:31');

-- ----------------------------
-- Table structure for `ra_app_network`
-- ----------------------------
DROP TABLE IF EXISTS `ra_app_network`;
CREATE TABLE `ra_app_network` (
  `app_id` varchar(50) NOT NULL,
  `network_id` varchar(50) NOT NULL,
  PRIMARY KEY (`app_id`,`network_id`)
);

-- ----------------------------
-- Records of ra_app_network
-- ----------------------------

-- ----------------------------
-- Table structure for `ra_app_volume`
-- ----------------------------
DROP TABLE IF EXISTS `ra_app_volume`;
CREATE TABLE `ra_app_volume` (
  `app_id` varchar(50) NOT NULL,
  `volume_id` varchar(50) NOT NULL,
  `container_path` varchar(256) NOT NULL,
  PRIMARY KEY (`app_id`,`volume_id`)
);

-- ----------------------------
-- Records of ra_app_volume
-- ----------------------------

-- ----------------------------
-- Table structure for `ra_cluster`
-- ----------------------------
DROP TABLE IF EXISTS `ra_cluster`;
CREATE TABLE `ra_cluster` (
  `id` varchar(50) NOT NULL,
  `name` varchar(40) NOT NULL,
  `coordinator_id` varchar(50) NOT NULL COMMENT '使用的coordinator的id',
  `description` varchar(512) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) COMMENT='docker宿主机集群';

-- ----------------------------
-- Records of ra_cluster
-- ----------------------------

-- ----------------------------
-- Table structure for `ra_coordinator`
-- ----------------------------
DROP TABLE IF EXISTS `ra_coordinator`;
CREATE TABLE `ra_coordinator` (
  `id` varchar(50) NOT NULL,
  `type` varchar(20) DEFAULT NULL COMMENT 'zookeeper, etcd, consul',
  `url` varchar(256) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) COMMENT='集群协调器，每个docker集群一个协调器';

-- ----------------------------
-- Records of ra_coordinator
-- ----------------------------

-- ----------------------------
-- Table structure for `ra_host`
-- ----------------------------
DROP TABLE IF EXISTS `ra_host`;
CREATE TABLE `ra_host` (
  `id` varchar(50) NOT NULL,
  `cluster_id` varchar(50) DEFAULT NULL COMMENT '所属集群id，默认为空，表示不属于任何集群',
  `ip` varchar(50) NOT NULL,
  `port` int(11) DEFAULT '22' COMMENT 'ssh端口，默认为22',
  `hostname` varchar(128) DEFAULT NULL,
  `user_name` varchar(30) DEFAULT NULL,
  `passwd` varchar(128) DEFAULT NULL,
  `registry_id` varchar(50) DEFAULT NULL,
  `daemon_started` tinyint(1) DEFAULT '0' COMMENT 'docker daemon是否启动',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) COMMENT='宿主机信息表';

-- ----------------------------
-- Records of ra_host
-- ----------------------------
INSERT INTO ra_host VALUES ('1', '1', '127.0.0.1', '22', 'localhost', 'root', 'root', '1', '1', '2017-04-16 08:10:45');

-- ----------------------------
-- Table structure for `ra_network`
-- ----------------------------
DROP TABLE IF EXISTS `ra_network`;
CREATE TABLE `ra_network` (
  `id` varchar(50) NOT NULL,
  `name` varchar(128) NOT NULL,
  `cluster_id` varchar(50) DEFAULT NULL COMMENT 'network所属集群id，为空时表示该network是local的',
  `ip_range` varchar(128) DEFAULT NULL COMMENT 'ip范围，cidr格式，如172.192.100.0/24',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
);

-- ----------------------------
-- Records of ra_network
-- ----------------------------

-- ----------------------------
-- Table structure for `ra_registry`
-- ----------------------------
DROP TABLE IF EXISTS `ra_registry`;
CREATE TABLE `ra_registry` (
  `id` varchar(50) NOT NULL,
  `name` varchar(128) DEFAULT NULL COMMENT 'registry名称',
  `url` varchar(256) NOT NULL COMMENT 'registry url, such as repository://admin:123456@registry.paas:443',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) COMMENT='docker镜像仓库信息';

-- ----------------------------
-- Records of ra_registry
-- ----------------------------
INSERT INTO ra_registry VALUES ('1', 'default', 'repository://admin:123456@registry.paas:443', '2017-04-09 16:54:58');

-- ----------------------------
-- Table structure for `ra_volume`
-- ----------------------------
DROP TABLE IF EXISTS `ra_volume`;
CREATE TABLE `ra_volume` (
  `id` varchar(50) NOT NULL,
  `name` varchar(128) DEFAULT NULL,
  `url` varchar(256) NOT NULL COMMENT 'volume url, such as //192.168.1.1:/tmp',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
);

-- ----------------------------
-- Records of ra_volume
-- ----------------------------
