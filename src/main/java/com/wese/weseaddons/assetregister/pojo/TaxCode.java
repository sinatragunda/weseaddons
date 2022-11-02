package com.wese.weseaddons.assetregister.pojo;

import com.wese.weseaddons.interfaces.PojoInterface;

public class TaxCode implements PojoInterface{


    private String code ;
    private String description ;
    private String app ;
    private int af ;
    private double year_1 ;
    private double year_2 ;
    private double year_3 ;
    private double year_4 ;
    private double year_5 ;



    @Override
    public Long getId() {
        return null;
    }


    @Override
    public String getSchema(){
        return "m_tax_codes";
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public int getAf() {
        return af;
    }

    public void setAf(int af) {
        this.af = af;
    }

    public double getYear_1() {
        return year_1;
    }

    public void setYear_1(double year_1) {
        this.year_1 = year_1;
    }

    public double getYear_2() {
        return year_2;
    }

    public void setYear_2(double year_2) {
        this.year_2 = year_2;
    }

    public double getYear_3() {
        return year_3;
    }

    public void setYear_3(double year_3) {
        this.year_3 = year_3;
    }

    public double getYear_4() {
        return year_4;
    }

    public void setYear_4(double year_4) {
        this.year_4 = year_4;
    }

    public double getYear_5() {
        return year_5;
    }

    public void setYear_5(double year_5) {
        this.year_5 = year_5;
    }
}
