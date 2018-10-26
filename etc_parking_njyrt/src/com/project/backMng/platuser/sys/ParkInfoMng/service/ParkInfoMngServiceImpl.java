package com.project.backMng.platuser.sys.ParkInfoMng.service;

import java.util.List;

import com.redoak.jar.base.model.OakTreeModel;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.SysLoginUserBean;
import com.project.common.tool.AppConst;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.redoak.jar.base.mybatis.service.BaseService;
import com.project.backMng.platuser.sys.ParkInfoMng.model.ParkInfoBean;

public class ParkInfoMngServiceImpl extends BaseService implements ParkInfoMngService{

	private final static Logger log=LogManager.getLogger(ParkInfoMngServiceImpl.class);

	public ParkInfoMngServiceImpl() {
		super();
		setIBATIS_NAME_SPACE("backMng.platuser.sys.ParkInfoMng");
		// TODO Auto-generated constructor stub
	}

	public List<ParkInfoBean> findList(ObjectMap queryParam, Page page) {
		// TODO Auto-generated method stub
		if(page==null){
			return super.findList(ns("findList"),queryParam);
		}
		page.setRecordCount(super.findInteger(ns("findCount"),queryParam));
		return super.findList(ns("findList"),queryParam,page);
	}

	public ParkInfoBean findInfo(String id) {
		// TODO Auto-generated method stub
		return super.findObj(ns("findInfo"),id);
	}

	public void save(ParkInfoBean bean, SysLoginUserBean userBean) {
		// TODO Auto-generated method stub
//		bean.setID(UuidGenerator.generateUUID());
		super.insert(ns("insert"), bean);
	}

	public void update(ParkInfoBean bean, SysLoginUserBean userBean) {
		// TODO Auto-generated method stub
		super.update(ns("update"), bean);
	}
	
	public void delete(String id,SysLoginUserBean userBean) {
		// TODO Auto-generated method stub
		super.update(ns("delete"), id);
		super.update(ns("deleteArea"), id);
		super.update(ns("deleteLane"), id);
	}

	@Override
	public List<OakTreeModel> loadParkTree() {
		// TODO Auto-generated method stub
		return super.findList(ns("loadParkTree"));
	}

}