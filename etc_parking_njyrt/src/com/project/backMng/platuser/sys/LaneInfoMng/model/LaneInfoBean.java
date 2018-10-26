package com.project.backMng.platuser.sys.LaneInfoMng.model;

import java.io.Serializable;

public class LaneInfoBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6410892445426996492L;

	private String park_id;
	
	private String area_id;
	
	private String lane_id;
	
	private String lane_flag;
	
	private String spare1;
	
	private String spare2;
	
	private String spare3;
	
	private String spare4;
	
	private String s_comment;
	
	private String area_name;
	
	private Integer delete_flag;
	
	private String lane_name;
	
	public String getLane_name() {
		return lane_name;
	}

	public void setLane_name(String lane_name) {
		this.lane_name = lane_name;
	}

	public Integer getDelete_flag() {
		return delete_flag;
	}

	public void setDelete_flag(Integer delete_flag) {
		this.delete_flag = delete_flag;
	}
	public String getArea_name() {
		return area_name;
	}

	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}

	public void setpark_id(String park_id){
		this.park_id=park_id;
	}
	
	public String getpark_id(){
		return park_id;
	}
	
	public void setarea_id(String area_id){
		this.area_id=area_id;
	}
	
	public String getarea_id(){
		return area_id;
	}
	
	public void setlane_id(String lane_id){
		this.lane_id=lane_id;
	}
	
	public String getlane_id(){
		return lane_id;
	}
	
	public void setlane_flag(String lane_flag){
		this.lane_flag=lane_flag;
	}
	
	public String getlane_flag(){
		return lane_flag;
	}
	
	public void setspare1(String spare1){
		this.spare1=spare1;
	}
	
	public String getspare1(){
		return spare1;
	}
	
	public void setspare2(String spare2){
		this.spare2=spare2;
	}
	
	public String getspare2(){
		return spare2;
	}
	
	public void setspare3(String spare3){
		this.spare3=spare3;
	}
	
	public String getspare3(){
		return spare3;
	}
	
	public void setspare4(String spare4){
		this.spare4=spare4;
	}
	
	public String getspare4(){
		return spare4;
	}
	
	public void sets_comment(String s_comment){
		this.s_comment=s_comment;
	}
	
	public String gets_comment(){
		return s_comment;
	}
	
	
}
