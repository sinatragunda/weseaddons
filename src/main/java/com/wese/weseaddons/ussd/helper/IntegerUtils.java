package com.wese.weseaddons.ussd.helper;

import com.wese.weseaddons.ussd.session.Session;
import com.wese.weseaddons.ussd.exceptions.ParseDoubleException;

public class IntegerUtils {

    public static int parseString(String arg) throws ParseDoubleException{

        return Integer.parseInt(arg);

    }

    public static double parseDouble(String arg){

        return Double.parseDouble(arg);

    }

    public static boolean compareSessionId(Session left , Session right){

        int leftSessionId = parseString(left.getSessionId());
        int rightSessionId = parseString(right.getSessionId());


        if(leftSessionId!=rightSessionId){
            return false ;
        }
        return true ;

    }



}
