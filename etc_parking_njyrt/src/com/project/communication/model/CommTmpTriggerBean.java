package com.project.communication.model;

import java.util.Date;

public class CommTmpTriggerBean {

	
	private String park_id;
	
	
	private String area_id;
	
	private int lane_id;
	
	private String veh_plate;
	
	private int veh_class;
	
	private Date trigger_time;
	
	private int status;

	
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

	public int getLane_id() {
		return lane_id;
	}

	public void setLane_id(int lane_id) {
		this.lane_id = lane_id;
	}

	public String getVeh_plate() {
		return veh_plate;
	}

	public void setVeh_plate(String veh_plate) {
		this.veh_plate = veh_plate;
	}

	public int getVeh_class() {
		return veh_class;
	}

	public void setVeh_class(int veh_class) {
		this.veh_class = veh_class;
	}

	public Date getTrigger_time() {
		return trigger_time;
	}

	public void setTrigger_time(Date trigger_time) {
		this.trigger_time = trigger_time;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
