package com.project.communication.model;

import java.io.Serializable;
import java.util.Date;

public class InputDataBean implements Serializable{
	private String type;
	
	private String mv_license;
	
	private String obu_id;
	
	private Integer car_color;
	
	private String park_id;
	
	private String area_id;
	
	private String lane_id;
	
	private Integer vehicle_class;
	
	private String now_time;
	
	private String black_list_province;
	
	private String card_id;
	
	private Date entry_time;
	
	private String verify_code;
	
	private String valid_type;
	
	private Date exit_time;
	
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

	public Integer getVehicle_class() {
		return vehicle_class;
	}

	public void setVehicle_class(Integer vehicle_class) {
		this.vehicle_class = vehicle_class;
	}

	public Integer getCar_color() {
		return car_color;
	}

	public void setCar_color(Integer car_color) {
		this.car_color = car_color;
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

	public void setType(String type) {
		this.type = type;
	}

	public String getMv_license() {
		return mv_license;
	}

	public void setMv_license(String mv_license) {
		this.mv_license = mv_license;
	}

	public String getNow_time() {
		return now_time;
	}

	public void setNow_time(String now_time) {
		this.now_time = now_time;
	}

	public String getBlack_list_province() {
		return black_list_province;
	}

	public void setBlack_list_province(String black_list_province) {
		this.black_list_province = black_list_province;
	}

	public String getCard_id() {
		return card_id;
	}

	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}

	public Date getEntry_time() {
		return entry_time;
	}

	public void setEntry_time(Date entry_time) {
		this.entry_time = entry_time;
	}

	public String getVerify_code() {
		return verify_code;
	}

	public void setVerify_code(String verify_code) {
		this.verify_code = verify_code;
	}

	public String getValid_type() {
		return valid_type;
	}

	public void setValid_type(String valid_type) {
		this.valid_type = valid_type;
	}

	public Date getExit_time() {
		return exit_time;
	}

	public void setExit_time(Date exit_time) {
		this.exit_time = exit_time;
	}

	
}
