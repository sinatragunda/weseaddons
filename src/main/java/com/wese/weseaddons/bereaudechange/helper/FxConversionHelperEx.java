package com.wese.weseaddons.bereaudechange.helper;

import com.wese.weseaddons.bereaudechange.dao.TradingRatesDAO;
import com.wese.weseaddons.bereaudechange.pojo.*;
import com.wese.weseaddons.bereaudechange.session.FxSession;

import java.util.List;

public class FxConversionHelperEx {

    public enum COMMON_CURRENCIES{
        NON,
        BASE ,
        QUOTE_IS_BASE ,
        BASE_IS_LOCAL ,
        QUOTE_IS_LOCAL
    }

    public static COMMON_CURRENCIES lookForCommonCurrencies(TradingRates tradingRates , Settings settings){

        CurrencyPair currencyPair = tradingRates.getCurrencyPair();
        StandardCurrency baseCurrency = currencyPair.getBaseCurrencyMoneyAccount().getTradingCurrency().getStandardCurrency();
        StandardCurrency quoteCurrency = currencyPair.getQuoteCurrencyMoneyAccount().getTradingCurrency().getStandardCurrency();

        if(baseCurrency.getId()==settings.getAccountingCurrency().getId()){
            System.err.println("Base is base");
            return COMMON_CURRENCIES.BASE;
            ///no need to convert just store the base as it is
        }

        if(quoteCurrency.getId()==settings.getAccountingCurrency().getId()){
            System.err.println("Quote is base");
            return COMMON_CURRENCIES.QUOTE_IS_BASE ;
            /// no need to convert just store quote amount as it is
        }

        if(baseCurrency.getId()==settings.getLocalCurrency().getId()){
            return COMMON_CURRENCIES.BASE_IS_LOCAL ;
            //// convert straight ,use this base as quote amount
        }
        if(quoteCurrency.getId()==settings.getLocalCurrency().getId()){
            return COMMON_CURRENCIES.QUOTE_IS_LOCAL ;
            //// convert straight us quote as quote
        }

        return COMMON_CURRENCIES.NON ;

    }
    public static TradingRates conversionTradingRates(FxDeal fxDeal ,String tenant){

        Settings settings = FxSession.getInstance().getSettings(tenant);

        long id = fxDeal.getTradingRates().getId();
        TradingRatesDAO tradingRatesDAO = new TradingRatesDAO(tenant);
        TradingRates tradingRates = tradingRatesDAO.find(id);
        CurrencyPair currencyPair = settings.getCurrencyPair();
        List<TradingRates> tradingRatesList = tradingRatesDAO.findWhere(currencyPair.getId() ,"currency_pair_id");

        int index = tradingRatesList.size()-1;
        return tradingRatesList.get(index);

    }


}
