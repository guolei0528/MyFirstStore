package com.project.reportForms.model;

public class InOrOutFlowInfo {

	//流量类型
	private Integer type;
	//驶入或驶出日期
	private String dateStr;
	//车流量值
	private Integer num;
	//收益（单位：分），实际收的钱
	private Integer income;
	//收益（单位：分），应该收的钱
	private Integer amount;
	//收益（单位：元），实际收的钱
	private Double incomeOfYuan;
	//收益（单位：元），应该收的钱
	private Double amountOfYuan;
	@Override
	public String toString() {
		return "InOrOutFlowInfo [type=" + type + ", dateStr=" + dateStr + ", num=" + num + ", income=" + income
				+ ", amount=" + amount + ", incomeOfYuan=" + incomeOfYuan + ", amountOfYuan=" + amountOfYuan + "]";
	}
	public String getDateStr() {
		return dateStr;
	}
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	public Double getAmountOfYuan() {
		return amountOfYuan;
	}
	public void setAmountOfYuan(Double amountOfYuan) {
		this.amountOfYuan = amountOfYuan;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Integer getIncome() {
		return income;
	}
	public void setIncome(Integer income) {
		this.income = income;
	}
	public Double getIncomeOfYuan() {
		return incomeOfYuan;
	}
	public void setIncomeOfYuan(Double incomeOfYuan) {
		this.incomeOfYuan = incomeOfYuan;
	}
}
