package com.project.backMng.admin.report.service;

import java.util.List;
import java.util.Map;

import com.project.backMng.admin.report.model.StatisticsReportBean;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;

public interface CashReportService {

	public List<StatisticsReportBean> findList(ObjectMap objectMap,Page page);
	
	public List<StatisticsReportBean> findList(ObjectMap queryParam);
	
	 
	public List<ObjectMap> findLaneList();
	
	public List<ObjectMap> findUserList();
	
	/**
	 * 
	   * @Title : exportExcelData 
	   * @功能描述: 根据收费点、收费员、日期查询收费明细
	   * @传入参数：@param queryParam
	   * @传入参数：@return
	   * @返回类型：List<Map<String,Object>> 
	   * @throws ：
	 */
	public List<Map<String,Object>> exportExcelData(ObjectMap queryParam);
	
	public List<Map<String, Object>> exportDetailData(ObjectMap queryParam);
	
	/**
	 * 
	   * @Title : findLaneType 
	   * @功能描述: 
	   * @传入参数：@param queryParam
	   * @传入参数：@return
	   * @返回类型：int 
	   * @throws ：
	 */
	public int findLaneType(ObjectMap queryParam);
	
	
	/**
	 * 
	   * @Title : findExitList 
	   * @功能描述: 查找出口收款信息
	   * @传入参数：@param queryParam
	   * @传入参数：@return
	   * @返回类型：List 
	   * @throws ：
	 */
	public List findExitList(ObjectMap queryParam,Page page);
	
	
	
	/**
	 * 
	   * @Title : findExitList 
	   * @功能描述: 查找出口收款信息
	   * @传入参数：@param queryParam
	   * @传入参数：@return
	   * @返回类型：List 
	   * @throws ：
	 */
	public List findCenterList(ObjectMap queryParam,Page page);
}
