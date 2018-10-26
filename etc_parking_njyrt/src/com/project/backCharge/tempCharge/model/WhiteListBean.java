package com.project.backCharge.tempCharge.model;

import java.util.Date;

public class WhiteListBean {

    private String mvLicense;

    private int color;

    private Integer deleteFlag;

    private Date lastModifyTime;
    
//    private String type;
    
    private Integer vehicleClass;

    private Integer spare1;

    private Integer spare2;

    private String spare3;

    private String spare4;

	public String getMvLicense() {
		return mvLicense;
	}

	public void setMvLicense(String mvLicense) {
		this.mvLicense = mvLicense;
	}
	
	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

//	public String getType() {
//		return type;
//	}
//
//	public void setType(String type) {
//		this.type = type;
//	}
	
	public Integer getSpare1() {
		return spare1;
	}

	
	public Integer getVehicleClass() {
		return vehicleClass;
	}

	public void setVehicleClass(Integer vehicleClass) {
		this.vehicleClass = vehicleClass;
	}

	public void setSpare1(Integer spare1) {
		this.spare1 = spare1;
	}

	public Integer getSpare2() {
		return spare2;
	}

	public void setSpare2(Integer spare2) {
		this.spare2 = spare2;
	}

	public String getSpare3() {
		return spare3;
	}

	public void setSpare3(String spare3) {
		this.spare3 = spare3;
	}

	public String getSpare4() {
		return spare4;
	}

	public void setSpare4(String spare4) {
		this.spare4 = spare4;
	}
    
}
