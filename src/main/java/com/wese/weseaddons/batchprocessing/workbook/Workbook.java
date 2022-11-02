/*

    Created by Sinatra Gunda
    At 9:43 AM on 3/12/2021

*/
package com.wese.weseaddons.batchprocessing.workbook;

import com.wese.weseaddons.batchprocessing.helper.Parameters;
import com.wese.weseaddons.errors.springexceptions.FailedToReadWorkbookException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class  Workbook {

    private FileInputStream fileInputStream ;
    private String filename ;
    private boolean initStatus ;
    private Parameters parameters ;

    public Workbook(String filename){
        this.filename = filename ;
        init();
    }

    public Workbook(InputStream inputStream , Parameters parameters){
        fileInputStream = (FileInputStream) inputStream ;
        this.parameters = parameters ;
        init();

    }

    public void init(){

        if(fileInputStream==null){
            initStatus = false ;
            return ;
        }
        initStatus = true ;
    }

    public Iterator<Row> readFile(int sheetIndex) throws IOException {

        if (!initStatus) {
            throw new FailedToReadWorkbookException();
        }

        Iterator<Row> rowIterator = WorkbookHelper.rowIteratorFromStream(fileInputStream ,sheetIndex);
        return rowIterator;

    }

    public XSSFSheet readFileXssfSheet(int sheetIndex){

        if (!initStatus) {
            throw new FailedToReadWorkbookException();
        }

        XSSFSheet xssfSheet = WorkbookHelper.xssfSheet(fileInputStream ,sheetIndex);
        return xssfSheet;


    }

}
