package com.project.communication.model;

import java.io.Serializable;

public class IncomeCountBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8130587168547923968L;
	private String income_date;
	private String park_id;
	private String area_id;
	private String lane_id;
	private String income_count;
	private String real_bill;
	private String etc_bill;
	private String mobile_bill;
	private String member_type;
	private String date_time;
	public String getIncome_date() {
		return income_date;
	}
	public void setIncome_date(String income_date) {
		this.income_date = income_date;
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
	public String getIncome_count() {
		return income_count;
	}
	public void setIncome_count(String income_count) {
		this.income_count = income_count;
	}
	public String getReal_bill() {
		return real_bill;
	}
	public void setReal_bill(String real_bill) {
		this.real_bill = real_bill;
	}
	public String getEtc_bill() {
		return etc_bill;
	}
	public void setEtc_bill(String etc_bill) {
		this.etc_bill = etc_bill;
	}
	public String getMobile_bill() {
		return mobile_bill;
	}
	public void setMobile_bill(String mobile_bill) {
		this.mobile_bill = mobile_bill;
	}
	public String getMember_type() {
		return member_type;
	}
	public void setMember_type(String member_type) {
		this.member_type = member_type;
	}
	public String getDate_time() {
		return date_time;
	}
	public void setDate_time(String date_time) {
		this.date_time = date_time;
	}
	
	

}
