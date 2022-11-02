package com.wese.weseaddons.ussd.interactive;

import org.json.JSONException;
import org.json.JSONObject;
import com.wese.weseaddons.helper.Constants;
import com.wese.weseaddons.ussd.interfaces.JsonParser;

public class CancelLoanParser implements JsonParser{

    private JSONObject jsonObject ;
    private String reason ;
    private String defaultReason ="No reason specified by client";


    @Override
    public JSONObject getJsonObject(){
        return jsonObject ;
    }

    public void init(){

        try{

            jsonObject = new JSONObject();
            //jsonObject.put("locale" ,Constants.locale);
            jsonObject.put("dateFormat", Constants.dateFormat);
          //  jsonObject.put("withdrawnOnDate",Constants.getTodayDate());
            jsonObject.put("note",reason);

        }

        catch (JSONException jsonException){


        }

    }

    public CancelLoanParser(String reason){
        this.reason = reason ;

        if(reason==null){

            reason = defaultReason ;
        }

        init();
    }


}
