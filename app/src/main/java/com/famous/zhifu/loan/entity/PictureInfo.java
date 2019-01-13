package com.famous.zhifu.loan.entity;

import java.io.Serializable;

public class PictureInfo implements Serializable{
	private String title;
	private String path;
	private String smallpath;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getSmallpath() {
		return smallpath;
	}
	public void setSmallpath(String smallpath) {
		this.smallpath = smallpath;
	}
}
