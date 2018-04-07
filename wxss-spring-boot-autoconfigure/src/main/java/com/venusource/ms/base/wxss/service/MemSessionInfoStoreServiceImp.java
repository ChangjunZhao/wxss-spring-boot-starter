package com.venusource.ms.base.wxss.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.tomcat.util.security.MD5Encoder;
import org.bouncycastle.util.Strings;

import com.venusource.ms.base.wxss.domain.CSessionInfo;

public class MemSessionInfoStoreServiceImp implements SessionInfoStoreService{
	
	private final static Map<String,CSessionInfo> sessionMap= new HashMap<String,CSessionInfo>();

	@Override
	public CSessionInfo add(CSessionInfo sessionInfo) {
		String key= MD5Encoder.encode(Strings.toByteArray(sessionInfo.getSkey()));
		sessionMap.put(key, sessionInfo);
		return sessionInfo;
	}

	@Override
	public void remove(CSessionInfo sessionInfo) {
		String key= MD5Encoder.encode(Strings.toByteArray(sessionInfo.getSkey()));
		sessionMap.remove(key);
	}

	@Override
	public CSessionInfo get(String skey) {
		String key= MD5Encoder.encode(Strings.toByteArray(skey));
		return sessionMap.get(key);
	}

	@Override
	public CSessionInfo update(CSessionInfo sessionInfo) {
		return add(sessionInfo);
	}
	
}
