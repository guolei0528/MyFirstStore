package com.project.backMng.admin.sys.service;

import java.util.List;

import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.SysLoginUserBean;
import com.project.backMng.admin.sys.model.PlatUserBean;

public interface PlatUserMngService {
	
	public List<PlatUserBean> findList(ObjectMap queryParam,Page page);
	
	public PlatUserBean findInfo(String id);
	
	public void save(PlatUserBean bean,String ROLE_ID,SysLoginUserBean userBean);
	
	public void update(PlatUserBean bean,String ROLE_ID,SysLoginUserBean userBean);
	
	public void delete(String id,SysLoginUserBean userBean);
}