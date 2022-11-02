package com.wese.weseaddons.bereaudechange.helper;

import com.wese.weseaddons.bereaudechange.dao.FxDailyTrackerDAO;
import com.wese.weseaddons.bereaudechange.dao.FxEquivalentDAO;
import com.wese.weseaddons.bereaudechange.dao.TradingRatesDAO;
import com.wese.weseaddons.bereaudechange.enumerations.TRADE_TYPE;
import com.wese.weseaddons.bereaudechange.pojo.*;

import java.util.List;

public class FxConversionHelper {


    private Settings settings ;
    private String tenant ;
    private FxDeal fxDeal ;
    private FxEquivalentDAO fxEquivalentDAO;
    private TradingRates tradingRates ;
    private TradingRates conversionTradingRates ;
    private TradingRates orignalTradingRates ;
    private List<TradingRates> tradingRatesList;
    private TradingRatesDAO tradingRatesDAO ;
    private FxDailyTrackerDAO fxDailyTrackerDAO ;
    private FxEquivalent fxEquivalant ;

    private double afterTransactionAmount =0;
    private CurrencyPair currencyPair ;


    public FxConversionHelper(Settings settings ,FxDeal fxDeal ,String tenant){
        this.settings = settings ;
        this.tenant = tenant ;
        this.fxDeal = fxDeal ;
       	this.fxDailyTrackerDAO = new FxDailyTrackerDAO(tenant);
       	init();

    }

    public void init(){
        try{

            long id = fxDeal.getTradingRates().getId();
            this.tradingRatesDAO = new TradingRatesDAO(tenant);
            this.tradingRates = tradingRatesDAO.find(id);
            this.currencyPair = settings.getCurrencyPair();
            this.tradingRatesList = tradingRatesDAO.findWhere(currencyPair.getId() ,"currency_pair_id");

            int index = tradingRatesList.size()-1;
            this.conversionTradingRates = tradingRatesList.get(index);
            this.orignalTradingRates = fxDeal.getTradingRates();

            convertCurrency(fxDeal);

        }

        catch (Exception exception){
            exception.printStackTrace();
        }


    }


    public boolean dailyLimitReached(){


        if(fxEquivalant==null){
            return false ;
        }

        double limit = settings.getDailyLimit();
        FxDailyTracker fxDailyTracker = fxDailyTrackerDAO.findWhereDayIsToday();

        if(fxDailyTracker==null){

        	fxDailyTracker = fxDailyTrackerDAO.createWhereDayIsToday();
        }

        double amount = fxDailyTracker.getAmount();

        if(amount > limit){
        	return true ;
        }


        this.afterTransactionAmount = amount + fxEquivalant.getEquivalantAmount();

        if(afterTransactionAmount > limit){

        	return true ;
        }


        return false ;

    }

    public Runnable convertCurrency(long id){
       
        Runnable runnable = ()->{

            fxEquivalant.getFxDeal().setId(id);
        	createRecord(fxEquivalant);
        	updateDailyTracker(afterTransactionAmount);
        };

        return runnable ;

    }

    public FxEquivalent convertCurrency(FxDeal fxDeal){

        System.out.println("FxConversion  class now entered");

        FxConversionHelperEx.COMMON_CURRENCIES commonCurrencies = FxConversionHelperEx.lookForCommonCurrencies(tradingRates ,settings);

        FxEquivalent fxEquivalant = null ;
        Blotter blotter = fxDeal.getBlotter();

        switch (commonCurrencies){
            case NON:
                break;
            case BASE:
                fxEquivalant = toDirectCurrency(fxDeal ,blotter.getNetBaseAmount());
                break;
            case BASE_IS_LOCAL:
                fxEquivalant = toDirectCurrency(fxDeal ,blotter.getNetQuoteAmount());
                break;
            case QUOTE_IS_LOCAL:
                fxEquivalant = toBaseCurrency(fxDeal ,blotter.getQuoteAmount());
                break;
            case QUOTE_IS_BASE:
                fxEquivalant = toBaseCurrency(fxDeal ,blotter.getBaseAmount());
                break;
        }


        return fxEquivalant ;

    }


    public FxEquivalent toBaseCurrency(FxDeal fxDeal ,double quoteAmount){

        System.out.println("We pass here with quote amount of "+quoteAmount);
        System.out.println("Lets convert to local currency now ");
        System.out.print("Exchange rate here is "+conversionTradingRates.getRate());


        ForexCalculator forexCalculator = new ForexCalculator(currencyPair ,tenant);
        ForexDealSums forexDealSums = forexCalculator.calculateForex(quoteAmount ,conversionTradingRates.getRate() , TRADE_TYPE.BUY,false);


        this.fxEquivalant  = new FxEquivalent(fxDeal ,quoteAmount ,forexDealSums.getNetBaseAmount(),conversionTradingRates ,orignalTradingRates);

        return fxEquivalant ;

    }

    public FxEquivalent toDirectCurrency(FxDeal fxDeal ,double amount){

        this.fxEquivalant  = new FxEquivalent(fxDeal ,amount ,amount ,conversionTradingRates ,orignalTradingRates);
        return fxEquivalant ;

    }

    public void createRecord(FxEquivalent fxEquivalant){

        fxEquivalentDAO = new FxEquivalentDAO(tenant);
        fxEquivalentDAO.create(fxEquivalant);
    }

    public void updateDailyTracker(double amount){

    	fxDailyTrackerDAO.updateWhereDayIsToday(amount);
    }


    public FxEquivalent getFxEquivalant(){
        return this.fxEquivalant ;
    }

}
