package com.venusource.ms.base.wxss.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.venusource.ms.base.wxss.domain.AuthRequest;
import com.venusource.ms.base.wxss.domain.CSessionInfo;
import com.venusource.ms.base.wxss.domain.IdKeyResponse;
import com.venusource.ms.base.wxss.domain.IdKeyResponseData;
import com.venusource.ms.base.wxss.domain.OpenIdResponse;
import com.venusource.ms.base.wxss.domain.QyWxAccessTokenResponse;
import com.venusource.ms.base.wxss.domain.QyWxssProperties;
import com.venusource.ms.base.wxss.domain.UserInfo;
import com.venusource.ms.base.wxss.domain.WxssProperties;
import com.venusource.ms.base.wxss.endpoint.support.WxssApiException;
import com.venusource.ms.base.wxss.endpoint.support.WeixinAppError;
import com.venusource.ms.base.wxss.util.HttpClientUtil;
import com.venusource.ms.base.wxss.util.SHA1;

@Service
public class WeChatSessionService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	WxssProperties props;

	@Autowired
	QyWxssProperties qyProps;

	@Autowired
	SessionInfoStoreService store;

	private String qyWeChatAccessToken;

	public IdKeyResponse handleRequest(AuthRequest authRequest) {
		boolean isQyWechat = "wxwork".equals(authRequest.getCinterface().getPara().getEnvironment());
		if ("qcloud.cam.id_skey".equals(authRequest.getCinterface().getInterfaceName())) {
			OpenIdResponse openIdResponse = getOpenIdByCode(authRequest.getCinterface().getPara().getCode(),isQyWechat);
			UserInfo userInfo = getUserInfo(authRequest.getCinterface().getPara().getEncryptData(),
					openIdResponse.getSessionKey(), authRequest.getCinterface().getPara().getIv());
			IdKeyResponseData responseData = buildResponseData(openIdResponse, userInfo);
			try {
				// 持久化session
				CSessionInfo sessionInfo = generateSessionInfo(responseData, openIdResponse,isQyWechat);
				store.add(sessionInfo);
			} catch (JsonProcessingException e) {
				throw new WxssApiException("add session fail.", "1006");
			}

			return buildIdKeyResponse(responseData, "OK");
		} else if ("qcloud.cam.auth".equals(authRequest.getCinterface().getInterfaceName())) {
			CSessionInfo sessionInfo = store.get(authRequest.getCinterface().getPara().getSkey());
			checkSessionInfo(sessionInfo);
			IdKeyResponseData responseData = new IdKeyResponseData();
			responseData.setId(null);
			responseData.setSkey(null);
			try {
				responseData.setUserInfo(objectMapper.readValue(sessionInfo.getUserInfo(), UserInfo.class));
			} catch (Exception e) {
				throw new WxssApiException("AUTH_FAIL", "60012");
			}
			return buildIdKeyResponse(responseData, "AUTH_SUCCESS");
		} else {
			throw new WxssApiException("interface not exist.", "1002");
		}
	}

	private void checkSessionInfo(CSessionInfo sessionInfo) {
		if (sessionInfo == null)
			throw new WxssApiException("AUTH_FAIL", "60012");
		Date now = new Date();
		long interval = (now.getTime() - sessionInfo.getCreateTime().getTime()) / 1000;
		if (interval > 7200) {
			throw new WxssApiException("AUTH_FAIL", "60012");
		}
	}

	private IdKeyResponseData buildResponseData(OpenIdResponse openIdResponse, UserInfo userInfo) {
		IdKeyResponseData responseData = new IdKeyResponseData();
		responseData.setId(generateId());
		responseData.setSkey(generateSkey(openIdResponse.getSessionKey()));
		responseData.setUserInfo(userInfo);
		return responseData;
	}

	private IdKeyResponse buildIdKeyResponse(IdKeyResponseData data, String message) {
		IdKeyResponse response = new IdKeyResponse();
		response.setReturnData(data);
		response.setReturnCode("0");
		response.setReturnMessage(message);
		return response;
	}

	private CSessionInfo generateSessionInfo(IdKeyResponseData data, OpenIdResponse openIdResponse,boolean isQyWechat)
			throws JsonProcessingException {
		CSessionInfo sessionInfo = new CSessionInfo();
		sessionInfo.setCreateTime(new Date());
		sessionInfo.setLastVisitTime(new Date());
		if(isQyWechat){
			sessionInfo.setOpenId(data.getUserInfo().getUserid());
		}else{
			sessionInfo.setOpenId(data.getUserInfo().getOpenId());
		}
		sessionInfo.setSessionKey(openIdResponse.getSessionKey());
		sessionInfo.setSkey(data.getSkey());
		sessionInfo.setUserInfo(objectMapper.writeValueAsString(data.getUserInfo()));
		sessionInfo.setUuid(data.getId());
		return sessionInfo;
	}

	public OpenIdResponse getOpenIdByCode(String code, boolean isQyWechat) {
		String url;
		if (isQyWechat) {
			url = "https://qyapi.weixin.qq.com/cgi-bin/miniprogram/jscode2session?access_token=" + qyWeChatAccessToken
					+ "&js_code=" + code + "&grant_type=authorization_code";
		} else {
			url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + props.getAppid() + "&secret="
					+ props.getSecret() + "&js_code=" + code + "&grant_type=authorization_code";
		}
		try {
			String result = HttpClientUtil.httpGet(url, "UTF-8");
			logger.debug(result);
			if (!result.contains("errcode")) {
				return objectMapper.readValue(result, OpenIdResponse.class);
			} else {
				WeixinAppError wxerror = objectMapper.readValue(result, WeixinAppError.class);
				if (wxerror != null) {
					throw new WxssApiException(wxerror.getErrmsg(), "1005");
				} else {
					throw new WxssApiException("internal error.", "1005");
				}
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new WxssApiException(e.getMessage(), "1005");
		} catch (URISyntaxException e) {
			logger.error(e.getMessage());
			throw new WxssApiException(e.getMessage(), "1005");
		}
	}

	@PostConstruct
	public void updateAccessToken() {
		String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=" + qyProps.getCorpid() + "&corpsecret="
				+ qyProps.getSecret();
		try {
			String result = HttpClientUtil.httpGet(url, "UTF-8");
			logger.debug(result);
			QyWxAccessTokenResponse accessTokenResponse = objectMapper.readValue(result, QyWxAccessTokenResponse.class);
			if (Integer.valueOf(0).equals(accessTokenResponse.getErrcode())
					&& !"".equals(accessTokenResponse.getAccessToken())) {
				qyWeChatAccessToken = accessTokenResponse.getAccessToken();
			}
		} catch (IOException e) {
			throw new WxssApiException(e.getMessage(), "1005");
		} catch (URISyntaxException e) {
			throw new WxssApiException(e.getMessage(), "1005");
		}
	}

	private String generateId() {
		return UUID.randomUUID().toString();
	}

	private String generateSkey(String sessionKey) {
		return SHA1.encode(props.getAppid() + props.getSecret() + sessionKey);
	}

	/**
	 * 解密用户敏感数据获取用户信息
	 * 
	 * @param sessionKey
	 *            数据进行加密签名的密钥
	 * @param encryptedData
	 *            包括敏感数据在内的完整用户信息的加密数据
	 * @param iv
	 *            加密算法的初始向量
	 * @return
	 */
	public UserInfo getUserInfo(String encryptedData, String sessionKey, String iv) {
		// 被加密的数据
		byte[] dataByte = Base64Utils.decodeFromString(encryptedData);
		// 加密秘钥
		byte[] keyByte = Base64Utils.decodeFromString(sessionKey);

		try {
			// 如果密钥不足16位，那么就补足. 这个if 中的内容很重要
			int base = 16;
			if (keyByte.length % base != 0) {
				int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
				byte[] temp = new byte[groups * base];
				Arrays.fill(temp, (byte) 0);
				System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
				keyByte = temp;
			}
			// 初始化
			Security.addProvider(new BouncyCastleProvider());
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
			SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
			AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
			if (iv != null || !"".equals(iv)) {
				// 偏移量
				byte[] ivByte = Base64Utils.decodeFromString(iv);
				parameters.init(new IvParameterSpec(ivByte));
			}
			cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
			byte[] resultByte = cipher.doFinal(dataByte);
			if (null != resultByte && resultByte.length > 0) {
				String result = new String(resultByte, "UTF-8");
				return objectMapper.readValue(result, UserInfo.class);
			}
		} catch (Exception e) {
			throw new WxssApiException("解密失败", "60021");
		}
		return null;
	}

}
