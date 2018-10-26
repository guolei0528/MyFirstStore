package com.project.backMng.admin.ChargeRuleMng.config.impl;

public class TLChargeRuleNodeBean {
	
	//nodeId主键
	private int id;
	//时间长度
	private int startLong ;
	//计费周期
	private int period;
	//限价
	private int limitToll;
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
	public int getStartLong() {
		return startLong;
	}
	public void setStartLong(int startLong) {
		this.startLong = startLong;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public int getLimitToll() {
		return limitToll;
	}
	public void setLimitToll(int limitToll) {
		this.limitToll = limitToll;
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
