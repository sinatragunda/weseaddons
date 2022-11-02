/*Created by Sinatra Gunda
  At 09:23 on 2/16/2021 */

package com.wese.weseaddons.sqlquerries.xlsreports;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.batchprocessing.workbook.ExcelWriter;
import com.wese.weseaddons.errors.springexceptions.AutoReportNotFoundException;
import com.wese.weseaddons.helper.ObjectNodeHelper;
import com.wese.weseaddons.helper.TimeHelper;
import com.wese.weseaddons.sqlquerries.enumerations.REPORT_NAME;
import com.wese.weseaddons.sqlquerries.helper.ActiveLoansHelper;
import com.wese.weseaddons.sqlquerries.helper.GeneralLedgerHelper;
import com.wese.weseaddons.sqlquerries.pojo.ActiveLoans;
import com.wese.weseaddons.sqlquerries.pojo.GeneralLedger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class XlsReportsAction {


    public static File runReport(ObjectNode objectNode ,REPORT_NAME reportName){

        switch (reportName){
            case ACTIVE_LOANS:
                return activeLoans(objectNode);
            case GENERAL_LEDGER:
                return generalLedger(objectNode);
        }

        return null ;
    }

    public static Callable runReportEx(String request){

        ObjectNode objectNode = ObjectNodeHelper.objectNodeFromString(request);

        String reportName = objectNode.get("reportName").asText();

        REPORT_NAME report_name = REPORT_NAME.fromString(reportName);

        if(report_name==null){
            throw new AutoReportNotFoundException(reportName);
        }

        Callable<File> fileCallable = ()->{
            File file = runReport(objectNode ,report_name);
            return file ;
        };

        return fileCallable ;
    }

    public static File activeLoans(ObjectNode objectNode){

        ActiveLoansHelper activeLoansHelper = new ActiveLoansHelper();

        List<ActiveLoans> activeLoansList = activeLoansHelper.getActiveLoans(objectNode);

        Map data = activeLoansHelper.xssfRows(activeLoansList);

        String filename = filenameCreater(objectNode);
        File file = new File(filename);

        try {
            file.createNewFile();
            //return ExcelWriter.write(file ,new XSSFWorkbook(), data ,null,"Active Loans",true ,0);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null ;

    }

    public static File generalLedger(ObjectNode objectNode){

        GeneralLedgerHelper generalLedgerHelper = new GeneralLedgerHelper();
        List<GeneralLedger> generalLedgerList = generalLedgerHelper.getGeneralLedger(objectNode);

        Map data = generalLedgerHelper.xssfRows(generalLedgerList ,objectNode);

        String filename = filenameCreater(objectNode);
        File file = new File(filename);

        try {

            file.createNewFile();
            //return ExcelWriter.write(file ,new XSSFWorkbook(),data ,null ,"General Ledger Report",true ,0);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null ;

    }

    public static String filenameCreater(ObjectNode objectNode){

        String tenant = objectNode.get("tenantIdentifier").asText();
        String reportName = objectNode.get("reportName").asText();
        Long timestamp  = TimeHelper.epochNow();
        String filename = String.format("%s_%s%d.xlsx",tenant ,reportName ,timestamp);
        return filename ;

    }

//
//    public static FileInputStream activeLoansDated(ObjectNode objectNode){
//
//        ActiveLoansHelper activeLoansHelper = new ActiveLoansHelper();
//        List<ActiveLoans> activeLoansList = activeLoansHelper.getActiveLoansDated(objectNode);
//
//        Map data = activeLoansHelper.xssfRows(activeLoansList);
//        File file = new File("Active Loans Test Excel.xlsx");
//
//        try {
//            file.createNewFile();
//            File file1 = ExcelWriter.write(file ,data ,"Active Loans Dated");
//            return new FileInputStream(file1);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
