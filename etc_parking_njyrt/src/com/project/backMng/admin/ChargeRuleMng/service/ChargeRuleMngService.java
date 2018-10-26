package com.project.backMng.admin.ChargeRuleMng.service;

import java.util.List;

import com.project.backMng.admin.ChargeRuleMng.model.ChargeRuleBean;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.SysLoginUserBean;

public interface ChargeRuleMngService {
	
	public List<ChargeRuleBean> findList(ObjectMap queryParam,Page page);
	
	public ChargeRuleBean findInfo(String id);
	
	public void save(ChargeRuleBean bean,SysLoginUserBean userBean);
	
	public void update(ChargeRuleBean bean,SysLoginUserBean userBean);
	
	public void delete(String id,SysLoginUserBean userBean);
}