package com.project.backMng.platuser.finance.InvoiceInfoMng.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.project.backMng.platuser.finance.InvoiceInfoMng.model.InvoiceInfoBean;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.SysLoginUserBean;
import com.redoak.jar.base.mybatis.service.BaseService;

public class InvoiceInfoMngServiceImpl extends BaseService implements InvoiceInfoMngService{

	private final static Logger log=LogManager.getLogger(InvoiceInfoMngServiceImpl.class);

	public InvoiceInfoMngServiceImpl() {
		super();
		setIBATIS_NAME_SPACE("backMng.platuser.finance.InvoiceInfoMng");
		// TODO Auto-generated constructor stub
	}

	public List<InvoiceInfoBean> findList(ObjectMap queryParam, Page page) {
		// TODO Auto-generated method stub
		page.setRecordCount(super.findInteger(ns("findCount"),queryParam));
		return super.findList(ns("findList"),queryParam,page);
	}

	public InvoiceInfoBean findInfo(String id) {
		// TODO Auto-generated method stub
		return super.findObj(ns("findInfo"),id);
	}

	public void save(InvoiceInfoBean bean, SysLoginUserBean userBean) {
		// TODO Auto-generated method stub
//		bean.setID(UuidGenerator.generateUUID());
		super.insert(ns("insert"), bean);
	}

	public void update(InvoiceInfoBean bean, SysLoginUserBean userBean) {
		// TODO Auto-generated method stub
		super.update(ns("update"), bean);
	}

	public void delete(String id,SysLoginUserBean userBean) {
		// TODO Auto-generated method stub
		super.update(ns("delete"), id);
	}

}