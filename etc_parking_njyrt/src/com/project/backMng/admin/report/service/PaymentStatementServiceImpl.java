package com.project.backMng.admin.report.service;

import java.util.List;

import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.mybatis.service.BaseService;

public class PaymentStatementServiceImpl  extends BaseService implements PaymentStatementService{

	
	public PaymentStatementServiceImpl() {
		super();
		setIBATIS_NAME_SPACE("backMng.admin.report.PaymentStatement");
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * 
	   * @Title : findSummaryList 
	   * @功能描述: 查询对账汇总
	   * @传入参数：@param queryParam
	   * @传入参数：@param page
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public List<ObjectMap> findSummaryList(ObjectMap queryParam, Page page) {
		// TODO Auto-generated method stub
		int count = super.findInteger(ns("findCountSummaryReport"), queryParam);
		page.setRecordCount(count);
		page.setPageSize(15);
		List<ObjectMap> dimension = super.findList(ns("findSummaryReportDimension"), queryParam,page);
		List<ObjectMap> paymentStatement = super.findList(ns("findSummaryReport"), queryParam);
		return backSummaryData(dimension,paymentStatement);
	}
	
	
	
	/**
	 * 
	   * @Title : backSummaryData 
	   * @功能描述: 判断结果集里的结果是否满足列表条件，满足进行复制
	   * @传入参数：@param dimension
	   * @传入参数：@param paymentStatement
	   * @传入参数：@return
	   * @返回类型：List<ObjectMap> 
	   * @throws ：
	 */
	private List<ObjectMap> backSummaryData(List<ObjectMap> dimension,List<ObjectMap> paymentStatement)
	{
		//迭代返回列表
		for(ObjectMap objectMap:dimension)
		{
			objectMap.put("etcAmount", 0);
			objectMap.put("etcTotalToll", 0);
//			objectMap.put("storeValueAmount", 0);
//			objectMap.put("storeValueTotalToll", 0);
			objectMap.put("accountAmount", 0);
			objectMap.put("accountTotalToll", 0);
			objectMap.put("mobileAmount", 0);
			objectMap.put("mobileTotalToll", 0);
			objectMap.put("otherAmount", 0);
			objectMap.put("otherTotalToll", 0);
			
			for(ObjectMap map:paymentStatement)
			{
				//判断列表是否一致
				if(objectMap.get("exitdate").equals(map.get("exitdate")))
				{
					//判断是否为etc支付
					if(Integer.parseInt(map.get("paymethod").toString())==2)
					{
						
						//判断是否是ETC支付
//						ObjectMap.put("accountAmount",map.get("amount"));
						int amount = Integer.parseInt(map.get("amount").toString())+
								Integer.parseInt(objectMap.get("etcAmount").toString());
						objectMap.put("etcAmount",amount);
						int totalToll = Integer.parseInt(map.get("totalToll").toString())+
								Integer.parseInt(objectMap.get("etcTotalToll").toString());
						objectMap.put("etcTotalToll",totalToll);
						continue;
						//判断是否为储值卡
//						if(Integer.parseInt(map.get("paymethod").toString())==22)
//						{
//							int amount = Integer.parseInt(map.get("amount").toString())+
//									Integer.parseInt(objectMap.get("storeValueAmount").toString());
//							objectMap.put("storeValueAmount",amount);
//							int totalToll = Integer.parseInt(map.get("totalToll").toString())+
//									Integer.parseInt(objectMap.get("storeValueTotalToll").toString());
//							objectMap.put("storeValueTotalToll",totalToll);
//							continue;
//						}
//						//判断是否为记账卡
//						else if(Integer.parseInt(map.get("paymethod").toString())==23)
//						{
////							ObjectMap.put("accountAmount",map.get("amount"));
//							int amount = Integer.parseInt(map.get("amount").toString())+
//									Integer.parseInt(objectMap.get("accountAmount").toString());
//							objectMap.put("accountAmount",amount);
//							int totalToll = Integer.parseInt(map.get("totalToll").toString())+
//									Integer.parseInt(objectMap.get("accountTotalToll").toString());
//							objectMap.put("accountTotalToll",totalToll);
//							continue;
//						}
//						else
//						{
//							int amount = Integer.parseInt(map.get("amount").toString())+
//									Integer.parseInt(objectMap.get("otherAmount").toString());
//							objectMap.put("otherAmount",amount);
//							int totalToll = Integer.parseInt(map.get("totalToll").toString())+
//									Integer.parseInt(objectMap.get("otherTotalToll").toString());
//							objectMap.put("otherTotalToll",totalToll);
//							continue;
//						}
					}
					
					//判断是否为移动支付
					if(Integer.parseInt(map.get("paymethod").toString())==3
				|| Integer.parseInt(map.get("paymethod").toString())==4)
					{
						int amount = Integer.parseInt(map.get("amount").toString())+
								Integer.parseInt(objectMap.get("mobileAmount").toString());
						objectMap.put("mobileAmount",amount);
						int totalToll = Integer.parseInt(map.get("totalToll").toString())+
								Integer.parseInt(objectMap.get("mobileTotalToll").toString());
						objectMap.put("mobileTotalToll",totalToll);
						continue;
					}
					//其他支付
					else
					{
						int amount = Integer.parseInt(map.get("amount").toString())+
								Integer.parseInt(objectMap.get("otherAmount").toString());
						objectMap.put("otherAmount",amount);
						int totalToll = Integer.parseInt(map.get("totalToll").toString())+
								Integer.parseInt(objectMap.get("otherTotalToll").toString());
						objectMap.put("otherTotalToll",totalToll);
						continue;
					}
				}
			}
			
			
		}
		
		return dimension;
	}

	/**
	 * 
	   * @Title : findDetailList 
	   * @功能描述: 查询对账明细
	   * @传入参数：@param queryParam
	   * @传入参数：@param page
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public List<ObjectMap> findDetailList(ObjectMap queryParam, Page page) {
		// TODO Auto-generated method stub
		int count = super.findInteger(ns("findCountDetailReport"), queryParam);
		page.setRecordCount(count);
		List<ObjectMap> paymentStatement = super.findList(ns("findDetailReport"), queryParam,page);
		
		/*StringBuilder strBuider = null;
		//隐藏7到13位的卡编号
		for(ObjectMap map:paymentStatement)
		{
			//判断卡号是否为空
			if(map.get("cardid")!=null && map.get("cardid").toString().length() == 16)
			{
				strBuider = new StringBuilder(map.get("cardid").toString());
				strBuider.replace(7, 13, "******");
				map.put("cardid", strBuider.toString());
			}
			strBuider.delete( 0, strBuider.length() );
		}*/
		
		return paymentStatement;
	}


	/**
	 * 
	   * @Title : findLaneList 
	   * @功能描述: 查询车道列表
	   * @传入参数：@param queryParam
	   * @传入参数：@param page
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public List<ObjectMap> findLaneList() {
		return super.findList(ns("queryLaneList"));
	}


	@Override
	public int totalDetailList(ObjectMap queryParam) {
		//判断是否为人工放行数据
		/*if(queryParam.get("query_pay_method") != null && 
				!"".equals(queryParam.get("query_pay_method").toString().trim()) &&
				Integer.parseInt(queryParam.get("query_pay_method").toString()) == 9)
		{
			return super.findInteger(ns("pdisTollDetailList"), queryParam);
		}
		else
		{*/
			return super.findInteger(ns("totalDetailList"), queryParam);
//		}
	}
	
	@Override
	public int receivablesDetailList(ObjectMap queryParam) {
		//判断是否为人工放行数据
		return super.findInteger(ns("pdisTollDetailList"), queryParam);
	}
	

}
