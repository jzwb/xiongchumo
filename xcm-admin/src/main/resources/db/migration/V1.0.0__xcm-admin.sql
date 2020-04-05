SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

---------------------------- 管理员

CREATE TABLE `sys_admin`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `name` varchar(255) NULL,
  `mobile` varchar(255) NULL,
  `email` varchar(255) NULL,
  `create_date` datetime NOT NULL,
  `modify_date` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

INSERT INTO `sys_admin` VALUES (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', '管理员', NULL,  NULL, '2020-01-01 00:00:00', '2020-01-01 00:00:00');

---------------------------- 角色

CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `modify_date` datetime NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `is_system` bit(1) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

INSERT INTO `sys_role` VALUES (1, '2014-07-02 00:02:44', '2014-07-02 00:02:44', '拥有管理后台最高权限', b'1', '超级管理员');

---------------------------- 管理员_角色（中间表）

CREATE TABLE `sys_admin_role` (
  `admins` bigint(20) NOT NULL,
  `roles` bigint(20) NOT NULL,
  PRIMARY KEY (`admins`,`roles`) USING BTREE,
  KEY `roles` (`roles`) USING BTREE,
  KEY `admins` (`admins`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `sys_admin_role` VALUES (1, 1);

---------------------------- 角色权限

CREATE TABLE `sys_role_authority` (
  `role_id` bigint(20) NOT NULL,
  `authorities` varchar(255) DEFAULT NULL,
  KEY `role_id` (`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `sys_role_authority`(`role_id`, `authorities`) VALUES (1, 'admin:admin');
INSERT INTO `sys_role_authority`(`role_id`, `authorities`) VALUES (1, 'admin:role');
INSERT INTO `sys_role_authority`(`role_id`, `authorities`) VALUES (1, 'admin:menu');
INSERT INTO `sys_role_authority`(`role_id`, `authorities`) VALUES (1, 'admin:storage_plugin');

---------------------------- 菜单

CREATE TABLE `sys_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `modify_date` datetime NOT NULL,
  `title` varchar(255) NOT NULL,
  `href` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `target` varchar(255) DEFAULT NULL,
  `grade` int(11) NOT NULL,
  `tree_path` varchar(255) NOT NULL,
  `authority` varchar(255) DEFAULT NULL,
  `parent` bigint(20) DEFAULT NULL,
  `orders` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

INSERT INTO `sys_menu`(`id`, `create_date`, `modify_date`, `title`, `href`, `icon`, `target`, `grade`, `tree_path`, `parent`) VALUES (1, '2020-03-04 22:07:31', '2020-03-04 22:07:34', '系统管理', '', 'fa fa-gear', '_self', 0, ',', NULL);
INSERT INTO `sys_menu`(`id`, `create_date`, `modify_date`, `title`, `href`, `icon`, `target`, `grade`, `tree_path`, `parent`, `authority`) VALUES (2, '2020-03-04 22:08:44', '2020-03-04 22:08:50', '用户管理', '/admin/admin/index/', 'fa fa-users', '_self', 1, ',1,', 1, 'admin:admin');
INSERT INTO `sys_menu`(`id`, `create_date`, `modify_date`, `title`, `href`, `icon`, `target`, `grade`, `tree_path`, `parent`, `authority`) VALUES (3, '2020-03-04 22:09:34', '2020-03-04 22:09:42', '角色管理', '/admin/role/index/', 'fa fa-users', '_self', 1, ',1,', 1, 'admin:role');
INSERT INTO `sys_menu`(`id`, `create_date`, `modify_date`, `title`, `href`, `icon`, `target`, `grade`, `tree_path`, `parent`, `authority`) VALUES (4, '2020-03-04 22:09:34', '2020-03-04 22:09:42', '菜单管理', '/admin/menu/index/', 'fa fa-window-maximize', '_self', 1, ',1,', 1, 'admin:menu');
INSERT INTO `sys_menu`(`id`, `create_date`, `modify_date`, `title`, `href`, `icon`, `target`, `grade`, `tree_path`, `parent`, `authority`) VALUES (5, '2020-03-04 22:09:34', '2020-03-04 22:09:42', '存储插件管理', '/admin/storage_plugin/index/', 'fa fa-align-justify', '_self', 1, ',1,', 1, 'admin:storage_plugin');

---------------------------- 插件配置

CREATE TABLE `sys_plugin_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `modify_date` datetime NOT NULL,
  `orders` int(11) DEFAULT NULL,
  `is_enabled` tinyint(1) NOT NULL,
  `plugin_id` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `plugin_id` (`plugin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

INSERT INTO `sys_plugin_config`(`id`, `create_date`, `modify_date`, `orders`, `is_enabled`, `plugin_id`) VALUES (1, '2020-03-08 23:23:29', '2020-03-08 23:23:29', NULL, 0, 'filePlugin');

CREATE TABLE `sys_plugin_config_attribute` (
  `plugin_config_id` bigint(20) NOT NULL,
  `attributes` varchar(255) DEFAULT NULL,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`plugin_config_id`,`name`),
  KEY `plugin_config_id` (`plugin_config_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

---------------------------- 日志

CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `modify_date` datetime NOT NULL,
  `content` longtext,
  `ip` varchar(255) NOT NULL,
  `operation` varchar(255) NOT NULL,
  `operator` varchar(255) DEFAULT NULL,
  `parameter` longtext,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `create_date` (`create_date`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

SET FOREIGN_KEY_CHECKS = 1;


---------------------------- 用户

CREATE TABLE `t_admin`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `password` varchar(255) NOT NULL,
  `mobile` varchar(255) NULL,
  `email` varchar(255) NULL,
  `union_id` varchar(255) NULL,
  `open_id` varchar(255) NULL,
  `create_date` datetime NOT NULL,
  `modify_date` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;