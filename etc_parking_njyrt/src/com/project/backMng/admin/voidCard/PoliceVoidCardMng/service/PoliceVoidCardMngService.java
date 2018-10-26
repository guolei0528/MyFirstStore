package com.project.backMng.admin.voidCard.PoliceVoidCardMng.service;

import java.util.List;

import com.project.backMng.admin.voidCard.PoliceVoidCardMng.model.PoliceVoidCardBean;
import com.project.backMng.platuser.sys.ParkInfoMng.model.ParkInfoBean;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.SysLoginUserBean;

public interface PoliceVoidCardMngService {
	
	public List<PoliceVoidCardBean> findList(ObjectMap queryParam,Page page);
	
	public PoliceVoidCardBean findInfo(String mv_license,String plate_color) ;
	
	public void save(PoliceVoidCardBean bean,SysLoginUserBean userBean);
	
	public void update(PoliceVoidCardBean bean,SysLoginUserBean userBean);
	
	public void delete(ObjectMap map,SysLoginUserBean userBean);
	
	public void synchronizeList();
	
	public List<ParkInfoBean> findParkList();
}