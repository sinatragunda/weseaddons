/*

    Created by Sinatra Gunda
    At 4:31 PM on 3/17/2022

*/
package com.wese.weseaddons.batchprocessing.helper;

import com.wese.weseaddons.pojo.TransactionChaining;

public class TransactionChainingHelper {

    public static void addEvent(TransactionChaining transactionChaining1){

        TransactionChaining transactionChaining = TransactionChaining.getInstance();
        //TransactionChaining transactionChaining1 = new TransactionChaining();
        transactionChaining.add(transactionChaining1);

    }


}
