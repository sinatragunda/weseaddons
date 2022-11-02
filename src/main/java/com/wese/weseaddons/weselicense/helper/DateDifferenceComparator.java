package com.wese.weseaddons.weselicense.helper;

import com.wese.weseaddons.helper.TimeHelper;

import javax.persistence.OneToOne;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateDifferenceComparator implements Comparator<Date> {


    @Override
    public int compare(Date left ,Date right){

        if(left.getYear()==right.getYear()){
            if(left.getMonth()==right.getMonth()){
                if(left.getDay()==right.getDay()){
                    return 1 ;
                }
            }
        }

        return 0 ;
    }

    public long differenceInDays(Date expirationDate ,Date today){

        long days = TimeHelper.getDateDiff (today, expirationDate, TimeUnit.DAYS);           //31
        long hours = TimeHelper.getDateDiff (today, expirationDate ,TimeUnit.HOURS);         //744
        long minutes = TimeHelper.getDateDiff (today, expirationDate, TimeUnit.MINUTES);     //44640
        long seconds = TimeHelper.getDateDiff (today, expirationDate, TimeUnit.SECONDS);     //2678400
        long mills = TimeHelper.getDateDiff (today, expirationDate, TimeUnit.MILLISECONDS);

        return days ;
    }
}
