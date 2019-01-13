package com.famous.zhifu.loan.entity;

import java.util.ArrayList;

public class HomeInfo {
	private ArrayList<CarInfo> carlists;
	private String is_registration;
	private String version_client ;
	private String update_desc ;
	private String update_force ;
	private String version_currect ;
	private String total_lend ;
	private String androidUrl ;
	private String total_rate;
	
	public String getTotal_rate() {
		return total_rate;
	}
	public void setTotal_rate(String total_rate) {
		this.total_rate = total_rate;
	}
	public ArrayList<CarInfo> getCarlists() {
		return carlists;
	}
	public void setCarlists(ArrayList<CarInfo> carlists) {
		this.carlists = carlists;
	}
	public String getIs_registration() {
		return is_registration;
	}
	public void setIs_registration(String is_registration) {
		this.is_registration = is_registration;
	}
	public String getVersion_client() {
		return version_client;
	}
	public void setVersion_client(String version_client) {
		this.version_client = version_client;
	}
	public String getUpdate_desc() {
		return update_desc;
	}
	public void setUpdate_desc(String update_desc) {
		this.update_desc = update_desc;
	}
	public String getUpdate_force() {
		return update_force;
	}
	public void setUpdate_force(String update_force) {
		this.update_force = update_force;
	}
	public String getVersion_currect() {
		return version_currect;
	}
	public void setVersion_currect(String version_currect) {
		this.version_currect = version_currect;
	}
	public String getTotal_lend() {
		return total_lend;
	}
	public void setTotal_lend(String total_lend) {
		this.total_lend = total_lend;
	}
	public String getAndroidUrl() {
		return androidUrl;
	}
	public void setAndroidUrl(String androidUrl) {
		this.androidUrl = androidUrl;
	}
	
}
