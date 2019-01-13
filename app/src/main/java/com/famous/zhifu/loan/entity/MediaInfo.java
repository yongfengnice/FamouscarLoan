package com.famous.zhifu.loan.entity;

import java.io.Serializable;

public class MediaInfo implements Serializable{
	private String news_id;
	private String title;
	private String views;
	private String add_time;
	private String description;
	private String news_sn;
	private String urlStr;
	private String islink;
	
	public String getNews_id() {
		return news_id;
	}
	public void setNews_id(String news_id) {
		this.news_id = news_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getViews() {
		return views;
	}
	public void setViews(String views) {
		this.views = views;
	}
	public String getAdd_time() {
		return add_time;
	}
	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getNews_sn() {
		return news_sn;
	}
	public void setNews_sn(String news_sn) {
		this.news_sn = news_sn;
	}
	public String getUrlStr() {
		return urlStr;
	}
	public void setUrlStr(String urlStr) {
		this.urlStr = urlStr;
	}
	
	public String getIslink() {
		return islink;
	}
	public void setIslink(String islink) {
		this.islink = islink;
	}
}
