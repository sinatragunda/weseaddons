package com.wese.weseaddons.weselicense.pojo;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "m_license_duration")
public class LicenseDuration {

    @Id
    private long id ;
    private long expiryDate ;


    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant ;


    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setExpiryDate(long expiryDate) {
        this.expiryDate = expiryDate;
    }

    public long getExpiryDate() {
        return expiryDate;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }
}
