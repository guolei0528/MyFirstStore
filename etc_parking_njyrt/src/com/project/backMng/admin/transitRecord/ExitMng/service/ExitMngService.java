package com.project.backMng.admin.transitRecord.ExitMng.service;

import java.util.List;

import com.project.backMng.admin.transitRecord.ExitMng.model.ExitBean;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.SysLoginUserBean;

public interface ExitMngService {
	
	public List<ExitBean> findList(ObjectMap queryParam,Page page);
	public List<ExitBean> findHisList(ObjectMap queryParam,Page page);
	
	public ExitBean findInfo(ObjectMap map);
	
	public void save(ExitBean bean,SysLoginUserBean userBean);
	
	public void update(ExitBean bean,SysLoginUserBean userBean);
	
	public void delete(String id,SysLoginUserBean userBean);
	
	public ExitBean findHisInfo(ObjectMap map);
}