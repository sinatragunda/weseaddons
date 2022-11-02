package com.wese.weseaddons.ussd.interactive;

import org.json.JSONException;
import org.json.JSONObject;
import com.wese.weseaddons.ussd.helper.Constants;
import com.wese.weseaddons.ussd.interfaces.JsonParser;
import com.wese.weseaddons.ussd.menu.USSDMenuRouter;
import com.wese.weseaddons.ussd.session.Session;
import com.wese.weseaddons.ussd.session.SessionDetails;
import com.wese.weseaddons.pojo.Client;


public class CreateSavingsAccountParser implements JsonParser{

    private Session session ;
    private JSONObject jsonObject ;


    public CreateSavingsAccountParser(Session session){
        this.session = session ;
        init();
    }

    @Override
    public JSONObject getJsonObject(){

        return jsonObject ;
    }


    public void init(){

        ///SessionDetails.getInstance().get
        Client client = session.getClient();

        try{

            jsonObject = new JSONObject();
            jsonObject.put("productId" ,Constants.drawdownSavingsId);
            jsonObject.put("locale" ,"en_ZW");
            jsonObject.put("dateFormat","dd MMM yyyy");
            jsonObject.put("submittedOnDate", Constants.getTodayDate());
            jsonObject.put("clientId",client.getId());

        }

        catch (JSONException jsonException){

            System.out.println(jsonObject.toString());

        }

    }

}
