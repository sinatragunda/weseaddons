/*Created by Sinatra Gunda
  At 15:04 on 9/3/2020 */

package com.wese.weseaddons.batchprocessing.workbook;


import com.wese.weseaddons.batchprocessing.pojo.Foreclosure;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

public class ForeclosureWorkbook extends Workbook{

    private List<Foreclosure> foreclosureList;

    public ForeclosureWorkbook(InputStream inputStream){
        super(inputStream ,null);
    }


    public List<Foreclosure> getForeclosureList() {
        return foreclosureList;
    }

    public void processFile() throws IOException{

        Iterator<Row> rowIterator = readFile(0);
        int count = 0 ;
        while(rowIterator.hasNext()){

            count++ ;
            Iterator<Cell> cellIterator = rowIterator.next().cellIterator();

            String name = null ;
            String loanAccountNumber = null ;

            while(cellIterator.hasNext()){

                Cell cell = cellIterator.next();
                String cellName = CellReference.convertNumToColString(cell.getColumnIndex());

                switch (cellName.toUpperCase()){

                     case "A":
                        loanAccountNumber = (String)WorkbookHelper.getValue(cell, null);
                        if(loanAccountNumber==null) {
                            continue ;
                        }
                        break;
                        
                    case "B":
                        name = (String)WorkbookHelper.getValue(cell  ,null);
                        if(name ==null){
                            continue;
                        }
                        break ;
                   
                }
            }

            if(count==1){
                ///first row in column skip or
                continue;
            }

            boolean isAnyNull = WorkbookHelper.isAnyNull(loanAccountNumber);
            if(!isAnyNull){
                Foreclosure foreclosure = new Foreclosure(loanAccountNumber ,name,count);
                foreclosureList.add(foreclosure);
            }
        }
    }

}

