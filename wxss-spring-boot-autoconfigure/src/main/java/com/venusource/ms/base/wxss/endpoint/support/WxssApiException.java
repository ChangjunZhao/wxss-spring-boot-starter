package com.venusource.ms.base.wxss.endpoint.support;


public class WxssApiException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2773889982787408484L;
	public String errorCode;

    public WxssApiException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
