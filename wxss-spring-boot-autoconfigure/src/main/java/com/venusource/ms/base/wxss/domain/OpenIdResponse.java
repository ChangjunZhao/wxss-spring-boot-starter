package com.venusource.ms.base.wxss.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OpenIdResponse {

	//{"session_key":"zCI+OK+qtts0ug4D\/6ShLQ==","expires_in":7200,
	//"openid":"ovvH90BZdlmlqr8q113l5aReAkrg"}
	
	@JsonProperty("session_key")
	String sessionKey;
	
	@JsonProperty("expires_in")
	int expiresIn;
	
	String openid;

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	

}
