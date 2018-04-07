package com.venusource.ms.base.wxss.endpoint.support;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


/**
 * Exception handler handles exceptions from api layer.
 * @author cj.zhao
 */
@ControllerAdvice(annotations = {Controller.class})
public class WxssApiExceptionHandler extends ResponseEntityExceptionHandler {
    public static final String HTTP_X_FORWARDED_FOR = "X-Forwarded-For";
    
    @ExceptionHandler(WxssApiException.class)
    public final ResponseEntity<ErrorResource> handleServiceException(WxssApiException ex, HttpServletRequest request) {
        ErrorResource resource = new ErrorResource(ex.errorCode, ex.getMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new ResponseEntity<ErrorResource>(resource, headers, HttpStatus.OK);
    }

}
