package com.project.backMng.admin.voidCard.EtcBlackListMng.model;

import java.io.Serializable;
import java.util.Date;

public class EtcBlackListBean implements Serializable{

	private String issuerID;
	
	private int netNo;
	
	private String cardID;
	
	private String obuID;
	
	private String license;
	
	private int status;
	
	private int issuerBlackVersion;
	
	private Date creationTime;
	
	private int validFlag;
	
	private Date startTime;
	
	private String sComment;
	
	private int version;
	
	private int blackType;
	
	private int spare1;
	
	private int spare2;
	
	private String spare3;

	public String getIssuerID() {
		return issuerID;
	}

	public void setIssuerID(String issuerID) {
		this.issuerID = issuerID;
	}

	public int getNetNo() {
		return netNo;
	}

	public void setNetNo(int netNo) {
		this.netNo = netNo;
	}

	public String getCardID() {
		return cardID;
	}

	public void setCardID(String cardID) {
		this.cardID = cardID;
	}

	public String getObuID() {
		return obuID;
	}

	public void setObuID(String obuID) {
		this.obuID = obuID;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getIssuerBlackVersion() {
		return issuerBlackVersion;
	}

	public void setIssuerBlackVersion(int issuerBlackVersion) {
		this.issuerBlackVersion = issuerBlackVersion;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public int getValidFlag() {
		return validFlag;
	}

	public void setValidFlag(int validFlag) {
		this.validFlag = validFlag;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getsComment() {
		return sComment;
	}

	public void setsComment(String sComment) {
		this.sComment = sComment;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getBlackType() {
		return blackType;
	}

	public void setBlackType(int blackType) {
		this.blackType = blackType;
	}

	public int getSpare1() {
		return spare1;
	}

	public void setSpare1(int spare1) {
		this.spare1 = spare1;
	}

	public int getSpare2() {
		return spare2;
	}

	public void setSpare2(int spare2) {
		this.spare2 = spare2;
	}

	public String getSpare3() {
		return spare3;
	}

	public void setSpare3(String spare3) {
		this.spare3 = spare3;
	}
	
}
