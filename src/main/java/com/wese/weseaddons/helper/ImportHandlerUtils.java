/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.wese.weseaddons.helper;

import org.apache.poi.ss.usermodel.*;
import org.joda.time.LocalDate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


// Added 28/09/2021


public class ImportHandlerUtils {

    public static Integer getNumberOfRows(Sheet sheet, int primaryColumn) {
        
        Integer noOfEntries = 0;

        // getLastRowNum and getPhysicalNumberOfRows showing false values
        // sometimes
        //while(sheet.getRow(noOfEntries+1) !=null){
          //  noOfEntries++;
        //}

        while (sheet.getRow(noOfEntries+1) !=null && sheet.getRow(noOfEntries+1).getCell(primaryColumn) != null) {
            noOfEntries++;
         }

        return noOfEntries;
    }


    public static Long readAsLong(int colIndex, Row row) {

        Cell c = row.getCell(colIndex);
        if (c == null || c.getCellType() == Cell.CELL_TYPE_BLANK)
            return null;
        FormulaEvaluator eval = row.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
        if(c.getCellType() == Cell.CELL_TYPE_FORMULA) {
            if(eval!=null) {
                CellValue val = eval.evaluate(c);
                return ((Double) val.getNumberValue()).longValue();
            }
        }

        else if (c.getCellType()==Cell.CELL_TYPE_NUMERIC){
            return ((Double) c.getNumericCellValue()).longValue();
        }
        else {
            try{
                return Long.parseLong(row.getCell(colIndex).getStringCellValue());
            }
            catch (NumberFormatException n){
                n.printStackTrace();
            }
        }
        return null;
    }


    public static String readAsString(int colIndex, Row row) {

        Cell c = row.getCell(colIndex);
        if (c == null || c.getCellType() == Cell.CELL_TYPE_BLANK){
            return null;
        }

        FormulaEvaluator eval = row.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
        String res = null ;
        if(c.getCellType() == Cell.CELL_TYPE_FORMULA) {

            if (eval!=null) {
                try {
                    CellValue val = eval.evaluate(c);
                    String valString = val.getStringValue();

                    boolean isValPresent = Optional.ofNullable(valString).isPresent();

                    if(!isValPresent){
                        valString = String.valueOf(val.getNumberValue());
                    }
                    res = trimEmptyDecimalPortion(valString);
                }
                catch (NullPointerException n){
                    n.printStackTrace();
                }
                if (res!=null) {
                    if (!res.equals("")) {
                        return res.trim();
                    } else {
                        return null;
                    }
                }else {
                    return null;
                }
                }else {
                    return null;
            }
        }

        else if(c.getCellType()==Cell.CELL_TYPE_STRING) {

            res = trimEmptyDecimalPortion(c.getStringCellValue().trim());
            boolean isPresent = Optional.ofNullable(res).isPresent();

            if(isPresent){
                return res.trim();
            }
            return null ;

        }else if(c.getCellType()==Cell.CELL_TYPE_NUMERIC) {
            return ((Double) row.getCell(colIndex).getNumericCellValue()).intValue() + "";
        }else if (c.getCellType()==Cell.CELL_TYPE_BOOLEAN){
            return c.getBooleanCellValue()+"";
        }else {
            return null;
        }
    }


    public static String trimEmptyDecimalPortion(String result) {
        if(result != null && result.endsWith(".0"))
            return	result.split("\\.")[0];
        else
            return result;
    }

    // should default all dates to today ? 


    public static Boolean readAsBoolean(int colIndex, Row row) {
            Cell c = row.getCell(colIndex);
            if(c == null || c.getCellType() == Cell.CELL_TYPE_BLANK)
                return false;
            FormulaEvaluator eval = row.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
            if(c.getCellType() == Cell.CELL_TYPE_FORMULA) {
                if(eval!=null) {
                    CellValue val = eval.evaluate(c);
                    return val.getBooleanValue();
                }
                return false;
            }else if(c.getCellType()==Cell.CELL_TYPE_BOOLEAN)
                return c.getBooleanCellValue();
            else {
                String booleanString = row.getCell(colIndex).getStringCellValue().trim();
                if (booleanString.equalsIgnoreCase("TRUE"))
                    return true;
                else
                    return false;
            }
        }

    public static Integer readAsInt(int colIndex, Row row) {
            Cell c = row.getCell(colIndex);
            if (c == null || c.getCellType() == Cell.CELL_TYPE_BLANK)
                return null;
            FormulaEvaluator eval = row.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
            if(c.getCellType() == Cell.CELL_TYPE_FORMULA) {
                if(eval!=null) {
                   CellValue val = eval.evaluate(c);
                    return ((Double) val.getNumberValue()).intValue();
                }
                return null;
            }else if (c.getCellType()==Cell.CELL_TYPE_NUMERIC) {
                return ((Double) c.getNumericCellValue()).intValue();
            }else {
                return Integer.parseInt(row.getCell(colIndex).getStringCellValue());
            }
    }

    public static Double readAsDouble(int colIndex, Row row) {
        Cell c = row.getCell(colIndex);
        if (c == null || c.getCellType() == Cell.CELL_TYPE_BLANK)
            return 0.0;
        FormulaEvaluator eval = row.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
        if(c.getCellType() == Cell.CELL_TYPE_FORMULA) {
                if (eval!=null) {
                    CellValue val = eval.evaluate(c);
                    return val.getNumberValue();
                }else {
                    return 0.0;
                }
        } else if (c.getCellType()==Cell.CELL_TYPE_NUMERIC) {
            return row.getCell(colIndex).getNumericCellValue();
        }else {
            Double val = 0.0;
            try{
                val = Double.parseDouble(row.getCell(colIndex).getStringCellValue());
            }
            catch(NullPointerException n){
                System.err.println("---------------value is "+row.getCell(colIndex).getStringCellValue());    
                System.err.println("-------------format exception caught son -----------"+n.getMessage());
            }
            return MoneyHelper.decimalFormat(val);
        }
    }

    public static void writeString(int colIndex, Row row, String value) {
        if(value!=null)
            row.createCell(colIndex).setCellValue(value);
    }

    public static CellStyle getCellStyle(Workbook workbook, IndexedColors color) {
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(color.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        return style;
    }


    public static String getErrorList(List<String> errorList){
        StringBuffer errors=new StringBuffer();
        for (String error: errorList) {
                errors=errors.append(error);
        }
        return errors.toString();
    }

    public static void writeErrorMessage(Sheet sheet,Integer rowIndex,String errorMessage,int statusColumn){
        Cell statusCell = sheet.getRow(rowIndex).createCell(statusColumn);
        statusCell.setCellValue(errorMessage);
        statusCell.setCellStyle(getCellStyle(sheet.getWorkbook(), IndexedColors.RED));
    }


}