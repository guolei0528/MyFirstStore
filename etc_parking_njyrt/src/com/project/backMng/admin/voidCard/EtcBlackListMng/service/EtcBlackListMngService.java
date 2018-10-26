package com.project.backMng.admin.voidCard.EtcBlackListMng.service;

import java.util.List;

import com.project.backMng.admin.voidCard.EtcBlackListMng.model.EtcBlackListBean;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;

public interface EtcBlackListMngService {

	
	public List<EtcBlackListBean> findList(ObjectMap queryParam,Page page);
	
	public void synchronizeList();
	
	public void findEtcList(ObjectMap queryParam);
}
