package com.venusource.ms.base.wxss;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeChatMiniAppUserInfoHandleConfiguration {
	
	class Marker {}

	@Bean
	public Marker enableWeChatUserInfoArgumentInjection() {
		return new Marker();
	}

}
