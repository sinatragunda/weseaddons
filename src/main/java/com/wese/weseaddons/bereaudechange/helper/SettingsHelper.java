package com.wese.weseaddons.bereaudechange.helper;

import com.wese.weseaddons.bereaudechange.dao.SettingsDAO;
import com.wese.weseaddons.bereaudechange.session.FxSession;
import com.wese.weseaddons.bereaudechange.pojo.Settings;

import java.time.LocalDateTime;

public class SettingsHelper{


	public static boolean isTradingHours(String tenant){

		LocalDateTime localDateTime = LocalDateTime.now();

		int hourNow = localDateTime.getHour();
		

		Settings settings = FxSession.getInstance().getSettings(tenant);
		
		if(settings==null) {
		    return false ;
		}

		if(hourNow > settings.getClosingTime()){
			return false ;
		}

		if(hourNow >= settings.getOpeningTime() && hourNow < settings.getClosingTime()){
			return true ;
		}

		return false ;
		
	}
	
	public boolean isAboveDailyLimit(){

		return false;
	}
}