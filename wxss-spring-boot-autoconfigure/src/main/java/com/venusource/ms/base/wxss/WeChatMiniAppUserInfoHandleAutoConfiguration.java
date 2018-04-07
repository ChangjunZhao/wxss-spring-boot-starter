package com.venusource.ms.base.wxss;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.venusource.ms.base.wxss.authorization.AuthorizationAPI;
import com.venusource.ms.base.wxss.config.UserInfoArgumentResolverConfig;
import com.venusource.ms.base.wxss.domain.WaferConfigProperties;

@Configuration
@ConditionalOnBean(WeChatMiniAppUserInfoHandleConfiguration.Marker.class)
@EnableConfigurationProperties(WaferConfigProperties.class)
public class WeChatMiniAppUserInfoHandleAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public UserInfoArgumentResolverConfig argumentResolverConfig() {
		return new UserInfoArgumentResolverConfig();
	}

	@Bean
	@ConditionalOnMissingBean
	public AuthorizationAPI authorizationAPI() {
		return new AuthorizationAPI();
	}

	@Bean
	@ConditionalOnMissingBean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
