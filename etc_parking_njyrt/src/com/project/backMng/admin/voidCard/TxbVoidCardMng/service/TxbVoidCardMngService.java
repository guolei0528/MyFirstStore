package com.project.backMng.admin.voidCard.TxbVoidCardMng.service;

import java.util.List;

import com.project.backMng.admin.voidCard.TxbVoidCardMng.model.TxbVoidCardBean;
import com.project.backMng.platuser.sys.ParkInfoMng.model.ParkInfoBean;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.SysLoginUserBean;

public interface TxbVoidCardMngService {
	
	public List<TxbVoidCardBean> findList(ObjectMap queryParam,Page page);
	
	public TxbVoidCardBean findInfo(String mv_license,String plate_color) ;
	
	public void save(TxbVoidCardBean bean,SysLoginUserBean userBean);
	
	public void update(TxbVoidCardBean bean,SysLoginUserBean userBean);
	
	public void delete(ObjectMap map,SysLoginUserBean userBean);
	
	public void synchronizeList();
	
	public List<ParkInfoBean> findParkList();
}