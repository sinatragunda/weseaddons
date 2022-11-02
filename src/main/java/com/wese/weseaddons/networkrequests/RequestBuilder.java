package com.wese.weseaddons.networkrequests;


import com.wese.weseaddons.http.HttpClientBridge;
import com.wese.weseaddons.utility.ThreadContext;
import org.json.JSONObject;

import com.wese.weseaddons.enumerations.REQUEST_METHOD;
import com.wese.weseaddons.ussd.helper.Constants;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.util.Optional;


public class RequestBuilder {

	private HttpClientBridge httpClientRequest ;
	private StringBuilder stringBuilder = null;
	private String apiUrl ;
	private JSONObject jsonObject ;
	private REQUEST_METHOD requestMethod ;
	private String tenantIdentifier ;

	public static RequestBuilder build(REQUEST_METHOD requestMethod, String url){
		return new RequestBuilder(requestMethod ,url ,null);
	}


	public static RequestBuilder build(REQUEST_METHOD requestMethod, String url ,String requestBody){
		return new RequestBuilder(requestMethod ,url ,requestBody);
	}

	public RequestBuilder(REQUEST_METHOD requestMethod , String url ,String requestBody){

		this.apiUrl = url;
		this.requestMethod = requestMethod ;
		this.tenantIdentifier = ThreadContext.getTenant();
		if(requestMethod == REQUEST_METHOD.POST || requestMethod == REQUEST_METHOD.PUT){
			System.err.println("-------"+requestBody);
			Optional.ofNullable(requestBody).ifPresent(e->{
				//System.err.println("-------init requestBody ------");
				this.jsonObject = new JSONObject(e);
			});
		}
	}

	public String makeRequest(){

		httpClientRequest = new HttpClientBridge(requestMethod);

		if(requestMethod == REQUEST_METHOD.POST || requestMethod == REQUEST_METHOD.PUT){
			httpClientRequest.setPostObject(jsonObject);
		}
		String response = httpClientRequest.makeRequest(apiUrl);
		return response ;
	}
}