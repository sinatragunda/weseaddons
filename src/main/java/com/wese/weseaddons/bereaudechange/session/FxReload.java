package com.wese.weseaddons.bereaudechange.session;

public class FxReload{

	public static FxReload instance ;
	
	public static FxReload getInstance(){
		if(instance==null){
			instance = new FxReload();
		}	
		return instance ;
	
	}
	
	public void start(){
		
		FxSession.instance=null ;	
		FxSession.getInstance();
	
	}

}