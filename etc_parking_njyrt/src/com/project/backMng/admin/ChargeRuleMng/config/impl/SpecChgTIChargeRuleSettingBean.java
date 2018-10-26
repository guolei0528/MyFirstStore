package com.project.backMng.admin.ChargeRuleMng.config.impl;

import java.util.List;

public class SpecChgTIChargeRuleSettingBean {

	private String id;
	// 计费类型
	private int type;
	// 节点个数
	private int num;
	// 免费时间
	private int freeTime;
	// 最大周期时长
//	private int maxCycle;
	// 最大周期上限金额
//	private int maxToll;
	// 免费周期内周几，比如周六周日：6|7
	private List<Integer> freeWeekday;
	// 变更的周期时长
	private int changeCycle;
	// 变更后的收费编号
	private String changeId;
	
	// 版本
	private int version;
	// 备注
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
	public List<Integer> getFreeWeekday() {
		return freeWeekday;
	}
	public void setFreeWeekday(List<Integer> freeWeekday) {
		this.freeWeekday = freeWeekday;
	}
	public int getChangeCycle() {
		return changeCycle;
	}
	public void setChangeCycle(int changeCycle) {
		this.changeCycle = changeCycle;
	}
	public String getChangeId() {
		return changeId;
	}
	public void setChangeId(String changeId) {
		this.changeId = changeId;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getsComment() {
		return sComment;
	}
	public void setsComment(String sComment) {
		this.sComment = sComment;
	}
	
}
