package com.wese.weseaddons.bereaudechange.profitandloss;

import com.wese.weseaddons.bereaudechange.dao.CurrencyPairDAO;
import com.wese.weseaddons.bereaudechange.dao.TradingRatesDAO;
import com.wese.weseaddons.bereaudechange.helper.ForexCalculator;
import com.wese.weseaddons.bereaudechange.helper.ForexDealSums;
import com.wese.weseaddons.bereaudechange.pojo.CurrencyPair;
import com.wese.weseaddons.bereaudechange.pojo.FxDeal;
import com.wese.weseaddons.bereaudechange.pojo.TradingRates;
import com.wese.weseaddons.bereaudechange.session.FxSession;
import com.wese.weseaddons.bereaudechange.enumerations.TRADE_TYPE;
import javax.validation.constraints.Null;
import java.util.List;
import java.util.function.Predicate;

public class FxOpeningClosingRateDiffHelper{

    private String tenant ;
    private FxDeal fxDeal ;
    private double openingRate ;
    private double closingRate;
    private double baseAmount ;
    private double quoteAmount ;
    private ForexCalculator forexCalculator ;
    private CurrencyPair currencyPair ;


    private Predicate<TradingRates> hasClosedRatePredicate = (e)->{

        if(e.getCloseTime()!=0){
            return true;
        }

        return false ;

    };

    public FxOpeningClosingRateDiffHelper(FxDeal fxDeal ,String tenant){
        this.tenant = tenant ;
        this.fxDeal = fxDeal ;
        this.currencyPair = fxDeal.getTradingRates().getCurrencyPair();
        this.forexCalculator = new ForexCalculator(currencyPair ,tenant);

    }


    public double getProfit(FxDeal fxDeal ,double accountRate){

        long currencyPairId = currencyPair.getId();
        TradingRatesDAO tradingRatesDAO = new TradingRatesDAO(tenant);
        List<TradingRates> tradingRatesList = tradingRatesDAO.findRatesWhereCurrencyPairId(currencyPairId);


        if(!hasClosedRate(tradingRatesList)){
            return 0;
        }
        try{
            initFxDealValues(tradingRatesList);
        }
        catch (NullPointerException n){
            n.printStackTrace();
            return -1 ;
        }

        if(isDirectCurrency()){
            return directCurrencyProfit();
        }

        return calculateProfit(fxDeal ,tradingRatesList,accountRate);

    }


    public boolean hasClosedRate(List<TradingRates> tradingRatesList){

        long count = tradingRatesList.stream().filter(hasClosedRatePredicate).count();
        if(count > 0){
            return true ;
        }

        return false ;

    }

    public void initFxDealValues(List<TradingRates> tradingRatesList){

        this.openingRate = 0 ;
        this.closingRate = 0 ;
        this.baseAmount = 0 ;
        this.quoteAmount = 0 ;

        int closingRateIndex = closingRateIndex(tradingRatesList);

        if(closingRateIndex == -1){
            throw new NullPointerException();
        }

        if(closingRateIndex ==tradingRatesList.size()){

            --closingRateIndex ;
        }

        switch (fxDeal.getTradeType()){

            case BUY:
                openingRate = fxDeal.getTradingRates().getRate();
                closingRate = tradingRatesList.get(closingRateIndex).getRate();
                baseAmount = fxDeal.getBlotter().getNetBaseAmount();
                quoteAmount = fxDeal.getBlotter().getNetQuoteAmount();
                break;

            case SELL:
                quoteAmount = fxDeal.getBlotter().getNetBaseAmount();
                baseAmount = fxDeal.getBlotter().getNetQuoteAmount();
                openingRate = fxDeal.getTradingRates().getSellRate();
                closingRate = tradingRatesList.get(closingRateIndex).getSellRate();
                break;

        }
    }


    public int closingRateIndex(List<TradingRates> tradingRatesList){

        for(int i = 0 ; i < tradingRatesList.size() ;++i){
            if(fxDeal.getTradingRates().getId() == tradingRatesList.get(i).getId()){
                return  ++i ;
            }
        }

        return -1 ;
    }

    public double calculateProfit(FxDeal fxDeal ,List<TradingRates> tradingRatesList ,double accountRate){

        double rateDiff = closingRate - openingRate;


        if(rateDiff==-1){
            return -1;
        }


        double profit = (rateDiff) * (accountRate * baseAmount);
        return profit ;

    }

    public double directCurrencyProfit(){

        ForexDealSums forexDealSums = null;

        if(fxDeal.getTradeType()==TRADE_TYPE.SELL){
            
            forexDealSums = forexCalculator.calculateForex(quoteAmount ,closingRate,fxDeal.getTradeType() ,fxDeal.isIncludeCharges());
            return (baseAmount - forexDealSums.getNetQuoteAmount());

        }

        forexDealSums = forexCalculator.calculateForex(quoteAmount ,closingRate,fxDeal.getTradeType() ,fxDeal.isIncludeCharges());
        return (baseAmount - forexDealSums.getNetBaseAmount());

    }

    public boolean isDirectCurrency(){

        CurrencyPair tradingCurrencyPair = fxDeal.getTradingRates().getCurrencyPair();
        CurrencyPair tenantCurrencySetting = FxSession.getInstance().getSettings(tenant).getCurrencyPair();

        if(tradingCurrencyPair.getId()==tenantCurrencySetting.getId()){
            return true;
        }

        return false;

    }


    public double getOpeningRate() {
        return openingRate;
    }

    public void setOpeningRate(double openingRate) {
        this.openingRate = openingRate;
    }

    public double getClosingRate() {
        return closingRate;
    }

    public void setClosingRate(double closingRate) {
        this.closingRate = closingRate;
    }
}
