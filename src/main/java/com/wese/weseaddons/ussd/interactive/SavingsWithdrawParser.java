package com.wese.weseaddons.ussd.interactive;

import org.json.JSONException;
import org.json.JSONObject;
import com.wese.weseaddons.ussd.helper.Constants;
import com.wese.weseaddons.ussd.interfaces.JsonParser;
import com.wese.weseaddons.ussd.menu.USSDMenuRouter;
import com.wese.weseaddons.ussd.session.Session;
import com.wese.weseaddons.ussd.session.SessionDetails;
import com.wese.weseaddons.pojo.Client;

import java.math.BigDecimal;

public class SavingsWithdrawParser implements JsonParser{

    private Session session ;
    private JSONObject jsonObject ;
    private BigDecimal transactionAmount ;

    public SavingsWithdrawParser(Session session){
        this.session = session ;
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
            jsonObject.put("locale" ,"en_ZW");
            jsonObject.put("dateFormat","dd MMM yyyy");
            jsonObject.put("transactionDate" ,Constants.getTodayDate());
            jsonObject.put("transactionAmount" ,transactionAmount);
            jsonObject.put("paymentTypeId" ,1);
            jsonObject.put("checkNumber",Constants.dummy);
            jsonObject.put("routingCode" ,Constants.dummy);
            jsonObject.put("receiptNumber",Constants.dummy);
            jsonObject.put("bankNumber",Constants.dummy);
            jsonObject.put("accountNumber",Constants.dummy);

        }

        catch (JSONException jsonException){

            System.out.println(jsonObject.toString());

        }

    }

    public SavingsWithdrawParser(Session session ,BigDecimal transactionAmount){
        this.transactionAmount = transactionAmount;
        this.session = session ;
        init();
    }

}
