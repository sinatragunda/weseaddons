package com.wese.weseaddons.bereaudechange.helper;

import com.wese.weseaddons.bereaudechange.enumerations.ROUNDING_RULE;
import com.wese.weseaddons.bereaudechange.enumerations.TRADE_TYPE;
import com.wese.weseaddons.bereaudechange.pojo.*;
import com.wese.weseaddons.bereaudechange.session.FxSession;

public class FxMarkUpHelper {

	private FxDeal fxDeal ;
	private String tenant ;
	private TRADE_TYPE tradeType ;
	private Settings settings ;
	private TradingRates tradingRates ;
	private CurrencyPair currencyPair ;
	private double positionRevaluation ;
	private double revaluationProfit ;


	public FxMarkUpHelper(FxDeal fxDeal , String tenant){

		try{
			this.tenant = tenant ;
			this.fxDeal = fxDeal ;
			this.tradeType = fxDeal.getTradeType();
			this.tradingRates = fxDeal.getTradingRates();
			this.currencyPair = tradingRates.getCurrencyPair();
			this.settings = FxSession.getInstance().getSettings(tenant);
		}

		catch (Exception e){
			e.printStackTrace();
			return ;
		}

	}


	public FxMarkUp profitAndLoss(){
	    
	    FxMarkUp fxMarkUp = null ;

	    try {

		    double revalRate = revalRate();
		    double baseAmount = fxDeal.getBlotter().getNetBaseAmount();
		    double quoteAmount = fxDeal.getBlotter().getQuoteAmount();

		    positionRevaluation = TradingAlgorithm.roundValue(ROUNDING_RULE.ROUND_UP, calculateForex(quoteAmount, revalRate));
		    revaluationProfit =  TradingAlgorithm.roundValue(ROUNDING_RULE.ROUND_UP ,positionRevaluation - baseAmount);

		    FxMarkUpHelperEx fxMarkUpHelperEx = new FxMarkUpHelperEx(fxDeal ,settings ,tenant);
		    FxEquivalent fxEquivalent = fxMarkUpHelperEx.calculateProfit(revaluationProfit);

		    double fcy = TradingAlgorithm.roundValue(ROUNDING_RULE.ROUND_UP , fxEquivalent.getEquivalantAmount()) ;
		    double fcyLocal = TradingAlgorithm.roundValue(ROUNDING_RULE.ROUND_UP , fxEquivalent.getLocalAmount());
		    fxMarkUp = new FxMarkUp(fxDeal ,revalRate,positionRevaluation ,revaluationProfit ,fcy ,fcyLocal);

	    }
	    
	    catch (Exception e) {
                // TODO: handle exception
            }
	    
	    return fxMarkUp;
		
	}

	public double revalRate(){

		double markUp = currencyPair.getMarkUp();
		double rate = tradingRates.getRate();
		double change = 0 ;
		if(markUp != 0) {
		    
		    change = (markUp / 100) * rate ; 
		}

		return (rate - change);
	}

	public double calculateForex(double baseAmount ,double revalRate){


		ForexCalculator forexCalculator = new ForexCalculator(currencyPair , tenant);
		ForexDealSums forexDealSums = forexCalculator.calculateForex(baseAmount ,revalRate ,tradeType ,false);
		return forexDealSums.getNetBaseAmount();

	}

	public void exchangeDeal(FxDeal fxDeal){



	}
}