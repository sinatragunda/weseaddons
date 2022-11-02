package com.wese.weseaddons.bereaudechange.helper;


import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.dao.TradingRatesDAO;
import com.wese.weseaddons.bereaudechange.enumerations.MARKET_TYPE;
import com.wese.weseaddons.bereaudechange.pojo.TradingRates;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.helper.TimeHelper;


import java.math.RoundingMode;
import java.util.List;

public class TradingRatesAnalyzer {

    private String tenant ;

    public TradingRatesAnalyzer(String s){
        this.tenant = s;
    }


    public ObjectNode analyse(long id){

        TradingRatesDAO tradingRatesDAO = new TradingRatesDAO(tenant);
        List<TradingRates> tradingRatesList = tradingRatesDAO.findWhere(id ,"currency_pair_id");

        TimestampComparator timestampComparator = new TimestampComparator();
        tradingRatesList.sort(timestampComparator);

        ArrayNode arrayNode = Helper.createArrayNode();

        if(tradingRatesList.isEmpty()){
            return Helper.statusNodes(false);
        }


        TradingRates tradingRates = tradingRatesList.get(0);

        boolean isLocalCurrency = tradingRates.getCurrencyPair().getMarketType()==MARKET_TYPE.LOCAL ? true : false ;

        ObjectNode objectNode1 = trendChange("hasGainedBuyRate","percentageChangeBuyRate",0,0);

        if(isLocalCurrency){

            ObjectNode sellNode = trendChange("hasGainedSellRate","percentageChangeSellRate",0,0);
            objectNode1.setAll(sellNode);
        }

        objectNode1.setAll(createNode(tradingRates));

        arrayNode.add(objectNode1);


        if(tradingRatesList.size() > 1){

            for(int i=0 ; i < tradingRatesList.size()-1 ;++i){

                TradingRates left = tradingRatesList.get(i);
                TradingRates right = tradingRatesList.get(i+1);

                ObjectNode objNode = trendChange("hasGainedBuyRate","percentageChangeBuyRate",left.getRate(),right.getRate());

                if(createNode(right)==null){
                    continue;
                }

                if(isLocalCurrency){
                    
                    ObjectNode sellNode = trendChange("hasGainedSellRate","percentageChangeSellRate",left.getSellRate(),right.getSellRate());
                    objNode.setAll(sellNode);
                }

                objNode.setAll(createNode(right));
                arrayNode.add(objNode);
            }
        }


        ObjectNode objectNode =Helper.createObjectNode();
        objectNode.put("pageItems",arrayNode);
        objectNode.put("status",true);
        objectNode.put("localCurrency" ,isLocalCurrency);
        objectNode.put("tick",tradingRates.getCurrencyPair().getTick());
        objectNode.put("baseCurrencyAccountId" ,tradingRates.getCurrencyPair().getBaseCurrencyMoneyAccount().getId());
        objectNode.put("baseCurrencyAccount" ,tradingRates.getCurrencyPair().getBaseCurrencyMoneyAccount().getName());
        objectNode.put("quoteCurrencyAccountId" ,tradingRates.getCurrencyPair().getQuoteCurrencyMoneyAccount().getId());
        objectNode.put("quoteCurrencyAccount" ,tradingRates.getCurrencyPair().getQuoteCurrencyMoneyAccount().getName());

        return objectNode;


    }

    public ObjectNode createNode(TradingRates tradingRates){

        if(tradingRates==null){
            return null ;
        }

        ObjectNode objectNode = Helper.createObjectNode();
        objectNode.put("rate" ,tradingRates.getRate());
        objectNode.put("sellRate",tradingRates.getSellRate());
        objectNode.put("openTime" , TimeHelper.timestampToString(tradingRates.getOpenTime()));
        objectNode.put("closeTime","Live Rate");
        if(tradingRates.getCloseTime() != 0){
            objectNode.put("closeTime" , TimeHelper.timestampToString(tradingRates.getCloseTime()));
        }

        return objectNode ;
    }

    public ObjectNode trendChange(String code ,String percentageCode, double orignal ,double change){

        ObjectNode objectNode = Helper.createObjectNode();

        objectNode.put(code ,true);
        if(orignal > change){
            objectNode.put(code,false);
        }

        double percentage = percentageChange(orignal ,change);
        objectNode.put(percentageCode ,percentage+"%");

        return objectNode;
    }

    public double percentageChange(double orignal ,double change){
        
        try {
            if(change==0){
                return 0;
            }
            
            double increase = change - orignal ;
            double percentage = (increase / orignal) * 100;

            return TradingAlgorithm.roundValue(percentage , RoundingMode.HALF_UP ,2);
        }
        
        catch (NumberFormatException e) {
            // TODO: handle exception
        }
        
        return 0 ;

    }
}
