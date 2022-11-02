package com.wese.weseaddons.batchprocessing.wese;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import com.wese.weseaddons.bereaudechange.enumerations.ROUNDING_RULE;
import com.wese.weseaddons.enumerations.REQUEST_METHOD;
import com.wese.weseaddons.helper.MoneyHelper;
import com.wese.weseaddons.http.HttpClientBridge;
import com.wese.weseaddons.batchprocessing.pojo.ExcelClientData;
import com.wese.weseaddons.pojo.MakerCheckerEntry;
import com.wese.weseaddons.pojo.SavingsAccount;
import com.wese.weseaddons.batchprocessing.client.SavingsTransactionParser;
import com.wese.weseaddons.batchprocessing.helper.Parameters;
import org.json.JSONException;
import org.json.JSONObject;

public class SavingsDeposit {

    private List<ExcelClientData> excelClientDataList;
    private HttpClientBridge httpClientBridge ;
    private double amount ;
    private SavingsAccount savingsAccount ;
    private String url ;
    private Parameters parameters ;

    public SavingsDeposit(SavingsAccount savingsAccount ,double amount ,Parameters parameters){

        this.savingsAccount = savingsAccount ;
        this.parameters = parameters ;
        this.amount = MoneyHelper.decimalFormat(amount) ;

    }

    public MakerCheckerEntry deposit(){

        boolean isPresent = Optional.ofNullable(savingsAccount).isPresent();

        if(!isPresent){
           return null;  
        }

        url = String.format("savingsaccounts/%d/transactions?command=deposit",savingsAccount.getId());

//        RoundingMode roundingMode = MoneyHelper.getRoundingMode();
//
//        BigDecimal amountRound = new BigDecimal(amount).setScale(2 , roundingMode);
//
//        Float flot = amountRound.floatValue();

        SavingsTransactionParser savingsTransactionParser = new SavingsTransactionParser(amount ,parameters);
        JSONObject jsonObject = savingsTransactionParser.getJsonObject();
        HttpClientBridge httpClientBridge = new HttpClientBridge(REQUEST_METHOD.POST);
        httpClientBridge.setPostObject(jsonObject);
        String httpResponse = httpClientBridge.makeRequest(url);
        
        return MakerCheckerEntry.fromHttpResponse(httpResponse);
    }
}
