package com.wese.weseaddons.sqlquerries.pojo;

import com.wese.weseaddons.interfaces.PojoInterface;

public class GenderDistrubution implements PojoInterface{


    private Long id ;
    private String gender ;
    private int count ;
    private double principalAmount ;

    @Override
    public String getSchema() {
        return null;
    }


    public GenderDistrubution() {
    }


    @Override
    public Long getId() {
        return id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    public double getPrincipalAmount() {
        return principalAmount;
    }

    public void setPrincipalAmount(double principalAmount) {
        this.principalAmount = principalAmount;
    }
}
