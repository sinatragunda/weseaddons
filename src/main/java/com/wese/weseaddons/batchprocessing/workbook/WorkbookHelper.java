/*Created by Sinatra Gunda
  At 14:05 on 9/14/2020 */

package com.wese.weseaddons.batchprocessing.workbook;

import com.wese.weseaddons.errors.springexceptions.FailedToReadWorkbookException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class WorkbookHelper {


    public static Object getValue(Cell cell , Class classType){

        int cellType = cell.getCellType();
        Object object = null ;

        try{

            switch (cellType) {
                case Cell.CELL_TYPE_STRING:
                    object = cell.getStringCellValue();
                    object = convertToString(object);
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    object = getNumericValue(cell);
                    break;
                case Cell.CELL_TYPE_ERROR:
                    break;
                case Cell.CELL_TYPE_BLANK:
                    break;
                default:
                    object = cell.getStringCellValue();
                    object = convertToString(object);
                    break;
            }
        }
        catch(Exception c){
            System.err.println("-----------------class cast exception on value --------"+c.getMessage());
        }
        
        return object ;
    }

    public static String convertToString(Object object){

        Boolean isString = object.getClass().equals(String.class);

        if(isString){
            return (String)object;
        }

        // if not cast it to string prolly double value

        String value[] = {null};
        Optional.ofNullable(object).ifPresent(e->{
            Double d = (Double)e;
            value[0] = d.toString();

        });

        return value[0];

    }

    public static Double getNumericValue(Cell cell){
        Double value = null;
        try{
            String rich = cell.getRichStringCellValue().getString();
            value = cell.getNumericCellValue();
        }
        catch (ClassCastException c){
            ///value is string needs to get double
            String str = cell.getStringCellValue();
            value = Double.valueOf(str);
        }
        return value;
    }


    public static boolean isAnyNull(Object ...object){

        for(int i =0 ; i < object.length ;i++){
            Object o = object[i];
            boolean isEmpty = ObjectUtils.isEmpty((Object[]) o) || Objects.isNull(o);
            if(isEmpty){
                return isEmpty ;
            }
        }
        return false ;
    }

    public static XSSFSheet xssfSheet(FileInputStream fileInputStream ,int sheetIndex){

        XSSFWorkbook xssfWorkbook = null ;//= new XSSFWorkbook(fileInputStream);
        XSSFSheet xssfSheet = null ;

        try {
            xssfWorkbook = new XSSFWorkbook(fileInputStream);
            xssfWorkbook.setForceFormulaRecalculation(true);
            xssfSheet = xssfWorkbook.getSheetAt(sheetIndex);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return xssfSheet ;
    }

    public static Iterator<Row> rowIterator(XSSFSheet xssfSheet){
        if(xssfSheet==null){
            throw new FailedToReadWorkbookException();
        }
        Iterator<Row> rowIterator = xssfSheet.rowIterator();
        return  rowIterator ;
    }

    public static Iterator<Row> rowIteratorFromStream(FileInputStream fileInputStream ,int sheetIndex){
        XSSFSheet xssfSheet = xssfSheet(fileInputStream ,sheetIndex);
        return rowIterator(xssfSheet);
    }

    public static File exportFile(File file , Map data , String sheetName){
        ExcelWriter excelWriter = new ExcelWriter(0 ,true);
        return excelWriter.write(file ,new XSSFWorkbook(),data ,null ,sheetName);

    }
}
