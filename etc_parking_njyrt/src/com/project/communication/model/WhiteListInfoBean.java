package com.project.communication.model;

import java.util.Date;

public class WhiteListInfoBean {

	public String mv_license;
	
	public Integer color;
	
	public String obu_id;
	
	public Integer vehicle_class;
	
	public Integer delete_flag;
	
	public Integer toll_type;
	
	public String charge_code;
	
	public Date valid_start_time;
	
	public Date valid_end_time;
	
	public Integer status;
	
	public String user_number;
	
	public Integer spare1;
	
	public Integer spare2;

	@Override
	public String toString() {
		return "WhiteListInfoBean [mv_license=" + mv_license + ", color=" + color + ", obu_id=" + obu_id
				+ ", vehicle_class=" + vehicle_class + ", delete_flag=" + delete_flag + ", toll_type=" + toll_type
				+ ", charge_code=" + charge_code + ", valid_start_time=" + valid_start_time + ", valid_end_time="
				+ valid_end_time + ", status=" + status + ", user_number=" + user_number + ", spare1=" + spare1
				+ ", spare2=" + spare2 + "]";
	}

	public String getMv_license() {
		return mv_license;
	}

	public void setMv_license(String mv_license) {
		this.mv_license = mv_license;
	}

	public Integer getColor() {
		return color;
	}

	public void setColor(Integer color) {
		this.color = color;
	}

	public String getObu_id() {
		return obu_id;
	}

	public void setObu_id(String obu_id) {
		this.obu_id = obu_id;
	}

	public Integer getVehicle_class() {
		return vehicle_class;
	}

	public void setVehicle_class(Integer vehicle_class) {
		this.vehicle_class = vehicle_class;
	}

	public Integer getDelete_flag() {
		return delete_flag;
	}

	public void setDelete_flag(Integer delete_flag) {
		this.delete_flag = delete_flag;
	}

	public Integer getToll_type() {
		return toll_type;
	}

	public void setToll_type(Integer toll_type) {
		this.toll_type = toll_type;
	}

	public String getCharge_code() {
		return charge_code;
	}

	public void setCharge_code(String charge_code) {
		this.charge_code = charge_code;
	}

	public Date getValid_start_time() {
		return valid_start_time;
	}

	public void setValid_start_time(Date valid_start_time) {
		this.valid_start_time = valid_start_time;
	}

	public Date getValid_end_time() {
		return valid_end_time;
	}

	public void setValid_end_time(Date valid_end_time) {
		this.valid_end_time = valid_end_time;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getUser_number() {
		return user_number;
	}

	public void setUser_number(String user_number) {
		this.user_number = user_number;
	}

	public Integer getSpare1() {
		return spare1;
	}

	public void setSpare1(Integer spare1) {
		this.spare1 = spare1;
	}

	public Integer getSpare2() {
		return spare2;
	}

	public void setSpare2(Integer spare2) {
		this.spare2 = spare2;
	}
}
