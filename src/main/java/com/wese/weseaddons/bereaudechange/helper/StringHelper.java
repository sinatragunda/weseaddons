package com.wese.weseaddons.bereaudechange.helper;

import com.wese.weseaddons.bereaudechange.dao.CurrencyPairDAO;
import com.wese.weseaddons.bereaudechange.pojo.CurrencyPair;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class StringHelper {

    public static String getInverseTick(String arg){

        String a = null ;
        String b = null;
        List<String> list= new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(arg ,"/");
        while (stringTokenizer.hasMoreElements()){
            list.add(stringTokenizer.nextToken());
        }
        
        
        if(list.isEmpty()) {
            return null ;
        }

        a = list.get(1);
        b = list.get(0);

        String value = String.format("%s/%s",a ,b);
        return value ;
    }

    public static String makeTick(CurrencyPair currencyPair , String tenant){

        CurrencyPairDAO currencyPairDAO = new CurrencyPairDAO(tenant);
        currencyPair = currencyPairDAO.find(currencyPair.getId());

        String a = currencyPair.getBaseCurrencyMoneyAccount().getTradingCurrency().getSymbol();
        String b = currencyPair.getQuoteCurrencyMoneyAccount().getTradingCurrency().getSymbol();
        return String.format("%s/%s" ,a ,b);
    }
}
