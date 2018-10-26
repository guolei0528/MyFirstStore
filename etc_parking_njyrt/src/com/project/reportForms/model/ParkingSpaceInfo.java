package com.project.reportForms.model;
/**
 * 停车位信息
 * @author ting
 *
 */
public class ParkingSpaceInfo {

	/**
	 * 停车场ID
	 */
	private String parkid;
	/**
	 * 停车场名称
	 */
	private String parkName;
	/**
	 * 区域ID
	 */
	private String areaid;
	/**
	 * 区域名称
	 */
	private String areaName;
	/**
	 * 停车位总数目
	 */
	private Integer totalNum;
	/**
	 * 已用停车位数目
	 */
	private Integer usedNum;
	/**
	 * 剩余停车位数目
	 */
	private Integer leftNum;
	@Override
	public String toString() {
		return "ParkingSpaceInfo [parkid=" + parkid + ", parkName=" + parkName + ", areaid=" + areaid + ", areaName="
				+ areaName + ", totalNum=" + totalNum + ", usedNum=" + usedNum + ", leftNum=" + leftNum + "]";
	}
	public String getParkid() {
		return parkid;
	}
	public void setParkid(String parkid) {
		this.parkid = parkid;
	}
	public String getParkName() {
		return parkName;
	}
	public void setParkName(String parkName) {
		this.parkName = parkName;
	}
	public String getAreaid() {
		return areaid;
	}
	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public Integer getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}
	public Integer getUsedNum() {
		return usedNum;
	}
	public void setUsedNum(Integer usedNum) {
		this.usedNum = usedNum;
	}
	public Integer getLeftNum() {
		return leftNum;
	}
	public void setLeftNum(Integer leftNum) {
		this.leftNum = leftNum;
	}
}
