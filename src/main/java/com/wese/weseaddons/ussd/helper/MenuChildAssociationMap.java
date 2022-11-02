package com.wese.weseaddons.ussd.helper;

import java.util.HashMap;
import java.util.Map;

public class MenuChildAssociationMap {

    private Map<Integer ,Boolean> hasChildMap ;
    
    
    public MenuChildAssociationMap() {
    	
    	init();
    }


    public void init(){

        hasChildMap = new HashMap<>();

    }

    public void setValue(int key ,boolean value){

        this.hasChildMap.put(key ,value);

    }

    public boolean getValue(int key){

        if(key > hasChildMap.size()){
            return false ;
        }

        if(!hasChildMap.containsKey(key)){

            return false ;
        }

        return hasChildMap.get(key);
    }



}
