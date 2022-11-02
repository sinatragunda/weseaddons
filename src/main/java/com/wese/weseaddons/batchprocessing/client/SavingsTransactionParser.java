package com.wese.weseaddons.batchprocessing.client;

import com.wese.weseaddons.batchprocessing.helper.Constants;
import com.wese.weseaddons.batchprocessing.helper.Parameters;
import org.json.JSONException;
import org.json.JSONObject;


public class SavingsTransactionParser{

    private JSONObject jsonObject ;
    private double transactionAmount ;
    private Parameters parameters ;


    public JSONObject getJsonObject(){

        return jsonObject ;
    }


    public void init(){

        try{
            jsonObject = new JSONObject();
            jsonObject.put("locale" ,"en_ZW");
            jsonObject.put("dateFormat","dd MMM yyyy");
            jsonObject.put("transactionDate" , parameters.getPostedDate());
            jsonObject.put("transactionAmount" ,transactionAmount);
            jsonObject.put("paymentTypeId" ,1);
            jsonObject.put("checkNumber", Constants.dummy);
            jsonObject.put("routingCode" ,Constants.dummy);
            jsonObject.put("receiptNumber",Constants.dummy);
            jsonObject.put("bankNumber",Constants.dummy);
            jsonObject.put("accountNumber",Constants.dummy);
            jsonObject.put("note" ,parameters.getTranferDescription());

        }

        catch (JSONException jsonException){
            jsonException.printStackTrace();
        }

    }

    public SavingsTransactionParser(double transactionAmount , Parameters parameters){
        this.transactionAmount = transactionAmount;
        this.parameters = parameters ;
        init();
    }

}
