package com.project.backMng.platuser.CareTrafficRecordMng.service;

import java.util.List;

import com.project.backMng.platuser.CareTrafficRecordMng.model.CareTrafficRecordBean;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.SysLoginUserBean;

public interface CareTrafficRecordMngService {
	
	public List<CareTrafficRecordBean> findList(ObjectMap queryParam,Page page);
	
	public CareTrafficRecordBean findInfo(String id);
	
	public void save(CareTrafficRecordBean bean,SysLoginUserBean userBean);
	
	public void update(CareTrafficRecordBean bean,SysLoginUserBean userBean);
	
	public void delete(String id,SysLoginUserBean userBean);
}