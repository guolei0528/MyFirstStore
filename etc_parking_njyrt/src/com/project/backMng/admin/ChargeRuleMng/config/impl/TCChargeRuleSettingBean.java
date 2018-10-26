package com.project.backMng.admin.ChargeRuleMng.config.impl;

public class TCChargeRuleSettingBean {

	private String id;
	
	private int type;
	
	private int num;
	
	private int freeTime;
	
	private int maxCycle;
	
	private int maxToll;
	
	private int timesUserMax;
	
	private int maxChargeNode;
	
	private String version;
	
	private String sComment;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getFreeTime() {
		return freeTime;
	}

	public void setFreeTime(int freeTime) {
		this.freeTime = freeTime;
	}

	public int getMaxCycle() {
		return maxCycle;
	}

	public void setMaxCycle(int maxCycle) {
		this.maxCycle = maxCycle;
	}

	public int getMaxToll() {
		return maxToll;
	}

	public void setMaxToll(int maxToll) {
		this.maxToll = maxToll;
	}

	public int getTimesUserMax() {
		return timesUserMax;
	}

	public void setTimesUserMax(int timesUserMax) {
		this.timesUserMax = timesUserMax;
	}

	public int getMaxChargeNode() {
		return maxChargeNode;
	}

	public void setMaxChargeNode(int maxChargeNode) {
		this.maxChargeNode = maxChargeNode;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getsComment() {
		return sComment;
	}

	public void setsComment(String sComment) {
		this.sComment = sComment;
	}
	
}
