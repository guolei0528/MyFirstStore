SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS authority;
DROP TABLE IF EXISTS login_user;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS role_menu;
DROP TABLE IF EXISTS role_user;
DROP TABLE IF EXISTS sys_menu;
DROP TABLE IF EXISTS sys_user;




/* Create Tables */

CREATE TABLE authority
(
	-- 权限ID
	authorid varchar(4) DEFAULT '0' NOT NULL COMMENT '权限ID',
	-- 权限名
	authorname varchar(50) COMMENT '权限名',
	-- 角色主键:
	-- 1、系统管理员
	-- 2、管理员（只能由系统管理员建立）
	-- 3、财务
	-- 4、收费员
	-- 默认值时0
	-- 
	-- 
	roleid varchar(2) COMMENT '角色主键:
1、系统管理员
2、管理员（只能由系统管理员建立）
3、财务
4、收费员
默认值时0

',
	-- 操作名:
	-- 0：查询（默认）
	-- 1：增加
	-- 2：删除
	-- 3：修改
	-- 
	operateid int DEFAULT 0 COMMENT '操作名:
0：查询（默认）
1：增加
2：删除
3：修改
',
	-- 操作的表明
	tablename varchar(25) COMMENT '操作的表明',
	-- 停车场编号
	parkid varchar(20) COMMENT '停车场编号',
	-- 所在区域id:
	-- 一个停车场可以有多个区域，默认值：0表示所有区域
	areaid varchar(8) DEFAULT '0' COMMENT '所在区域id:
一个停车场可以有多个区域，默认值：0表示所有区域',
	-- 备用1:不确定时为0；
	spare1 int DEFAULT 0 COMMENT '备用1:不确定时为0；',
	-- 备用2:不确定时为0；
	spare2 int DEFAULT 0 COMMENT '备用2:不确定时为0；',
	-- 备用3:不确定时为0;
	spare3 varchar(50) DEFAULT '0' COMMENT '备用3:不确定时为0;',
	-- 备用4:不确定时为0；
	spare4 varchar(50) DEFAULT '0' COMMENT '备用4:不确定时为0；',
	-- 注释
	s_comment varchar(255) COMMENT '注释',
	PRIMARY KEY (authorid)
) DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;


CREATE TABLE login_user
(
	-- 用户ID
	user_id varchar(25) NOT NULL COMMENT '用户ID',
	login_name varchar(32),
	-- 密码
	password varchar(25) COMMENT '密码',
	-- 用户名
	user_name varchar(25) COMMENT '用户名',
	-- 性别:
	-- 0：男
	-- 1：女
	-- 
	sex varchar(2) DEFAULT '0' COMMENT '性别:
0：男
1：女
',
	-- 收费员编号:当班收费员工号
	user_operator int DEFAULT 0 COMMENT '收费员编号:当班收费员工号',
	-- 收费员班次:1~4
	user_shift int DEFAULT 0 COMMENT '收费员班次:1~4',
	-- 入口收费班组
	operator_shift int DEFAULT 0 COMMENT '入口收费班组',
	-- 电话号码
	phone varchar(11) COMMENT '电话号码',
	in_use_flag int,
	delete_flag int,
	-- 备用1:不确定时为0；
	spare1 int DEFAULT 0 COMMENT '备用1:不确定时为0；',
	-- 备用2:不确定时为0；
	spare2 int COMMENT '备用2:不确定时为0；',
	-- 备用3不确定时为0；
	spare3 varchar(50) DEFAULT '0' COMMENT '备用3不确定时为0；',
	-- 备用4:不确定时为0；
	spare4 varchar(50) DEFAULT '0' COMMENT '备用4:不确定时为0；',
	-- 注释
	s_comment varchar(255) COMMENT '注释',
	PRIMARY KEY (user_id)
) DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;


CREATE TABLE role
(
	-- 用户角色ID:
	-- 0、系统管理员
	-- 1、管理员
	-- 2、普通用户
	-- 默认值时0
	role_id varchar(2) DEFAULT '0' NOT NULL COMMENT '用户角色ID:
0、系统管理员
1、管理员
2、普通用户
默认值时0',
	-- 角色用户名:
	-- RoleID = 0时
	-- 为RoleName=系统管理员；
	-- RoleID = 1时
	-- 为RoleName=管理员；
	-- RoleID = 2时
	-- 为RoleName=普通用户；
	-- 
	role_name varchar(30) COMMENT '角色用户名:
RoleID = 0时
为RoleName=系统管理员；
RoleID = 1时
为RoleName=管理员；
RoleID = 2时
为RoleName=普通用户；
',
	delete_flag int,
	-- 备用1:不确定时为0；
	spare1 int DEFAULT 0 COMMENT '备用1:不确定时为0；',
	-- 备用2:不确定时为0；
	spare2 int DEFAULT 0 COMMENT '备用2:不确定时为0；',
	-- 备用3:不确定时为0；
	spare3 varchar(50) DEFAULT '0' COMMENT '备用3:不确定时为0；',
	-- 备用4:不确定时为0；
	spare4 varchar(50) DEFAULT '0' COMMENT '备用4:不确定时为0；',
	-- 注释
	s_comment varchar(255) COMMENT '注释',
	PRIMARY KEY (role_id)
) DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;


CREATE TABLE role_menu
(
	id int NOT NULL AUTO_INCREMENT,
	role_id varchar(32) NOT NULL,
	menu_id int NOT NULL,
	PRIMARY KEY (id)
);


CREATE TABLE role_user
(
	id int NOT NULL AUTO_INCREMENT,
	role_id varchar(32) NOT NULL,
	user_id varchar(32) NOT NULL,
	related_time datetime NOT NULL,
	PRIMARY KEY (id)
);


CREATE TABLE sys_menu
(
	id int NOT NULL,
	name varchar(32) NOT NULL,
	url varchar(256),
	parent_id int,
	show_order int NOT NULL,
	user_type int NOT NULL,
	PRIMARY KEY (id)
);


-- 可管理后台系统的用户信息
CREATE TABLE sys_user
(
	id int NOT NULL,
	login_name varchar(32) NOT NULL,
	password varchar(32) NOT NULL,
	-- 1系统用户0普通用户
	user_type int NOT NULL COMMENT '1系统用户0普通用户',
	PRIMARY KEY (id)
) COMMENT = '可管理后台系统的用户信息';



