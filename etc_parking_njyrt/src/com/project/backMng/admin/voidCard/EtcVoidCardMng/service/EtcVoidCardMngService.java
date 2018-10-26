package com.project.backMng.admin.voidCard.EtcVoidCardMng.service;

import java.util.List;

import com.project.backMng.admin.voidCard.EtcVoidCardMng.model.EtcVoidCardBean;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.SysLoginUserBean;

public interface EtcVoidCardMngService {
	
	public List<EtcVoidCardBean> findList(ObjectMap queryParam,Page page);
	
	public EtcVoidCardBean findInfo(String card,String card_network);
	
	public void save(EtcVoidCardBean bean,SysLoginUserBean userBean);
	
	public void update(EtcVoidCardBean bean,SysLoginUserBean userBean);
	
	public void delete(String id,SysLoginUserBean userBean);
	
	public void synchronizeList();
	
	public void insertETC();
	
	public void testETC(String card);
}