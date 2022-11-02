/*

    Created by Sinatra Gunda
    At 8:59 AM on 2/16/2022

*/
package com.wese.weseaddons.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MoneyHelper {

    public static boolean depositable(BigDecimal amount){
        return ComparatorUtility.isDepositableAmount(amount);
    }

    public static Double decimalFormat(Double value){
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static Double decimalFormat(BigDecimal value){
        BigDecimal bd = value;
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
