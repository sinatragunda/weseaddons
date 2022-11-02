package com.wese.weseaddons.bereaudechange.helper;

import com.wese.weseaddons.bereaudechange.dao.CurrencyPairDAO;
import com.wese.weseaddons.bereaudechange.pojo.CurrencyPair;
import com.wese.weseaddons.bereaudechange.pojo.FxDeal;

public class FxValidationHelper {


    public static boolean clientPairExists(CurrencyPairDAO currencyPairDAO ,CurrencyPair currencyPair){

        long baseCurrencyId = currencyPair.getBaseCurrencyMoneyAccount().getId();
        long quoteCurrencyId = currencyPair.getQuoteCurrencyMoneyAccount().getId();
        CurrencyPair tempCurrencyPair = currencyPairDAO.findWherePairMatch(baseCurrencyId ,quoteCurrencyId);

        if(tempCurrencyPair==null){
            return false ;
        }

        return true ;

    }

    public static boolean fxDealValiation(FxDeal fxDeal){

        if(fxDeal.getPurpose()==null){
            return false ;
        }

        return true ;
    }

}
