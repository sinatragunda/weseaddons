package com.wese.weseaddons.batchprocessing.wese;



import com.wese.weseaddons.enumerations.REQUEST_METHOD;
import com.wese.weseaddons.http.HttpClientBridge;
import com.wese.weseaddons.batchprocessing.client.ActiveLoanBalance;
import com.wese.weseaddons.batchprocessing.helper.Parameters;
import com.wese.weseaddons.utility.ThreadContext;
import org.json.JSONException;
import org.json.JSONObject;

public class ActiveLoanBalanceRequest {
    

    public ActiveLoanBalance makeRequest(Parameters parameters , long loanId){


        ActiveLoanBalance activeLoanBalance = null ;
        StringBuilder stringBuilder = new StringBuilder("/loans/");
        stringBuilder.append(String.valueOf(loanId));
        String restUrl = stringBuilder.toString();

        JSONObject jsonObject = null;
        HttpClientBridge httpClientBridge = new HttpClientBridge(REQUEST_METHOD.GET);
        String response = httpClientBridge.makeRequest(restUrl);


        if(response==null){
            return null ;

        }

        try{

            jsonObject = new JSONObject(response);
            double principal = jsonObject.getDouble("principal");
            JSONObject summaryJsonObject = jsonObject.getJSONObject("summary");
            double principalOutstanding = summaryJsonObject.getDouble("principalOutstanding");
            double interestOutstanding = summaryJsonObject.getDouble("interestOutstanding");
            double totalOutstanding = summaryJsonObject.getDouble("totalOutstanding");

            activeLoanBalance = new ActiveLoanBalance(principal ,totalOutstanding);

        }

        catch (JSONException j){
            System.out.println("Exception thrown here "+j.getMessage());
        }

        return activeLoanBalance ;

    }
}
