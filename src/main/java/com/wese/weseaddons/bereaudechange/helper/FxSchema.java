package com.wese.weseaddons.bereaudechange.helper ;

import com.wese.weseaddons.bereaudechange.pojo.FxDeal;
import com.wese.weseaddons.bereaudechange.pojo.MoneyAccount;

import java.util.HashMap;
import java.util.Map;

public class FxSchema{

	public static Map<Object ,String> map ;
	public static FxSchema instance ;

	public static void setInstance(){

		if(instance==null){
			
			instance = new FxSchema();
		}
	}

	public FxSchema(){
		init();
	}

	public void init(){

		map = new HashMap();

		map.put(FxDeal.class ,"m_fx_deal");
		map.put(MoneyAccount.class ,"m_fx_money_account");
	}

	public static String get(Object object){

		return map.get(object);
	}

}