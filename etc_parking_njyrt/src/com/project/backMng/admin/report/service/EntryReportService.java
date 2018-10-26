package com.project.backMng.admin.report.service;

import java.util.List;
import java.util.Map;

import com.project.backMng.admin.report.model.StatisticsReportBean;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;

public interface EntryReportService {

	public List<StatisticsReportBean> findList(ObjectMap objectMap,Page page);
	
	public List<StatisticsReportBean> findList(ObjectMap objectMap);
	
	public List<ObjectMap> findLaneList();
	
	/**
	 * 
	* @Title: findList 
	* @Description: TODO(获取移动支付总表信息) 
	* @param @param queryParam
	* @param @return    设定文件 
	* @return List<StatisticsReportBean>    返回类型 
	* @throws
	 */
	public List<Map<String, Object>> exportTotalSheet(ObjectMap queryParam);
	
	/**
	 * 
	* @Title: findList 
	* @Description: TODO(获取移动支付总表信息) 
	* @param @param queryParam
	* @param @return    设定文件 
	* @return List<StatisticsReportBean>    返回类型 
	* @throws
	 */
	public List<Map<String, Object>> exportAllSheet(ObjectMap queryParam);
	
	
	/**
	 * 
	   * @Title : findExitList 
	   * @功能描述: 查找出口收款信息
	   * @传入参数：@param queryParam
	   * @传入参数：@return
	   * @返回类型：List 
	   * @throws ：
	 */
	public List findEntryList(ObjectMap queryParam,Page page);
	
	
	/**
	 * 
	   * @Title : exportDetailData 
	   * @功能描述: TODO
	   * @传入参数：@param queryParam
	   * @传入参数：@return
	   * @返回类型：List<Map<String,Object>> 
	   * @throws ：
	 */
	public List<Map<String, Object>> exportDetailData(ObjectMap queryParam);
	
}
