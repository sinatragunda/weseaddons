package com.wese.weseaddons.bereaudechange.pojo;

import java.sql.Date;

public class FxDailyTracker {

    private long id ;
    public double amount ;
    private Date date ;

    public FxDailyTracker() {
    }

    public FxDailyTracker(long id ,double amount ,Date date){
        this.id = id ;
        this.amount = amount ;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
