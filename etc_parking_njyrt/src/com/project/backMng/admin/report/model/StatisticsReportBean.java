package com.project.backMng.admin.report.model;


/**
 * 
   * @类 名： StatisticalReportBean
   * @功能描述： 统计报表实体类
   * @作者信息：吴超
   * @创建时间： 2018年3月11日上午10:40:07
   * @修改备注：
 */
public class StatisticsReportBean {

	private String park_id;
	private String area_id;
	private int lane_id;
	private int lane_type;
	private int statistics_date;
	private String operator;
	private int shift;
	private int flow_total;
	private int flow_etc;
	private int flow_plate;
	private int flow_manual;
	private int flow_cash_total;
	private int toll_cash_total;
	private int flow_cash_full;
	private int toll_cash_full;
	private int flow_cash_coupon;
	private int toll_cash_coupon_ea;
	private int toll_cash_coupon_eb;
	private int flow_cash_refund;
	private int toll_cash_refund;
	private int flow_cash_prepay;
	private int flow_cash_free;
	private int flow_cash_manual;
	private int flow_etc_total;
	private int toll_etc_total;
	private int toll_etc_full;
	private int flow_etc_coupon;
	private int toll_etc_coupon_ea;
	private int toll_etc_coupon_eb;
	private int flow_etc_free;
	private int mpay_type;
	private int flow_wx_total;
	private int toll_wx_total;
	private int flow_wx_full;
	private int toll_wx_full;
	private int flow_wx_coupon;
	private int toll_wx_coupon_ea;
	private int toll_wx_coupon_eb;
	private int flow_wx_refund;
	private int toll_wx_refund;
	
	private int flow_zfb_total;
	private int toll_zfb_total;
	private int flow_zfb_full;
	private int toll_zfb_full;
	private int flow_zfb_coupon;
	private int toll_zfb_coupon_ea;
	private int toll_zfb_coupon_eb;
	private int flow_zfb_refund;
	private int toll_zfb_refund;
	
	
	
	private String park_name;
	private String area_name;
	private String lane_name;
	private String operator_name;
	
	public String getPark_id() {
		return park_id;
	}
	public void setPark_id(String park_id) {
		this.park_id = park_id;
	}
	public String getArea_id() {
		return area_id;
	}
	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}
	public int getLane_id() {
		return lane_id;
	}
	public void setLane_id(int lane_id) {
		this.lane_id = lane_id;
	}
	public int getLane_type() {
		return lane_type;
	}
	public void setLane_type(int lane_type) {
		this.lane_type = lane_type;
	}
	public int getStatistics_date() {
		return statistics_date;
	}
	public void setStatistics_date(int statistics_date) {
		this.statistics_date = statistics_date;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public int getShift() {
		return shift;
	}
	public void setShift(int shift) {
		this.shift = shift;
	}
	public int getFlow_total() {
		return flow_total;
	}
	public void setFlow_total(int flow_total) {
		this.flow_total = flow_total;
	}
	public int getFlow_etc() {
		return flow_etc;
	}
	public void setFlow_etc(int flow_etc) {
		this.flow_etc = flow_etc;
	}
	public int getFlow_plate() {
		return flow_plate;
	}
	public void setFlow_plate(int flow_plate) {
		this.flow_plate = flow_plate;
	}
	public int getFlow_manual() {
		return flow_manual;
	}
	public void setFlow_manual(int flow_manual) {
		this.flow_manual = flow_manual;
	}
	public int getFlow_cash_total() {
		return flow_cash_total;
	}
	public void setFlow_cash_total(int flow_cash_total) {
		this.flow_cash_total = flow_cash_total;
	}
	public int getToll_cash_total() {
		return toll_cash_total;
	}
	public void setToll_cash_total(int toll_cash_total) {
		this.toll_cash_total = toll_cash_total;
	}
	public int getFlow_cash_full() {
		return flow_cash_full;
	}
	public void setFlow_cash_full(int flow_cash_full) {
		this.flow_cash_full = flow_cash_full;
	}
	public int getToll_cash_full() {
		return toll_cash_full;
	}
	public void setToll_cash_full(int toll_cash_full) {
		this.toll_cash_full = toll_cash_full;
	}
	public int getFlow_cash_coupon() {
		return flow_cash_coupon;
	}
	public void setFlow_cash_coupon(int flow_cash_coupon) {
		this.flow_cash_coupon = flow_cash_coupon;
	}
	public int getToll_cash_coupon_ea() {
		return toll_cash_coupon_ea;
	}
	public void setToll_cash_coupon_ea(int toll_cash_coupon_ea) {
		this.toll_cash_coupon_ea = toll_cash_coupon_ea;
	}
	public int getToll_cash_coupon_eb() {
		return toll_cash_coupon_eb;
	}
	public void setToll_cash_coupon_eb(int toll_cash_coupon_eb) {
		this.toll_cash_coupon_eb = toll_cash_coupon_eb;
	}
	public int getFlow_cash_refund() {
		return flow_cash_refund;
	}
	public void setFlow_cash_refund(int flow_cash_refund) {
		this.flow_cash_refund = flow_cash_refund;
	}
	public int getToll_cash_refund() {
		return toll_cash_refund;
	}
	public void setToll_cash_refund(int toll_cash_refund) {
		this.toll_cash_refund = toll_cash_refund;
	}
	public int getFlow_cash_free() {
		return flow_cash_free;
	}
	public void setFlow_cash_free(int flow_cash_free) {
		this.flow_cash_free = flow_cash_free;
	}
	public int getFlow_cash_manual() {
		return flow_cash_manual;
	}
	public void setFlow_cash_manual(int flow_cash_manual) {
		this.flow_cash_manual = flow_cash_manual;
	}
	public int getFlow_etc_total() {
		return flow_etc_total;
	}
	public void setFlow_etc_total(int flow_etc_total) {
		this.flow_etc_total = flow_etc_total;
	}
	public int getToll_etc_total() {
		return toll_etc_total;
	}
	public void setToll_etc_total(int toll_etc_total) {
		this.toll_etc_total = toll_etc_total;
	}
	public int getToll_etc_full() {
		return toll_etc_full;
	}
	public void setToll_etc_full(int toll_etc_full) {
		this.toll_etc_full = toll_etc_full;
	}
	public int getFlow_etc_coupon() {
		return flow_etc_coupon;
	}
	public void setFlow_etc_coupon(int flow_etc_coupon) {
		this.flow_etc_coupon = flow_etc_coupon;
	}
	public int getToll_etc_coupon_ea() {
		return toll_etc_coupon_ea;
	}
	public void setToll_etc_coupon_ea(int toll_etc_coupon_ea) {
		this.toll_etc_coupon_ea = toll_etc_coupon_ea;
	}
	public int getToll_etc_coupon_eb() {
		return toll_etc_coupon_eb;
	}
	public void setToll_etc_coupon_eb(int toll_etc_coupon_eb) {
		this.toll_etc_coupon_eb = toll_etc_coupon_eb;
	}
	public int getFlow_etc_free() {
		return flow_etc_free;
	}
	public void setFlow_etc_free(int flow_etc_free) {
		this.flow_etc_free = flow_etc_free;
	}
	public int getMpay_type() {
		return mpay_type;
	}
	public void setMpay_type(int mpay_type) {
		this.mpay_type = mpay_type;
	}
	public int getFlow_wx_total() {
		return flow_wx_total;
	}
	public void setFlow_wx_total(int flow_wx_total) {
		this.flow_wx_total = flow_wx_total;
	}
	public int getToll_wx_total() {
		return toll_wx_total;
	}
	public void setToll_wx_total(int toll_wx_total) {
		this.toll_wx_total = toll_wx_total;
	}
	public int getFlow_zfb_total() {
		return flow_zfb_total;
	}
	public void setFlow_zfb_total(int flow_zfb_total) {
		this.flow_zfb_total = flow_zfb_total;
	}
	public int getToll_zfb_total() {
		return toll_zfb_total;
	}
	public void setToll_zfb_total(int toll_zfb_total) {
		this.toll_zfb_total = toll_zfb_total;
	}
	public String getLane_name() {
		return lane_name;
	}
	public void setLane_name(String lane_name) {
		this.lane_name = lane_name;
	}
	public String getOperator_name() {
		return operator_name;
	}
	public void setOperator_name(String operator_name) {
		this.operator_name = operator_name;
	}
	public String getPark_name() {
		return park_name;
	}
	public void setPark_name(String park_name) {
		this.park_name = park_name;
	}
	public String getArea_name() {
		return area_name;
	}
	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}
	public int getFlow_wx_full() {
		return flow_wx_full;
	}
	public void setFlow_wx_full(int flow_wx_full) {
		this.flow_wx_full = flow_wx_full;
	}
	public int getToll_wx_full() {
		return toll_wx_full;
	}
	public void setToll_wx_full(int toll_wx_full) {
		this.toll_wx_full = toll_wx_full;
	}
	public int getFlow_wx_coupon() {
		return flow_wx_coupon;
	}
	public void setFlow_wx_coupon(int flow_wx_coupon) {
		this.flow_wx_coupon = flow_wx_coupon;
	}
	public int getToll_wx_coupon_ea() {
		return toll_wx_coupon_ea;
	}
	public void setToll_wx_coupon_ea(int toll_wx_coupon_ea) {
		this.toll_wx_coupon_ea = toll_wx_coupon_ea;
	}
	public int getToll_wx_coupon_eb() {
		return toll_wx_coupon_eb;
	}
	public void setToll_wx_coupon_eb(int toll_wx_coupon_eb) {
		this.toll_wx_coupon_eb = toll_wx_coupon_eb;
	}
	public int getFlow_wx_refund() {
		return flow_wx_refund;
	}
	public void setFlow_wx_refund(int flow_wx_refund) {
		this.flow_wx_refund = flow_wx_refund;
	}
	public int getToll_wx_refund() {
		return toll_wx_refund;
	}
	public void setToll_wx_refund(int toll_wx_refund) {
		this.toll_wx_refund = toll_wx_refund;
	}
	public int getFlow_zfb_coupon() {
		return flow_zfb_coupon;
	}
	public void setFlow_zfb_coupon(int flow_zfb_coupon) {
		this.flow_zfb_coupon = flow_zfb_coupon;
	}
	public int getToll_zfb_coupon_ea() {
		return toll_zfb_coupon_ea;
	}
	public void setToll_zfb_coupon_ea(int toll_zfb_coupon_ea) {
		this.toll_zfb_coupon_ea = toll_zfb_coupon_ea;
	}
	public int getToll_zfb_coupon_eb() {
		return toll_zfb_coupon_eb;
	}
	public void setToll_zfb_coupon_eb(int toll_zfb_coupon_eb) {
		this.toll_zfb_coupon_eb = toll_zfb_coupon_eb;
	}
	public int getFlow_zfb_refund() {
		return flow_zfb_refund;
	}
	public void setFlow_zfb_refund(int flow_zfb_refund) {
		this.flow_zfb_refund = flow_zfb_refund;
	}
	public int getToll_zfb_refund() {
		return toll_zfb_refund;
	}
	public void setToll_zfb_refund(int toll_zfb_refund) {
		this.toll_zfb_refund = toll_zfb_refund;
	}
	public int getFlow_zfb_full() {
		return flow_zfb_full;
	}
	public void setFlow_zfb_full(int flow_zfb_full) {
		this.flow_zfb_full = flow_zfb_full;
	}
	public int getToll_zfb_full() {
		return toll_zfb_full;
	}
	public void setToll_zfb_full(int toll_zfb_full) {
		this.toll_zfb_full = toll_zfb_full;
	}
	public int getFlow_cash_prepay() {
		return flow_cash_prepay;
	}
	public void setFlow_cash_prepay(int flow_cash_prepay) {
		this.flow_cash_prepay = flow_cash_prepay;
	}
	
}
