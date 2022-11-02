/*Created by Sinatra Gunda
  At 14:11 on 9/14/2020 */

package com.wese.weseaddons.batchprocessing.workbook;

import com.wese.weseaddons.batchprocessing.pojo.Foreclosure;
import com.wese.weseaddons.pojo.LoanApplication;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LoanApplicationWorkbook {

    private FileInputStream fileInputStream ;
    private String filename ;
    private boolean initStatus ;
    private List<LoanApplication> loanApplicationList;

    public LoanApplicationWorkbook(String filename){
        this.filename = filename ;
        init();
    }

    public LoanApplicationWorkbook(InputStream inputStream){

        fileInputStream = (FileInputStream) inputStream ;
        init();

    }

    public void init(){

        if(fileInputStream==null){
            initStatus = false ;
            return ;
        }
        initStatus = true ;
        loanApplicationList = new ArrayList<>();
    }

    public List<LoanApplication> getForeclosureList() {
        return loanApplicationList;
    }

    public void readFile() throws IOException {

        if(!initStatus) {

            System.out.println("File Error HandlerExceptionResolver ");
            return ;
        }

        XSSFSheet xssfSheet = WorkbookHelper.xssfSheet(fileInputStream ,0) ;

        Iterator<Row> rowIterator = WorkbookHelper.rowIterator(xssfSheet);
        int count = 0 ;

        while(rowIterator.hasNext()){

            count++ ;
            Iterator<Cell> cellIterator = rowIterator.next().cellIterator();

            String name = null ;
            String clientId = null ;
            Double amount = null ;

            while(cellIterator.hasNext()){

                Cell cell = cellIterator.next();

                String cellName = CellReference.convertNumToColString(cell.getColumnIndex());

                switch (cellName.toUpperCase()){

                    case "A":
                        clientId = (String)WorkbookHelper.getValue(cell, null);
                        break;

                    case "B":
                        amount = (Double)WorkbookHelper.getValue(cell ,null);
                        break ;
                }
            }

            if(count==1){
                ///first row in column skip or
                continue;
            }

            boolean isAnyNull = WorkbookHelper.isAnyNull(clientId ,amount);

            if(!isAnyNull){

                LoanApplication loanApplication =new LoanApplication();
                loanApplicationList.add(loanApplication);
            }
        }

    }

}
