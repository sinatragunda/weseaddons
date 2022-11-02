/*

    Created by Sinatra Gunda
    At 3:04 PM on 3/17/2021

*/
package com.wese.weseaddons.utility.chartofaccounts.pojo;

import com.wese.weseaddons.batchprocessing.workbook.ChartOfAccountsWorkbook;
import com.wese.weseaddons.utility.chartofaccounts.enmerations.CHART_OF_ACCOUNT_TYPE;

public class ChartOfAccountPortfolioMapping {

    private CHART_OF_ACCOUNT_TYPE type ;
    private String glName ;

    public ChartOfAccountPortfolioMapping(){}

    public CHART_OF_ACCOUNT_TYPE getType() {
        return type;
    }

    public void setType(String type) {
        this.type = CHART_OF_ACCOUNT_TYPE.fromString(type);
    }

    public String getGlName() {
        return glName;
    }

    public void setGlName(String glName) {
        this.glName = glName;
    }
}
