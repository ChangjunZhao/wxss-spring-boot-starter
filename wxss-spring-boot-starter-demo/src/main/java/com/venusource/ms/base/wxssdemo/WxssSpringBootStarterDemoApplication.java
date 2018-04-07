package com.venusource.ms.base.wxssdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.venusource.ms.base.wxss.EnableWeChatSessionServer;
import com.venusource.ms.base.wxss.EnableWeChatWaferLoginEndpoint;


@SpringBootApplication
@EnableWeChatSessionServer
@EnableWeChatWaferLoginEndpoint
public class WxssSpringBootStarterDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(WxssSpringBootStarterDemoApplication.class, args);
	}
}
