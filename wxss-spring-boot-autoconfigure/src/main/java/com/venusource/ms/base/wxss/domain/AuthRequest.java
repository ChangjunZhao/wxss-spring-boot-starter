package com.venusource.ms.base.wxss.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthRequest {
	String version;
	String componentName;
	
	@JsonProperty("interface")
	Interface cinterface;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getComponentName() {
		return componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	public Interface getCinterface() {
		return cinterface;
	}

	public void setCinterface(Interface cinterface) {
		this.cinterface = cinterface;
	}
	
	
}
