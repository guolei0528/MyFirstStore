package com.project.communication.model;

import java.io.Serializable;

public class InsideJsonBean implements Serializable{
	private String type;
	private String execute_status;
	private String user_type;
	private String valid_type;
	private String wl_type;
	private String now_time;
	private String mv_license;
	private String obu_id;
	private String vehicle_class;
	private String car_id;
	
	
	public String getMv_license() {
		return mv_license;
	}
	public void setMv_license(String mv_license) {
		this.mv_license = mv_license;
	}
	public String getObu_id() {
		return obu_id;
	}
	public void setObu_id(String obu_id) {
		this.obu_id = obu_id;
	}
	public String getType() {
		return type;
	}
	public  InsideJsonBean(){}
	public InsideJsonBean(String type, String execute_status, String user_type, String valid_type, String nowTime) {
		this.type = type;
		this.execute_status = execute_status;
		this.user_type = user_type;
		this.valid_type = valid_type;
		this.now_time = nowTime;
	}

	public void setType(String type) {
		this.type = type;
	}
	public String getExecute_status() {
		return execute_status;
	}
	public void setExecute_status(String execute_status) {
		this.execute_status = execute_status;
	}
	public String getUser_type() {
		return user_type;
	}
	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}
	public String getValid_type() {
		return valid_type;
	}
	public void setValid_type(String valid_type) {
		this.valid_type = valid_type;
	}
	
	public String getNow_time() {
		return now_time;
	}
	public void setNow_time(String now_time) {
		this.now_time = now_time;
	}
	public String getVehicle_class() {
		return vehicle_class;
	}
	public void setVehicle_class(String vehicle_class) {
		this.vehicle_class = vehicle_class;
	}
	public String getCar_id() {
		return car_id;
	}
	public void setCar_id(String car_id) {
		this.car_id = car_id;
	}
	public String getWl_type() {
		return wl_type;
	}
	public void setWl_type(String wl_type) {
		this.wl_type = wl_type;
	}
	
}
