package com.project.communication.model;

public class OutsideJsonBean {
	private String type;
	private String execute_status;
	private String obu_id;
	private String pay_method;
	private String vehicle_class;
	private String pay_free_time;
	private String prepay_toll;
	private String car_color;
	private String card_id;
	
	public String getObu_id() {
		return obu_id;
	}

	public void setObu_id(String obu_id) {
		this.obu_id = obu_id;
	}
	private String mv_license;
	
	public String getMv_license() {
		return mv_license;
	}

	public void setMv_license(String mv_license) {
		this.mv_license = mv_license;
	}

	public String getType() {
		return type;
	}
	
	public OutsideJsonBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public String getValid_type() {
		return valid_type;
	}

	public void setValid_type(String valid_type) {
		this.valid_type = valid_type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public String getExecute_status() {
		return execute_status;
	}
	public void setExecute_status(String execute_status) {
		this.execute_status = execute_status;
	}
	public String getFinal_toll() {
		return final_toll;
	}
	public void setFinal_toll(String final_toll) {
		this.final_toll = final_toll;
	}
	public String getBase_toll() {
		return base_toll;
	}
	public void setBase_toll(String base_toll) {
		this.base_toll = base_toll;
	}
	public String getEntry_lane() {
		return entry_lane;
	}
	public void setEntry_lane(String entry_lane) {
		this.entry_lane = entry_lane;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getCoupon_type() {
		return coupon_type;
	}
	public void setCoupon_type(String coupon_type) {
		this.coupon_type = coupon_type;
	}
	public String getCoupon() {
		return coupon;
	}
	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}
	
	public String getVehicle_class() {
		return vehicle_class;
	}

	public void setVehicle_class(String vehicle_class) {
		this.vehicle_class = vehicle_class;
	}
	
	public String getPrepay_toll() {
		return prepay_toll;
	}

	public void setPrepay_toll(String prepay_toll) {
		this.prepay_toll = prepay_toll;
	}
	

	public String getCar_color() {
		return car_color;
	}

	public void setCar_color(String car_color) {
		this.car_color = car_color;
	}
	
	public String getPay_method() {
		return pay_method;
	}

	public void setPay_method(String pay_method) {
		this.pay_method = pay_method;
	}

	public String getPay_free_time() {
		return pay_free_time;
	}

	public void setPay_free_time(String pay_free_time) {
		this.pay_free_time = pay_free_time;
	}

	public String getPay_status() {
		return pay_status;
	}

	public void setPay_status(String pay_status) {
		this.pay_status = pay_status;
	}

	public String getPay_time() {
		return pay_time;
	}

	public void setPay_time(String pay_time) {
		this.pay_time = pay_time;
	}

	public String getEntry_time() {
		return entry_time;
	}

	public void setEntry_time(String entry_time) {
		this.entry_time = entry_time;
	}

	public String getNow_time() {
		return now_time;
	}

	public void setNow_time(String now_time) {
		this.now_time = now_time;
	}
	
	public String getCard_id() {
		return card_id;
	}

	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}
	private String valid_type;
	private String pay_status;
	private String pay_time;
	private String final_toll;
	private String base_toll;
	private String entry_time;
	
	private String entry_lane;
	private String image;
	
	private String coupon_type;
	private String coupon;
	private String now_time;
}
