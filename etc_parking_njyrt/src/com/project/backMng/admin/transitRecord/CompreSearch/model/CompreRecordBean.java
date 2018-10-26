package com.project.backMng.admin.transitRecord.CompreSearch.model;

import java.io.Serializable;

public class CompreRecordBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8334766616126669984L;
	private String license;
	private String lanename;
	private String time;
	private String operator;
	private String totaltoll;
	private String paymethod;
	private String typename;
	private Integer type;
	@Override
	public String toString() {
		return "CompreRecordBean [license=" + license + ", lanename=" + lanename + ", time=" + time + ", operator="
				+ operator + ", totaltoll=" + totaltoll + ", paymethod=" + paymethod + ", typename=" + typename
				+ ", type=" + type + "]";
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public String getLanename() {
		return lanename;
	}
	public void setLanename(String lanename) {
		this.lanename = lanename;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getTotaltoll() {
		return totaltoll;
	}
	public void setTotaltoll(String totaltoll) {
		this.totaltoll = totaltoll;
	}
	public String getPaymethod() {
		return paymethod;
	}
	public void setPaymethod(String paymethod) {
		this.paymethod = paymethod;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
}
