package com.project.backMng.platuser.sys.LaneInfoMng.service;

import java.util.List;

import com.project.backMng.platuser.sys.LaneInfoMng.model.LaneInfoBean;
import com.redoak.jar.base.model.OakTreeModel;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.SysLoginUserBean;
import com.redoak.jar.base.mybatis.service.BaseService;

public class LaneInfoMngServiceImpl extends BaseService implements LaneInfoMngService{


	public LaneInfoMngServiceImpl() {
		super();
		setIBATIS_NAME_SPACE("backMng.platuser.sys.LaneInfoMng");
		// TODO Auto-generated constructor stub
	}

	public List<LaneInfoBean> findList(ObjectMap queryParam, Page page) {
		// TODO Auto-generated method stub
		if(page==null)
		{	
			return super.findList(ns("findList"),queryParam);
		}
		page.setRecordCount(super.findInteger(ns("findCount"),queryParam));
		return super.findList(ns("findList"),queryParam,page);
	}

	public LaneInfoBean findInfo(String id) {
		// TODO Auto-generated method stub
		return super.findObj(ns("findInfo"),id);
	}

	public void save(LaneInfoBean bean, SysLoginUserBean userBean) {
		// TODO Auto-generated method stub
//		bean.setID(UuidGenerator.generateUUID());
		super.insert(ns("insert"), bean);
	}

	public void update(LaneInfoBean bean, SysLoginUserBean userBean) {
		// TODO Auto-generated method stub
		super.update(ns("update"), bean);
	}

	public void delete(String id,SysLoginUserBean userBean) {
		// TODO Auto-generated method stub
		super.update(ns("delete"), id);
	}

	@Override
	public List<OakTreeModel> loadLaneTree(String PARENT_ID) {
		return super.findList(ns("loadLaneTree"),PARENT_ID);
	}
	
	public Integer findSameLane(LaneInfoBean bean){
		return super.findInteger(ns("findSameLane"),bean);
	}

}