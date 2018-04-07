package com.venusource.ms.base.wxss.endpoint.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.venusource.ms.base.wxss.authorization.AuthorizationAPI;
import com.venusource.ms.base.wxss.domain.Constants;
import com.venusource.ms.base.wxss.domain.UserInfo;
/**
 * inject UserInfo into method argument
 * @author ChangjunZhao
 * created at 2018-04-06
 */
public class UserInfoHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver{
	
	private AuthorizationAPI api;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public UserInfoHandlerMethodArgumentResolver(AuthorizationAPI api){
		this.api = api;
	}
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().equals(UserInfo.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
		//String id = webRequest.getHeader(Constants.WX_HEADER_ID);
		String skey = webRequest.getHeader(Constants.WX_HEADER_SKEY);
		try{
		return api.checkLogin(null, skey).getReturnData().getUserInfo();
		}catch(WxssApiException e){
			logger.info("Resolve UserInfo Argument Fail");
		}
		return null;
	}

}
