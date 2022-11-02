/*

    Created by Sinatra Gunda
    At 10:34 AM on 3/18/2021

*/
package com.wese.weseaddons.utility.chartofaccounts.action;

import com.wese.weseaddons.utility.chartofaccounts.ChartOfAccountsHelper;
import com.wese.weseaddons.utility.chartofaccounts.pojo.ChartOfAccount;
import com.wese.weseaddons.utility.chartofaccounts.pojo.ChartOfAccountPortfolioMapping;

import java.util.List;

public class ChartOfAccountAction {


    public static void createChartOfAccounts(ChartOfAccount chartOfAccount){


    }

    public static void portfolioMapping(List<ChartOfAccountPortfolioMapping> chartOfAccountList){

        for(ChartOfAccountPortfolioMapping chartOfAccount : chartOfAccountList){
            ChartOfAccountsHelper.readPortfolioMappingFile(chartOfAccount);
        }
    }
}
