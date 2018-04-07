package com.venusource.ms.base.wxss;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;
/**
 * 
 * @author ChangjunZhao
 * created at 2018-04-06
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(WeChatSessionServerConfiguration.class)
public @interface EnableWeChatSessionServer {

}
