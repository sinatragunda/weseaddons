package com.wese.weseaddons.weselicense.body;

import com.wese.weseaddons.weselicense.pojo.Service;
import com.wese.weseaddons.weselicense.pojo.Tarrif;
import com.wese.weseaddons.weselicense.pojo.Tenant;

public class BillRequestBody {

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }


    private String date ;
    private int duration ;
    private Tenant tenant ;
    private Tarrif tarrif ;
    private Service service ;


    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public Tarrif getTarrif() {
        return tarrif;
    }

    public void setTarrif(Tarrif tarrif) {
        this.tarrif = tarrif;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }


}
