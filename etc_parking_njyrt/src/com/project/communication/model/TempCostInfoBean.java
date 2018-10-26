package com.project.communication.model;

import java.util.Date;

public class TempCostInfoBean {

	public String park_id;
	
	public String area_id;
	
	public Integer entry_lane;
	
	public Date entry_time;
	
	public Integer vehicle_class;
	
	public String mv_license;
	
	public String spare4;
	
	public Integer car_color;
	
	public String entry_image;
	
	public Date begin_time;
	
	public Integer pay_status;
	
	public Integer pay_method;
	
	public Date pay_time;
	
	public Integer prepay_bill;
	
	public Integer spare1;

	@Override
	public String toString() {
		return "TempCostInfoBean [park_id=" + park_id + ", area_id=" + area_id + ", entry_lane=" + entry_lane
				+ ", entry_time=" + entry_time + ", vehicle_class=" + vehicle_class + ", mv_license=" + mv_license
				+ ", spare4=" + spare4 + ", car_color=" + car_color + ", entry_image=" + entry_image + ", begin_time="
				+ begin_time + ", pay_status=" + pay_status + ", pay_method=" + pay_method + ", pay_time=" + pay_time
				+ ", prepay_bill=" + prepay_bill + ", spare1=" + spare1 + "]";
	}

	public Integer getPay_status() {
		return pay_status;
	}

	public void setPay_status(Integer pay_status) {
		this.pay_status = pay_status;
	}

	public Integer getPay_method() {
		return pay_method;
	}

	public void setPay_method(Integer pay_method) {
		this.pay_method = pay_method;
	}

	public Date getPay_time() {
		return pay_time;
	}

	public void setPay_time(Date pay_time) {
		this.pay_time = pay_time;
	}

	public Integer getPrepay_bill() {
		return prepay_bill;
	}

	public void setPrepay_bill(Integer prepay_bill) {
		this.prepay_bill = prepay_bill;
	}

	public Date getBegin_time() {
		return begin_time;
	}

	public void setBegin_time(Date begin_time) {
		this.begin_time = begin_time;
	}

	public Integer getSpare1() {
		return spare1;
	}

	public void setSpare1(Integer spare1) {
		this.spare1 = spare1;
	}

	public String getEntry_image() {
		return entry_image;
	}

	public void setEntry_image(String entry_image) {
		this.entry_image = entry_image;
	}

	public Integer getCar_color() {
		return car_color;
	}

	public void setCar_color(Integer car_color) {
		this.car_color = car_color;
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

	public Integer getEntry_lane() {
		return entry_lane;
	}

	public void setEntry_lane(Integer entry_lane) {
		this.entry_lane = entry_lane;
	}

	public Date getEntry_time() {
		return entry_time;
	}

	public void setEntry_time(Date entry_time) {
		this.entry_time = entry_time;
	}

	public Integer getVehicle_class() {
		return vehicle_class;
	}

	public void setVehicle_class(Integer vehicle_class) {
		this.vehicle_class = vehicle_class;
	}

	public String getMv_license() {
		return mv_license;
	}

	public void setMv_license(String mv_license) {
		this.mv_license = mv_license;
	}

	public String getSpare4() {
		return spare4;
	}

	public void setSpare4(String spare4) {
		this.spare4 = spare4;
	}
}
