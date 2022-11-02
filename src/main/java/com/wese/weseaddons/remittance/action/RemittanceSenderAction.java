package com.wese.weseaddons.remittance.action;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.dao.FxCashierDAO;
import com.wese.weseaddons.bereaudechange.dao.StandardCurrencyDAO;
import com.wese.weseaddons.bereaudechange.fxrequest.OrganisationCurrencyRequest;
import com.wese.weseaddons.bereaudechange.helper.ForeignKeyHelper;
import com.wese.weseaddons.bereaudechange.pojo.FxCashier;
import com.wese.weseaddons.bereaudechange.pojo.StandardCurrency;
import com.wese.weseaddons.helper.Constants;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.helper.TimeHelper;
import com.wese.weseaddons.remittance.dao.RemittanceTransactionDAO;
import com.wese.weseaddons.remittance.enumerations.REMITTANCE_TRANSACTION_STATE;
import com.wese.weseaddons.remittance.helper.RemittanceFundsHelper;
import com.wese.weseaddons.remittance.helper.RemittanceFundsKey;
import com.wese.weseaddons.remittance.helper.RemittanceSettleHelper;
import com.wese.weseaddons.remittance.pojo.RemittanceTransaction;

public class RemittanceSenderAction {


    public static ObjectNode send(RemittanceTransaction remittanceTransaction ,String tenant){

        double amount = remittanceTransaction.getAmount();
        double commission = remittanceTransaction.getCommission();

        StandardCurrency standardCurrency = (StandardCurrency) ForeignKeyHelper.get(new StandardCurrencyDAO(tenant) ,remittanceTransaction.getStandardCurrency().getId());


        if(standardCurrency !=null){

            remittanceTransaction.setStandardCurrency(standardCurrency);
            OrganisationCurrencyRequest.staticCreate(standardCurrency ,tenant);

        }

        String fundsKey = RemittanceFundsKey.generateKey(standardCurrency);
        remittanceTransaction.setFundsKey(fundsKey);

 
        long timeNow = TimeHelper.epochNow();
        remittanceTransaction.setTransactionDate(timeNow);
        remittanceTransaction.setRemittanceTransactionState(REMITTANCE_TRANSACTION_STATE.OPENED.ordinal());

        RemittanceFundsHelper remittanceFundsHelper = new RemittanceFundsHelper(amount ,commission);

        double transactionCommission = remittanceFundsHelper.getCommission();
        double transanctionAmount = remittanceFundsHelper.getAmount();

        remittanceTransaction.setAmount(transanctionAmount);
        remittanceTransaction.setCommission(transactionCommission);
        
        FxCashier fxCashier = remittanceTransaction.getFxCashierSender() ;

        long appUserId = fxCashier.getId() ;

        FxCashierDAO fxCashierDAO = new FxCashierDAO(tenant);

        fxCashier = fxCashierDAO.findWhere(appUserId ,"app_user_id");

        remittanceTransaction.setFxCashierSender(fxCashier);

        RemittanceSettleHelper remittanceSettleHelper = new RemittanceSettleHelper(tenant);

        boolean status = remittanceSettleHelper.settle(remittanceTransaction ,fxCashier ,true);

        if(!status){
            return Helper.statusNodes(status).put("message" , Constants.failedTransaction);
        }


        if(remittanceTransaction ==null){
            System.err.println("This is null son");
        }

        RemittanceTransactionDAO remittanceTransactionDAO = new RemittanceTransactionDAO(tenant);
        return remittanceTransactionDAO.create(remittanceTransaction);

    }

}
