
package com.wese.weseaddons.bereaudechange.session;


import com.wese.weseaddons.pojo.AppUser;
import com.wese.weseaddons.bereaudechange.pojo.FxDeal;
import com.wese.weseaddons.bereaudechange.pojo.Settings;
import com.wese.weseaddons.pojo.Client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FxSession{

	public static FxSession instance ;
	private List<TenantSession> tenantSessionList ;

	public static FxSession getInstance(){

		if(instance==null){

			instance = new FxSession();
		}

		return instance ;
	}

	public FxSession(){

		tenantSessionList = new ArrayList<>();
		List<String> tenantsList = Arrays.asList("demo");

		for(String s : tenantsList){

			TenantSession tenantSession = new TenantSession(s);
			//tenantSession.loadClients();
			//tenantSession.loadAppUsers();
			tenantSession.loadSettings();

			tenantSessionList.add(tenantSession);
		}
	}

	public TenantSession getTenantSession(String tenant){

		for(TenantSession t : tenantSessionList){
			if(t.getTenant().equalsIgnoreCase(tenant)){
				return t ;
			}
		}

		return null;
	}

	public Client getClient(FxDeal fxDeal ,String tenant){

		return  getTenantSession(tenant).getClient(fxDeal.getClient().getId());

	}

	public AppUser getAppUser(FxDeal fxDeal , String tenant){

		return  getTenantSession(tenant).getAppUser(fxDeal.getAppUser().getId());

	}

	public AppUser getAppUser(String tenant ,long id){

		return  getTenantSession(tenant).getAppUser(id);

	}

	public Settings getSettings(String tenant){
	    
	    Settings settings = null ;
	    try {
	        settings = getTenantSession(tenant).getSettings();
	    }
	    
	    catch (Exception e) {
                // TODO: handle exception
			e.printStackTrace();
	        return null ;
            }
	    
	    return settings ;
	    

	}
}

