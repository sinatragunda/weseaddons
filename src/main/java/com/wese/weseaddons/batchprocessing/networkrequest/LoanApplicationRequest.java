/*Created by Sinatra Gunda
  At 14:29 on 9/14/2020 */

package com.wese.weseaddons.batchprocessing.networkrequest;

import com.wese.weseaddons.batchprocessing.parser.ForeclosureParser;
import com.wese.weseaddons.batchprocessing.pojo.Foreclosure;
import com.wese.weseaddons.enumerations.REQUEST_METHOD;
import com.wese.weseaddons.http.HttpClientBridge;
import com.wese.weseaddons.pojo.LoanApplication;
import org.json.JSONException;
import org.json.JSONObject;

public class LoanApplicationRequest {

    private String tenantIdentifier ;
    public LoanApplicationRequest(String tenantIdentifier){
        this.tenantIdentifier = tenantIdentifier ;
    }


    public Long loanApplication(LoanApplication loanApplication) {

        String url = "/loans";
        JSONObject jsonObject = loanApplication.jsonObject();
        HttpClientBridge httpClientBridge = new HttpClientBridge(REQUEST_METHOD.POST);
        httpClientBridge.setPostObject(jsonObject);

        String httpResponse = httpClientBridge.makeRequest(url);

        JSONObject jsonObject1 = null;
        Long loanId = null ;

        try {

            //System.err.println("-----------------------------"+foreclosureParser.jsonObject().toString());
            jsonObject1 = new JSONObject(httpResponse);
            if(jsonObject1.has("loanId")){
                loanId = jsonObject1.getLong("loanId");
            }
        } catch (NullPointerException | JSONException jsonException) {

            /// means its a failed deposit

        }

        return loanId;
    }
}
