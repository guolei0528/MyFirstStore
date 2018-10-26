package com.project.backMng.admin.ChargeRuleMng.model;

import java.io.Serializable;

public class ChargeRuleBean implements Serializable{

	private String charge_id;
	
	private String charge_type;
	
	private String begin_time;
	
	private String end_time;
	
	private String money;
	
	private String period;
	
	
	private String money_limit;
	
	
	private String spare1;
	
	private String spare2;
	
	private String spare3;
	
	private String spare4;
	
	private String s_comment;
	
	private String valid_begin_time;
	
	private String  valid_end_time;
	
	private String last_modify_time;
	
	private Integer delete_flag;
	
	private String car_type;
	
	public String getLast_modify_time() {
		return last_modify_time;
	}

	public void setLast_modify_time(String last_modify_time) {
		this.last_modify_time = last_modify_time;
	}

	public Integer getDelete_flag() {
		return delete_flag;
	}

	public void setDelete_flag(Integer delete_flag) {
		this.delete_flag = delete_flag;
	}

	public String getCar_type() {
		return car_type;
	}

	public void setCar_type(String car_type) {
		this.car_type = car_type;
	}

	public String getFree_time() {
		return free_time;
	}

	public void setFree_time(String free_time) {
		this.free_time = free_time;
	}

	private String free_time;
	
	public String getValid_begin_time() {
		return valid_begin_time;
	}

	public void setValid_begin_time(String valid_begin_time) {
		this.valid_begin_time = valid_begin_time;
	}

	public String getValid_end_time() {
		return valid_end_time;
	}

	public void setValid_end_time(String valid_end_time) {
		this.valid_end_time = valid_end_time;
	}

	public void setcharge_id(String charge_id){
		this.charge_id=charge_id;
	}
	
	public String getcharge_id(){
		return charge_id;
	}
	
	public void setcharge_type(String charge_type){
		this.charge_type=charge_type;
	}
	
	public String getcharge_type(){
		return charge_type;
	}
	
	public void setbegin_time(String begin_time){
		this.begin_time=begin_time;
	}
	
	public String getbegin_time(){
		return begin_time;
	}
	
	public void setend_time(String end_time){
		this.end_time=end_time;
	}
	
	public String getend_time(){
		return end_time;
	}
	
	public void setmoney(String money){
		this.money=money;
	}
	
	public String getmoney(){
		return money;
	}
	
	public void setperiod(String period){
		this.period=period;
	}
	
	public String getperiod(){
		return period;
	}
	
	
	public void setmoney_limit(String money_limit){
		this.money_limit=money_limit;
	}
	
	public String getmoney_limit(){
		return money_limit;
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
