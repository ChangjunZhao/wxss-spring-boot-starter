package com.venusource.ms.base.wxss.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Interface {

	@JsonProperty("interfaceName")
	String interfaceName;
	
	InterfacePara para;

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public InterfacePara getPara() {
		return para;
	}

	public void setPara(InterfacePara para) {
		this.para = para;
	}

	
}
