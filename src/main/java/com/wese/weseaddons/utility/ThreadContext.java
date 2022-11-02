/*

    Created by Sinatra Gunda
    At 7:01 AM on 3/18/2021

*/
package com.wese.weseaddons.utility;

public final class ThreadContext {

    public static ThreadLocal<String> tenant = new ThreadLocal<>();

    public static String getTenant(){
        return tenant.get();
    }

    public static void setTenant(String tenant) {
        ThreadContext.tenant.set(tenant);
    }


}
