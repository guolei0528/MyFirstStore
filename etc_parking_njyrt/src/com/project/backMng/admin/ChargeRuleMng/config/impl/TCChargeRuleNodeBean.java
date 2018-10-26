package com.project.backMng.admin.ChargeRuleMng.config.impl;

public class TCChargeRuleNodeBean {

	private int id;
	
	private String startTime;
	
	private String endTime;
	
	private int smallToll;
	
	private int largeToll;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public int getSmallToll() {
		return smallToll;
	}

	public void setSmallToll(int smallToll) {
		this.smallToll = smallToll;
	}

	public int getLargeToll() {
		return largeToll;
	}

	public void setLargeToll(int largeToll) {
		this.largeToll = largeToll;
	}
	
}
