package com.wese.weseaddons.remittance.helper;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.pojo.FxCashier;
import com.wese.weseaddons.helper.Constants;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.remittance.dao.RemittanceTransactionDAO;
import com.wese.weseaddons.remittance.enumerations.REMITTANCE_TRANSACTION_STATE;
import com.wese.weseaddons.remittance.pojo.RemittanceTransaction;

public class RemittanceDisburse {

    public static ObjectNode disburse(RemittanceTransaction remittanceTransaction , FxCashier fxCashier, String tenant){

        RemittanceSettleHelper remittanceSettleHelper = null ;

        remittanceSettleHelper = new RemittanceSettleHelper(tenant);

        if(!remittanceSettleHelper.settle(remittanceTransaction ,fxCashier ,false)){

            return Helper.statusNodes(false).put("message" , Constants.insufficientFunds) ;
        }

        RemittanceTransactionDAO remittanceTransactionDAO = new RemittanceTransactionDAO(tenant);
        remittanceTransactionDAO.closeTransaction(remittanceTransaction);
        

        return Helper.statusNodes(true);

    }



}
