package com.venusource.ms.base.wxss;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.venusource.ms.base.wxss.domain.WxssProperties;
import com.venusource.ms.base.wxss.endpoint.WechatSessionServerAuthEndpoint;
import com.venusource.ms.base.wxss.endpoint.support.WxssApiExceptionHandler;
import com.venusource.ms.base.wxss.service.MemSessionInfoStoreServiceImp;
import com.venusource.ms.base.wxss.service.SessionInfoStoreService;
import com.venusource.ms.base.wxss.service.WeChatSessionService;

@Configuration
@ConditionalOnBean(WeChatSessionServerConfiguration.Marker.class)
@EnableConfigurationProperties(WxssProperties.class)
public class WeChatSessionServerAutoConfiguration {
	
	@Bean
	@ConditionalOnMissingBean
	public WechatSessionServerAuthEndpoint wechatSessionServerAuthEndpoint() {
		return new WechatSessionServerAuthEndpoint();
	}
	
	@Bean
	@ConditionalOnMissingBean
	public WeChatSessionService weChatSessionService(){
		return new WeChatSessionService();
	}
	
	@Bean
	@ConditionalOnMissingBean
	public WxssApiExceptionHandler wxssApiExceptionHandler(){
		return new WxssApiExceptionHandler();
	}
	
	@Bean
	@ConditionalOnMissingBean
	public SessionInfoStoreService sessionInfoStoreService(){
		return new MemSessionInfoStoreServiceImp();
	}
	
	@Bean
	@ConditionalOnMissingBean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
