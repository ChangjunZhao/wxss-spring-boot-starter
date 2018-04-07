package com.venusource.ms.base.wxss.domain;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "weixin.app")
public class WxssProperties {
	
	private String appid;
	private String secret;
	
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
	
	
	
}
