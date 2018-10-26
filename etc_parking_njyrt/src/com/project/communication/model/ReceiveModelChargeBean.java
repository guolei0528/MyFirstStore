package com.project.communication.model;

import java.util.Date;

public class ReceiveModelChargeBean {

	public String type;
	
	public String park_id;
	
	public String area_id;
	
	public String lane_id;
	
	public String mv_license;
	
	public int car_color;
	
	public int vehicle_class;
	
	public Date entry_time;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

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

	public String getLane_id() {
		return lane_id;
	}

	public void setLane_id(String lane_id) {
		this.lane_id = lane_id;
	}

	public String getMv_license() {
		return mv_license;
	}

	public void setMv_license(String mv_license) {
		this.mv_license = mv_license;
	}

	public int getCar_color() {
		return car_color;
	}

	public void setCar_color(int car_color) {
		this.car_color = car_color;
	}

	public int getVehicle_class() {
		return vehicle_class;
	}

	public void setVehicle_class(int vehicle_class) {
		this.vehicle_class = vehicle_class;
	}

	public Date getEntry_time() {
		return entry_time;
	}

	public void setEntry_time(Date entry_time) {
		this.entry_time = entry_time;
	}
	
}
