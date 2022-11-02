/*Created by Sinatra Gunda
  At 7:09 AM on 2/27/2020 */

package com.wese.weseaddons.interceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class InterceptorHelper {


    public static Object getParameterValue(HttpServletRequest httpSevletRequest , String parameter){

        String url = httpSevletRequest.getRequestURL().toString();
        Enumeration enumeration = httpSevletRequest.getParameterNames();
        Map<String ,String> parametersMap = new HashMap<>();

        while(enumeration.hasMoreElements()){

            String paramName = (String)enumeration.nextElement();
            parametersMap.put(paramName ,httpSevletRequest.getParameter(paramName));
        }

        return parametersMap.get(parameter);
    }


    public static String getHeaderValue(HttpServletRequest httpSevletRequest , String header){
        String url = httpSevletRequest.getRequestURL().toString();
        System.err.println(url);
        return httpSevletRequest.getHeader(header);
    }
}
