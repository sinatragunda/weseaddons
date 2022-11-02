package com.wese.weseaddons.interceptor;


import com.wese.weseaddons.bereaudechange.exceptions.ClientNotRegisteredToService;
import com.wese.weseaddons.bereaudechange.helper.SettingsHelper;
import com.wese.weseaddons.helper.Constants;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.invoke.ConstantCallSite;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class BereauDeChangeInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest httpSevletRequest , HttpServletResponse httpSevletResponse , Object handler){


		String tenant = (String)InterceptorHelper.getParameterValue(httpSevletRequest ,"tenantIdentifier");

        System.err.println("Interceptor for bdx "+tenant);
		for(String t : Constants.bereauDeChangeClients){
			if(tenant.equalsIgnoreCase(t)){
				return true ;
			}
		}

		throw new ClientNotRegisteredToService();

		//System.out.println("false returning");

		//boolean isTradingHours = SettingsHelper.isTradingHours(tenant);
		//return false ;

	}
} 