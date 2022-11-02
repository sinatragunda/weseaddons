package com.wese.weseaddons.ussd.interactive;

import org.json.JSONException;
import org.json.JSONObject;
import com.wese.weseaddons.ussd.helper.Constants;
import com.wese.weseaddons.ussd.interfaces.JsonParser;
import com.wese.weseaddons.ussd.menu.USSDMenuRouter;
import com.wese.weseaddons.ussd.session.Session;
import com.wese.weseaddons.pojo.Client;

public class ApproveSavingsAplicationParser implements JsonParser {

    private JSONObject jsonObject ;

    @Override
    public JSONObject getJsonObject(){
        return jsonObject ;
    }

    public void init(){

        try{

            jsonObject = new JSONObject();
            jsonObject.put("locale" ,"en_ZW");
            jsonObject.put("dateFormat","dd MMM yyyy");
            jsonObject.put("approvedOnDate" , Constants.getTodayDate());

        }

        catch (JSONException jsonException){

            System.out.println(jsonObject.toString());

        }

    }
    public ApproveSavingsAplicationParser(){
        init();
    }

}
