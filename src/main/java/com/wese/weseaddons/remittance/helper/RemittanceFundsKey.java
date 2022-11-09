package com.wese.weseaddons.remittance.helper;

import com.wese.weseaddons.bereaudechange.pojo.StandardCurrency;
import com.wese.weseaddons.helper.TimeHelper;

import java.util.Date;
import java.util.Random;

public class RemittanceFundsKey {

    public static String generateKey(StandardCurrency standardCurrency){

        long timeNow = TimeHelper.epochNow();

        Random random = new Random(timeNow);

        long key = random.nextLong();

        if(key < 0){
            key = key * (-1);
        }

        Date date = TimeHelper.timestampToDate(timeNow);
        String fundsKey = String.format("%d%d%s/%d",date.getDate(),date.getYear() , standardCurrency.getName(),key);
        return fundsKey ;
    }

}
