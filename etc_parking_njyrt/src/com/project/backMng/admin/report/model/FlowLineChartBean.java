package com.project.backMng.admin.report.model;

import java.util.Date;

public class FlowLineChartBean {

	private Date date_time;
	
	private int total_flow;
	
	private int cash_flow;
	
	private int etc_flow;
	
	private int mobile_flow;
	
	private int manual_flow;
	
	public Date getDate_time() {
		return date_time;
	}

	public void setDate_time(Date date_time) {
		this.date_time = date_time;
	}

	public int getTotal_flow() {
		return total_flow;
	}

	public void setTotal_flow(int total_flow) {
		this.total_flow = total_flow;
	}

	public int getCash_flow() {
		return cash_flow;
	}

	public void setCash_flow(int cash_flow) {
		this.cash_flow = cash_flow;
	}

	public int getEtc_flow() {
		return etc_flow;
	}

	public void setEtc_flow(int etc_flow) {
		this.etc_flow = etc_flow;
	}

	public int getMobile_flow() {
		return mobile_flow;
	}

	public void setMobile_flow(int mobile_flow) {
		this.mobile_flow = mobile_flow;
	}

	public int getManual_flow() {
		return manual_flow;
	}

	public void setManual_flow(int manual_flow) {
		this.manual_flow = manual_flow;
	}
	
}