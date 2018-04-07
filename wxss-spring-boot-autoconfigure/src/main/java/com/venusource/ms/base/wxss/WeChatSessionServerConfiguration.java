package com.venusource.ms.base.wxss;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeChatSessionServerConfiguration {

	class Marker {}

	@Bean
	public Marker enableWeChatSessionServer() {
		return new Marker();
	}
}
