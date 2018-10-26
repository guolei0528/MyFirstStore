package com.project.backMng.platuser.CareTrafficRecordMng.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.project.backMng.platuser.CareTrafficRecordMng.model.CareTrafficRecordBean;
import com.project.common.tool.AppConst;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.SysLoginUserBean;
import com.redoak.jar.base.mybatis.service.BaseService;

public class CareTrafficRecordMngServiceImpl extends BaseService implements CareTrafficRecordMngService{

	private final static Logger log=LogManager.getLogger(CareTrafficRecordMngServiceImpl.class);

	public CareTrafficRecordMngServiceImpl() {
		super();
		setIBATIS_NAME_SPACE("backMng.platuser.sys.CareTrafficRecordMng");
		// TODO Auto-generated constructor stub
	}

	public List<CareTrafficRecordBean> findList(ObjectMap queryParam, Page page) {
		// TODO Auto-generated method stub
		page.setRecordCount(super.findInteger(ns("findCount"),queryParam));
		return super.findList(ns("findList"),queryParam,page);
	}

	public CareTrafficRecordBean findInfo(String id) {
		// TODO Auto-generated method stub
		return super.findObj(ns("findInfo"),id);
	}

	public void save(CareTrafficRecordBean bean, SysLoginUserBean userBean) {
		// TODO Auto-generated method stub
//		bean.setID(UuidGenerator.generateUUID());
		super.insert(ns("insert"), bean);
	}

	public void update(CareTrafficRecordBean bean, SysLoginUserBean userBean) {
		// TODO Auto-generated method stub
		super.update(ns("update"), bean);
	}

	public void delete(String id,SysLoginUserBean userBean) {
		// TODO Auto-generated method stub
		super.update(ns("delete"), id);
	}

}