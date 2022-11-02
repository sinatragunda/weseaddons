package com.wese.weseaddons.ussd.helper;

import java.util.HashMap;
import java.util.Map;

public class MenuFunctionAssociationMap {

    private Map<Integer ,String> functionMap ;


    public MenuFunctionAssociationMap(){

        init();

    }

    public void init(){

        functionMap = new HashMap<>();

    }

    public void setValue(int key ,String value){

        functionMap.putIfAbsent(key ,value);

    }

    public String getValue(int key){

        if(key > functionMap.size()){
            return null ;
        }

        if(!functionMap.containsKey(key)){

            return null ;
        }

        return functionMap.get(key);
    }

}
