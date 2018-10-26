package com.project.backMng.platuser.sys.LaneInfoMng.service;

import java.util.List;

import com.redoak.jar.base.model.OakTreeModel;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.SysLoginUserBean;
import com.project.backMng.platuser.sys.LaneInfoMng.model.LaneInfoBean;

public interface LaneInfoMngService {
	
	public List<LaneInfoBean> findList(ObjectMap queryParam,Page page);
	
	public LaneInfoBean findInfo(String id);
	
	public void save(LaneInfoBean bean,SysLoginUserBean userBean);
	
	public void update(LaneInfoBean bean,SysLoginUserBean userBean);
	
	public void delete(String id,SysLoginUserBean userBean);
	
	public List<OakTreeModel> loadLaneTree(String PARENT_ID);
	
	public Integer findSameLane(LaneInfoBean bean);
}