package com.project.communication.model;

import java.util.Date;

public class ReceiveModelPayBean {
	
	public String type;
	
	public String mv_license;
	
	public int car_color;
	
	public int vehicle_class;
	
	public String park_id;
	
	public String area_id;
	
	public String lane_id;
	
	public int terminal_type;
	
	public String trml_park_id;
	
	public String trml_area_id;
	
	public String terminal_id;
	
	public int pay_method;
	
	public int prepay_toll;
	
	public Date pay_time;
	
	public Date entry_time;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public int getTerminal_type() {
		return terminal_type;
	}

	public void setTerminal_type(int terminal_type) {
		this.terminal_type = terminal_type;
	}

	public String getTrml_park_id() {
		return trml_park_id;
	}

	public void setTrml_park_id(String trml_park_id) {
		this.trml_park_id = trml_park_id;
	}

	public String getTrml_area_id() {
		return trml_area_id;
	}

	public void setTrml_area_id(String trml_area_id) {
		this.trml_area_id = trml_area_id;
	}

	public String getTerminal_id() {
		return terminal_id;
	}

	public void setTerminal_id(String terminal_id) {
		this.terminal_id = terminal_id;
	}

	public int getPay_method() {
		return pay_method;
	}

	public void setPay_method(int pay_method) {
		this.pay_method = pay_method;
	}
	
	public int getPrepay_toll() {
		return prepay_toll;
	}

	public void setPrepay_toll(int prepay_toll) {
		this.prepay_toll = prepay_toll;
	}

	public Date getPay_time() {
		return pay_time;
	}

	public void setPay_time(Date pay_time) {
		this.pay_time = pay_time;
	}

	public Date getEntry_time() {
		return entry_time;
	}

	public void setEntry_time(Date entry_time) {
		this.entry_time = entry_time;
	}
	
}
