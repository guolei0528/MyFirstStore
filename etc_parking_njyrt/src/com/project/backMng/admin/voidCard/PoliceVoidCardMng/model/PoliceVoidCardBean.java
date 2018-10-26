package com.project.backMng.admin.voidCard.PoliceVoidCardMng.model;

import java.io.Serializable;
import java.util.Date;

public class PoliceVoidCardBean implements Serializable{

	private String mv_license;
	
	private Date in_time;
	
	private Date cancel_time;
	
	private String b_list_type;
	
	private String park_id;
	
	private String user_id;
	
	private String spare1;
	
	private String spare2;
	
	private String spare3;
	
	private String spare4;
	
	private String s_comment;
	
	private String park_name;
	
	private String plate_color;
	
	
	public String getPlate_color() {
		return plate_color;
	}

	public void setPlate_color(String plate_color) {
		this.plate_color = plate_color;
	}

	public String getPark_name() {
		return park_name;
	}

	public void setPark_name(String park_name) {
		this.park_name = park_name;
	}

	public void setmv_license(String mv_license){
		this.mv_license=mv_license;
	}
	
	public String getmv_license(){
		return mv_license;
	}
	
	public Date getIn_time() {
		return in_time;
	}

	public void setIn_time(Date in_time) {
		this.in_time = in_time;
	}

	public Date getCancel_time() {
		return cancel_time;
	}

	public void setCancel_time(Date cancel_time) {
		this.cancel_time = cancel_time;
	}

	public void setb_list_type(String b_list_type){
		this.b_list_type=b_list_type;
	}
	
	public String getb_list_type(){
		return b_list_type;
	}
	
	public void setpark_id(String park_id){
		this.park_id=park_id;
	}
	
	public String getpark_id(){
		return park_id;
	}
	
	public void setuser_id(String user_id){
		this.user_id=user_id;
	}
	
	public String getuser_id(){
		return user_id;
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
