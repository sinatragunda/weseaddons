package com.wese.weseaddons.bereaudechange.helper;

import com.wese.weseaddons.bereaudechange.enumerations.ROUNDING_RULE;
import com.wese.weseaddons.bereaudechange.enumerations.TRADE_TYPE;
import com.wese.weseaddons.bereaudechange.pojo.CurrencyPair;
import com.wese.weseaddons.bereaudechange.pojo.FxDeal;
import com.wese.weseaddons.bereaudechange.pojo.TradingRates;
import com.wese.weseaddons.bereaudechange.pojo.Revaluation ;

import java.io.IOException;

public class RevaluationHelper{

	private FxDeal fxDeal ;
	private String tenant ;
	private TRADE_TYPE tradeType ;
	private TradingRates tradingRates ;
	private CurrencyPair currencyPair ;
	private double positionRevaluation ;
	private double revaluationProfit ;


	public RevaluationHelper(FxDeal fxDeal ,String tenant){

		try{
			this.tenant = tenant ;
			this.fxDeal = fxDeal ;
			this.tradeType = fxDeal.getTradeType();
			this.tradingRates = fxDeal.getTradingRates();
			this.currencyPair = tradingRates.getCurrencyPair();
		}

		catch (Exception e){
			e.printStackTrace();
			return ;
		}

	}


	public Revaluation profitAndLoss(){
	    
	    Revaluation revaluation = null ;
	        
	    try {
		    double revalRate = revalRate();
		    double baseAmount = fxDeal.getBlotter().getNetBaseAmount();
		    double quoteAmount = fxDeal.getBlotter().getQuoteAmount();
		    positionRevaluation = TradingAlgorithm.roundValue(ROUNDING_RULE.NATURAL, calculateForex(quoteAmount, revalRate));
		    revaluationProfit = TradingAlgorithm.roundValue(ROUNDING_RULE.NATURAL, (positionRevaluation - baseAmount));
		    double fcy =  TradingAlgorithm.roundValue(ROUNDING_RULE.NATURAL, revaluationProfit * revalRate) ;

		    revaluation = new Revaluation(fxDeal ,revalRate,positionRevaluation ,revaluationProfit ,fcy);
	    }
	    
	    catch (Exception e) {
                // TODO: handle exception
            }
	    
	    return revaluation ;
		
	}

	public double revalRate(){

		double markUp = currencyPair.getMarkUp();
		double rate = tradingRates.getRate();
		double change = 0 ;
		if(markUp != 0) {
		    
		    change = (markUp / 100) * rate ; 
		}
		
		System.out.print("Rate is "+rate+" and reval rate are "+(rate -change));
		
		return (rate - change);
	}

	public double calculateForex(double baseAmount ,double revalRate){
	    
	        

		ForexCalculator forexCalculator = new ForexCalculator(currencyPair , tenant);
		ForexDealSums forexDealSums = forexCalculator.calculateForex(baseAmount ,revalRate ,tradeType ,false);
		return forexDealSums.getNetBaseAmount();

	}
}