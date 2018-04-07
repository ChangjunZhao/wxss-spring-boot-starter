package com.venusource.ms.base.wxss.domain;

public class LoginResponseData {
	String skey;
	Long time;
	UserInfo userinfo;
	
	public String getSkey() {
		return skey;
	}
	public void setSkey(String skey) {
		this.skey = skey;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public UserInfo getUserinfo() {
		return userinfo;
	}
	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}
	
	
}
