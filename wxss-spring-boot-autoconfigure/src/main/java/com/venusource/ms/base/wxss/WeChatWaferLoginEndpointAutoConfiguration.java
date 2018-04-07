package com.venusource.ms.base.wxss;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.venusource.ms.base.wxss.authorization.AuthorizationAPI;
import com.venusource.ms.base.wxss.domain.WaferConfigProperties;
import com.venusource.ms.base.wxss.endpoint.WeChatWaferLoginEndpoint;
import com.venusource.ms.base.wxss.endpoint.support.WxssApiExceptionHandler;
import com.venusource.ms.base.wxss.service.MemSessionInfoStoreServiceImp;
import com.venusource.ms.base.wxss.service.SessionInfoStoreService;

@Configuration
@ConditionalOnBean(WeChatWaferLoginEndpointConfiguration.Marker.class)
@EnableConfigurationProperties(WaferConfigProperties.class)
public class WeChatWaferLoginEndpointAutoConfiguration {
	
	@Bean
	@ConditionalOnMissingBean
	public WeChatWaferLoginEndpoint weChatWaferLoginEndpoint() {
		return new WeChatWaferLoginEndpoint();
	}
	
	@Bean
	@ConditionalOnMissingBean
	public AuthorizationAPI authorizationAPI() {
		return new AuthorizationAPI();
	}
	
	@Bean
	@ConditionalOnMissingBean
	public WxssApiExceptionHandler wxssApiExceptionHandler(){
		return new WxssApiExceptionHandler();
	}
	
	@Bean
	@ConditionalOnMissingBean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
