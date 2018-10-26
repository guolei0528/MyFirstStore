package com.project.backMng.admin.sys.service;

import java.util.List;

import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.SysLoginUserBean;
import com.project.common.tool.AppConst;
import com.project.tools.UuidGenerator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.redoak.jar.base.mybatis.service.BaseService;
import com.redoak.jar.util.PropertiesUtil;
import com.redoak.jar.util.StringUtil;
import com.project.backMng.admin.sys.model.PlatUserBean;

public class PlatUserMngServiceImpl extends BaseService implements PlatUserMngService{

	private final static Logger log=LogManager.getLogger(PlatUserMngServiceImpl.class);

	public PlatUserMngServiceImpl() {
		super();
		setIBATIS_NAME_SPACE("backMng.admin.sys.PlatUserMng");
		// TODO Auto-generated constructor stub
	}

	public List<PlatUserBean> findList(ObjectMap queryParam, Page page) {
		// TODO Auto-generated method stub
		page.setRecordCount(super.findInteger(ns("findCount"),queryParam));
		return super.findList(ns("findList"),queryParam,page);
	}

	public PlatUserBean findInfo(String id) {
		// TODO Auto-generated method stub
		return super.findObj(ns("findInfo"),id);
	}

	public void save(PlatUserBean bean,String ROLE_ID, SysLoginUserBean userBean) {
		// TODO Auto-generated method stub
		bean.setID(UuidGenerator.generateUUID());
		bean.setDELETE_FLAG(AppConst.DELETE_FLAG.NO);
		super.insert(ns("insert"), bean);
		
		//保存到T_LOGIN_USER数据表		
		ObjectMap o=ObjectMap.newInstance();
		o.put("USER_ID", bean.getID());
		o.put("LOGIN_NAME", bean.getMOBILE());
		o.put("PASSWORD", PropertiesUtil.get("PLATUSER_PASSWORD"));
		o.put("USER_TYPE", AppConst.USER_TYPE.PLAT_USER);
		o.put("IN_USE_FLAG", bean.getIN_USE_FLAG());
		o.put("DELETE_FLAG", bean.getDELETE_FLAG());
		super.insert(ns("insertLoginUser"), o);
		
		//保存角色
		saveUserRoleID(bean.getID(), ROLE_ID);
	}
	
	private void saveUserRoleID(String USER_ID,String ROLE_ID){
		super.delete(ns("deleteUserRoleByUserId"), USER_ID);
		
		if(StringUtil.isEmpty(ROLE_ID)==false){
			ObjectMap o=ObjectMap.newInstance();
			o.put("USER_ID", USER_ID);
			o.put("ROLE_ID", ROLE_ID);
			super.insert(ns("saveUserRoleByUserId"), o);
		}
	}

	public void update(PlatUserBean bean,String ROLE_ID, SysLoginUserBean userBean) {
		// TODO Auto-generated method stub
		super.update(ns("update"), bean);
		
		//更新T_LOGIN_USER中的用户名及是否启用标志位
		super.update(ns("updateLoginUser"), bean);
		
		//保存角色
		saveUserRoleID(bean.getID(), ROLE_ID);
	}

	public void delete(String id,SysLoginUserBean userBean) {
		// TODO Auto-generated method stub
		super.update(ns("delete"), id);
		
		//删除操作用户
		super.update(ns("deleteLoginUser"), id);
	}

}