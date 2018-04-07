package com.venusource.ms.base.wxss.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "weixin.wafer")
public class WaferConfigProperties {
	
	/**
	 * "serverHost": "199447.qcloud.la",
    "authServerUrl": "http://10.0.12.135/mina_auth/",
    "tunnelServerUrl": "https://ws.qcloud.com",
    "tunnelSignatureKey": "my$ecretkey",
    "networkTimeout": 30000
	 */
	
	private String serverHost;
	private String authServerUrl;
	
	private String tunnelServerUrl;
	
	private String tunnelSignatureKey;
	
	private String networkTimeout;

	public String getServerHost() {
		return serverHost;
	}

	public void setServerHost(String serverHost) {
		this.serverHost = serverHost;
	}

	public String getAuthServerUrl() {
		return authServerUrl;
	}

	public void setAuthServerUrl(String authServerUrl) {
		this.authServerUrl = authServerUrl;
	}

	public String getTunnelServerUrl() {
		return tunnelServerUrl;
	}

	public void setTunnelServerUrl(String tunnelServerUrl) {
		this.tunnelServerUrl = tunnelServerUrl;
	}

	public String getTunnelSignatureKey() {
		return tunnelSignatureKey;
	}

	public void setTunnelSignatureKey(String tunnelSignatureKey) {
		this.tunnelSignatureKey = tunnelSignatureKey;
	}

	public String getNetworkTimeout() {
		return networkTimeout;
	}

	public void setNetworkTimeout(String networkTimeout) {
		this.networkTimeout = networkTimeout;
	}

}
