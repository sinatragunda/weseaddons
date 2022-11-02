package com.wese.weseaddons.bereaudechange.helper;

import com.wese.weseaddons.bereaudechange.action.FxWeightedAverageAction;
import com.wese.weseaddons.bereaudechange.pojo.FxDeal;
import com.wese.weseaddons.bereaudechange.pojo.FxWeightedAverage;

public class FxWeightedAverageHelper implements Runnable{
	
	private FxDeal fxDeal ; 
	private String tenant ;
	
	public FxWeightedAverageHelper(FxDeal fxDeal ,String tenant){
		this.fxDeal = fxDeal ;
		this.tenant = tenant ;
	}

	@Override
	public void run() {
		
		
		FxWeightedAverage fxWeightedAverage = new FxWeightedAverage(fxDeal);
		
		FxWeightedAverageAction fxWeightedAverageAction = new FxWeightedAverageAction(tenant);
		fxWeightedAverageAction.update(fxWeightedAverage);
	}
}