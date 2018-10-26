package com.project.backMng.admin.transitRecord.ExitMng.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.project.backMng.admin.transitRecord.ExitMng.model.ExitBean;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.SysLoginUserBean;
import com.redoak.jar.base.mybatis.service.BaseService;

public class ExitMngServiceImpl extends BaseService implements ExitMngService{

	private final static Logger log=LogManager.getLogger(ExitMngServiceImpl.class);

	public ExitMngServiceImpl() {
		super();
		setIBATIS_NAME_SPACE("backMng.admin.transitRecord.ExitMng");
		// TODO Auto-generated constructor stub
	}

	public List<ExitBean> findList(ObjectMap queryParam, Page page) {
		queryParam.put("skipSize", (page.getCurrentPage()-1)*page.getPageSize());
		queryParam.put("pageSize", page.getPageSize());
		page.setRecordCount(super.findInteger(ns("findCount"),queryParam));
		return super.findList(ns("findList"),queryParam);
	}

	public ExitBean findInfo(ObjectMap map) {
		return (ExitBean)super.findList(ns("findInfo"),map).get(0);
	}
	public ExitBean findHisInfo(ObjectMap map) {
		return super.findObj(ns("findHisInfo"),map);
	}

	public void save(ExitBean bean, SysLoginUserBean userBean) {
		// TODO Auto-generated method stub
//		bean.setID(UuidGenerator.generateUUID());
		super.insert(ns("insert"), bean);
	}

	public void update(ExitBean bean, SysLoginUserBean userBean) {
		// TODO Auto-generated method stub
		super.update(ns("update"), bean);
	}

	public void delete(String id,SysLoginUserBean userBean) {
		// TODO Auto-generated method stub
		super.update(ns("delete"), id);
	}
	
	public List<ExitBean> findHisList(ObjectMap queryParam, Page page) {
		// TODO Auto-generated method stub
		page.setRecordCount(super.findInteger(ns("findHisCount"),queryParam));
		return super.findList(ns("findHisList"),queryParam,page);
	}
	

}