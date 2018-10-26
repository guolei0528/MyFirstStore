package com.project.backMng.platuser.sys.ParkInfoMng.model;

import java.io.Serializable;
import java.util.List;

import com.project.backMng.platuser.sys.AreaInfoMng.model.AreaInfoBean;

public class ParkInfoBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7940729351400594191L;

	private String park_id;
	
	private String park_name;
	
	private String park_address;
	
	private String spare1;
	
	private String spare2;
	
	private String spare3;
	
	private String spare4;
	
	private String s_comment;
	
	private List<AreaInfoBean> areas;
	
	private Integer delete_flag;
	
	public Integer getDelete_flag() {
		return delete_flag;
	}

	public void setDelete_flag(Integer delete_flag) {
		this.delete_flag = delete_flag;
	}
	
	public List<AreaInfoBean> getAreas() {
		return areas;
	}

	public void setAreas(List<AreaInfoBean> areas) {
		this.areas = areas;
	}

	public void setpark_id(String park_id){
		this.park_id=park_id;
	}
	
	public String getpark_id(){
		return park_id;
	}
	
	public void setpark_name(String park_name){
		this.park_name=park_name;
	}
	
	public String getpark_name(){
		return park_name;
	}
	
	public void setpark_address(String park_address){
		this.park_address=park_address;
	}
	
	public String getpark_address(){
		return park_address;
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
