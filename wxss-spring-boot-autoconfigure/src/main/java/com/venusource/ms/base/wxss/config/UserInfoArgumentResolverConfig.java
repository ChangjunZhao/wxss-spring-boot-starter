package com.venusource.ms.base.wxss.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.venusource.ms.base.wxss.authorization.AuthorizationAPI;
import com.venusource.ms.base.wxss.endpoint.support.UserInfoHandlerMethodArgumentResolver;

@Configuration 
public class UserInfoArgumentResolverConfig implements WebMvcConfigurer{
	
	@Autowired
	AuthorizationAPI api;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new UserInfoHandlerMethodArgumentResolver(api));
		WebMvcConfigurer.super.addArgumentResolvers(resolvers);
	}

}
