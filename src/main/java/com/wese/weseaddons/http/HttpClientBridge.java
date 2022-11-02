package com.wese.weseaddons.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;


import com.fasterxml.jackson.databind.util.JSONPObject;

import com.wese.weseaddons.enumerations.REQUEST_METHOD;
import com.wese.weseaddons.helper.Constants;

import com.wese.weseaddons.utility.ThreadContext;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;



public class HttpClientBridge {

    private JSONObject jsonPostObject ;
    private REQUEST_METHOD requestMethod ;
    private String url ;
    private String tenantIdentifier ;

    public HttpClientBridge(REQUEST_METHOD requestMethod){
        this.requestMethod = requestMethod ;
    }

    public String makeRequest(String argUrl){

        url = Constants.apiUrl(argUrl);
        this.tenantIdentifier = ThreadContext.getTenant();

        try{
            return requestMethod();
        }
        catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        return null ;
    }

    public String postRequest(){

        HttpPostReq httpPostReq = new HttpPostReq();
        HttpPost httpPost = httpPostReq.createConnectivity(url);
        String responseString = httpPostReq.executeReq( jsonPostObject , httpPost);

        return responseString ;

    }
    
    public String putRequest(){

        HttpPutReq httpPutReq = new HttpPutReq();
        HttpPut httpPut = httpPutReq.createConnectivity(url);
        String responseString = httpPutReq.executeReq( jsonPostObject , httpPut);

        return responseString ;

    }

    public void setPostObject(JSONObject jsonObject){
        this.jsonPostObject = jsonObject ;
    }

    public String getRequest(){
        
        HttpGetReq httpGetReq = new HttpGetReq();
        HttpGet httpGet = httpGetReq.createConnectivity(url);
        String responseString = httpGetReq.executeReq( null, httpGet);
        return responseString ;
    }


    public String plainPostRequest(){

        HttpPlainPostReq httpPlainPostReq = new HttpPlainPostReq();
        HttpPost httpPost = httpPlainPostReq.createConnectivity(url);
        String responseString = httpPlainPostReq.executeReq( null, httpPost);
        return responseString ;
    }

    public String requestMethod(){

        String responseString =null;

        switch(requestMethod){

            case GET:
                responseString = getRequest();
                break ;

            case POST:
                responseString = postRequest();
                break ;
                
            case PUT:
                responseString = putRequest();
                break ;
            case PLAIN:
                responseString = plainPostRequest();
                break;
                
        }

        return responseString ;

    }

}
