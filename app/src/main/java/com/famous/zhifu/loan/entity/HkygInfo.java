package com.famous.zhifu.loan.entity;

import java.util.ArrayList;

public class HkygInfo {
	private String loansn;
	private String issuesn;
	private String tendersn;
	private String title;
	private String money;
	private String tendermoney;
	private String ratemoney;
	private String repaymenttime;
	private String loanmonth;
	private String allot_periods;
	private String loanstatus;
	private ArrayList<HkygDetailInfo> lists;
	
	public String getLoansn() {
		return loansn;
	}
	public void setLoansn(String loansn) {
		this.loansn = loansn;
	}
	public String getIssuesn() {
		return issuesn;
	}
	public void setIssuesn(String issuesn) {
		this.issuesn = issuesn;
	}
	public String getTendersn() {
		return tendersn;
	}
	public void setTendersn(String tendersn) {
		this.tendersn = tendersn;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getTendermoney() {
		return tendermoney;
	}
	public void setTendermoney(String tendermoney) {
		this.tendermoney = tendermoney;
	}
	public String getRatemoney() {
		return ratemoney;
	}
	public void setRatemoney(String ratemoney) {
		this.ratemoney = ratemoney;
	}
	public String getRepaymenttime() {
		return repaymenttime;
	}
	public void setRepaymenttime(String repaymenttime) {
		this.repaymenttime = repaymenttime;
	}
	public String getLoanmonth() {
		return loanmonth;
	}
	public void setLoanmonth(String loanmonth) {
		this.loanmonth = loanmonth;
	}
	public String getAllot_periods() {
		return allot_periods;
	}
	public void setAllot_periods(String allot_periods) {
		this.allot_periods = allot_periods;
	}
	public String getLoanstatus() {
		return loanstatus;
	}
	public void setLoanstatus(String loanstatus) {
		this.loanstatus = loanstatus;
	}
	public ArrayList<HkygDetailInfo> getLists() {
		return lists;
	}
	public void setLists(ArrayList<HkygDetailInfo> lists) {
		this.lists = lists;
	}
	
}
