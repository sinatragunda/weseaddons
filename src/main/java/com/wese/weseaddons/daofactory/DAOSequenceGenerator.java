package com.wese.weseaddons.daofactory;

public class DAOSequenceGenerator {

    public static String getSequence(String prefix ,String id){

        StringBuilder stringBuilder = new StringBuilder(prefix);
        stringBuilder.append("-");
        stringBuilder.append(id);
        return stringBuilder.toString();
    }
}
