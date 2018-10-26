package com.project.backCharge.tempCharge.model;


public class ChargeEntryBean {

	//入口编号
	private String entrylane;
	
	//入口时间
	private String entrytime;
	
	private String mvlicense;
	
	private String imagepath;

	public String getEntrylane() {
		return entrylane;
	}

	public void setEntrylane(String entrylane) {
		this.entrylane = entrylane;
	}

	public String getEntrytime() {
		return entrytime;
	}

	public void setEntrytime(String entrytime) {
		this.entrytime = entrytime;
	}

	public String getMvlicense() {
		return mvlicense;
	}

	public void setMvlicense(String mvlicense) {
		this.mvlicense = mvlicense;
	}

	public String getImagepath() {
		return imagepath;
	}

	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}
	
}
