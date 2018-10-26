package com.project.backMng.platuser.finance.InvoiceInfoMng.model;

import java.io.Serializable;

public class InvoiceInfoBean implements Serializable{

	private String sn;
	
	private String code;
	
	private String begin_number;
	
	private String end_number;
	
	private String flag;
	
	private String count;
	
	private String user_id;
	
	private String begin_time;
	
	private String cancel_record;
	
	private String cancel_time;
	
	private String cancel_user_id;
	
	private String s_comment;
	
	
	public void setsn(String sn){
		this.sn=sn;
	}
	
	public String getsn(){
		return sn;
	}
	
	public void setcode(String code){
		this.code=code;
	}
	
	public String getcode(){
		return code;
	}
	
	public void setbegin_number(String begin_number){
		this.begin_number=begin_number;
	}
	
	public String getbegin_number(){
		return begin_number;
	}
	
	public void setend_number(String end_number){
		this.end_number=end_number;
	}
	
	public String getend_number(){
		return end_number;
	}
	
	public void setflag(String flag){
		this.flag=flag;
	}
	
	public String getflag(){
		return flag;
	}
	
	public void setcount(String count){
		this.count=count;
	}
	
	public String getcount(){
		return count;
	}
	
	public void setuser_id(String user_id){
		this.user_id=user_id;
	}
	
	public String getuser_id(){
		return user_id;
	}
	
	public void setbegin_time(String begin_time){
		this.begin_time=begin_time;
	}
	
	public String getbegin_time(){
		return begin_time;
	}
	
	public void setcancel_record(String cancel_record){
		this.cancel_record=cancel_record;
	}
	
	public String getcancel_record(){
		return cancel_record;
	}
	
	public void setcancel_time(String cancel_time){
		this.cancel_time=cancel_time;
	}
	
	public String getcancel_time(){
		return cancel_time;
	}
	
	public void setcancel_user_id(String cancel_user_id){
		this.cancel_user_id=cancel_user_id;
	}
	
	public String getcancel_user_id(){
		return cancel_user_id;
	}
	
	public void sets_comment(String s_comment){
		this.s_comment=s_comment;
	}
	
	public String gets_comment(){
		return s_comment;
	}
	
	
}
