/*

    Created by Sinatra Gunda
    At 9:59 AM on 3/18/2021

*/
package com.wese.weseaddons.networkrequests;

import com.wese.weseaddons.enumerations.REQUEST_METHOD;
import com.wese.weseaddons.helper.Constants;
import com.wese.weseaddons.http.HttpClientBridge;
import com.wese.weseaddons.utility.ThreadContext;
import com.wese.weseaddons.utility.chartofaccounts.pojo.ChartOfAccount;
import org.json.JSONObject;

public class ChartOfAccountRequest {

    public static void createChart(ChartOfAccount chartOfAccount){

        HttpClientBridge httpClientBridge = new HttpClientBridge(REQUEST_METHOD.POST);
        JSONObject jsonObject = chartOfAccount.jsonObject();
        httpClientBridge.setPostObject(jsonObject);

        String url = Constants.apiUrl("glaccounts");
        String tenant = ThreadContext.getTenant();
        String response = httpClientBridge.makeRequest(url);

    }

    public static ChartOfAccount getAccount(String reference){

        HttpClientBridge httpClientBridge = new HttpClientBridge(REQUEST_METHOD.GET);

        String url = new String("glaccounts/glname/"+reference);
        String tenant = ThreadContext.getTenant();
        String response = httpClientBridge.makeRequest(url);

        return null ;


    }
}
