package com.wese.weseaddons.bereaudechange.helper;

import com.wese.weseaddons.bereaudechange.enumerations.TRADE_TYPE;
import com.wese.weseaddons.bereaudechange.pojo.FxDeal;
import com.wese.weseaddons.bereaudechange.pojo.FxEquivalent;
import com.wese.weseaddons.bereaudechange.pojo.Settings;
import com.wese.weseaddons.bereaudechange.pojo.TradingRates;

public class FxMarkUpHelperEx {

    private String tenant ;
    private FxDeal fxDeal ;
    private Settings settings ;
    private TradingRates conversionRate ;
    private ForexCalculator forexCalculator ;


    public FxMarkUpHelperEx(FxDeal fxDeal ,Settings settings ,String tenant){
        this.fxDeal = fxDeal ;
        this.settings =settings ;
        this.tenant =tenant ;
        this.conversionRate = FxConversionHelperEx.conversionTradingRates(fxDeal ,tenant);
        this.forexCalculator = new ForexCalculator(fxDeal.getTradingRates().getCurrencyPair() ,tenant);

    }

    public FxEquivalent calculateProfit(double positionRevaluation){

        FxConversionHelperEx.COMMON_CURRENCIES commonCurrencies = FxConversionHelperEx.lookForCommonCurrencies(fxDeal.getTradingRates() ,settings);
        FxEquivalent fxEquivalent = null ;
        switch(commonCurrencies){

            case BASE:
                fxEquivalent = directDeal(positionRevaluation ,true);
                break;
            case BASE_IS_LOCAL:
                fxEquivalent = directDeal(positionRevaluation ,false);
                break;
            case QUOTE_IS_BASE:
                 fxEquivalent = conversionDeal(positionRevaluation ,true);
                 break;
            case QUOTE_IS_LOCAL:
                fxEquivalent = conversionDeal(positionRevaluation ,false);
                break;

        }

        return fxEquivalent ;

    }

    public FxEquivalent directDeal(double positionRevaluation ,boolean isBase){

        ForexDealSums forexDealSums = null ;
        FxEquivalent fxEquivalent = new FxEquivalent();

        if(isBase){

            forexDealSums = forexCalculator.calculateForex(positionRevaluation ,conversionRate.getSellRate(), TRADE_TYPE.SELL ,fxDeal.isIncludeCharges());
            fxEquivalent.setEquivalantAmount(positionRevaluation);
            fxEquivalent.setLocalAmount(forexDealSums.getNetBaseAmount());

            return fxEquivalent ;

        }

        forexDealSums = forexCalculator.calculateForex(positionRevaluation ,conversionRate.getRate(), TRADE_TYPE.SELL ,fxDeal.isIncludeCharges());
        fxEquivalent.setEquivalantAmount(forexDealSums.getNetBaseAmount());
        fxEquivalent.setLocalAmount(positionRevaluation);

        return fxEquivalent ;

    }


    public FxEquivalent conversionDeal(double positionRevaluation ,boolean isLocalCurrencyBase){

        if(isLocalCurrencyBase){

            return  conversionDealEx(positionRevaluation);

        }

        //// buy back the local currency using the profit from the deal

        ForexDealSums forexDealSums = forexCalculator.calculateForex(positionRevaluation ,fxDeal.getTradingRates().getRate() ,TRADE_TYPE.SELL ,fxDeal.isIncludeCharges());

        double newPositionRevaluation = forexDealSums.getNetBaseAmount() ;

        return  conversionDealEx(newPositionRevaluation);


    }

    public FxEquivalent conversionDealEx(double positionRevaluation){

        ForexCalculator forexCalculator = new ForexCalculator(settings.getCurrencyPair() ,tenant);

        FxEquivalent fxEquivalent = new FxEquivalent();

        ForexDealSums forexDealSums = forexCalculator.calculateForex(positionRevaluation ,conversionRate.getRate(), TRADE_TYPE.BUY ,fxDeal.isIncludeCharges());
        fxEquivalent.setEquivalantAmount(forexDealSums.getNetBaseAmount());
        fxEquivalent.setLocalAmount(positionRevaluation);

        return fxEquivalent ;

    }
}
