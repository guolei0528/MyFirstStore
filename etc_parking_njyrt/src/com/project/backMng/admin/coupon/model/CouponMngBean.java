package com.project.backMng.admin.coupon.model;

import java.util.Date;

public class CouponMngBean {

	private String coupon_type;
	private int use_restrict;
	private String station_id;
	private String station_name;
	private int issue_date;
	private int batch_id;
	private String issuer_code;
	private String issuer_name;
	private String verify_code;
	private int coupon_toll;
	private String coupon_code;
	private Date start_time;
	private Date end_time;
	private int issuer_id;
	private int status;
	private String create_user;
	private String user_name;
	private Date create_time;
	
	public String getCoupon_type() {
		return coupon_type;
	}
	public void setCoupon_type(String coupon_type) {
		this.coupon_type = coupon_type;
	}
	public int getUse_restrict() {
		return use_restrict;
	}
	public void setUse_restrict(int use_restrict) {
		this.use_restrict = use_restrict;
	}
	public String getStation_id() {
		return station_id;
	}
	public void setStation_id(String station_id) {
		this.station_id = station_id;
	}
	public int getIssue_date() {
		return issue_date;
	}
	public void setIssue_date(int issue_date) {
		this.issue_date = issue_date;
	}
	public String getIssuer_code() {
		return issuer_code;
	}
	public void setIssuer_code(String issuer_code) {
		this.issuer_code = issuer_code;
	}
	
	public String getIssuer_name() {
		return issuer_name;
	}
	public void setIssuer_name(String issuer_name) {
		this.issuer_name = issuer_name;
	}
	public String getVerify_code() {
		return verify_code;
	}
	public void setVerify_code(String verify_code) {
		this.verify_code = verify_code;
	}
	public int getCoupon_toll() {
		return coupon_toll;
	}
	public void setCoupon_toll(int coupon_toll) {
		this.coupon_toll = coupon_toll;
	}
	public String getCoupon_code() {
		return coupon_code;
	}
	public void setCoupon_code(String coupon_code) {
		this.coupon_code = coupon_code;
	}
	public Date getStart_time() {
		return start_time;
	}
	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}
	public Date getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}
	public int getIssuer_id() {
		return issuer_id;
	}
	public void setIssuer_id(int issuer_id) {
		this.issuer_id = issuer_id;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public int getBatch_id() {
		return batch_id;
	}
	public void setBatch_id(int batch_id) {
		this.batch_id = batch_id;
	}
	public String getStation_name() {
		return station_name;
	}
	public void setStation_name(String station_name) {
		this.station_name = station_name;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	

}
