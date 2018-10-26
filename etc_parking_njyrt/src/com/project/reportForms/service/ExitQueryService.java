package com.project.reportForms.service;

import java.util.List;

import com.project.reportForms.model.InOrOutFlowInfo;
import com.redoak.jar.base.model.ObjectMap;

public interface ExitQueryService {

	/**
	 * 根据停车场编号、时间，查询该停车场在当天的总收入，返回单位为元的小数值，如512.32
	 * @param param
	 * @return
	 */
	public double getAllIncome(ObjectMap param);
	
	/**
	 * 根据停车场编号、时间，统计该停车场在当天的入口流量信息
	 * @param param
	 * @return
	 */
	public List<InOrOutFlowInfo> countEntranceFlow(ObjectMap param);
	
	/**
	 * 根据停车场编号、时间，统计该停车场在当天的出口流量及收益
	 * @param param
	 * @return
	 */
	public List<InOrOutFlowInfo> countExitFlow(ObjectMap param);
	
	/**
	 * 根据停车场编号、时间，统计该停车场在当天的出口总流量
	 * @param param
	 * @return
	 */
	public int countTotalFlowOfExit(ObjectMap param);
	
	/**
	 * 根据停车场编号、时间，统计该停车场在当天的计费为0元的流量
	 * @param param
	 * @return
	 */
	public int countFlowOfZero(ObjectMap param);
	
	/**
	 * 根据停车场编号、时间，统计该停车场在当天的入口总流量
	 * @param param
	 * @return
	 */
	public int countTotalFlowOfEntrance(ObjectMap param);
	
	/**
	 * 根据停车场编号、时间，统计该停车场在当天的无人值守期间的入口流量
	 * @param param
	 * @return
	 */
	public int countNightFlowOfEntrance(ObjectMap param);
	
	/**
	 * 根据停车场编号、时间，统计该停车场在当天的无人值守期间的出口流量
	 * @param param
	 * @return
	 */
	public InOrOutFlowInfo countNightFlowOfExit(ObjectMap param);
	
	/**
	 * 统计入口表里在某天驶入的车流量
	 * @param param
	 * @return
	 */
	public int countFlowInEntrance(ObjectMap param);
	
	/**
	 * 统计出口表里在某天驶入的车流量
	 * @param param
	 * @return
	 */
	public int countFlowInExit(ObjectMap param);
	
	/**
	 * 在入口表里查询某天驶入的车辆牌照
	 * @param param
	 * @return
	 */
	public List<String> getLicensesByDayInEntrance(ObjectMap param);
	
	/**
	 * 在出口表里查询某天驶入的车辆牌照
	 * @param param
	 * @return
	 */
	public List<String> getLicensesByDayInExit(ObjectMap param);
	
	/**
	 * 查询当日驶出的车辆中无入口信息（即入口时间为1970年）的车有几辆
	 * @param param
	 * @return
	 */
	public int countFlowWithoutEntrytime(ObjectMap param);
	
	/**
	 * 查询当日驶出的车辆分别是哪几日驶入的，各有几辆
	 * @param param
	 * @return
	 */
	public List<InOrOutFlowInfo> groupFlowByEntrytime(ObjectMap param);
}
