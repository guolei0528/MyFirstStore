package com.project.backMng.admin.sys.service;

import java.util.Date;
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
import com.project.backMng.admin.sys.model.UserBean;

public class UserMngServiceImpl extends BaseService implements UserMngService{

	private final static Logger log=LogManager.getLogger(PlatUserMngServiceImpl.class);

	public UserMngServiceImpl() {
		super();
		setIBATIS_NAME_SPACE("backMng.admin.sys.UserMng");
		// TODO Auto-generated constructor stub
	}

	public List<UserBean> findList(ObjectMap queryParam, Page page) {
		// TODO Auto-generated method stub
		page.setRecordCount(super.findInteger(ns("findCount"),queryParam));
		return super.findList(ns("findList"),queryParam,page);
	}

	public UserBean findInfo(String id) {
		// TODO Auto-generated method stub
		return super.findObj(ns("findInfo"),id);
	}

	public String save(UserBean bean) {
		
		ObjectMap o=ObjectMap.newInstance();
		o.put("login_name", bean.getLoginName());
		
		//唯一性校验
		int countByUserOperator = super.findInteger(ns("checkUniqueWorkID"), o);
		if(countByUserOperator >0 )
		{
			return "系统中已有相同工号！";
		}		
		
		bean.setUserId(UuidGenerator.generateUUID());
		bean.setDeleteFlag(AppConst.DELETE_FLAG.NO);
//		super.insert(ns("insert"), bean);
		
		//保存到用户数据表		
		o.put("user_id", bean.getUserId());
		o.put("name", bean.getName());
		o.put("sex", bean.getSex());
		o.put("user_operator", bean.getUserOperator());
		o.put("password", PropertiesUtil.get("PLATUSER_PASSWORD"));
		o.put("phone", bean.getPhone());
		o.put("user_shift", bean.getUserShift());
//		o.put("USER_TYPE", AppConst.USER_TYPE.PLAT_USER);
		Date date = new Date();
		o.put("create_time", date);
		o.put("last_modify_time", date);
		o.put("in_use_flag", bean.getInUseFlag());
		o.put("delete_flag", bean.getDeleteFlag());
		o.put("role_id", bean.getRoleId());
		o.put("s_comment", bean.getsComment());
		o.put("area_id", bean.getAreaId());
		super.insert(ns("insertUser"), o);
		return "success";
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

	/**
	 * 更新用户表数据
	 */
	public String update(UserBean bean) {
		//封装用户信息
		ObjectMap o=ObjectMap.newInstance();
		o.put("user_id", bean.getUserId());
		o.put("login_name", bean.getLoginName());
		//查询是否有相同工号的用户
		int countByUserOperator = super.findInteger(ns("checkUniqueWorkID"), o);
		if(countByUserOperator >0 )
		{
			return "系统中已有相同工号！";
		}		
		super.update(ns("update"), bean);
		return "success";
	}

	public void delete(String id) {
		// TODO Auto-generated method stub
		//删除操作用户
		super.update(ns("deleteUser"), id);
		
//		super.update(ns("deleteLoginUser"), id);
	}

}