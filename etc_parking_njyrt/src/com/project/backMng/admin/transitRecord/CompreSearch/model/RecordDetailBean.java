package com.project.backMng.admin.transitRecord.CompreSearch.model;

import java.io.Serializable;

public class RecordDetailBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -395471471804788475L;

	private String mv_license;
	private String entry_lane;
	private String entry_time;
	private String entry_operator;
	private String terminal_time;
	private String terminal_operator;
	private String exit_lane;
	private String exit_time;
	private String exit_operator;
	private String totaltoll;
	private String pay_method;
	private String imagepath;
	private String order_id;
	private String pay_bill;
	private String pay_time;
	@Override
	public String toString() {
		return "RecordDetailBean [mv_license=" + mv_license + ", entry_lane=" + entry_lane + ", entry_time="
				+ entry_time + ", entry_operator=" + entry_operator + ", terminal_time=" + terminal_time
				+ ", terminal_operator=" + terminal_operator + ", exit_lane=" + exit_lane + ", exit_time=" + exit_time
				+ ", exit_operator=" + exit_operator + ", totaltoll=" + totaltoll + ", pay_method=" + pay_method
				+ ", imagepath=" + imagepath + ", order_id=" + order_id + ", pay_bill=" + pay_bill + ", pay_time="
				+ pay_time + "]";
	}
	public String getMv_license() {
		return mv_license;
	}
	public void setMv_license(String mv_license) {
		this.mv_license = mv_license;
	}
	public String getEntry_lane() {
		return entry_lane;
	}
	public void setEntry_lane(String entry_lane) {
		this.entry_lane = entry_lane;
	}
	public String getEntry_time() {
		return entry_time;
	}
	public void setEntry_time(String entry_time) {
		this.entry_time = entry_time;
	}
	public String getEntry_operator() {
		return entry_operator;
	}
	public void setEntry_operator(String entry_operator) {
		this.entry_operator = entry_operator;
	}
	public String getTerminal_time() {
		return terminal_time;
	}
	public void setTerminal_time(String terminal_time) {
		this.terminal_time = terminal_time;
	}
	public String getTerminal_operator() {
		return terminal_operator;
	}
	public void setTerminal_operator(String terminal_operator) {
		this.terminal_operator = terminal_operator;
	}
	public String getExit_lane() {
		return exit_lane;
	}
	public void setExit_lane(String exit_lane) {
		this.exit_lane = exit_lane;
	}
	public String getExit_time() {
		return exit_time;
	}
	public void setExit_time(String exit_time) {
		this.exit_time = exit_time;
	}
	public String getExit_operator() {
		return exit_operator;
	}
	public void setExit_operator(String exit_operator) {
		this.exit_operator = exit_operator;
	}
	public String getTotaltoll() {
		return totaltoll;
	}
	public void setTotaltoll(String totaltoll) {
		this.totaltoll = totaltoll;
	}
	public String getPay_method() {
		return pay_method;
	}
	public void setPay_method(String pay_method) {
		this.pay_method = pay_method;
	}
	public String getImagepath() {
		return imagepath;
	}
	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getPay_bill() {
		return pay_bill;
	}
	public void setPay_bill(String pay_bill) {
		this.pay_bill = pay_bill;
	}
	public String getPay_time() {
		return pay_time;
	}
	public void setPay_time(String pay_time) {
		this.pay_time = pay_time;
	}
}
