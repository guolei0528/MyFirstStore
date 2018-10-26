package com.project.backMng.admin.transitRecord.EntryMng.service;

import java.util.List;

import com.project.backMng.admin.transitRecord.EntryMng.model.EntryBean;
import com.project.backMng.platuser.sys.AreaInfoMng.model.AreaInfoBean;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.SysLoginUserBean;

public interface EntryMngService {
	
	public List<EntryBean> findList(ObjectMap queryParam,Page page);
	public List<EntryBean> findHisList(ObjectMap queryParam,Page page);
	
	public EntryBean findInfo(ObjectMap map) ;
	
	public void save(EntryBean bean,SysLoginUserBean userBean);
	
	public void update(EntryBean bean,SysLoginUserBean userBean);
	
	public void delete(String mv_license,int car_color);
	public List<EntryBean> findTimeoutCarList(ObjectMap queryParam, Page page);
	public List<ObjectMap> findExcessiveCarList(ObjectMap queryParam, Page page);
	
	public List<AreaInfoBean> findEmptyPosition(ObjectMap queryParam, Page page);
	
	public ObjectMap findAreaInfo(String area_id);
	public EntryBean findHisInfo(ObjectMap map);
	
}