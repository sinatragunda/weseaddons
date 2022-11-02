/*

    Created by Sinatra Gunda
    At 1:06 PM on 3/17/2021

*/
package com.wese.weseaddons.interceptor;

import com.wese.weseaddons.helper.Constants;
import com.wese.weseaddons.utility.ThreadContext;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FineractInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest httpSevletRequest , HttpServletResponse httpSevletResponse , Object handler){

        String tenant = InterceptorHelper.getHeaderValue(httpSevletRequest ,"Fineract-Platform-TenantId");
        ThreadContext threadContext = new ThreadContext();
        threadContext.setTenant(tenant);
        return true ;

    }
}
