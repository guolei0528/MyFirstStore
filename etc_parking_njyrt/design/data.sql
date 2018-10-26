SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS INIT_WX_MAIN_PAGE;
DROP TABLE IF EXISTS T_AREA;
DROP TABLE IF EXISTS T_OPE_LOG;
DROP TABLE IF EXISTS T_WX_USER;




/* Create Tables */

-- 微信首页
CREATE TABLE INIT_WX_MAIN_PAGE
(
	ID int NOT NULL COMMENT '流水号',
	NAME varchar(32) NOT NULL COMMENT '流水号',
	PAGE_URL varchar(128) NOT NULL COMMENT '页面URL地址',
	ICON_URL varchar(128) NOT NULL COMMENT '图标URL',
	SHOW_ORDER int NOT NULL COMMENT '显示顺序',
	-- 同T_LOGIN_USER中USER_TYPE
	USER_TYPE int NOT NULL COMMENT '用户类型 : 同T_LOGIN_USER中USER_TYPE',
	PRIMARY KEY (ID)
) COMMENT = '微信首页';


-- 区域信息
CREATE TABLE T_AREA
(
	ID int NOT NULL AUTO_INCREMENT COMMENT '流水号',
	NAME varchar(64) NOT NULL COMMENT '名称',
	-- 每一级分配3位编码
	CODE varchar(32) NOT NULL COMMENT '编码 : 每一级分配3位编码',
	PARENT_ID int COMMENT '上级的流水号',
	-- 1省2市3区4组5小区
	DEPTH_LEVEL int NOT NULL COMMENT '区域类型 : 1省2市3区4组5小区',
	IN_USE_FLAG int COMMENT '是否启用',
	-- 优先级小显示在前，优先级默认1-5
	SHOW_PRIORITY int NOT NULL COMMENT '显示优先级 : 优先级小显示在前，优先级默认1-5',
	DELETE_FLAG int NOT NULL COMMENT '删除标志位',
	PRIMARY KEY (ID)
) COMMENT = '区域信息';


-- 操作日志
CREATE TABLE T_OPE_LOG
(
	ID int NOT NULL AUTO_INCREMENT COMMENT '流水号',
	-- 1新增2修改3删除4其他
	OPE_TYPE int NOT NULL COMMENT '操作类型 : 1新增2修改3删除4其他',
	OPE_CONTENT varchar(512) NOT NULL COMMENT '操作内容',
	RELATED_ID varchar(32) COMMENT '关联对象流水号',
	OPE_USER_ID varchar(32) NOT NULL COMMENT '操作用户流水号',
	OPE_USER_NAME varchar(32) NOT NULL COMMENT '操作人姓名',
	OPE_TIME datetime NOT NULL COMMENT '操作时间',
	-- 操作动作
	OPE_ACTION varchar(512) COMMENT '操作动作 : 操作动作',
	PRIMARY KEY (ID)
) COMMENT = '操作日志';


-- 微信用户
CREATE TABLE T_WX_USER
(
	ID varchar(32) NOT NULL COMMENT '流水号',
	OPEN_ID varchar(32) NOT NULL COMMENT '微信OPEN_ID',
	NICK_NAME varchar(128) COMMENT '呢称信息',
	HEAD_IMGURL varchar(256) COMMENT '头像链接',
	CREATE_TIME datetime NOT NULL COMMENT '创建时间',
	LAST_MODIFY_TIME datetime COMMENT '最后修改时间',
	PRIMARY KEY (ID),
	UNIQUE (OPEN_ID)
) COMMENT = '微信用户';



