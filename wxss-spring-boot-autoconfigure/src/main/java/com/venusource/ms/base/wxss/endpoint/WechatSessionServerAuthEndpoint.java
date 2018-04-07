package com.venusource.ms.base.wxss.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.venusource.ms.base.wxss.domain.AuthRequest;
import com.venusource.ms.base.wxss.service.WeChatSessionService;

/**
 * Wafer Session Server Endpoint
 * @author ChangjunZhao
 * created at 2018-04-06
 */
@Controller
public class WechatSessionServerAuthEndpoint{
	
	@Autowired
	WeChatSessionService service;
	
	@PostMapping(value="/mina_auth")
	public ResponseEntity<Object> minaAuth(@RequestBody AuthRequest authRequest) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.writeValueAsString(authRequest));
		return ResponseEntity.ok().body(service.handleRequest(authRequest));
	}

}
