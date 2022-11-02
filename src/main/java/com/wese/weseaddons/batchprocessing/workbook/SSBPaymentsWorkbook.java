package com.wese.weseaddons.batchprocessing.workbook;


import com.wese.weseaddons.batchprocessing.enumerations.PORTFOLIO_TYPE;
import com.wese.weseaddons.batchprocessing.enumerations.STAGING;
import com.wese.weseaddons.batchprocessing.helper.SsbPaymentsConstants;
import com.wese.weseaddons.batchprocessing.pojo.SSbTransactionsAdapter;
import com.wese.weseaddons.batchprocessing.pojo.SsbPayment;
import com.wese.weseaddons.enumerations.STATUS;
import com.wese.weseaddons.helper.ImportHandlerUtils;
import com.wese.weseaddons.helper.MoneyHelper;
import com.wese.weseaddons.pojo.FundDDAAccount;
import com.wese.weseaddons.pojo.SavingsAccount;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.wese.weseaddons.batchprocessing.pojo.ExcelClientData;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class SSBPaymentsWorkbook extends Workbook{

    private SsbPayment ssbPayment ;
    private SSbTransactionsAdapter excelClientDataList = new SSbTransactionsAdapter();
    private List<FundDDAAccount> fundDDAAccountList = new ArrayList<>();

    public SSBPaymentsWorkbook(InputStream inputStream , SsbPayment ssbPayment){
        super(inputStream,null);
        this.ssbPayment = ssbPayment ;
    }

    public SSbTransactionsAdapter getExcelClientDataList() {
        return excelClientDataList;
    }

    public List<FundDDAAccount> getFundDDAAccountsList(){
        return this.fundDDAAccountList ;
    }

    private ExcelClientData readRow(Row row,int index){

        String name = ImportHandlerUtils.readAsString(SsbPaymentsConstants.CLIENT_NAME_COL, row);
        String loanAccountNumber = ImportHandlerUtils.readAsString(SsbPaymentsConstants.LOAN_ACCOUNT_NO_COL ,row);
        String externalId = ImportHandlerUtils.readAsString(SsbPaymentsConstants.DOCUMENT_ID_COL ,row);
        Double amount = ImportHandlerUtils.readAsDouble(SsbPaymentsConstants.AMOUNT_COL ,row);

        String accountName = ImportHandlerUtils.readAsString(SsbPaymentsConstants.DDA_FUND_ACCOUNT_NAME_COL ,row);

        ExcelClientData excelClientData = new ExcelClientData(name ,new BigDecimal(amount) ,externalId ,null,loanAccountNumber , STAGING.fromInt(0),null ,accountName  ,index);
        return excelClientData ;

    }


    private SavingsAccount readDDAFundRow(Row row,int index){

        Long id = ImportHandlerUtils.readAsLong(0, row);
        String clientName = ImportHandlerUtils.readAsString(3 ,row);
        Double amount = ImportHandlerUtils.readAsDouble(4 ,row);

        //String loanAccountNumber = ImportHandlerUtils.readAsString(SsbPaymentsConstants.LOAN_ACCOUNT_NO_COL ,row);
        //String externalId = ImportHandlerUtils.readAsString(SsbPaymentsConstants.DOCUMENT_ID_COL ,row);
        //Double amount = ImportHandlerUtils.readAsDouble(SsbPaymentsConstants.AMOUNT_COL ,row);

       // String accountName = ImportHandlerUtils.readAsString(SsbPaymentsConstants.DDA_FUND_ACCOUNT_NAME_COL ,row);

//        String accNo = ImportHandlerUtils.readAsString(SsbPaymentsConstants.DDA_FUND_ACCOUNT_ID_COL ,row);

        //      System.err.println(accNo);

        //int staging = ImportHandlerUtils.readAsInt(SsbPaymentsConstants.STAGING_COL ,row);


        //System.err.println("-----------name ----"+name+"----------------external-----------"+externalId+"---------amount "+amount+"-----------------account name"+accountName);


        //Long ddaFundAccountId = ImportHandlerUtils.readAsLong(SsbPaymentsConstants.DDA_FUND_ACCOUNT_ID_COL ,row);

        FundDDAAccount fundDDAAccount = new FundDDAAccount(id, clientName, new BigDecimal(amount), STAGING.fromInt(0), index);
        //fundDDAAccountList.add(fundDDAAccount);
        //ExcelClientData excelClientData = new ExcelClientData(name ,amount ,externalId ,null,loanAccountNumber , STAGING.fromInt(0),null ,index);
        return fundDDAAccount ;

    }

    public void readAccountsSheetEx(){

        XSSFSheet clientSheet = readFileXssfSheet(0);
        Integer noOfEntries = ImportHandlerUtils.getNumberOfRows(clientSheet,0);

        //Integer noOfEntries = 10 ;

        for (int rowIndex=1;rowIndex<=noOfEntries;rowIndex++){
            Row row =clientSheet.getRow(rowIndex);
            try{
                ExcelClientData excelClientData = readRow(row ,rowIndex);
                Optional.ofNullable(excelClientData).ifPresent(e->{
                    excelClientDataList.add(excelClientData);
                });
            }
            catch (Exception ex){
                System.err.println("-------------exception caught reading row "+ex.getMessage());
            }
        }

    }

    public void readDDAFundsSheetEx(){

        XSSFSheet clientSheet = readFileXssfSheet(1);
        Integer noOfEntries= ImportHandlerUtils.getNumberOfRows(clientSheet,3);

        System.err.println("total entries ---------"+noOfEntries);

        for (int rowIndex=1;rowIndex<=noOfEntries;rowIndex++){
            Row row =clientSheet.getRow(rowIndex);
            try{
                SavingsAccount savingsAccount = readDDAFundRow(row ,rowIndex);
                Optional.ofNullable(savingsAccount).ifPresent(e->{
                    fundDDAAccountList.add((FundDDAAccount) savingsAccount);

                });
            }
            catch (Exception ex){
                ex.printStackTrace();
                System.err.println("-------------exception caught reading row "+ex.getMessage());
            }

        }
    }

    public void readAccountsSheet() throws IOException{

        Iterator<Row> rowIterator = readFile(0);
        int count = 0 ;

        while(rowIterator.hasNext()){

            count++ ;
            if(count <= 1){
                rowIterator.next();
                continue;
            }

            System.err.println(count);

            Iterator<Cell> cellIterator = rowIterator.next().cellIterator();
            String name = null ;
            BigDecimal amount = null;
            String nrcNumber = null ;
            String loanAccountNumber = null ;
            Integer staging[] = {null} ;
            SavingsAccount savingsAccount[] = {null} ;
            STATUS status[] ={null}; 
            PORTFOLIO_TYPE portfolioType[] = {null};

            while(cellIterator.hasNext()){

                Cell cell = cellIterator.next();

                String cellName = CellReference.convertNumToColString(cell.getColumnIndex());
                switch (cellName.toUpperCase()){

                    case "A":
                        name = (String)WorkbookHelper.getValue(cell ,null);
                        break ;
                    case "B":
                        loanAccountNumber=(String)WorkbookHelper.getValue(cell, null);
                        break;
                    case "D":
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        nrcNumber=(String)WorkbookHelper.getValue(cell ,null);
                        break;
                    case "E":
                        //cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                        amount=(BigDecimal)WorkbookHelper.getValue(cell ,null);
                        break;
                    case "F":
                        Double temp = (Double)WorkbookHelper.getValue(cell ,null);
                        staging[0] = null;
                        Optional.ofNullable(temp).ifPresent(e->{
                            staging[0] = temp.intValue();
                        });
                        break;
                    case "J":
                        cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                        Double accountIdDouble = (Double) WorkbookHelper.getValue(cell ,null);
                        Optional.ofNullable(accountIdDouble).ifPresent(e->{
                            Long accountId = accountIdDouble.longValue();
                            savingsAccount[0] = new SavingsAccount(accountId);
                        });
                        break;
                }
            }

            boolean isAnyNull = WorkbookHelper.isAnyNull(nrcNumber ,amount);
            if(isAnyNull){
                System.err.println(isAnyNull);
                continue;
            }

            ExcelClientData excelClientData = new ExcelClientData(name ,amount ,nrcNumber ,null,loanAccountNumber , STAGING.fromInt(staging[0]),savingsAccount[0] ,null  , count);
            excelClientDataList.add(excelClientData);

        }
    }


    public void readFundDDASheet() throws IOException{

        Iterator<Row> rowIterator = readFile(1);
        int count = 0 ;

        while(rowIterator.hasNext()) {

            count++;
            if (count <= 1) {
                rowIterator.next();
                continue;
            }

            Iterator<Cell> cellIterator = rowIterator.next().cellIterator();
            String clientName = null;
            Double amount = null;
            String accountNumber = null;
            Long accountId = null;
            Integer staging[] = {null};

            while (cellIterator.hasNext()) {

                Cell cell = cellIterator.next();

                String cellName = CellReference.convertNumToColString(cell.getColumnIndex());
                switch (cellName.toUpperCase()) {

                    case "A":
                        accountId = (Long) WorkbookHelper.getValue(cell,  null);
                        break;
                    case "B":
                        accountNumber = (String) WorkbookHelper.getValue(cell,  null);
                        break;
                    case "D":
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        clientName = (String) WorkbookHelper.getValue(cell, null);
                        break;
                    case "E":
                        cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                        amount = (Double) WorkbookHelper.getValue(cell,  null);
                        amount = MoneyHelper.decimalFormat(amount);
                        break;
                    case "F":
                        Double temp = (Double) WorkbookHelper.getValue(cell,  null);
                        staging[0] = null;
                        Optional.ofNullable(temp).ifPresent(e -> {
                            staging[0] = temp.intValue();
                        });
                        break;

                }

                boolean isAnyNull = WorkbookHelper.isAnyNull(accountId);
                if (isAnyNull) {
                    continue;
                }

                FundDDAAccount fundDDAAccount = new FundDDAAccount(accountId, clientName, new BigDecimal(amount), STAGING.fromInt(staging[0]), count);
                fundDDAAccountList.add(fundDDAAccount);
            }
        }
    }

}
