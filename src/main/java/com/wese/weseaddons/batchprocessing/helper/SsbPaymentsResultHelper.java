/*

    Created by Sinatra Gunda
    At 5:13 PM on 6/19/2021

*/
package com.wese.weseaddons.batchprocessing.helper;

import com.wese.weseaddons.batchprocessing.pojo.ExcelClientData;
import com.wese.weseaddons.batchprocessing.enumerations.PORTFOLIO_TYPE;
import com.wese.weseaddons.batchprocessing.enumerations.SSB_REPORT_TYPE;
import com.wese.weseaddons.batchprocessing.init.SSBPaymentsInit;
import com.wese.weseaddons.batchprocessing.pojo.SsbPaymentsResults;
import com.wese.weseaddons.batchprocessing.workbook.ExcelWriter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SsbPaymentsResultHelper {

    public static boolean updateResults(ExcelClientData excelClientData , SSB_REPORT_TYPE ssbReportType , PORTFOLIO_TYPE portfolioType){

        SsbPaymentsResults ssbPaymentsResults = SSBPaymentsInit.getInstance().getPaymentResults();
        boolean hasUpdated = ssbPaymentsResults.update(ssbReportType ,excelClientData ,portfolioType);
        return hasUpdated ;
    }

    public static boolean isTransactionReversable(SSB_REPORT_TYPE ssbReportType){

        boolean reversable = false; 
        switch(ssbReportType){
                case INVALID_SEARCH_DATA:
                case FAILED_REPAYMENTS:
                case NO_ACTIVE_LOANS:
                case NO_MATCHING_LOANS:
                case INVALID_NRC_SEARCH_NUMBER:
                case FAILED_DEPOSITS:
                case NO_VALID_SAVINGS_ACCOUNT:
                case NO_ACTIVE_SHARES_ACCOUNT:
                case SHARES_INTERNAL_ERROR:
                case FAILED:
                case SKIPPED:
                case NONE:
                case SHARES_MAXIMUM_REACHED:
                    break;
                default:
                    reversable = true;
                    break;
        }
        
        return reversable ;

    }


    public static File results(File file ,SSB_REPORT_TYPE type){

        SsbPaymentsResults ssbPaymentResults = SSBPaymentsInit.getInstance().getPaymentResults();
        Map map = ssbPaymentResults.getSSbResults();
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        ExcelWriter excelWriter = new ExcelWriter(0 ,true);

        switch (type){
            case ALL:
                file = all(map ,file ,xssfWorkbook);
                break;
            case SUCCESS:
                file = otherReports(map ,file ,xssfWorkbook ,true);
                break;
            case FAILED:
                file = otherReports(map ,file ,xssfWorkbook ,false);
                break ;
            default:
               List list = (List<ExcelClientData>) map.getOrDefault(type ,new ArrayList<>());
               Map results= mapObject(list ,1);
               file = excelWriter.write(file ,xssfWorkbook,results ,headers() ,type.getCode());
               break;
        }
        return file ;
    }

    public static List results(SSB_REPORT_TYPE type){

        SsbPaymentsResults ssbPaymentResults = SSBPaymentsInit.getInstance().getPaymentResults();
        Map map = ssbPaymentResults.getSSbResults();
        List clientDataList = new ArrayList();

        switch (type){
            case ALL:
                clientDataList = getAllResults(map);
                break;
            case SUCCESS:
                clientDataList = getFilteredResults(map ,true);
                break;
            case FAILED:
                clientDataList = getFilteredResults(map  ,false);
                break ;
            default:
               clientDataList = (List<ExcelClientData>) map.getOrDefault(type ,new ArrayList<>());
               break;
        }
        return clientDataList ;
    }

    public static Object[] cellObject(ExcelClientData excelClientData){

        Object object[] = new Object[]{
                excelClientData.getCount(),
                excelClientData.getName(),
                excelClientData.getLoanAccountNumber(),
                "",
                excelClientData.getNrcNumber(),
                excelClientData.getAmount(),
                excelClientData.getStatusDescription(),
                excelClientData.getStaging().ordinal(),
                excelClientData.getStatus().toString(),
                excelClientData.getPortfolioType(),
                excelClientData.isReversed(),
                excelClientData.getObjectId()

        };

        return object ;
    }

    public static Map mapObject(List<ExcelClientData> excelClientDataList ,int count){

        Map<Integer ,Object[]> map = new TreeMap<>();
        for (ExcelClientData e : excelClientDataList){
            Object[] objects = cellObject(e);
            map.put(count++ ,objects);

        }
        return map;
    }

    private static List getAllResults(Map<SSB_REPORT_TYPE ,List<ExcelClientData>> map){

        List clientDataList = new ArrayList();

        for (Map.Entry<SSB_REPORT_TYPE ,List<ExcelClientData>> entry : map.entrySet()){
            List list = entry.getValue();
            clientDataList.addAll(list);
        }
        return clientDataList ;
    }


    private static List getFilteredResults(Map<SSB_REPORT_TYPE ,List<ExcelClientData>> map ,boolean isReportSuccess){

        List clientDataList = new ArrayList();

        for (Map.Entry<SSB_REPORT_TYPE ,List<ExcelClientData>> entry : map.entrySet()){
            SSB_REPORT_TYPE type = entry.getKey();
            switch(type){
                case INVALID_SEARCH_DATA:
                case FAILED_REPAYMENTS:
                case NO_ACTIVE_LOANS:
                case NO_MATCHING_LOANS:
                case INVALID_NRC_SEARCH_NUMBER:
                case FAILED_DEPOSITS:
                case NO_VALID_SAVINGS_ACCOUNT:
                case NO_ACTIVE_SHARES_ACCOUNT:
                case SHARES_INTERNAL_ERROR:
                        if(!isReportSuccess){
                            clientDataList.addAll(entry.getValue());
                        }
                    break;
                default:
                    clientDataList.addAll(entry.getValue());
                    break;
            }
        }

        return clientDataList ;   
    }

    private static File all(Map<SSB_REPORT_TYPE ,List<ExcelClientData>> map ,File file ,XSSFWorkbook xssfWorkbook){

        int count = 1 ;
        boolean firstEntry = true ;
        Integer rowId = 0;
        ExcelWriter excelWriter = new ExcelWriter(rowId ,firstEntry);
        for (Map.Entry<SSB_REPORT_TYPE ,List<ExcelClientData>> entry : map.entrySet()){
            Map object = mapObject(entry.getValue() ,count);
            excelWriter.write(file ,xssfWorkbook ,object ,headers(),"ALL");
        }
        return file ;
    }

    private static File otherReports(Map<SSB_REPORT_TYPE ,List<ExcelClientData>> map ,File file ,XSSFWorkbook xssfWorkbook ,boolean successReport){

        int count = 1 ;
        boolean isFirstEntry = true ;
        int rowId = 0 ;
        ExcelWriter excelWriter = new ExcelWriter(0 ,true);
        for (Map.Entry<SSB_REPORT_TYPE ,List<ExcelClientData>> entry : map.entrySet()){
            SSB_REPORT_TYPE type = entry.getKey();
            switch(type){
                case INVALID_SEARCH_DATA:
                case FAILED_REPAYMENTS:
                case NO_ACTIVE_LOANS:
                case NO_MATCHING_LOANS:
                case INVALID_NRC_SEARCH_NUMBER:
                case FAILED_DEPOSITS:
                case NO_VALID_SAVINGS_ACCOUNT:
                case NO_ACTIVE_SHARES_ACCOUNT:
                case SHARES_INTERNAL_ERROR:
                    if(!successReport) {
                        Map object = mapObject(entry.getValue() ,count);
                        excelWriter.write(file, xssfWorkbook, object, headers(), "Failed");
                    }
                    break;
                default:
                    Map object = mapObject(entry.getValue(),count);
                    excelWriter.write(file, xssfWorkbook, object, headers(), "Successful");
                    break;
            }
        }

        return file ;   
    }

    public static Object[] headers(){
        return new Object[]{
                "#",
                "Client Name",
                "Loan Account Number",
                "External ID",
                "Client Number/ID",
                "Amount",
                "Message",
                "Staging(For Internal Use)",
                "Status",
                "Portflio Type",
                "Reversed",
                "Account ID"

        };
    }
}
