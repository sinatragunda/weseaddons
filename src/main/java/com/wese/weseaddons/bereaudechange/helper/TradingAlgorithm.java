package com.wese.weseaddons.bereaudechange.helper;

import com.wese.weseaddons.bereaudechange.dao.MoneyAccountChargesDAO;
import com.wese.weseaddons.bereaudechange.enumerations.CHARGE_CRITERIA;
import com.wese.weseaddons.bereaudechange.enumerations.ROUNDING_RULE;
import com.wese.weseaddons.bereaudechange.enumerations.TRADE_TYPE;
import com.wese.weseaddons.bereaudechange.enumerations.T_ACCOUNT;
import com.wese.weseaddons.bereaudechange.pojo.Charges;
import com.wese.weseaddons.bereaudechange.pojo.CurrencyPair;
import com.wese.weseaddons.bereaudechange.pojo.FxDeal;
import com.wese.weseaddons.bereaudechange.pojo.MoneyAccount;
import com.wese.weseaddons.bereaudechange.pojo.MoneyAccountChargesRM;
import com.wese.weseaddons.bereaudechange.pojo.TradingRates;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class TradingAlgorithm {

    private String tenant ;
    private MoneyAccount baseMoneyAccount ;
    private MoneyAccount quoteMoneyAccount ;

    public TradingAlgorithm(String tenant){

        this.tenant = tenant ;
    }

  
    public TradingAlgorithm(MoneyAccount baseMoneyAccount, MoneyAccount quoteMoneyAccount){

        this.baseMoneyAccount = baseMoneyAccount;
        this.quoteMoneyAccount = quoteMoneyAccount;

    }

    public List<Charges> getActiveCharges(MoneyAccount moneyAccount){

        List<Charges> activeChargesList = new ArrayList<>();

        MoneyAccountChargesDAO moneyAccountChargesDAO = new MoneyAccountChargesDAO(tenant);

        if(moneyAccountChargesDAO==null){

            return activeChargesList ;
        }

        List<MoneyAccountChargesRM> moneyAccountChargesList = moneyAccountChargesDAO.findWhere(moneyAccount.getId());

        for(MoneyAccountChargesRM m : moneyAccountChargesList){

            if(m.isActive()){

                activeChargesList.add(m.getCharges());
            }
        }

        return activeChargesList ;

    }


    public double deductCharges(MoneyAccount moneyAccount , T_ACCOUNT tAccount ,double quoteAmount){

        ///remember you always buying base account
        List<Charges> chargesList = getActiveCharges(moneyAccount) ;
        List<Double> doubleList = new ArrayList<>();
        if(chargesList.isEmpty()){
            return 0 ;
        }

        Iterator<Charges> chargesIterator = chargesList.iterator();
        while (chargesIterator.hasNext()){
            
            Charges charges = chargesIterator.next();
            T_ACCOUNT t = charges.gettAccount();

            if(t==tAccount){
                
                Double val = criteriaBasedCharge(charges ,quoteAmount);
                doubleList.add(val);

            }
        }

        if(doubleList.isEmpty()){
            return 0 ;
        }

        double val = doubleList.stream().mapToDouble(Double::doubleValue).sum() ;
        return val ;
    }

    public Double criteriaBasedCharge(Charges charges, double amount){

        CHARGE_CRITERIA chargeCriteria = charges.getChargeCriteria();
        Double chargeAmount =new Double(0);
        switch (chargeCriteria){
            case FIXED_AMOUNT:
                chargeAmount = charges.getAmount();
                break;

            case THRESHOLD_AMOUNT:
                chargeAmount = amount;
                break;

            case PERCENTAGE_OF_AMOUNT:
                chargeAmount = amount * (charges.getAmount() / 100);
                break;
        }

        return chargeAmount ;
    }


    public double tradingRate(CurrencyPair currencyPair ,double rate){

       double rateIncludingMarkUp = rate ;


//        if(currencyPair.getMarkUp()!= 0){
//
//            rateIncludingMarkUp += (currencyPair.getMarkUp() / 100) * 1.65 = 1.85 ;
//
//        }
//
//
//        double newRate = roundValue(currencyPair.getRoundingRule() ,rateIncludingMarkUp);
//        return newRate ;

        return rate ;

    }
    
    public static double getRateWithMarkUp(FxDeal fxDeal) {
    	
    	TradingRates tradingRates = fxDeal.getTradingRates();
    	CurrencyPair currencyPair = tradingRates.getCurrencyPair();
    	
        double rateIncludingMarkUp = tradingRates.getRate() ;
        
        if(fxDeal.getTradeType()==TRADE_TYPE.SELL) {
        	rateIncludingMarkUp = tradingRates.getSellRate();
        }


	      if(currencyPair.getMarkUp()!= 0){
	
	          rateIncludingMarkUp += (currencyPair.getMarkUp() / 100) * rateIncludingMarkUp ;
	
	      }
	
	      return rateIncludingMarkUp ;
	    	
    }

    public static double roundValue(ROUNDING_RULE roundingRule ,double amount){

        int scale = 2 ;

        switch (roundingRule){
            case ROUND_UP:
                amount = roundValue(amount,RoundingMode.UP ,scale);
                break;
            case ROUND_DOWN:
                amount = roundValue(amount ,RoundingMode.DOWN ,scale);
                break;
            case NATURAL:
                amount = roundValue(amount ,RoundingMode.HALF_UP ,0);
                break;

            default:
                amount = roundValue(amount ,RoundingMode.HALF_EVEN,scale);
                break ;
        }

        return amount ;
    }

    public static Double roundValue(double value , RoundingMode roundingMode ,int scale){

        BigDecimal bigDecimal =new BigDecimal(value);

        try{
            bigDecimal = new BigDecimal(value).setScale(scale ,roundingMode);

        }

        catch (NumberFormatException n){
            n.printStackTrace();
        }

        return bigDecimal.doubleValue();
    }

    public static Double roundRate(double value){

        BigDecimal bigDecimal = new BigDecimal(value).setScale(4 ,RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }
}
