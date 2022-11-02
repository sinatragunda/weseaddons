package com.wese.weseaddons.bereaudechange.helper;

import com.wese.weseaddons.bereaudechange.pojo.TradingRates;

import java.util.Comparator;

public class TimestampComparator implements Comparator<TradingRates> {

    @Override
    public int compare(TradingRates o1, TradingRates o2){

        Long a = o1.getOpenTime();
        Long b = o2.getOpenTime();
        return a.compareTo(b);
    }
}
