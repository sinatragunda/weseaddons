package com.wese.weseaddons.bereaudechange.helper;

import com.wese.weseaddons.bereaudechange.enumerations.TRADE_TYPE;
import com.wese.weseaddons.bereaudechange.enumerations.T_ACCOUNT;
import com.wese.weseaddons.bereaudechange.pojo.CurrencyPair;
import com.wese.weseaddons.bereaudechange.pojo.MoneyAccount;

public class ForexCalculator {

    private TradingAlgorithm tradingAlgorithm ;
    private MoneyAccount baseMoneyAccount ;
    private MoneyAccount quoteMoneyAccount ;
    private CurrencyPair currencyPair ;
    private String tenant ;

    public ForexCalculator(CurrencyPair currencyPair ,String tenant) {

        this.tenant = tenant ;
        this.currencyPair =currencyPair ;
        init();
    }

    public void init(){

        tradingAlgorithm = new TradingAlgorithm(tenant);

    }

    public ForexDealSums calculateForex(double quoteAmount , double rate , TRADE_TYPE tradeType , boolean includeCharges){

        double baseAmount = 0 ;

        ForexDealSums forexDealSums = new ForexDealSums(currencyPair);

        switch (tradeType){
            case BUY:
                baseAmount = quoteAmount / rate ;
                baseMoneyAccount = currencyPair.getBaseCurrencyMoneyAccount();
                quoteMoneyAccount = currencyPair.getQuoteCurrencyMoneyAccount();
                break;
            case SELL:
                baseAmount =  quoteAmount * rate ;
                baseMoneyAccount = currencyPair.getQuoteCurrencyMoneyAccount();
                quoteMoneyAccount = currencyPair.getBaseCurrencyMoneyAccount();
                break;
        }


        forexDealSums.setQuoteAmount(quoteAmount);

        if(currencyPair==null){

        }

        //rate = tradingAlgorithm.tradingRate(currencyPair ,rate);

        double quoteCharges = 0 ;

        if(includeCharges){

            quoteCharges = tradingAlgorithm.deductCharges(quoteMoneyAccount , T_ACCOUNT.DEPOSIT ,quoteAmount);
        
        }

      
        forexDealSums.setQuoteCharges(quoteCharges);

        double netQuoteAmount = quoteAmount + quoteCharges ;
        forexDealSums.setNetQuoteAmount(netQuoteAmount);

      
        forexDealSums.setBaseAmount(baseAmount);

        double baseCharges = 0 ;

        if(includeCharges){
            
            baseCharges = tradingAlgorithm.deductCharges(baseMoneyAccount , T_ACCOUNT.WITHDRAW ,baseAmount); 
        }

        forexDealSums.setBaseCharges(baseCharges);

        double netBaseAmount = baseAmount - baseCharges;
        forexDealSums.setNetBaseAmount(netBaseAmount);

        return forexDealSums ;

    }
}
