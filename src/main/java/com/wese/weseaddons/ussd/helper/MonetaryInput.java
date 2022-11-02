package com.wese.weseaddons.ussd.helper;

public class MonetaryInput {

    private double amount ;
    private int option ;
    public static MonetaryInput instance = null ;


    public static MonetaryInput getInstance(){

        if(instance==null){
            instance = new MonetaryInput();
        }

        return instance ;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getOption(){
        return option ;
    }

    public void setOption(int option){
        this.option = option ;
    }


}
