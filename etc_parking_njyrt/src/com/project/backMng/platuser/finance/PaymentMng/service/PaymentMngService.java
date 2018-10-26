package com.project.backMng.platuser.finance.PaymentMng.service;

import java.util.List;

import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.SysLoginUserBean;
import com.project.backMng.platuser.finance.PaymentMng.model.PaymentBean;

public interface PaymentMngService {
	
	public List<PaymentBean> findList(ObjectMap queryParam,Page page);
	
	public PaymentBean findInfo(String operator_id,String begin_time);
	
	public void save(PaymentBean bean,SysLoginUserBean userBean);
	
	public void update(PaymentBean bean,SysLoginUserBean userBean);
	
	public void delete(String id,SysLoginUserBean userBean);
	
	public List<ObjectMap> findUser(String date);
	
	public ObjectMap findAmountAndBill(ObjectMap queryParam);
	
	public ObjectMap findUserOperator(String userid);
	
	public Integer findSamePayment(String operator_id,String date);
}