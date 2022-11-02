package com.wese.weseaddons.weselicense.pojo;

import com.wese.weseaddons.weselicense.enumeration.TIME_UNIT_TYPE;

public class Tarrif {

    private long id ;
    private String name ;
    private double amount ;
    private int timeUnit ;
    private TIME_UNIT_TYPE timeUnitType ;


    public Tarrif(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public int getTimeUnit() {
        return timeUnit;
    }

    public TIME_UNIT_TYPE getTimeUnitType() {
        return timeUnitType;
    }

    public void setTimeUnit(int timeUnit) {
        this.timeUnit = timeUnit;
    }

    public void setTimeUnitType(TIME_UNIT_TYPE timeUnitType) {
        this.timeUnitType = timeUnitType;
    }

}
