# 兼容Wafer2 微信小程序用户认证Spring Boot Starter

微信小程序开发最基础的功能就是用户的认证过程，在使用spring cloud微服务架构进行开发时为了简化开发周期，我们参考[Wafer](https://github.com/tencentyun/wafer)的架构，实现了java版本的会话服务器，由于目前wafer1官方已经停止更新，[Wafer2](https://github.com/tencentyun/wafer2-quickstart)服务器端完全托管在腾讯云，不对外公开会话服务器的实现，因此本项目实现的会话服务器接口参考了Wafer2的小程序客户端[wafer2-client-sdk](https://github.com/tencentyun/wafer2-client-sdk)的代码，目前与其完全兼容(仅登录接口)。

## 使用方法

**目前只支持Spring Boot 2.0以上版本。**

在您的spring boot项目中引入本项目依赖，然后增加相应功能注解和配置即可。

maven:

```xml
<dependency>
<groupId>com.venusource.ms.base</groupId>
<artifactId>wxss-spring-boot-starter</artifactId>
<version>1.0.2</version>
</dependency>
```

gradle:

```
compile('com.venusource.ms.base:wxss-spring-boot-starter:1.0.1')
```

### 会话服务器

> 实现参考php版本 [wafer-session-server](https://github.com/tencentyun/wafer-session-server) ，使用前请详细阅读。

在Spring Boot的入口类中加入`@EnableWeChatSessionServer`注解即可。如下所示：

```java
package com.venusource.ms.base.wxssdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.venusource.ms.base.wxss.EnableWeChatSessionServer;

@SpringBootApplication
@EnableWeChatSessionServer //启用会话服务器
public class WxssSpringBootStarterDemoApplication {

public static void main(String[] args) {
    SpringApplication.run(WxssSpringBootStarterDemoApplication.class, args);
}
}
```

在spring的配置文件application.properties中配置小程序的appid和secret，如下所示：

```
weixin.app.appid= yourappid
weixin.app.secret= yoursecret

//1.0.2以后版本支持企业微信小程序
qyweixin.app.corpid = yourcorpid
qyweixin.app.secret = yoursecret
```

启动服务后，会自动在服务内对外暴露`/mina_auth` 会话服务接口。

接口与php版本完全相同，见[wafer-session-server](https://github.com/tencentyun/wafer-session-server)。

增加environment参数，值为: wxwork ,即会调用企业微信的认证。

注：会话服务会和微信的服务器进行通信，获取session\_key，需要能访问外网，同时为了安全性会话服务一般只对内部服务提供服务，不对外暴露接口，因此如果使用Spring Cloud的服务网关，需要把它忽略。

**Session数据的存储**

默认情况下，所有的用户数据存储在内存内（不推荐），您需要实现如下接口来存储自己的数据：

```java
package com.venusource.ms.base.wxss.service;

import com.venusource.ms.base.wxss.domain.CSessionInfo;

public interface SessionInfoStoreService {
    CSessionInfo add(CSessionInfo sessionInfo);
    void remove(CSessionInfo sessionInfo);
    CSessionInfo update(CSessionInfo sessionInfo);
    CSessionInfo get(String skey);
}
```
在实现类内注解`@Service`即可，系统会自动替换session存储类型。

### 登录入口

登录入口是为wafer2客户端提供的用户授权登录地址，您可以自己基于上面的会话服务器单独开发，也可以在服务内增加注解`@EnableWeChatWaferLoginEndpoint`，自动提供登录入口`/login`，如下所示：

```java
package com.venusource.ms.base.wxssdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.venusource.ms.base.wxss.EnableWeChatWaferLoginEndpoint;


@SpringBootApplication
@EnableWeChatWaferLoginEndpoint //启用wafer2登录入口
public class WxssSpringBootStarterDemoApplication {

public static void main(String[] args) {
    SpringApplication.run(WxssSpringBootStarterDemoApplication.class, args);
}
}
```

在spring的配置文件application.properties中配置会话服务器地址，如下所示：

```
weixin.wafer.authServerUrl = http://ms-session-server/mina_auth/
```

ms-session-server为在Eureka中注册的服务名，因为我们使用ribbon作客户端负载，可以部署多个ms-session-server服务。

### 业务服务用户信息

基于Spring Cloud微服务架构开发时，业务服务需要获取用户信息时，starter可提供自动的参数注入功能，只需要在业务服务内增加注解`@EnableWeChatUserInfoArgumentInjection`即可，如下所示：

```java
package com.venusource.app.oa.microserviceprovideruser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.venusource.ms.base.wxss.EnableWeChatUserInfoArgumentInjection;

@SpringBootApplication
@EnableDiscoveryClient
@EnableWeChatUserInfoArgumentInjection //启用微信用户信息注入。
public class MicroserviceProviderUserApplication {

public static void main(String[] args) {
    SpringApplication.run(MicroserviceProviderUserApplication.class, args);
}
}
```

在spring的配置文件application.properties中配置会话服务器地址，如下所示：

```
weixin.wafer.authServerUrl = http://ms-session-server/mina_auth/
```

ms-session-server为在Eureka中注册的服务名，因为我们使用ribbon作客户端负载，可以部署多个ms-session-server服务。

在controller的方法参数中，会自动注入`UserInfo`类，可以直接获取登录用户的信息，如下所示：

```java
@GetMapping("/product/{id}")
public Product showInfo(@PathParam Long id, UserInfo userInfo) {
    String uid = userInfo.getOpenId();
    ...
}
```

> 项目目前代码还很丑陋，测试还不全面，如您在使用中发现问题，请及时告诉我们，或直接pull request。

## LICENSE

[MIT](LICENSE)
