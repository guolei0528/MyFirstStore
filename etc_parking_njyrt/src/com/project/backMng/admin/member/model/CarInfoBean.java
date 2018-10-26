package com.project.backMng.admin.member.model;

import java.util.Date;

public class CarInfoBean {

    private String mvLicense;

    private Integer carColor;

    private Integer deleteFlag;

    private Date lastModifyTime;
    
    private String obuId;

    private String memberId;
    
    private String memberName;

    private String license;

    private String type;

    private String sComment;

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


	
	public Integer getCarColor() {
		return carColor;
	}

	public void setCarColor(Integer carColor) {
		this.carColor = carColor;
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

	public String getObuId() {
		return obuId;
	}

	public void setObuId(String obuId) {
		this.obuId = obuId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getsComment() {
		return sComment;
	}

	public void setsComment(String sComment) {
		this.sComment = sComment;
	}

	public Integer getSpare1() {
		return spare1;
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
