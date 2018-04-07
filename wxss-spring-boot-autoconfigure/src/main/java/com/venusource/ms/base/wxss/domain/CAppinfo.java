package com.venusource.ms.base.wxss.domain;

public class CAppinfo {

	String appid;
	
	String secret;
	
	Integer loginDuration;
	
	Integer sessionDuration;
	
	String qcloudAppid;
	
	String ip;

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public Integer getLoginDuration() {
		return loginDuration;
	}

	public void setLoginDuration(Integer loginDuration) {
		this.loginDuration = loginDuration;
	}

	public Integer getSessionDuration() {
		return sessionDuration;
	}

	public void setSessionDuration(Integer sessionDuration) {
		this.sessionDuration = sessionDuration;
	}

	public String getQcloudAppid() {
		return qcloudAppid;
	}

	public void setQcloudAppid(String qcloudAppid) {
		this.qcloudAppid = qcloudAppid;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
