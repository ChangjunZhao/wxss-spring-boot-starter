package com.venusource.ms.base.wxss.service;

import com.venusource.ms.base.wxss.domain.CSessionInfo;

public interface SessionInfoStoreService {
	
	CSessionInfo add(CSessionInfo sessionInfo);
	
	void remove(CSessionInfo sessionInfo);
	
	CSessionInfo update(CSessionInfo sessionInfo);
	
	CSessionInfo get(String skey);
}
