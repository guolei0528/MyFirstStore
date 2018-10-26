package com.project.backMng.platuser.sys.AreaInfoMng.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.project.backMng.platuser.sys.AreaInfoMng.model.AreaInfoBean;
import com.redoak.jar.base.model.OakTreeModel;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.SysLoginUserBean;
import com.redoak.jar.base.mybatis.service.BaseService;

public class AreaInfoMngServiceImpl extends BaseService implements AreaInfoMngService{


	public AreaInfoMngServiceImpl() {
		super();
		setIBATIS_NAME_SPACE("backMng.platuser.sys.AreaInfoMng");
		// TODO Auto-generated constructor stub
	}

	public List<AreaInfoBean> findList(ObjectMap queryParam, Page page) {
		// TODO Auto-generated method stub
		if(page==null){
			return super.findList(ns("findList"),queryParam);
		}
		page.setRecordCount(super.findInteger(ns("findCount"),queryParam));
		return super.findList(ns("findList"),queryParam,page);
	}

	public AreaInfoBean findInfo(String id) {
		// TODO Auto-generated method stub
		return super.findObj(ns("findInfo"),id);
	}

	public void save(AreaInfoBean bean, SysLoginUserBean userBean) {
		// TODO Auto-generated method stub
//		bean.setID(UuidGenerator.generateUUID());
		super.insert(ns("insert"), bean);
	}

	public void update(AreaInfoBean bean, SysLoginUserBean userBean) {
		// TODO Auto-generated method stub
		super.update(ns("update"), bean);
	}

	public void delete(String id,SysLoginUserBean userBean) {
		// TODO Auto-generated method stub
		super.update(ns("delete"), id);
		super.update(ns("caseCadeDelete"), id);
	}

	@Override
	public List<OakTreeModel> loadAreaTree(String PARENT_ID) {
		// TODO Auto-generated method stub
		return super.findList(ns("loadAreaTree"),PARENT_ID);
	}
	public Integer findSameArea(AreaInfoBean bean){
		
		return super.findInteger(ns("findSameArea"),bean);
	}

	@Override
	public List<Map> findAllArea() {
		Map<String,String> map=new HashMap<String,String>();
		List<Map> list = super.findList(ns("findAllList"), map);
		return list;
	}

}