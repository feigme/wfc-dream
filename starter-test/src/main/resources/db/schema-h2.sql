DROP TABLE IF EXISTS user;

CREATE TABLE user
(
	id BIGINT(20) NOT NULL COMMENT '主键ID',
	name VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
	age INT(11) NULL DEFAULT NULL COMMENT '年龄',
	email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
	PRIMARY KEY (id)
);

create table wfc_account (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'pk',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `login_name` varchar(32) DEFAULT NULL COMMENT '登陆名称',
  `phone_num` varchar(16) DEFAULT NULL COMMENT '手机号码',
  `password` varchar(128) DEFAULT NULL COMMENT '密码',
  `slat` varchar(8) DEFAULT NULL COMMENT '盐',
  `disabled` tinyint(4) DEFAULT '0' COMMENT '是否禁用(默认是0)',
  PRIMARY KEY (id)
);