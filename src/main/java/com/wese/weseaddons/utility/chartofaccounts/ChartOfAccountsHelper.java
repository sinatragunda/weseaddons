/*

    Created by Sinatra Gunda
    At 9:28 AM on 3/15/2021

*/
package com.wese.weseaddons.utility.chartofaccounts;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.wese.weseaddons.helper.FileHelper;
import com.wese.weseaddons.networkrequests.ChartOfAccountRequest;
import com.wese.weseaddons.utility.chartofaccounts.enmerations.CHART_OF_ACCOUNT_TYPE;
import com.wese.weseaddons.utility.chartofaccounts.pojo.ChartOfAccount;
import com.wese.weseaddons.utility.chartofaccounts.pojo.ChartOfAccountPortfolioMapping;
import javafx.scene.chart.Chart;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Set;

public class ChartOfAccountsHelper {


    private static int lastCount = 0 ;


    public void createChart(ChartOfAccount chartOfAccount ,boolean isHeader ,int level){

        int glCode = generateLedgerId(chartOfAccount.getChartOfAccountType() ,isHeader ,level);
        chartOfAccount.setGlCode(glCode);
        ChartOfAccountRequest.createChart(chartOfAccount);
    }


    ///level from parent to child etc 1 ,2,3,4
    public int generateLedgerId(CHART_OF_ACCOUNT_TYPE chartOfAccountType ,boolean isHeader ,int level){

        int parentId = chartOfAccountType.getTypeIdRange();
        //100000
        int rangeValue = getRangeValue(parentId ,level);
        int endId =  parentId | rangeValue ;

        if(!isHeader){
            String buffer[] = new String[5];
            //int index = arg.length()-level;
            //buffer[index];
            //endId = Integer.valueOf(arg[index]);
        }
        return endId ;
    }

    public int getRangeValue(int parentValue ,int level){

        String value = String.valueOf(parentValue);
        value = value.substring(value.length()-1);
        return Integer.valueOf(value);
    }

    public static void readPortfolioMappingFile(ChartOfAccountPortfolioMapping chartOfAccount){

        String data = FileHelper.getInstance().readFileAsResource("portfoliomapping.json",false);
        JSONObject jsonObject = new JSONObject(data);
        Set<String> set = jsonObject.keySet();

        for(String key : set){

            ChartOfAccount parent = ChartOfAccountRequest.getAccount(key);
            if(chartOfAccount==null){
                ///create something here
            }
        }
    }

    public static ChartOfAccount chartOfAccount(String response){

        ChartOfAccount chartOfAccount = null ;

        try{
            JSONObject jsonObject = new JSONObject(response);
            String glCode = jsonObject.getString("glCode");
            Long id = jsonObject.getLong("id");
            int type = jsonObject.getJSONObject("type").getInt("id");
            //chartOfAccount = new ChartOfAccount(id ,null ,glCode ,type ,null ,null,);
        }
        catch (JSONException j){
            j.printStackTrace();
        }
        return chartOfAccount ;
    }

}
