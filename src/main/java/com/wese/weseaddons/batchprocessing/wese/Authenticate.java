package com.wese.weseaddons.batchprocessing.wese;



import com.wese.weseaddons.utility.ThreadContext;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.enumerations.REQUEST_METHOD;
import com.wese.weseaddons.http.HttpClientBridge;
import com.wese.weseaddons.batchprocessing.client.User;
import com.wese.weseaddons.batchprocessing.helper.Constants;
import com.wese.weseaddons.batchprocessing.helper.ResponseBuilder;



public class Authenticate {

    private HttpClientBridge httpClientBridge ;
    private String username ;
    private String password ;
    private String tenantName;

    public Authenticate(String username ,String password){
        this.tenantName = ThreadContext.getTenant();
        this.username = username ;
        this.password = password ;
    }

    public ObjectNode login(){

        httpClientBridge = new HttpClientBridge(REQUEST_METHOD.POST);

        StringBuilder stringBuilder = new StringBuilder(Constants.apiUrl);
        stringBuilder.append(String.format("/authentication?username=%s&password=%s",username ,password));

        String url = stringBuilder.toString();

        httpClientBridge.setPostObject(null);
        String response = httpClientBridge.makeRequest(url);

        try{

            JSONObject jsonObject = new JSONObject(response);
            boolean isAuthenticated = jsonObject.getBoolean("authenticated");
            String username = jsonObject.getString("username");

            if(!isAuthenticated){

                return ResponseBuilder.authentication(false ,null);

            }

            User user = new User(username);
            return ResponseBuilder.authentication(true ,user);
        }

        catch (JSONException jsonException){
            System.out.println("Invalid login thrown exception "+jsonException.getMessage());
        }

        return ResponseBuilder.authentication(false ,null);
    }




}
