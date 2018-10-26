SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS T_LOGIN_USER;
DROP TABLE IF EXISTS T_PLAT_USER;




/* Create Tables */

-- 系统登录用户资料
CREATE TABLE T_LOGIN_USER
(
	ID int NOT NULL AUTO_INCREMENT COMMENT '流水号',
	USER_ID varchar(32) NOT NULL COMMENT '用户流水号',
	LOGIN_NAME varchar(16) NOT NULL COMMENT '登录的用户名',
	PASSWORD varchar(32) NOT NULL COMMENT '用户密码',
	-- 1、平台用户
	USER_TYPE int NOT NULL COMMENT '用户类型 : 1、平台用户',
	OPEN_ID varchar(32) COMMENT '用户关联的OPEN_ID',
	NICK_NAME varchar(32) COMMENT '微信呢称',
	HEAD_IMG varchar(256) COMMENT '头像地址',
	IN_USE_FLAG int NOT NULL COMMENT '是否启用标志位',
	DELETE_FLAG int NOT NULL COMMENT '删除标志位',
	PRIMARY KEY (ID),
	UNIQUE (LOGIN_NAME)
) COMMENT = '系统登录用户资料';


-- 系统平台用户
CREATE TABLE T_PLAT_USER
(
	ID varchar(32) NOT NULL COMMENT '流水号',
	NAME varchar(64) NOT NULL COMMENT '名称',
	-- 1男0女
	SEX int NOT NULL COMMENT '性别 : 1男0女',
	WORK_NO varchar(16) COMMENT '工号',
	MOBILE varchar(16) NOT NULL COMMENT '联系人手机',
	REMARK varchar(512) COMMENT '备注',
	CREATE_TIME datetime NOT NULL COMMENT '创建时间',
	LAST_MODIFY_TIME datetime COMMENT '最后修改时间',
	IN_USE_FLAG int NOT NULL COMMENT '是否启用',
	DELETE_FLAG int NOT NULL COMMENT '删除标志位',
	PRIMARY KEY (ID)
) COMMENT = '系统平台用户';



