package com.project.backMng.platuser.sys.ParkInfoMng.service;

import java.util.List;

import com.redoak.jar.base.model.OakTreeModel;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.SysLoginUserBean;
import com.project.backMng.platuser.sys.ParkInfoMng.model.ParkInfoBean;

public interface ParkInfoMngService {
	
	public List<ParkInfoBean> findList(ObjectMap queryParam,Page page);
	
	public ParkInfoBean findInfo(String id);
	
	public void save(ParkInfoBean bean,SysLoginUserBean userBean);
	
	public void update(ParkInfoBean bean,SysLoginUserBean userBean);
	
	public void delete(String id,SysLoginUserBean userBean);
	
	public List<OakTreeModel> loadParkTree();
}