/*

    Created by Sinatra Gunda
    At 3:37 AM on 8/18/2021

*/
package com.wese.weseaddons.batchprocessing.helper;

import com.wese.weseaddons.batchprocessing.pojo.ExcelClientData;

import java.util.Comparator;

public class SsbTransactionComparator implements Comparator<ExcelClientData> {

    // -1 means second is bigger
    // 0 means equal
    // 1 means first is greater than second


    @Override
    public int compare(ExcelClientData o1, ExcelClientData o2){

        Long o1Timestamp = o1.getTimestamp();
        Long o2Timestamp = o2.getTimestamp();

        int cmp = o1Timestamp.compareTo(o2Timestamp);

        System.err.println("-----------------------cmp is --------"+cmp);
        return cmp;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
