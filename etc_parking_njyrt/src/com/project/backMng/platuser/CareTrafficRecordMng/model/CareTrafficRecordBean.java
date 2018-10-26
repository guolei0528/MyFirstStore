package com.project.backMng.platuser.CareTrafficRecordMng.model;

import java.io.Serializable;

public class CareTrafficRecordBean implements Serializable{

	private String traffic_date;
	
	private String park_id;
	
	private String area_id;
	
	private String lane_id;
	
	private String lane_flag;
	
	private String care_traffic;
	
	private String pay_method;
	
	private String member_type;
	
	private String datetime;
	
	private String s_comment;
	
	
	public void settraffic_date(String traffic_date){
		this.traffic_date=traffic_date;
	}
	
	public String gettraffic_date(){
		return traffic_date;
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
	
	public void setcare_traffic(String care_traffic){
		this.care_traffic=care_traffic;
	}
	
	public String getcare_traffic(){
		return care_traffic;
	}
	
	public void setpay_method(String pay_method){
		this.pay_method=pay_method;
	}
	
	public String getpay_method(){
		return pay_method;
	}
	
	public void setmember_type(String member_type){
		this.member_type=member_type;
	}
	
	public String getmember_type(){
		return member_type;
	}
	
	public void setdatetime(String datetime){
		this.datetime=datetime;
	}
	
	public String getdatetime(){
		return datetime;
	}
	
	public void sets_comment(String s_comment){
		this.s_comment=s_comment;
	}
	
	public String gets_comment(){
		return s_comment;
	}
	
	
}
