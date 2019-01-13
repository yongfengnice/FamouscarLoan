package com.famous.zhifu.loan.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class CarPictureInfo implements Serializable{
	
	private String name;	
	private String code;
	private ArrayList<PictureInfo> picLists;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public ArrayList<PictureInfo> getPicLists() {
		return picLists;
	}
	public void setPicLists(ArrayList<PictureInfo> picLists) {
		this.picLists = picLists;
	}
	
}
