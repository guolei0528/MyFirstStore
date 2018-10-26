package com.project.backMng.platuser.finance.InvoiceInfoMng.service;

import java.util.List;

import com.project.backMng.platuser.finance.InvoiceInfoMng.model.InvoiceInfoBean;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.SysLoginUserBean;

public interface InvoiceInfoMngService {
	
	public List<InvoiceInfoBean> findList(ObjectMap queryParam,Page page);
	
	public InvoiceInfoBean findInfo(String id);
	
	public void save(InvoiceInfoBean bean,SysLoginUserBean userBean);
	
	public void update(InvoiceInfoBean bean,SysLoginUserBean userBean);
	
	public void delete(String id,SysLoginUserBean userBean);
}