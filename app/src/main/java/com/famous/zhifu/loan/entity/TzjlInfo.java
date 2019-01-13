package com.famous.zhifu.loan.entity;

import java.io.Serializable;

public class TzjlInfo implements Serializable {
	private String id;
	private String enddate;
	private String repaymentStatus;
	private String loanmonth;
	private String title;
	private String loansn;
	private String timeadd;
	private String yearrate;
	private String repayment;
	private String tendersn;
	private String money;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLoanmonth() {
		return loanmonth;
	}
	public void setLoanmonth(String loanmonth) {
		this.loanmonth = loanmonth;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLoansn() {
		return loansn;
	}
	public void setLoansn(String loansn) {
		this.loansn = loansn;
	}
	public String getTimeadd() {
		return timeadd;
	}
	public void setTimeadd(String timeadd) {
		this.timeadd = timeadd;
	}
	public String getYearrate() {
		return yearrate;
	}
	public void setYearrate(String yearrate) {
		this.yearrate = yearrate;
	}
	public String getRepayment() {
		return repayment;
	}
	public void setRepayment(String repayment) {
		this.repayment = repayment;
	}
	public String getTendersn() {
		return tendersn;
	}
	public void setTendersn(String tendersn) {
		this.tendersn = tendersn;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getRepaymentStatus() {
		return repaymentStatus;
	}
	public void setRepaymentStatus(String repaymentStatus) {
		this.repaymentStatus = repaymentStatus;
	}
	
}
