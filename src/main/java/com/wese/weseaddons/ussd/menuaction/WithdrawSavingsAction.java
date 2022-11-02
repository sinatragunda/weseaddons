package com.wese.weseaddons.ussd.menuaction;

import com.wese.weseaddons.enumerations.REQUEST_METHOD;
import com.wese.weseaddons.http.HttpClientBridge;
import com.wese.weseaddons.pojo.Client;
import com.wese.weseaddons.ussd.helper.Constants;
import com.wese.weseaddons.ussd.interactive.SavingsWithdrawParser;
import com.wese.weseaddons.ussd.menu.USSDMenuRouter;
import com.wese.weseaddons.ussd.tree.ExecuteMenuAction;
import com.wese.weseaddons.networkrequests.RequestBuilder;
import com.wese.weseaddons.networkrequests.SavingsRequest;
import com.wese.weseaddons.ussd.session.Session;
import com.wese.weseaddons.pojo.SavingsAccount;

import java.math.BigDecimal;
import java.util.List;

import com.wese.weseaddons.ussd.tree.TreeDataStructure;
import org.json.JSONObject;

public class WithdrawSavingsAction extends TreeDataStructure implements ExecuteMenuAction{



    @Override
    public String execute(Session session){

        List<SavingsAccount> savingsAccountList = session.getClientSavingsAccountList();

        String stringAmount = session.getOption();
        BigDecimal transactionAmount = new BigDecimal(stringAmount) ;
        int executeIndex = session.getExecuteIndex();

        SavingsAccount savingsAccount = savingsAccountList.get(executeIndex-1);

        BigDecimal balance = savingsAccount.getAccountBalance();

        int cmp = transactionAmount.compareTo(balance);

        if(cmp > 0){

            return String.format("You have insufficient balance ,savings account has %.2f\n%s\n",balance,Constants.backRequest) ;

        }

        SavingsWithdrawParser savingsWithdrawParser = new SavingsWithdrawParser(session ,transactionAmount);

        JSONObject httpRequestJsonObject = savingsWithdrawParser.getJsonObject();

        Long savingsAccountId = Long.valueOf(savingsAccount.getAccountNumber());

        StringBuilder stringBuilder = new StringBuilder("/savingsaccounts/");
        stringBuilder.append(savingsAccountId);
        stringBuilder.append("/transactions?command=withdrawal");
        String restUrl = stringBuilder.toString();

        HttpClientBridge httpClientBridge = new HttpClientBridge(REQUEST_METHOD.POST);

        httpClientBridge.setPostObject(httpRequestJsonObject);

        String httpResponse = httpClientBridge.makeRequest(restUrl);

        return String.format("You have withdrawn %.2f from your savings account sucessfully\n" ,transactionAmount);

    }
}
