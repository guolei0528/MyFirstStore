package com.project.communication.model;

import java.util.Date;

public class PaymentChannelBean {

	
	private String type;
	
	private int terminal_type;
	
	private String trml_park_id;
	
	private String trml_area_id;
	
	private int terminal_id;
	
	private int pay_method;
	
	private String mv_license;
	
	private int car_color;
	
	private int vehicle_class;
	
	private Date entry_time;
	
	private String park_id;
	
	private String area_id;
	
	private int entry_lane;
	
	private int prepay_bill;
	
	private Date pay_time;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public int getTerminal_id() {
		return terminal_id;
	}

	public void setTerminal_id(int terminal_id) {
		this.terminal_id = terminal_id;
	}

	public int getPay_method() {
		return pay_method;
	}

	public void setPay_method(int pay_method) {
		this.pay_method = pay_method;
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

	public int getPrepay_bill() {
		return prepay_bill;
	}

	public void setPrepay_bill(int prepay_bill) {
		this.prepay_bill = prepay_bill;
	}

	public Date getPay_time() {
		return pay_time;
	}

	public void setPay_time(Date pay_time) {
		this.pay_time = pay_time;
	}
	
}
