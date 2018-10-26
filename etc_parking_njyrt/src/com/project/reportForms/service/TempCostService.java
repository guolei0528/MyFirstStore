package com.project.reportForms.service;

import java.util.List;

import com.project.reportForms.model.ParkingSpaceInfo;
import com.redoak.jar.base.model.ObjectMap;

public interface TempCostService {

	/**
	 * 根据停车场ID，查询该停车场各区域的停车位信息
	 * @param parkid
	 * @return
	 */
	public List<ParkingSpaceInfo> getParkingSpaceInfo(String parkid);
	
	/**
	 * 查询临时计费表里某天驶入的车辆牌照
	 * @param param
	 * @return
	 */
	public List<String> getLicensesByDay(ObjectMap param);
	
	/**
	 * 统计临时计费表里某天驶入的车流量
	 * @param param
	 * @return
	 */
	public int countFlow(ObjectMap param);
}
