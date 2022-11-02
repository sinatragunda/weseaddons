package com.wese.weseaddons.weselicense.body;

public class TenantRequestBody {

    private String tenantIdentifier ;

    public TenantRequestBody(){}

    public void setTenantIdentifier(String tenantIdentifier) {
        this.tenantIdentifier = tenantIdentifier;
    }

    public String getTenantIdentifier() {
        return tenantIdentifier;
    }
}
