package com.project.backMng.admin.report.model;

public class FlowReportTempBean {

	private int date;
	
	private String parkId;
	
	private String areaId;
	
	private int laneId;
	
	private int laneFlag;
	
	private int vehicleclass;
	
	private int paymethod;
	
	private int cardnetwork;
	
    private int ecardtype;
    
    private int countFlow;

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}
	
	public String getParkId() {
		return parkId;
	}

	public void setParkId(String parkId) {
		this.parkId = parkId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public int getLaneId() {
		return laneId;
	}

	public void setLaneId(int laneId) {
		this.laneId = laneId;
	}

	public int getLaneFlag() {
		return laneFlag;
	}

	public void setLaneFlag(int laneFlag) {
		this.laneFlag = laneFlag;
	}

	public int getVehicleclass() {
		return vehicleclass;
	}

	public void setVehicleclass(int vehicleclass) {
		this.vehicleclass = vehicleclass;
	}

	public int getPaymethod() {
		return paymethod;
	}

	public void setPaymethod(int paymethod) {
		this.paymethod = paymethod;
	}

	public int getCardnetwork() {
		return cardnetwork;
	}

	public void setCardnetwork(int cardnetwork) {
		this.cardnetwork = cardnetwork;
	}

	public int getEcardtype() {
		return ecardtype;
	}

	public void setEcardtype(int ecardtype) {
		this.ecardtype = ecardtype;
	}

	public int getCountFlow() {
		return countFlow;
	}

	public void setCountFlow(int countFlow) {
		this.countFlow = countFlow;
	}

}
