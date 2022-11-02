package com.wese.weseaddons.ussd.interactive;

import org.json.JSONException;
import org.json.JSONObject;
import com.wese.weseaddons.ussd.interfaces.JsonParser;

public class ApproveLoanParser implements JsonParser {


    private JSONObject jsonObject ;

    @Override
    public JSONObject getJsonObject(){

        return jsonObject ;
    }

    public void init(){

        try{

        }

        catch (JSONException j){

            System.out.println(j.getMessage());
        }
    }

    public ApproveLoanParser(){

        init();
    }
}
