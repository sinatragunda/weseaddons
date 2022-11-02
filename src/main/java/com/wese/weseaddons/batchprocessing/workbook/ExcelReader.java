package com.wese.weseaddons.batchprocessing.workbook;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ExcelReader {

    public static FileInputStream open(String filename){

        FileInputStream fileInputStream = null ;
        try{
            File file = new File(filename);
            fileInputStream = new FileInputStream(file);
        }

        catch(FileNotFoundException f){
            ///do something to throw this error to user interface
            return null;

        }
        return fileInputStream ;
    }
}
