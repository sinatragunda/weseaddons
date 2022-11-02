package com.wese.weseaddons.weselicense.pojo;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="m_tenants")
public class Tenant {

    @Id
    private long id ;

    private String tenantName ;
    private boolean exemption ;
    private long gracePeriod ;

    public Tenant(){}

    public long getGracePeriod() {
        return gracePeriod;
    }

    public void setGracePeriod(long gracePeriod) {
        this.gracePeriod = gracePeriod;
    }

    public Tenant(String tenantName){
        this.tenantName = tenantName ;
    }

    public long getId() {
        return id;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isExemption() {
        return exemption;
    }

    public void setExemption(boolean exemption) {
        this.exemption = exemption;
    }
}
