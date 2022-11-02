package com.wese.weseaddons.bereaudechange.helper;

import com.wese.weseaddons.enumerations.REQUEST_METHOD;
import com.wese.weseaddons.http.HttpClientBridge;
import org.json.JSONException;
import org.json.JSONObject;

public class FxAuthorizationHelper {


    public static boolean authorize(String tenant ,String username ,String password){


        boolean authenticated = false ;

        if(username ==null || password==null){
            return authenticated ;
        }

        String endpoint = String.format("/authentication?username=%s&password=%s",username ,password);
        HttpClientBridge httpClientBridge = new HttpClientBridge(REQUEST_METHOD.POST);
        httpClientBridge.setPostObject(null);
        String response = httpClientBridge.makeRequest(endpoint);

        try{

            System.out.println("The response is "+response);

            JSONObject jsonObject = new JSONObject(response);
            authenticated = jsonObject.getBoolean("authenticated");
        }

        catch (JSONException j){

        }

        return authenticated ;
    }
}
