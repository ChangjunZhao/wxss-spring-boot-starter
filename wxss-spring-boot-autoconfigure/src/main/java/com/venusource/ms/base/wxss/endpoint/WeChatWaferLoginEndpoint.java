package com.venusource.ms.base.wxss.endpoint;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.venusource.ms.base.wxss.authorization.AuthorizationAPI;
import com.venusource.ms.base.wxss.domain.Constants;
import com.venusource.ms.base.wxss.domain.IdKeyResponse;
import com.venusource.ms.base.wxss.domain.LoginResponse;
import com.venusource.ms.base.wxss.domain.LoginResponseData;
import com.venusource.ms.base.wxss.service.WeChatSessionService;
/**
 * wafer2-client-sdk login endpoint, not support wafer1
 * @author ChangjunZhao
 * created at 2018-04-06
 */
@Controller
public class WeChatWaferLoginEndpoint{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	AuthorizationAPI api;
	
	@GetMapping(value="/login")
	public ResponseEntity<Object> login(HttpServletRequest request) throws JsonProcessingException{
		String code = request.getHeader(Constants.WX_HEADER_CODE);
		String encryptedData = request.getHeader(Constants.WX_HEADER_ENCRYPTED_DATA);
		String iv = request.getHeader(Constants.WX_HEADER_IV);
		LoginResponse response = null;
		try{
			response = buildLoginResponse(api.login(code, encryptedData, iv));
		}catch(Exception e){
			logger.error("login fail:" + e.getMessage());
			response = new LoginResponse();
			response.setCode(500);
		}
		return ResponseEntity.ok().body(response);
	}
	
	private LoginResponse buildLoginResponse(IdKeyResponse idKeyResponse){
		LoginResponse response = new LoginResponse();
		if("0".equals(idKeyResponse.getReturnCode())){
			LoginResponseData data = new LoginResponseData();
			data.setSkey(idKeyResponse.getReturnData().getSkey());
			data.setUserinfo(idKeyResponse.getReturnData().getUserInfo());
			data.setTime((new Date()).getTime());
			response.setCode(0);
			response.setData(data);
		}else{
			response.setCode(500);
		}
		return response;
	}
}
