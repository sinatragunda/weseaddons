package com.wese.weseaddons.taskmanager.body;

public class NotificationRequestBody {

    private long userId ;
    private String tenantIdentifier ;
    private String code ;
    private long objectIdentifier ;

    public NotificationRequestBody(){

    }

    public long getObjectIdentifier() {
        return objectIdentifier;
    }

    public void setObjectIdentifier(long objectIdentifier) {
        this.objectIdentifier = objectIdentifier;
    }

    public void setTenantIdentifier(String tenantIdentifier) {
        this.tenantIdentifier = tenantIdentifier;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTenantIdentifier() {
        return tenantIdentifier;
    }

    public long getUserId() {
        return userId;
    }
}
