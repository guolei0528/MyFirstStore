package com.project.backMng.platuser.sys.AreaInfoMng.service;

import java.util.List;
import java.util.Map;

import com.redoak.jar.base.model.OakTreeModel;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.SysLoginUserBean;
import com.project.backMng.platuser.sys.AreaInfoMng.model.AreaInfoBean;

public interface AreaInfoMngService {
	
	public List<AreaInfoBean> findList(ObjectMap queryParam,Page page);
	
	public AreaInfoBean findInfo(String id);
	
	public void save(AreaInfoBean bean,SysLoginUserBean userBean);
	
	public void update(AreaInfoBean bean,SysLoginUserBean userBean);
	
	public void delete(String id,SysLoginUserBean userBean);
	
	public List<OakTreeModel> loadAreaTree(String PARENT_ID);
	public Integer findSameArea(AreaInfoBean bean);
	
	public List<Map> findAllArea();
	
}