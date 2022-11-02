/*

    Created by Sinatra Gunda
    At 4:13 PM on 3/17/2022

*/
package com.wese.weseaddons.pojo;

import com.wese.weseaddons.enumerations.TRANSACTION_TYPE;
import com.wese.weseaddons.helper.ComparatorUtility;

import java.util.*;

public class TransactionChaining extends ArrayList{

    private static TransactionChaining instance ;
    private Client client ;
    private int index ;
    private Map<Long ,TRANSACTION_TYPE> transactionTypeMap ;

    public TransactionChaining(){
        //transactionTypeMap = new HashMap<>();
    }

    public TransactionChaining(Client client ,Map map){
        this.client = client ;
        this.transactionTypeMap = map ;
    }

    public static TransactionChaining getInstance(){
        if(instance == null){
            instance = new TransactionChaining();
        }
        return instance;
    }

    public void add(TransactionChaining transactionChaining){

        boolean hasItem = contains(transactionChaining);
        if(!hasItem){
            super.add(transactionChaining);
            return;
        }

        // else update
        TransactionChaining current = (TransactionChaining) transactionChaining.get(index);
        current.transactionTypeMap = transactionChaining.getTransactionTypeMap();
        super.add(index ,current);
    }

    public boolean contains(){

        Iterator it = iterator();
        index = 0 ;
        while (it.hasNext()){
            TransactionChaining transactionChaining = (TransactionChaining) it.next();
            Client client1 = transactionChaining.getClient();
            boolean equals = ComparatorUtility.areObjectsEqual(client1.getId() ,client.getId());
            if(equals){
                return true ;
            }
            ++index ;
        }
        return false ;

    }

    public int getIndex() {
        return index;
    }

    public Client getClient() {
        return client;
    }

    public Map<Long, TRANSACTION_TYPE> getTransactionTypeMap() {
        return transactionTypeMap;
    }
}
