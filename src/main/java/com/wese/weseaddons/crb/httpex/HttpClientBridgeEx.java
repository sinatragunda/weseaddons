package com.wese.weseaddons.crb.httpex;


import com.wese.weseaddons.enumerations.AUTHENTICATION_TYPE;
import com.wese.weseaddons.enumerations.REQUEST_METHOD;
import com.wese.weseaddons.helper.Constants;

import org.apache.http.client.methods.HttpPost;
import org.json.JSONObject;


public class HttpClientBridgeEx {

    private JSONObject jsonPostObject ;
    private REQUEST_METHOD requestMethod ;
    private String url ;
    private String token ;
    private AUTHENTICATION_TYPE authenticationType ;


    public HttpClientBridgeEx(REQUEST_METHOD requestMethod){
        this.requestMethod = requestMethod ;
    }


    public String makeRequest(String argUrl ,String token ,AUTHENTICATION_TYPE authenticationType){

        url = argUrl ;
        this.token = token ;
        this.authenticationType = authenticationType ;
        return requestMethod();

    }

    public String makeRequest(String url){
        this.url = url ;
        return requestMethod();

    }

    public String plainPostReqest(){

        HttpPostReqEx httpPostReqEx = new HttpPostReqEx();
        HttpPost httpPost = httpPostReqEx.createConnectivity(url);
        String responseString = httpPostReqEx.executeReq( jsonPostObject , httpPost);

        return responseString ;

    }



    public String postRequest(){

        HttpPostReqEx httpPostReqEx = new HttpPostReqEx();
        HttpPost httpPost = httpPostReqEx.createConnectivity(url ,token ,authenticationType);
        String responseString = httpPostReqEx.executeReq( jsonPostObject , httpPost);

        return responseString ;

    }


    public void setPostObject(JSONObject jsonObject){
        this.jsonPostObject = jsonObject ;
    }


    public String requestMethod(){

        String responseString =null;

        switch(requestMethod){

            case POST:
                responseString = postRequest();
                break ;

            case PLAIN:
                responseString = plainPostReqest();
                break;
                
        }

        return responseString ;

    }

}
