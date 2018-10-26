
INSERT INTO t_sysuser (ID, LOGIN_NAME, PASSWORD, USER_TYPE)
VALUES (-1, 'admin', 'wateradmin', 1);


DELETE FROM t_sys_menu;
/*系统管理员*/
INSERT INTO t_sys_menu (ID, NAME, URL, PARENT_ID, SHOW_ORDER, USER_TYPE)
VALUES (10, '系统管理', null, null, 10, 1);
INSERT INTO t_sys_menu (ID, NAME, URL, PARENT_ID, SHOW_ORDER, USER_TYPE)
VALUES (1001, '平台用户管理', 'backMng/admin/sys/PlatUserMng/list.shtml', 10, 10, 1);
INSERT INTO t_sys_menu (ID, NAME, URL, PARENT_ID, SHOW_ORDER, USER_TYPE)
VALUES (1002, '角色管理', 'backMng/pub/sys/RoleMng/list.shtml', 10, 20, 1);
INSERT INTO t_sys_menu (ID, NAME, URL, PARENT_ID, SHOW_ORDER, USER_TYPE)
VALUES (1003, '日志管理', 'backMng/opelog/Ope_log/list.shtml', 10, 30, 1);

insert into sys_menu(id,name,url,parent_id,show_order)
values(4001,'本地停车场黑名单管理','backMng/admin/voidCard/ParkVoidCardMng/list.shtml',40,10);

insert into sys_menu(id,name,url,parent_id,show_order)
values(4002,'公安黑名单管理','backMng/admin/voidCard/PoliceVoidCardMng/list.shtml',40,20);
insert into sys_menu(id,name,url,parent_id,show_order)
values(4003,'ETC黑名单管理','backMng/admin/voidCard/EtcVoidCardMng/list.shtml',40,30);
insert into sys_menu(id,name,url,parent_id,show_order)
values(4004,'通行宝黑名单管理','backMng/admin/voidCard/TxbVoidCardMng/list.shtml',40,40);


insert into sys_menu(id,name,url,parent_id,show_order)
values(6001,'当前入口流量检索','backMng/admin/transitRecord/EntryMng/list.shtml',60,10);
insert into sys_menu(id,name,url,parent_id,show_order)
values(6002,'当前出口流量检索','backMng/admin/transitRecord/ExitMng/list.shtml',60,20);
insert into sys_menu(id,name,url,parent_id,show_order)
values(6003,'历史入口流量检索','backMng/admin/transitRecord/EntryMng/hisList.shtml',60,30);
insert into sys_menu(id,name,url,parent_id,show_order)
values(6004,'历史出口流量检索','backMng/admin/transitRecord/ExitMng/hisList.shtml',60,40);
insert into sys_menu(id,name,url,parent_id,show_order)
values(6005,'车位检索','backMng/admin/transitRecord/EntryMng/emptyPositionList.shtml',60,50);
insert into sys_menu(id,name,url,parent_id,show_order)
values(6006,'超额车辆检索','backMng/admin/transitRecord/EntryMng/excessiveCar.shtml',60,60);


insert into sys_menu(id,name,url,parent_id,show_order)
values(7001,'流量统计','backMng/platuser/sys/CareTrafficRecordMng/list.shtml',70,10);
insert into sys_menu(id,name,url,parent_id,show_order)
values(9003,'发票查询','backMng/platuser/finance/InvoiceInfoMng/list.shtml',90,30);

insert into sys_menu(id,name,url,parent_id,show_order)
values(9004,'现金解缴','backMng/platuser/finance/PaymentMng/list.shtml',90,40);

/*平台客户*/
INSERT INTO t_sys_menu (ID, NAME, URL, PARENT_ID, SHOW_ORDER, USER_TYPE)
VALUES (20, '平台客户', null, null, 10, 2);
INSERT INTO t_sys_menu (ID, NAME, URL, PARENT_ID, SHOW_ORDER, USER_TYPE)
VALUES (2001, '功能1', '', 20, 10, 2);
INSERT INTO t_sys_menu (ID, NAME, URL, PARENT_ID, SHOW_ORDER, USER_TYPE)
VALUES (2002, '功能2', '', 20, 20, 2);
INSERT INTO t_sys_menu (ID, NAME, URL, PARENT_ID, SHOW_ORDER, USER_TYPE)
VALUES (2003, '功能3', '', 20, 30, 2);


DELETE FROM INIT_PROPERTIES;
INSERT INTO INIT_PROPERTIES(PROP_KEY,PROP_VALUE) VALUES('appId','1');
INSERT INTO INIT_PROPERTIES(PROP_KEY,PROP_VALUE) VALUES('appSecret','1');
INSERT INTO INIT_PROPERTIES(PROP_KEY,PROP_VALUE) VALUES('DOMAIN.WEB_SERVER','http://192.168.31.188');
INSERT INTO INIT_PROPERTIES(PROP_KEY,PROP_VALUE) VALUES('PROJECT_NAME','PROJECT_NAME');
INSERT INTO INIT_PROPERTIES(PROP_KEY,PROP_VALUE) VALUES('PLATUSER_PASSWORD','123456');

INSERT INTO INIT_PROPERTIES(PROP_KEY,PROP_VALUE) VALUES('FTP.SERVER_IP','');
INSERT INTO INIT_PROPERTIES(PROP_KEY,PROP_VALUE) VALUES('FTP.SERVER_PORT','1');
INSERT INTO INIT_PROPERTIES(PROP_KEY,PROP_VALUE) VALUES('FTP.FTP_USER','');
INSERT INTO INIT_PROPERTIES(PROP_KEY,PROP_VALUE) VALUES('FTP.FTP_PASSWORD','');

