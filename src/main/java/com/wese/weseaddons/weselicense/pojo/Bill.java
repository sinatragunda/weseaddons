package com.wese.weseaddons.weselicense.pojo;

import com.wese.weseaddons.weselicense.enumeration.BILL_STATE;

public class Bill {

    private long id ;
    private Tenant tenant ;
    private Service service ;
    private double amount ;
    private BILL_STATE billState ;
    private long date ;
    private int duration ;



    public Bill(){}

    public Bill(Tenant tenant, Service service, double amount, BILL_STATE billState, long date ,int duration) {
        this.tenant = tenant;
        this.service = service;
        this.amount = amount;
        this.billState = billState;
        this.date = date;
        this.duration = duration ;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public BILL_STATE getBillState() {
        return billState;
    }

    public void setBillState(BILL_STATE billState) {
        this.billState = billState;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getDate() {
        return date;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }
}
