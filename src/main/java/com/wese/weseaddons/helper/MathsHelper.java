package com.wese.weseaddons.helper;

import com.wese.weseaddons.bereaudechange.enumerations.ROUNDING_RULE;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathsHelper {

    public static double simpleRound(double value){

        int scale = 2 ;

        BigDecimal bigDecimal =new BigDecimal(value);

        try{
            bigDecimal = new BigDecimal(value).setScale(scale ,RoundingMode.HALF_UP);

        }

        catch (NumberFormatException n){
            n.printStackTrace();
        }

        return bigDecimal.doubleValue();

    }
}
