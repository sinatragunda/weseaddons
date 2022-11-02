package com.wese.weseaddons.bereaudechange.profitandloss;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.dao.TradingRatesDAO;
import com.wese.weseaddons.bereaudechange.pojo.CurrencyPair;
import com.wese.weseaddons.bereaudechange.pojo.FxDeal;
import com.wese.weseaddons.bereaudechange.pojo.Settings;
import com.wese.weseaddons.bereaudechange.pojo.TradingRates;
import com.wese.weseaddons.bereaudechange.session.FxSession;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.helper.TimeHelper;

import java.util.List;

public class FxDifferentialRatesMethod {


    private String tenant ;
    private FxDeal fxDeal ;
    private CurrencyPair currencyPair ;
    private Settings settings ;
    private FxOpeningClosingRateDiffHelper fxOpeningClosingRateDiffHelper ;


    public FxDifferentialRatesMethod(FxDeal fxDeal ,String tenant){

        this.fxDeal = fxDeal ;
        this.tenant = tenant ;
        this.currencyPair = fxDeal.getTradingRates().getCurrencyPair();
        this.fxOpeningClosingRateDiffHelper = new FxOpeningClosingRateDiffHelper(fxDeal ,tenant);

        init();
    }


    public void init(){

        try{
            settings = FxSession.getInstance().getSettings(tenant) ;
        }

        catch (NullPointerException n){
            n.printStackTrace();
        }
    }


    public ObjectNode getProfitNode(){

        double tradePrice = 0 ;
        String action = null;
        String tick = null ;

        switch (fxDeal.getTradeType()){
            case SELL:

                tradePrice = fxDeal.getTradingRates().getSellRate();
                action = "Sell/Short";
                break;
            case BUY:
                tradePrice = fxDeal.getTradingRates().getRate();
                action = "Buy/Long";
                break;
        }

        if(settings==null){

            return Helper.statusNodes(false).put("message","Accounting currency is invalid ,not registered in settings");

        }

        TradingRatesDAO tradingRatesDAO = new TradingRatesDAO(tenant);
        List<TradingRates> tradingRatesList = tradingRatesDAO.findRatesWhereCurrencyPairId(settings.getCurrencyPair().getId());

        TradingRates tradingRates = tradingRatesList.get(tradingRatesList.size()-1);


        double profit = fxOpeningClosingRateDiffHelper.getProfit(fxDeal ,tradingRates.getRate());

        if(profit ==-1){

            return Helper.statusNodes(false).put("message" ,"Transaction cant be yet calculated for profit at this current moment");

        }

        String date = TimeHelper.timestampToStringWithTimeForSecond(fxDeal.getDate());



        ObjectNode objectNode = Helper.createObjectNode();
        objectNode.put("fxDealId" ,fxDeal.getId());
        objectNode.put("fxDealDate",date);
        objectNode.put("accountCurrency",settings.getAccountingCurrency().getName());
        objectNode.put("currencyPair",currencyPair.getTick());
        objectNode.put("baseRate" ,tradingRates.getRate());
        objectNode.put("baseCurrency",settings.getCurrencyPair().getTick());
        objectNode.put("currencyPairPrice" ,settings.getCurrencyPair().getRate());
        objectNode.put("tradePrice",tradePrice);
        objectNode.put("action" ,action);
        objectNode.put("amount" ,fxDeal.getBlotter().getNetQuoteAmount());
        objectNode.put("openingRate" ,fxOpeningClosingRateDiffHelper.getOpeningRate());
        objectNode.put("closingRate",fxOpeningClosingRateDiffHelper.getClosingRate());
        objectNode.put("profit" ,String.format("%.3f %s",profit ,settings.getAccountingCurrency().getName()));
        objectNode.put("profitPosition" ,"Profit");

        if(profit < 0){
            objectNode.put("profitPosition" ,"Loss");
        }

        if(profit==0){
            objectNode.put("profitPosition" ,"Equilibrum");
        }

        System.err.println(objectNode);

        return objectNode ;

    }

}
