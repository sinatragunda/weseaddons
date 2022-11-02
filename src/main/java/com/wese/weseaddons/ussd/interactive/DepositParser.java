package com.wese.weseaddons.ussd.interactive;

import org.json.JSONException;
import org.json.JSONObject;
import com.wese.weseaddons.ussd.helper.Constants;
import com.wese.weseaddons.ussd.interfaces.JsonParser;
import com.wese.weseaddons.ussd.menu.USSDMenuRouter;
import com.wese.weseaddons.ussd.session.Session;
import com.wese.weseaddons.pojo.Client;

import java.math.BigDecimal;

public class DepositParser implements JsonParser{

    private Session session ;
    private JSONObject jsonObject ;
    private BigDecimal transactionAmount ;

    public DepositParser(Session session){
        this.session = session ;
    }

    @Override
    public JSONObject getJsonObject(){

        return jsonObject ;
    }


    public void init(){

        Client client = session.getClient();

        SavingsWithdrawParser savingsWithdrawParser = new SavingsWithdrawParser(session ,transactionAmount);
        jsonObject = savingsWithdrawParser.getJsonObject();

    }

    public DepositParser(Session session ,BigDecimal transactionAmount){
        this.transactionAmount = transactionAmount;
        this.session = session;
        init();
    }

}
