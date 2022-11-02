package com.wese.weseaddons.helper;

import java.util.Comparator;
import java.util.Date;

public class DateComparator implements Comparator<Date> {

    @Override
    public int compare(Date dateLeft ,Date dateRight){

        if(dateLeft.getYear()==dateRight.getYear()){
            if(dateLeft.getMonth()==dateRight.getMonth()){
                if(dateLeft.getDay()==dateRight.getDay()){
                    return 0 ;
                }
            }
        }
        return 1 ;
    }


}
