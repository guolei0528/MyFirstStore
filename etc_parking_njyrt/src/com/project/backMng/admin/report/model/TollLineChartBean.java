package com.project.backMng.admin.report.model;

import java.util.Date;

/**
 * 
   * @类 名： TollReportBean
   * @功能描述： 存储过程查出的折线图bean对象
   * @作者信息：吴超
   * @创建时间： 2017年10月26日下午4:37:54
   * @修改备注：
 */
public class TollLineChartBean {

	private Date date_time;
	
	private int total_toll_receivable;
	
	private int total_toll_receipt;
	
	private int cash_toll_receivable;
	
	private int cash_toll_receipt;
	
	private int etc_toll_receivable;
	
	private int etc_toll_receipt;
	
	private int mobile_toll_receivable;
	
	private int mobile_toll_receipt;
	
	private int manual_toll_receivable;
	
	private int manual_toll_receipt;

	public Date getDate_time() {
		return date_time;
	}

	public void setDate_time(Date date_time) {
		this.date_time = date_time;
	}

	public int getTotal_toll_receivable() {
		return total_toll_receivable;
	}

	public void setTotal_toll_receivable(int total_toll_receivable) {
		this.total_toll_receivable = total_toll_receivable;
	}

	public int getTotal_toll_receipt() {
		return total_toll_receipt;
	}

	public void setTotal_toll_receipt(int total_toll_receipt) {
		this.total_toll_receipt = total_toll_receipt;
	}

	public int getCash_toll_receivable() {
		return cash_toll_receivable;
	}

	public void setCash_toll_receivable(int cash_toll_receivable) {
		this.cash_toll_receivable = cash_toll_receivable;
	}

	public int getCash_toll_receipt() {
		return cash_toll_receipt;
	}

	public void setCash_toll_receipt(int cash_toll_receipt) {
		this.cash_toll_receipt = cash_toll_receipt;
	}

	public int getEtc_toll_receivable() {
		return etc_toll_receivable;
	}

	public void setEtc_toll_receivable(int etc_toll_receivable) {
		this.etc_toll_receivable = etc_toll_receivable;
	}

	public int getEtc_toll_receipt() {
		return etc_toll_receipt;
	}

	public void setEtc_toll_receipt(int etc_toll_receipt) {
		this.etc_toll_receipt = etc_toll_receipt;
	}

	public int getMobile_toll_receivable() {
		return mobile_toll_receivable;
	}

	public void setMobile_toll_receivable(int mobile_toll_receivable) {
		this.mobile_toll_receivable = mobile_toll_receivable;
	}

	public int getMobile_toll_receipt() {
		return mobile_toll_receipt;
	}

	public void setMobile_toll_receipt(int mobile_toll_receipt) {
		this.mobile_toll_receipt = mobile_toll_receipt;
	}

	public int getManual_toll_receivable() {
		return manual_toll_receivable;
	}

	public void setManual_toll_receivable(int manual_toll_receivable) {
		this.manual_toll_receivable = manual_toll_receivable;
	}

	public int getManual_toll_receipt() {
		return manual_toll_receipt;
	}

	public void setManual_toll_receipt(int manual_toll_receipt) {
		this.manual_toll_receipt = manual_toll_receipt;
	}
	
}
