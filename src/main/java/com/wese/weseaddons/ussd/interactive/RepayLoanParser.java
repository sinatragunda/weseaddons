package com.wese.weseaddons.ussd.interactive;

import org.json.JSONException;
import org.json.JSONObject;
import com.wese.weseaddons.ussd.helper.Constants;
import com.wese.weseaddons.ussd.interfaces.JsonParser;

public class RepayLoanParser implements JsonParser{

    private JSONObject jsonObject ;
    private double transactionAmount ;
    private String note ;

    @Override
    public JSONObject getJsonObject() {
    	
        return jsonObject;
    }


    public void init(){

        try{

            this.jsonObject = new JSONObject();
            jsonObject.put("locale" ,"en_ZW");
            jsonObject.put("dateFormat" ,"dd MMMM yyyy");
            jsonObject.put("transactionDate" , Constants.getTodayDate());
            jsonObject.put("transactionAmount" ,transactionAmount);
            jsonObject.put("note" ,note);

            /// these are static but should be dynamic ;
            jsonObject.put("paymentTypeId" ,1);

        }

        catch(JSONException jsonException){

            System.out.println("Is there an exception here ?"+jsonException.getMessage());
        }

    }

    public RepayLoanParser(double transactionAmount ,String note){
        this.transactionAmount = transactionAmount ;
        this.note = note ;
        init();
    }

}
