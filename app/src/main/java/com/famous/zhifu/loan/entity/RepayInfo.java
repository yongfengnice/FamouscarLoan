package com.famous.zhifu.loan.entity;

import java.io.Serializable;

public class RepayInfo implements Serializable{
	private String num;
	private String date;
	private String status;
	private String residuemoney;
	
	public String getResiduemoney() {
		return residuemoney;
	}
	public void setResiduemoney(String residuemoney) {
		this.residuemoney = residuemoney;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
