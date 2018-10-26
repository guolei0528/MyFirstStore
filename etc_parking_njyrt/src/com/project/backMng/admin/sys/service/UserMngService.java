package com.project.backMng.admin.sys.service;

import java.util.List;

import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.SysLoginUserBean;
import com.project.backMng.admin.sys.model.PlatUserBean;
import com.project.backMng.admin.sys.model.UserBean;

public interface UserMngService {
	
	public List<UserBean> findList(ObjectMap queryParam,Page page);
	
	public UserBean findInfo(String id);
	
	public String save(UserBean bean);
	
	public String update(UserBean bean);
	
	public void delete(String id);
}