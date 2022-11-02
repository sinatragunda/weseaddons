/*

    Created by Sinatra Gunda
    At 3:42 AM on 8/18/2021

*/
package com.wese.weseaddons.batchprocessing.pojo;

import com.wese.weseaddons.batchprocessing.enumerations.PORTFOLIO_TYPE;
import com.wese.weseaddons.batchprocessing.enumerations.SSB_REPORT_TYPE;
import com.wese.weseaddons.batchprocessing.helper.ReverseTransactionHelper;
import com.wese.weseaddons.pojo.FundDDAAccount;

import java.util.ArrayList;
import java.util.List;

public class SSbTransactionsAdapter extends ArrayList<ExcelClientData> {

    private int count = 0 ;

    @Override
    public boolean add(ExcelClientData e){
        /**
         * Modified 14/10/2022 at 0220
         * To enable the initialization of ExcelClientData in the function instead of being done by the caller
         * Makes the code cleaner that way
         */ 
        //ExcelClientData excelClientData = new ExcelClientData(e);
        return super.add(e);
    }

    public boolean add(ExcelClientData excelClientData , SSB_REPORT_TYPE ssbReportType , PORTFOLIO_TYPE portfolioType,SSbTransactionsAdapter emergencyReverseList ,SsbPaymentsResults ssbPaymentResult){
        excelClientData.setPortfolioType(portfolioType);
        ExcelClientData record = ExcelClientData.instance(excelClientData ,ssbReportType);
        emergencyReverseList.add(record);
        ssbPaymentResult.add(ssbReportType ,record);
        return add(record);
       
    }
    public boolean addEx(ExcelClientData excelClientData ,SSbTransactionsAdapter emergencyReverseList){
        emergencyReverseList.add(excelClientData);
        return add(excelClientData);
    }

    /**
     * Added 14/10/2022 at 0345
     * This is for failed process payments that need to be reversed
     * Throws exception if its reversing
     */

    public void addOnFail(ExcelClientData excelClientData ,SSB_REPORT_TYPE ssbReportType ,PORTFOLIO_TYPE portfolioType, SSbTransactionsAdapter emergencyReverseList, boolean isReverseOnFail ,SsbPaymentsResults ssbPaymentsResults){
        excelClientData.setPortfolioType(portfolioType);
        ExcelClientData record = ExcelClientData.instance(excelClientData,ssbReportType);
        add(record);
        ReverseTransactionHelper.reverseOnFail(record ,emergencyReverseList ,isReverseOnFail,ssbPaymentsResults);
    }
}
