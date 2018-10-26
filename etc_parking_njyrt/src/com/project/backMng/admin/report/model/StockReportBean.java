package com.project.backMng.admin.report.model;

import java.util.Date;

public class StockReportBean {
	
	private String park_id;
	
	private String area_id;
	
	private int totalspace;
	
	private int tmpcar;
	
	private int fixcar;
	
	private Date updatetime;

	public String getPark_id() {
		return park_id;
	}

	public void setPark_id(String park_id) {
		this.park_id = park_id;
	}

	public String getArea_id() {
		return area_id;
	}

	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}

	public int getTotalspace() {
		return totalspace;
	}

	public void setTotalspace(int totalspace) {
		this.totalspace = totalspace;
	}

	public int getTmpcar() {
		return tmpcar;
	}

	public void setTmpcar(int tmpcar) {
		this.tmpcar = tmpcar;
	}

	public int getFixcar() {
		return fixcar;
	}

	public void setFixcar(int fixcar) {
		this.fixcar = fixcar;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
}
