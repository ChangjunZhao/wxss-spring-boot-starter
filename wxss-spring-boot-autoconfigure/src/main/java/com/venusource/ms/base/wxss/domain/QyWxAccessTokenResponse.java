package com.venusource.ms.base.wxss.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QyWxAccessTokenResponse {

	//{"session_key":"zCI+OK+qtts0ug4D\/6ShLQ==","expires_in":7200,
	//"openid":"ovvH90BZdlmlqr8q113l5aReAkrg"}
	
	@JsonProperty("access_token")
	String accessToken;
	
	@JsonProperty("expires_in")
	Integer expiresIn;
	
	Integer errcode;
	
	String errmsg;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Integer getErrcode() {
		return errcode;
	}

	public void setErrcode(Integer errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

}
