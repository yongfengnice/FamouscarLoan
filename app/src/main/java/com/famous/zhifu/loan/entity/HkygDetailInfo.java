package com.famous.zhifu.loan.entity;

import java.io.Serializable;

public class HkygDetailInfo implements Serializable{
	private String paymoney;
	private String paystatus;
	private String paystatusmsg;
	private String paytime;
	private String money;
	private String payendtime;
	private String starttime;
	private String repaymenttime;
	private String isoverdue;
	private String rewardmoney;
	private String status;
	
	public String getPaymoney() {
		return paymoney;
	}
	public void setPaymoney(String paymoney) {
		this.paymoney = paymoney;
	}
	public String getPaystatus() {
		return paystatus;
	}
	public void setPaystatus(String paystatus) {
		this.paystatus = paystatus;
	}
	public String getPaystatusmsg() {
		return paystatusmsg;
	}
	public void setPaystatusmsg(String paystatusmsg) {
		this.paystatusmsg = paystatusmsg;
	}
	public String getPaytime() {
		return paytime;
	}
	public void setPaytime(String paytime) {
		this.paytime = paytime;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getPayendtime() {
		return payendtime;
	}
	public void setPayendtime(String payendtime) {
		this.payendtime = payendtime;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getRepaymenttime() {
		return repaymenttime;
	}
	public void setRepaymenttime(String repaymenttime) {
		this.repaymenttime = repaymenttime;
	}
	public String getIsoverdue() {
		return isoverdue;
	}
	public void setIsoverdue(String isoverdue) {
		this.isoverdue = isoverdue;
	}
	public String getRewardmoney() {
		return rewardmoney;
	}
	public void setRewardmoney(String rewardmoney) {
		this.rewardmoney = rewardmoney;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
