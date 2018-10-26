package com.project.backMng.admin.report.service;

import java.util.List;
import java.util.Map;

import com.project.backMng.admin.report.model.OrderCarReportBean;
import com.project.backMng.admin.report.model.StatisticsReportBean;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;

public interface MobileReportService {
	
	//获取手机网页报表数据
	public List<OrderCarReportBean> findOrderList(ObjectMap objectMap,Page page);
	
	//获取手机支付
	
	//获取查询报表收费金额
	public int totalList(ObjectMap queryParam);
	
	//根据日期统计移动支付报表
	public List<StatisticsReportBean> findList(ObjectMap queryParam,Page page);
	
	/**
	 * 
	* @Title: findLaneList 
	* @Description: 查询出所有出口和集中缴费信息
	* @param @return    设定文件 
	* @return List<ObjectMap>    返回类型 
	* @throws
	 */
	public List<ObjectMap> findLaneList();
	
	/**
	 * 
	* @Title: findUserList 
	* @Description: 查询出所有收费员名称列表
	* @param @return    设定文件 
	* @return List<ObjectMap>    返回类型 
	* @throws
	 */
	public List<ObjectMap> findUserList();
	
	
	
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
	
	public List<Map<String, Object>> exportDetailSheet(ObjectMap queryParam);
	
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
	
	
	/**
	 * 
	   * @Title : findExitList 
	   * @功能描述: 查找出口收款信息
	   * @传入参数：@param queryParam
	   * @传入参数：@return
	   * @返回类型：List 
	   * @throws ：
	 */
	public List findOrderDetailList(ObjectMap queryParam,Page page);
	
}
