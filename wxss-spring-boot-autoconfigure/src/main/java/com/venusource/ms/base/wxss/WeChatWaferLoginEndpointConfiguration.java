package com.venusource.ms.base.wxss;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeChatWaferLoginEndpointConfiguration {

	class Marker {}

	@Bean
	public Marker enableWeChatWaferLoginEndpoint() {
		return new Marker();
	}
}
