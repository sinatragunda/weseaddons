/*Created by Sinatra Gunda
  At 7:17 AM on 2/27/2020 */

package com.wese.weseaddons.interceptor;


import com.wese.weseaddons.bereaudechange.helper.SettingsHelper;
import com.wese.weseaddons.crb.error.NotSubscribedToCrbException;
import com.wese.weseaddons.helper.Constants;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.invoke.ConstantCallSite;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class CrbInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest httpSevletRequest , HttpServletResponse httpSevletResponse , Object handler){

        String tenant = (String)InterceptorHelper.getParameterValue(httpSevletRequest ,"tenantIdentifier");
        
        if(tenant==null) {
            return true ;
        }

        boolean found = false ;

        for(String t : Constants.crbClients){

            if(tenant.equalsIgnoreCase(t)){

                return true ;
            }
        }

        if(!found){
            //throw new NotSubscribedToCrbException();
        }

        return false ;

    }
}
