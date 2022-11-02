/*Created by Sinatra Gunda
  At 14:42 on 9/14/2020 */

package com.wese.weseaddons.batchprocessing.networkrequest;

import com.wese.weseaddons.enumerations.REQUEST_METHOD;
import com.wese.weseaddons.http.HttpClientBridge;
import com.wese.weseaddons.pojo.LoanApplication;
import org.json.JSONException;
import org.json.JSONObject;

public class DisburseToSavingsRequest {

    private String tenantIdentifier ;

    public DisburseToSavingsRequest(String tenantIdentifier){
        this.tenantIdentifier = tenantIdentifier ;
    }


    public Long disburse(LoanApplication loanApplication) {

        String url = String.format("/loans/%d?command=disburseToSavings");

        JSONObject jsonObject = loanApplication.jsonObject();
        HttpClientBridge httpClientBridge = new HttpClientBridge(REQUEST_METHOD.POST);
        httpClientBridge.setPostObject(jsonObject);

        String httpResponse = httpClientBridge.makeRequest(url);

        JSONObject jsonObject1 = null;
        Long clientId = null ;

        try {

            jsonObject1 = new JSONObject(httpResponse);
            if(jsonObject1.has("loanId")){
                clientId = jsonObject1.getLong("loanId");
            }
        } catch (NullPointerException | JSONException jsonException) {

            /// means its a failed deposit
        }

        return clientId;
    }
}
