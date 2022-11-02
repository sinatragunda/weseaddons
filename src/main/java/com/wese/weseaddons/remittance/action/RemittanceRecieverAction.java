package com.wese.weseaddons.remittance.action;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.dao.FxCashierDAO;
import com.wese.weseaddons.bereaudechange.dao.StandardCurrencyDAO;
import com.wese.weseaddons.bereaudechange.helper.ForeignKeyHelper;
import com.wese.weseaddons.bereaudechange.pojo.FxCashier;
import com.wese.weseaddons.bereaudechange.pojo.StandardCurrency;
import com.wese.weseaddons.helper.Constants;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.remittance.dao.RemittanceTransactionDAO;
import com.wese.weseaddons.remittance.helper.RemittanceDisburse;

import com.wese.weseaddons.remittance.pojo.RemittanceTransaction;

public class RemittanceRecieverAction {

    public static ObjectNode recieve(RemittanceTransaction remittanceTransaction,String tenant){

        long appUserId = remittanceTransaction.getFxCashierReciever().getId();
        FxCashierDAO fxCashierDAO = new FxCashierDAO(tenant);

        FxCashier fxCashier = fxCashierDAO.findWhere(appUserId ,"app_user_id");

        remittanceTransaction = getObject(remittanceTransaction.getFundsKey(),tenant);
        remittanceTransaction.setFxCashierReciever(fxCashier);

        return RemittanceDisburse.disburse(remittanceTransaction ,fxCashier ,tenant);

    }


    public static ObjectNode verify(String key ,String tenant){

        if(key==null){
            return Helper.statusNodes(false).put("message" ,"Empty funds key");
        }

        RemittanceTransaction remittanceTransaction = getObject(key ,tenant);

        if(remittanceTransaction==null){
            return Helper.statusNodes(false).put("message" ,"Invalid remittance key");
        }
        if(!remittanceTransaction.getFundsKey().equalsIgnoreCase(key)){
            return Helper.statusNodes(false).put("message" ,"Mismatching remittance key");
        }

        RemittanceTransactionAction remittanceTransactionAction = new RemittanceTransactionAction(tenant);
        return remittanceTransactionAction.createNode(remittanceTransaction).put("status",true);

    }

    public static RemittanceTransaction getObject(String key ,String tenant){

        RemittanceTransactionDAO remittanceTransactionDAO = new RemittanceTransactionDAO(tenant);
        RemittanceTransaction remittanceTransaction = remittanceTransactionDAO.findWhereKey(key);
        return remittanceTransaction;
    }

}
