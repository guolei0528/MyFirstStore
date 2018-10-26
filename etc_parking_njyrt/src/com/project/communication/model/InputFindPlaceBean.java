package com.project.communication.model;

import java.io.Serializable;

public class InputFindPlaceBean implements Serializable{
	
	private String type;
	
	private String park_id;
	
	private String area_id;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

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

}
