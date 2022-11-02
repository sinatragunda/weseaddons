/*Created by Sinatra Gunda
  At 15:29 on 9/3/2020 */

package com.wese.weseaddons.batchprocessing.networkrequest;

import com.wese.weseaddons.batchprocessing.client.SavingsTransactionParser;
import com.wese.weseaddons.batchprocessing.parser.ForeclosureParser;
import com.wese.weseaddons.batchprocessing.pojo.Foreclosure;
import com.wese.weseaddons.enumerations.REQUEST_METHOD;
import com.wese.weseaddons.http.HttpClientBridge;
import org.json.JSONException;
import org.json.JSONObject;

public class ForeclosureRequest {


    private String tenantIdentifier ;

    public ForeclosureRequest(String tenantIdentifier){
        this.tenantIdentifier = tenantIdentifier ;
    }


    public Long foreclose(Foreclosure foreclosure) {

        String url = String.format("/loans/%s/transactions?command=foreclosure", foreclosure.getLoanAccountNumber());

        ForeclosureParser foreclosureParser = new ForeclosureParser(foreclosure);

        JSONObject jsonObject = foreclosureParser.jsonObject();
        HttpClientBridge httpClientBridge = new HttpClientBridge(REQUEST_METHOD.POST);
        httpClientBridge.setPostObject(jsonObject);

        String httpResponse = httpClientBridge.makeRequest(url);

        JSONObject jsonObject1 = null;
        Long clientId = null ;

        try {

            //System.err.println("-----------------------------"+foreclosureParser.jsonObject().toString());
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
