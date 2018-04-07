package com.venusource.ms.base.wxss.endpoint.support;


/**
 * Represents an error response.
 * @author cj.zhao
 */
public class ErrorResource {
	public String returnCode;
	public String returnMessage;

    public ErrorResource(String code, String message) {
        this.returnCode = code;
        this.returnMessage = message;
    }
}
