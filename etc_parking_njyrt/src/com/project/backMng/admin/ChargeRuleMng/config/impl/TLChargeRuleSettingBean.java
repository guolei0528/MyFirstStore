package com.project.backMng.admin.ChargeRuleMng.config.impl;

/**
   * @类 名： ChargeRuleSettingBean
   * @功能描述： TODO
   * @作者信息：吴超
   * @创建时间： 2017年9月1日下午5:33:35
   * @修改备注：
   */
public class TLChargeRuleSettingBean {

	private String id;
	// 计费类型
	private int type;
	// 节点个数
	private int num;
	// 免费时间
	private int freeTime;
	
	//启用涟水模式 出口在免费时间段收费为0  1启用  0不启用
	private int useFreeTime;
	
	//免费时间段开始时间
	private String freeStartTime;
	
	//免费时间段结束时间
	private String freeEndTime;
	
	// 最大周期时长
	private int maxCycle;
	
	// 0点重启
	private int reset;
	
	// 最大周期上限金额(小车)
	private int smallMaxToll;
	
	// 最大周期上限金额(大车)
	private int largeMaxToll;
	
	//超过最大收费周期计算规则 1-重复计算 2-累计计算
	private int beyoneChargeFlag;
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
	public int getUseFreeTime() {
		return useFreeTime;
	}
	public void setUseFreeTime(int useFreeTime) {
		this.useFreeTime = useFreeTime;
	}
	public String getFreeStartTime() {
		return freeStartTime;
	}
	public void setFreeStartTime(String freeStartTime) {
		this.freeStartTime = freeStartTime;
	}
	public String getFreeEndTime() {
		return freeEndTime;
	}
	public void setFreeEndTime(String freeEndTime) {
		this.freeEndTime = freeEndTime;
	}
	public int getMaxCycle() {
		return maxCycle;
	}
	public void setMaxCycle(int maxCycle) {
		this.maxCycle = maxCycle;
	}
	public int getSmallMaxToll() {
		return smallMaxToll;
	}
	public void setSmallMaxToll(int smallMaxToll) {
		this.smallMaxToll = smallMaxToll;
	}
	public int getLargeMaxToll() {
		return largeMaxToll;
	}
	public void setLargeMaxToll(int largeMaxToll) {
		this.largeMaxToll = largeMaxToll;
	}
	public int getBeyoneChargeFlag() {
		return beyoneChargeFlag;
	}
	public void setBeyoneChargeFlag(int beyoneChargeFlag) {
		this.beyoneChargeFlag = beyoneChargeFlag;
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
	public int getReset() {
		return reset;
	}
	public void setReset(int reset) {
		this.reset = reset;
	}
	
}
