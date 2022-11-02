/*Created by Sinatra Gunda
  At 08:18 on 2/16/2021 */

package com.wese.weseaddons.batchprocessing.workbook;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ExcelWriter {

    private boolean isFirstEntry = true ;
    private Integer rowId = 0;


    public ExcelWriter(Integer rowId ,Boolean isFirstEntry){
        this.rowId = rowId ;
        this.isFirstEntry = isFirstEntry;
    }


    public File write(File file ,XSSFWorkbook xssfWorkbook, Map data ,Object[] headers ,String sheetName){

        XSSFSheet xssfSheet = xssfSheetWithErrorDetection(xssfWorkbook, sheetName);
        XSSFRow xssfRow ;

        Map<Integer ,Object[]> rows = new TreeMap<>();
        if(isFirstEntry){
            rows.put(rowId ,headers);
            isFirstEntry = false;
        }

        rows.putAll(data);
        Set<Integer> keyid = rows.keySet();

        for(Integer key : keyid){

            xssfRow = xssfSheet.createRow(rowId++);
            Object[] objects = (Object[]) rows.get(key);
            int cellId = 0 ;

            for(Object o : objects){
                Cell cell = xssfRow.createCell(cellId++);
                try{
                    cell.setCellValue((String)o);
                }
                catch (ClassCastException c){
                    String message = c.getMessage();
                    if(message.contains("Double")){
                        cell.setCellValue((Double)o);
                    }
                    else if (message.contains("Long")){
                        cell.setCellValue((Long)o);
                    }
                    else if(message.contains("Integer")){
                        cell.setCellValue((Integer)o);
                    }
                }
            }
        }
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            xssfWorkbook.write(fileOutputStream);
            fileOutputStream.close();
        }
        catch (IOException i){
            i.printStackTrace();
        }
        return file ;
    }

    public XSSFSheet xssfSheetWithErrorDetection(XSSFWorkbook xssfWorkbook, String sheetName){

        XSSFSheet xssfSheet = null ;
        try{
            xssfSheet = xssfWorkbook.createSheet(sheetName);
        }
        catch (IllegalArgumentException e){
            xssfSheet = xssfWorkbook.getSheet(sheetName);
        }
        return xssfSheet;

    }
}
