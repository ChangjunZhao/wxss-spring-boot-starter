package com.venusource.ms.base.wxss.domain;

public class LoginResponse {
	int code;
	LoginResponseData data;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public LoginResponseData getData() {
		return data;
	}
	public void setData(LoginResponseData data) {
		this.data = data;
	}
}
