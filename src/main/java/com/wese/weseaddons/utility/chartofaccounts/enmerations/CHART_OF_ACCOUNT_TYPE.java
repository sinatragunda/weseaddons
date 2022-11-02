/*

    Created by Sinatra Gunda
    At 3:07 PM on 3/17/2021

*/
package com.wese.weseaddons.utility.chartofaccounts.enmerations;

public enum CHART_OF_ACCOUNT_TYPE {

    ASSETS("Assets",100000),
    LIABILITY("LIABILITY",200000),
    EQUITY("EQUITY" ,300000),
    INCOME("INCOME" ,400000),
    EXPENSE("EXPENSE",500000),
    ALL("ALL",0);


    private String code ;
    private int type ;

    CHART_OF_ACCOUNT_TYPE(String code ,int type){
        this.code = code ;
        this.type = type ;
    }

    public int getTypeIdRange(){
        return type ;
    }

    public static CHART_OF_ACCOUNT_TYPE fromString(String arg){
        for(CHART_OF_ACCOUNT_TYPE chartOfAccountType : values()){
            if(arg.equalsIgnoreCase(chartOfAccountType.code)){
                return chartOfAccountType;
            }
        }
        return null;
    }

}
