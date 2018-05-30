package com.venusource.ms.base.wxss.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

public class HttpClientUtil {
	
	public static String httpGet(String url,String code) throws IOException, URISyntaxException {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		ClientHttpRequest request = factory.createRequest(new URI(url), HttpMethod.GET);
		return inputStreamToString(request.execute().getBody(),code);
	}
	
	public static String httpPost(String url,String body, String encode) throws IOException, URISyntaxException{
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		ClientHttpRequest request = factory.createRequest(new URI(url), HttpMethod.POST);
		OutputStreamWriter requestWriter = new OutputStreamWriter(request.getBody(), "UTF-8");
		requestWriter.write(body);
		requestWriter.flush();
		return inputStreamToString(request.execute().getBody(),encode);
	}
	
	public static String inputStreamToString(InputStream inputStream, String code) throws IOException{
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length;
		while ((length = inputStream.read(buffer)) != -1) {
		    result.write(buffer, 0, length);
		}
		return result.toString(code);
	}
}