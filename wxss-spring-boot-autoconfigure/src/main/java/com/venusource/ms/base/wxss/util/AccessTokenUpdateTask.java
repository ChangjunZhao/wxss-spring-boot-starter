package com.venusource.ms.base.wxss.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.venusource.ms.base.wxss.service.WeChatSessionService;

@Component
public class AccessTokenUpdateTask {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private WeChatSessionService weChatSessionService;
	
	@Scheduled(cron="0 0 0/2 * * ?")
	public void updateKey(){
		try {
			weChatSessionService.updateAccessToken();;
		} catch (Exception e) {
			logger.info("update access token and jsticket fail:{}",e.getMessage());
		}
		logger.info("update access token and jsticket success!");
	}
}
