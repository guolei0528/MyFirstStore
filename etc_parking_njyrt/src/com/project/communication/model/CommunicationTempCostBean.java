package com.project.communication.model;

import java.util.Date;

/**
   * @类 名： CommunicationTempCostBean
   * @功能描述： TODO
   * @作者信息：吴超
   * @创建时间： 2017年8月18日下午5:27:36
   * @修改备注：
   */
public class CommunicationTempCostBean {

	
	private String park_id;
	
	
	private String area_id;
	
	
	private int entry_lane;
	
	private Date entry_time;
	
	private int car_color;

	public String getPark_id() {
		return park_id;
	}

	public void setPark_id(String park_id) {
		this.park_id = park_id;
	}

	public String getArea_id() {
		return area_id;
	}

	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}

	public int getEntry_lane() {
		return entry_lane;
	}

	public void setEntry_lane(int entry_lane) {
		this.entry_lane = entry_lane;
	}

	public Date getEntry_time() {
		return entry_time;
	}

	public void setEntry_time(Date entry_time) {
		this.entry_time = entry_time;
	}

	public int getCar_color() {
		return car_color;
	}

	public void setCar_color(int car_color) {
		this.car_color = car_color;
	}
	
}
