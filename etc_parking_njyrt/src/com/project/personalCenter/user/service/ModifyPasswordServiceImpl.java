package com.project.personalCenter.user.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.project.backMng.pub.sys.service.RoleMngServiceImpl;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.mybatis.service.BaseService;

public class ModifyPasswordServiceImpl extends BaseService implements ModifyPasswordService{
	
	private final static Logger log = LogManager.getLogger(ModifyPasswordServiceImpl.class);

	public ModifyPasswordServiceImpl() {
		super();
		setIBATIS_NAME_SPACE("personalCenter.user.ModifyPassword");
	}
	
	/**
	 * 
	   * @Title : updatePwd 
	   * @功能描述: 修改密码
	   * @传入参数：@param objectMap
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public String updatePwd(ObjectMap objectMap) {
		// TODO Auto-generated method stub
		String oldPwd = super.findString(ns("selectPwd"), objectMap);
		if(!oldPwd.equals(objectMap.get("old_pwd")))
		{
			return "原密码不正确！";
		}
		super.update(ns("updatePwd"), objectMap);
	    return "success";
	}

}
