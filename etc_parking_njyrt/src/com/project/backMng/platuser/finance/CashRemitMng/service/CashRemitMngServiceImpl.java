package com.project.backMng.platuser.finance.CashRemitMng.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.project.backMng.platuser.finance.CashRemitMng.model.CashRemitBean;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.mybatis.service.BaseService;

public class CashRemitMngServiceImpl extends BaseService implements CashRemitMngService{

	private final static Logger log=LogManager.getLogger(CashRemitMngServiceImpl.class);

	public CashRemitMngServiceImpl() {
		super();
		setIBATIS_NAME_SPACE("backMng.platuser.finance.CashRemitMng");
		// TODO Auto-generated constructor stub
	}
	

	/**
	 *   
	   * @Title : findList 
	   * @功能描述: 查询现金解缴list
	   * @传入参数：@param queryParam
	   * @传入参数：@param page
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public List<CashRemitBean> findList(ObjectMap queryParam, Page page) {
		page.setRecordCount(super.findInteger(ns("findCount"),queryParam));
		List<CashRemitBean> paymentBeans = super.findList(ns("findList"),queryParam,page);
		return paymentBeans;
	}


	/**
	 * 
	   * @Title : findInfo 
	   * @功能描述: 根据主键查询现金解缴信息
	   * @传入参数：@param queryParam
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public CashRemitBean findInfo(ObjectMap queryParam) {
		//获取现金解缴bean
		CashRemitBean cashRemitBean = super.findObj(ns("findInfo"),queryParam);
		return cashRemitBean;
	}


	/**
	 * 
	   * @Title : checkDescribeInfo 
	   * @功能描述: 根据金额比对返回描述信息
	   * @传入参数：@param bean
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public String checkDescribeInfo(CashRemitBean bean) {
		
		StringBuffer sb = new StringBuffer();
		//现金实收金额统计(recodtype =3)
		int receipts_toll_count = bean.getReceipts_toll_count();
		//现金实收金额(recodetype =128)
		int receipts_toll = bean.getReceipts_toll();
		//判断下班的实收金额是否与流水的实收金额是否一致
		if(receipts_toll_count > receipts_toll)
		{
			
		}
		return null;
	}
	
	
	
	

}
