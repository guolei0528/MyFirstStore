package com.project.backMng.platuser.sys.AreaInfoMng.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.project.backMng.platuser.sys.LaneInfoMng.model.LaneInfoBean;

public class AreaInfoBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7509532263783276282L;

	private String area_id;
	
	private String park_id;
	
	private String area_name;
	
	private String space_number;
	
	private String pay_free_time;
	
	private String charge_code;
	
	private String picture_type;
	
	private String picture_addr;
	
	private String server_ip;
	
	private String db_name;
	
	private String db_password;
	
	private String day_number;
	
	private String log_address;
	
	private String spare1;
	
	private String spare2;
	
	private String spare3;
	
	private String spare4;
	
	private String s_comment;
	
	private Integer delete_flag;
	

	public String getCharge_code() {
		return charge_code;
	}

	public void setCharge_code(String charge_code) {
		this.charge_code = charge_code;
	}

	public Integer getDelete_flag() {
		return delete_flag;
	}

	public void setDelete_flag(Integer delete_flag) {
		this.delete_flag = delete_flag;
	}

	private List<LaneInfoBean>	lanes;
	public List<LaneInfoBean> getLanes() {
		return lanes;
	}

	public void setLanes(List<LaneInfoBean> lanes) {
		this.lanes = lanes;
	}

	public void setarea_id(String area_id){
		this.area_id=area_id;
	}
	
	public String getarea_id(){
		return area_id;
	}
	
	public void setpark_id(String park_id){
		this.park_id=park_id;
	}
	
	public String getpark_id(){
		return park_id;
	}
	
	public void setarea_name(String area_name){
		this.area_name=area_name;
	}
	
	public String getarea_name(){
		return area_name;
	}
	
	public void setspace_number(String space_number){
		this.space_number=space_number;
	}
	
	public String getspace_number(){
		return space_number;
	}
	
	public void setpicture_type(String picture_type){
		this.picture_type=picture_type;
	}
	
	public String getpicture_type(){
		return picture_type;
	}
	
	public void setpicture_addr(String picture_addr){
		this.picture_addr=picture_addr;
	}
	
	public String getpicture_addr(){
		return picture_addr;
	}
	
	public void setserver_ip(String server_ip){
		this.server_ip=server_ip;
	}
	
	public String getserver_ip(){
		return server_ip;
	}
	
	public void setdb_name(String db_name){
		this.db_name=db_name;
	}
	
	public String getdb_name(){
		return db_name;
	}
	
	public void setdb_password(String db_password){
		this.db_password=db_password;
	}
	
	public String getdb_password(){
		return db_password;
	}
	
	public void setday_number(String day_number){
		this.day_number=day_number;
	}
	
	public String getday_number(){
		return day_number;
	}
	
	public void setlog_address(String log_address){
		this.log_address=log_address;
	}
	
	public String getlog_address(){
		return log_address;
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

	public String getPay_free_time() {
		return pay_free_time;
	}

	public void setPay_free_time(String pay_free_time) {
		this.pay_free_time = pay_free_time;
	}
}
