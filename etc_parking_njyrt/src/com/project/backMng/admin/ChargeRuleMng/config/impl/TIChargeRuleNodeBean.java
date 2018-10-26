package com.project.backMng.admin.ChargeRuleMng.config.impl;

public class TIChargeRuleNodeBean {
	
	//nodeId主键
	private int id;
	//开始时间`
	private String startTime ;
	//结束时间
	private String endTime;
	//计费周期
	private int period;
	//最大上限类型
	private int limitType;
	
	//小车限价时间
	private int smallLimitTime;
	
	//大车限价时间
	private int largeLimitTime;
	
	//小车限价
	private int smallLimitToll;
	//大车限价
	private int largeLimitToll;
	//小车单价
	private int smallToll;
	//大车单价
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
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public int getSmallLimitToll() {
		return smallLimitToll;
	}
	public void setSmallLimitToll(int smallLimitToll) {
		this.smallLimitToll = smallLimitToll;
	}
	public int getLargeLimitToll() {
		return largeLimitToll;
	}
	public void setLargeLimitToll(int largeLimitToll) {
		this.largeLimitToll = largeLimitToll;
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
	public int getLimitType() {
		return limitType;
	}
	public void setLimitType(int limitType) {
		this.limitType = limitType;
	}
	public int getSmallLimitTime() {
		return smallLimitTime;
	}
	public void setSmallLimitTime(int smallLimitTime) {
		this.smallLimitTime = smallLimitTime;
	}
	public int getLargeLimitTime() {
		return largeLimitTime;
	}
	public void setLargeLimitTime(int largeLimitTime) {
		this.largeLimitTime = largeLimitTime;
	}
	
}
