/*

    Created by Sinatra Gunda
    At 9:30 AM on 9/14/2022

*/
package com.wese.weseaddons.networkrequests;

import com.wese.weseaddons.enumerations.REQUEST_METHOD;
import com.wese.weseaddons.pojo.LoginData;
import com.wese.weseaddons.pojo.ShareAccount;

public class CredentialsRequest {

    public static LoginData login(String username ,String password){

        String url = String.format("authentication?username=%s&password=%s",username ,password);
        REQUEST_METHOD requestMethod = REQUEST_METHOD.PLAIN;

        String response = RequestBuilder.build(requestMethod,url).makeRequest();
        return LoginData.fromHttpResponse(response);
    }

}
