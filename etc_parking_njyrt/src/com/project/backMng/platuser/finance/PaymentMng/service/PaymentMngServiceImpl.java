package com.project.backMng.platuser.finance.PaymentMng.service;

import java.util.List;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.project.backMng.platuser.finance.PaymentMng.model.PaymentBean;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.SysLoginUserBean;
import com.redoak.jar.base.mybatis.service.BaseService;

public class PaymentMngServiceImpl extends BaseService implements PaymentMngService{

	private final static Logger log=LogManager.getLogger(PaymentMngServiceImpl.class);

	public PaymentMngServiceImpl() {
		super();
		setIBATIS_NAME_SPACE("backMng.platuser.finance.PaymentMng");
		// TODO Auto-generated constructor stub
	}

	public List<PaymentBean> findList(ObjectMap queryParam, Page page) {
		// TODO Auto-generated method stub
		page.setRecordCount(super.findInteger(ns("findCount"),queryParam));
		List<PaymentBean> paymentBeans = super.findList(ns("findList"),queryParam,page);
		return paymentBeans;
	}

	public PaymentBean findInfo(String operator_id,String date) {
		ObjectMap objectMap=ObjectMap.newInstance();
		objectMap.put("date", date);
		objectMap.put("operator_id", operator_id);
		return super.findObj(ns("findInfo"),objectMap);
	}

	public void save(PaymentBean bean, SysLoginUserBean userBean) {
		// TODO Auto-generated method stub
//		bean.setID(UuidGenerator.generateUUID());
		super.insert(ns("insert"), bean);
	}

	public void update(PaymentBean bean, SysLoginUserBean userBean) {
		// TODO Auto-generated method stub
		super.update(ns("update"), bean);
	}

	public void delete(String id,SysLoginUserBean userBean) {
		// TODO Auto-generated method stub
		super.update(ns("delete"), id);
	}
	
	
	/**
	 * 
	   * @Title : findUser 
	   * @功能描述: 根据日期查询平台用户
	   * @传入参数：@param date
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public List<ObjectMap> findUser(String date) {
		// TODO Auto-generated method stub
		date=date.replace("-",""); 
		return super.findList(ns("findUserByDate"),date);
	}

	/**
	 * 
	   * @Title : findAmountAndBill 
	   * @功能描述: 查询流量与金额数据
	   * @传入参数：@param queryParam
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public ObjectMap findAmountAndBill(ObjectMap queryParam) {
		
		
		
		//查询全部统计流量和金额
		ObjectMap objectMap = super.findObj(ns("findAllFlowAndBill"),queryParam);
		//查询现金统计流量和金额
		ObjectMap objectMapCash = super.findObj(ns("findCashFlowAndBill"),queryParam);
		//查询下班统计流量和金额
		ObjectMap objectMapReal = super.findObj(ns("findRealFlowAndBill"),queryParam);
		
		//判断是否没有统计流量和金额
		if(objectMap == null)
		{
			objectMap = new ObjectMap();
		}
		if(objectMapCash == null)
		{
			objectMapCash = new ObjectMap();
		}
		if(objectMapReal == null)
		{
			objectMapReal = new ObjectMap();
		}
		
		
		//迭代放入统计map
		for (Entry<String, Object> entry : objectMapCash.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue().toString();
			objectMap.put(key, value);
			}
		
		//迭代放入统计map
		for (Entry<String, Object> entry : objectMapReal.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue().toString();
			objectMap.put(key, value);
			}
		return objectMap;
	}
	
	
	@Override
	public ObjectMap findUserOperator(String userid){
		return super.findObj(ns("findUserOperator"),userid);
	}

	@Override
	public Integer findSamePayment(String operator_id, String date) {
		ObjectMap map=ObjectMap.newInstance();
		map.put("operator_id", operator_id);
		map.put("date", date);
		return super.findInteger(ns("findSamePayment"),map);
	}

}