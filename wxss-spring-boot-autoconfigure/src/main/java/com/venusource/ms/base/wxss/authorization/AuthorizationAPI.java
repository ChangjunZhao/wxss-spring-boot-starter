package com.venusource.ms.base.wxss.authorization;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.venusource.ms.base.wxss.domain.IdKeyResponse;
import com.venusource.ms.base.wxss.domain.WaferConfigProperties;
import com.venusource.ms.base.wxss.endpoint.support.WxssApiException;

@Component
public class AuthorizationAPI {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	WaferConfigProperties props;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	RestTemplate restTemplate;
		
	private String getAPIUrl(){
		return props.getAuthServerUrl();
	}

	public IdKeyResponse login(String code, String encryptedData, String iv) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("code", code);
		params.put("encrypt_data", encryptedData);
		params.put("iv", iv);
		return request("qcloud.cam.id_skey", params);
	}
	
	public IdKeyResponse checkLogin(String id, String skey) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("skey", skey);
		return request("qcloud.cam.auth", params);
	}

	public IdKeyResponse request(String apiName, Map<String, Object> apiParams) {
		String requestBody = null;
		IdKeyResponse response = null;
		try {
			requestBody = buildRequestBody(apiName, apiParams);
			logger.debug(getAPIUrl());
			HttpHeaders requestHeaders = new HttpHeaders();
	        requestHeaders.add("Content-Type", "application/json");
	        HttpEntity<String> requestEntity = new HttpEntity<String>(requestBody, requestHeaders);
	        ResponseEntity<IdKeyResponse> responseEntity = restTemplate.exchange(getAPIUrl(), HttpMethod.POST, requestEntity, IdKeyResponse.class);
			response = responseEntity.getBody();
		} catch (Exception e) {
			logger.error("连接鉴权服务错误，请检查网络状态" + getAPIUrl() + e.getMessage());
			throw new WxssApiException(e.getMessage(), "1005");
		}
		if(!"0".equals(response.getReturnCode())){
			throw new WxssApiException(response.getReturnMessage(), response.getReturnCode());
		}
		return response;
	}

	private String buildRequestBody(String apiName, Map<String, Object> apiParams) {
		JSONObject jsonObject = new JSONObject();
		try {
			JSONObject interfaceJson = new JSONObject();
			interfaceJson.put("interfaceName", apiName);
			interfaceJson.put("para", apiParams);
			
			jsonObject.put("version", 1);
			jsonObject.put("componentName", "MA");
			jsonObject.put("interface", interfaceJson);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return jsonObject.toString();
	}
}
