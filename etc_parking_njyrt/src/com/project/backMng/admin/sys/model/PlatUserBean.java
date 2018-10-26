package com.project.backMng.admin.sys.model;

public class PlatUserBean{

	private String ID;
	
	private String NAME;
	
	private String SEX;
	
	private String WORK_NO;
	
	private String MOBILE;
	
	private String REMARK;
	
	private String CREATE_TIME;
	
	private String LAST_MODIFY_TIME;
	
	private Integer IN_USE_FLAG;
	
	private Integer DELETE_FLAG;
	
	
	public void setID(String ID){
		this.ID=ID;
	}
	
	public String getID(){
		return ID;
	}
	
	public void setNAME(String NAME){
		this.NAME=NAME;
	}
	
	public String getNAME(){
		return NAME;
	}
	
	public void setSEX(String SEX){
		this.SEX=SEX;
	}
	
	public String getSEX(){
		return SEX;
	}
	
	public void setWORK_NO(String WORK_NO){
		this.WORK_NO=WORK_NO;
	}
	
	public String getWORK_NO(){
		return WORK_NO;
	}
	
	public void setMOBILE(String MOBILE){
		this.MOBILE=MOBILE;
	}
	
	public String getMOBILE(){
		return MOBILE;
	}
	
	public void setREMARK(String REMARK){
		this.REMARK=REMARK;
	}
	
	public String getREMARK(){
		return REMARK;
	}
	
	public void setCREATE_TIME(String CREATE_TIME){
		this.CREATE_TIME=CREATE_TIME;
	}
	
	public String getCREATE_TIME(){
		return CREATE_TIME;
	}
	
	public void setLAST_MODIFY_TIME(String LAST_MODIFY_TIME){
		this.LAST_MODIFY_TIME=LAST_MODIFY_TIME;
	}
	
	public String getLAST_MODIFY_TIME(){
		return LAST_MODIFY_TIME;
	}
	
	public void setIN_USE_FLAG(Integer IN_USE_FLAG){
		this.IN_USE_FLAG=IN_USE_FLAG;
	}
	
	public Integer getIN_USE_FLAG(){
		return IN_USE_FLAG;
	}
	
	public void setDELETE_FLAG(Integer DELETE_FLAG){
		this.DELETE_FLAG=DELETE_FLAG;
	}
	
	public Integer getDELETE_FLAG(){
		return DELETE_FLAG;
	}
	
	
}
