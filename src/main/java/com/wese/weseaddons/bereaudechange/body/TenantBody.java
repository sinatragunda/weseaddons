package com.wese.weseaddons.bereaudechange.body;

public class TenantBody {

    private String tenant ;

    public TenantBody(String tenant) {
        this.tenant = tenant;
    }

    public TenantBody() {
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }
}
