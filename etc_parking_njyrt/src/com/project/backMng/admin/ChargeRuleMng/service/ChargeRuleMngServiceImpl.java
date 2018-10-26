package com.project.backMng.admin.ChargeRuleMng.service;

import java.util.List;

import com.project.backMng.admin.ChargeRuleMng.model.ChargeRuleBean;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.SysLoginUserBean;
import com.redoak.jar.base.mybatis.service.BaseService;

public class ChargeRuleMngServiceImpl extends BaseService implements ChargeRuleMngService{


	public ChargeRuleMngServiceImpl() {
		super();
		setIBATIS_NAME_SPACE("backMng.admin.sys.ChargeRuleMng");
		// TODO Auto-generated constructor stub
	}

	public List<ChargeRuleBean> findList(ObjectMap queryParam, Page page) {
		// TODO Auto-generated method stub
		page.setRecordCount(super.findInteger(ns("findCount"),queryParam));
		return super.findList(ns("findList"),queryParam,page);
	}

	public ChargeRuleBean findInfo(String id) {
		// TODO Auto-generated method stub
		return super.findObj(ns("findInfo"),id);
	}

	public void save(ChargeRuleBean bean, SysLoginUserBean userBean) {
		// TODO Auto-generated method stub
//		bean.setID(UuidGenerator.generateUUID());
		super.insert(ns("insert"), bean);
	}

	public void update(ChargeRuleBean bean, SysLoginUserBean userBean) {
		// TODO Auto-generated method stub
		super.update(ns("update"), bean);
	}

	public void delete(String id,SysLoginUserBean userBean) {
		// TODO Auto-generated method stub
		super.update(ns("delete"), id);
	}

}