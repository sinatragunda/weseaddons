package com.wese.weseaddons.bereaudechange.helper;



import com.wese.weseaddons.helper.TimeHelper;
import com.wese.weseaddons.pojo.AppUser ;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.enumerations.TRADE_TYPE;
import com.wese.weseaddons.bereaudechange.pojo.FxDeal;
import com.wese.weseaddons.bereaudechange.pojo.Settings;
import com.wese.weseaddons.bereaudechange.session.FxSession;
import com.wese.weseaddons.helper.Helper;

import com.wese.weseaddons.pojo.Client;

public class FxReceiptHelper {

    public static  ObjectNode getObjectNode(FxDeal fxDeal ,String tenant){

        Settings settings = FxSession.getInstance().getSettings(tenant);
        AppUser appUser = FxSession.getInstance().getAppUser(tenant ,fxDeal.getAppUser().getId());

        ObjectNode objectNode = Helper.createObjectNode();
        ObjectNode companyData = Helper.createObjectNode();
        ObjectNode receiptData = Helper.createObjectNode() ;
        ObjectNode tellerData = Helper.createObjectNode() ;
        ObjectNode clientDetails = Helper.createObjectNode() ;
        ArrayNode transactionsData = Helper.createArrayNode() ;

        transactionsData.add(transactions(fxDeal));


        Client client = FxSession.getInstance().getClient(fxDeal ,tenant);
        if(client!=null) {
            
            clientDetails.put("name" ,client.getDisplayName());
            clientDetails.put("ID" ,client.getId());

        }

        receiptData.put("date" ,TimeHelper.timestampToString(fxDeal.getDate()));
        receiptData.put("time" ,TimeHelper.timestampToStringTime(fxDeal.getDate()));
        receiptData.put("receipt_no" ,fxDeal.getId());


        companyData.put("company_name",settings.getCompanyName());
        companyData.put("company_address" ,settings.getCompanyAddress());
        companyData.put("company_city" ,settings.getCompanyCity());

        receiptData.put("time" , TimeHelper.timestampToStringTime(fxDeal.getDate()));
        receiptData.put("date" , TimeHelper.timestampToString(fxDeal.getDate()));

        tellerData.put("teller_name" ,appUser.getFxCashier().getTellerName());
        tellerData.put("teller_id" ,appUser.getFxCashier().getId());


        objectNode.putPOJO("teller_data",tellerData);
        objectNode.putPOJO("company_data",companyData);
        objectNode.putPOJO("transactions" ,transactionsData);
        objectNode.putPOJO("client_data" ,clientDetails);
        objectNode.putPOJO("receipt_data",receiptData);

        System.out.println(objectNode);

        return objectNode;

    }


    public static ObjectNode transactions(FxDeal fxDeal){

        ObjectNode objectNode = Helper.createObjectNode();
        objectNode.put("date" , TimeHelper.timestampToString(fxDeal.getDate()));
        objectNode.put("time" ,TimeHelper.timestampToStringTime(fxDeal.getDate()));
        objectNode.put("currency_pair" ,fxDeal.getTradingRates().getCurrencyPair().getTick());
        objectNode.put("transaction_type" ,fxDeal.getTradeType().getCode());
        objectNode.put("deal_type" ,fxDeal.getFxDealType().getCode());
        objectNode.put("exchange_rate" ,fxDeal.getTradingRates().getRate());

        if(fxDeal.getTradeType() == TRADE_TYPE.SELL){

            objectNode.put("exchange_rate" ,fxDeal.getTradingRates().getSellRate());
        }

        objectNode.put("quote_amount" ,fxDeal.getBlotter().getNetQuoteAmount());
        objectNode.put("charges" ,fxDeal.getBlotter().getQuoteCharges());
        objectNode.put("sub_total" ,fxDeal.getBlotter().getBaseAmount());
        objectNode.put("total" ,fxDeal.getBlotter().getNetBaseAmount());

        return objectNode ;

    }

}
