package com.project.backMng.platuser.finance.CashRemitMng.service;

import java.util.List;

import com.project.backMng.platuser.finance.CashRemitMng.model.CashRemitBean;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;

public interface CashRemitMngService {
	
	public List<CashRemitBean> findList(ObjectMap queryParam,Page page);
	
	public CashRemitBean findInfo(ObjectMap queryParam);
	
	public String checkDescribeInfo(CashRemitBean bean);
	
	
}
