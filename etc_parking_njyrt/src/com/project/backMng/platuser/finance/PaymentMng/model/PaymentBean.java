package com.project.backMng.platuser.finance.PaymentMng.model;

import java.io.Serializable;

public class PaymentBean implements Serializable{

	private String begin_time;
	
	private String date;
	
	private String login_name;
	
	private String operator_id;
	
	private String park_id;
	
	private String area_id;
	
	private String lane_id;
	
	private String exit_shift;
	
	private String real_bill;
	
	private String pay_bill;
	
	private String long_bill;
	
	private String invoice_count;
	
	private String spare1;
	
	private String spare2;
	
	private String spare3;
	
	private String spare4;
	
	private String s_comment;
	
	private String car_flow_statistic_total;
	private String car_flow_real_total;
	private String car_flow_statistic_cash;
	private String car_flow_real_cash;
	private String statistic_cash;
	private String real_cash;
	private Integer delete_flag;
	
	
	public Integer getDelete_flag() {
		return delete_flag;
	}

	public void setDelete_flag(Integer delete_flag) {
		this.delete_flag = delete_flag;
	}

	public String getCar_flow_statistic_total() {
		return car_flow_statistic_total;
	}

	public void setCar_flow_statistic_total(String car_flow_statistic_total) {
		this.car_flow_statistic_total = car_flow_statistic_total;
	}

	public String getCar_flow_real_total() {
		return car_flow_real_total;
	}

	public void setCar_flow_real_total(String car_flow_real_total) {
		this.car_flow_real_total = car_flow_real_total;
	}

	public String getCar_flow_statistic_cash() {
		return car_flow_statistic_cash;
	}

	public void setCar_flow_statistic_cash(String car_flow_statistic_cash) {
		this.car_flow_statistic_cash = car_flow_statistic_cash;
	}

	public String getCar_flow_real_cash() {
		return car_flow_real_cash;
	}

	public void setCar_flow_real_cash(String car_flow_real_cash) {
		this.car_flow_real_cash = car_flow_real_cash;
	}

	public String getStatistic_cash() {
		return statistic_cash;
	}

	public void setStatistic_cash(String statistic_cash) {
		this.statistic_cash = statistic_cash;
	}

	public String getReal_cash() {
		return real_cash;
	}

	public void setReal_cash(String real_cash) {
		this.real_cash = real_cash;
	}

	public void setbegin_time(String begin_time){
		this.begin_time=begin_time;
	}
	
	public String getbegin_time(){
		return begin_time;
	}
	
	public void setdate(String date){
		this.date=date;
	}
	
	public String getdate(){
		return date;
	}
	
	public String getLogin_name() {
		return login_name;
	}

	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}

	public void setoperator_id(String operator_id){
		this.operator_id=operator_id;
	}
	
	public String getoperator_id(){
		return operator_id;
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
	
	public void setexit_shift(String exit_shift){
		this.exit_shift=exit_shift;
	}
	
	public String getexit_shift(){
		return exit_shift;
	}
	
	public void setreal_bill(String real_bill){
		this.real_bill=real_bill;
	}
	
	public String getreal_bill(){
		return real_bill;
	}
	
	public void setpay_bill(String pay_bill){
		this.pay_bill=pay_bill;
	}
	
	public String getpay_bill(){
		return pay_bill;
	}
	
	public void setlong_bill(String long_bill){
		this.long_bill=long_bill;
	}
	
	public String getlong_bill(){
		return long_bill;
	}
	
	public void setinvoice_count(String invoice_count){
		this.invoice_count=invoice_count;
	}
	
	public String getinvoice_count(){
		return invoice_count;
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
