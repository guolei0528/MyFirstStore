package com.project.backMng.admin.ChargeRuleMng.config.impl;

/**
   * @类 名： ChargeRuleSettingBean
   * @功能描述： TODO
   * @作者信息：吴超
   * @创建时间： 2017年9月1日下午5:33:35
   * @修改备注：
   */
public class TIChargeRuleSettingBean {

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
