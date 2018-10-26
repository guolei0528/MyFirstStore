package com.project.backCharge.tempCharge.model;

import java.util.Date;

public class TempCostBean {
	
    private String mvLicense;

    private String parkId;

    private String areaId;

    private Integer entryLane;

    private Date entryTime;

    private Integer entryDate;

    private Integer vehicleClass;

    private Integer incomeBill;

    private Integer realBill;

    private Integer isRebate;
    
    private Integer isModel;

    private Date beginTime;

    private Date endTime;

    private String laneId;

    private Date dateTime;

    private Integer spare1;

    private Integer spare2;

    private String spare3;

    private String spare4;

    private String sComment;

    private Integer payMethod;
    
    private Date payTime;

    private Integer prepayBill;

    private String tradNo;
    
    private String obuId;
    
    private Integer cardNetwork;

    private String cardId;

    private Boolean cardType;
    
    private Integer carColor;
    
    private Integer payStatus;
    
    private String entryImage;
    
    
    public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public String getMvLicense() {
        return mvLicense;
    }

    public void setMvLicense(String mvLicense) {
        this.mvLicense = mvLicense == null ? null : mvLicense.trim();
    }

    public String getParkId() {
        return parkId;
    }

    public void setParkId(String parkId) {
        this.parkId = parkId == null ? null : parkId.trim();
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId == null ? null : areaId.trim();
    }

    public Integer getEntryLane() {
        return entryLane;
    }

    public void setEntryLane(Integer entryLane) {
        this.entryLane = entryLane;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    public Integer getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Integer entryDate) {
        this.entryDate = entryDate;
    }

    public Integer getVehicleClass() {
        return vehicleClass;
    }

    public void setVehicleClass(Integer vehicleClass) {
        this.vehicleClass = vehicleClass;
    }

    public Integer getIncomeBill() {
        return incomeBill;
    }

    public void setIncomeBill(Integer incomeBill) {
        this.incomeBill = incomeBill;
    }

    public Integer getRealBill() {
        return realBill;
    }

    public void setRealBill(Integer realBill) {
        this.realBill = realBill;
    }
    
    public Integer getIsModel() {
		return isModel;
	}

	public void setIsModel(Integer isModel) {
		this.isModel = isModel;
	}

	public Integer getIsRebate() {
        return isRebate;
    }

    public void setIsRebate(Integer isRebate) {
        this.isRebate = isRebate;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getLaneId() {
        return laneId;
    }

    public void setLaneId(String laneId) {
        this.laneId = laneId == null ? null : laneId.trim();
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
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
        this.spare3 = spare3 == null ? null : spare3.trim();
    }

    public String getSpare4() {
        return spare4;
    }

    public void setSpare4(String spare4) {
        this.spare4 = spare4 == null ? null : spare4.trim();
    }

    public String getsComment() {
        return sComment;
    }

    public void setsComment(String sComment) {
        this.sComment = sComment == null ? null : sComment.trim();
    }

    public Integer getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(Integer payMethod) {
		this.payMethod = payMethod;
	}

	public Integer getPrepayBill() {
        return prepayBill;
    }

    public void setPrepayBill(Integer prepayBill) {
        this.prepayBill = prepayBill;
    }

    public String getTradNo() {
        return tradNo;
    }

    public void setTradNo(String tradNo) {
        this.tradNo = tradNo == null ? null : tradNo.trim();
    }
    
	public String getObuId() {
		return obuId;
	}

	public void setObuId(String obuId) {
		this.obuId = obuId;
	}

	public Integer getCardNetwork() {
		return cardNetwork;
	}

	public void setCardNetwork(Integer cardNetwork) {
		this.cardNetwork = cardNetwork;
	}

	public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId == null ? null : cardId.trim();
    }

    public Boolean getCardType() {
        return cardType;
    }

    public void setCardType(Boolean cardType) {
        this.cardType = cardType;
    }

	public Integer getCarColor() {
		return carColor;
	}

	public void setCarColor(Integer carColor) {
		this.carColor = carColor;
	}

	public String getEntryImage() {
		return entryImage;
	}

	public void setEntryImage(String entryImage) {
		this.entryImage = entryImage;
	}
    
}