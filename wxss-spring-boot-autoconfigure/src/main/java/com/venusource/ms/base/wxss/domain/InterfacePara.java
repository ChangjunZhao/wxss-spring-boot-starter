package com.venusource.ms.base.wxss.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InterfacePara {
	
	String code;
	
	@JsonProperty("encrypt_data")
	String encryptData;
	
	
	String iv;
	
	String id;
	
	String skey;
	
	String environment;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getEncryptData() {
		return encryptData;
	}

	public void setEncryptData(String encryptData) {
		this.encryptData = encryptData;
	}

	public String getIv() {
		return iv;
	}

	public void setIv(String iv) {
		this.iv = iv;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSkey() {
		return skey;
	}

	public void setSkey(String skey) {
		this.skey = skey;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	

}
