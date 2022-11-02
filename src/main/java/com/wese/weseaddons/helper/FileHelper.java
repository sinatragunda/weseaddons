package com.wese.weseaddons.helper;

import com.wese.weseaddons.errors.springexceptions.FailedToReadWorkbookException;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;

public class FileHelper {


    public static FileHelper instance ;


    public static FileHelper getInstance(){

        if(instance==null){
            instance = new FileHelper();
        }

        return instance ;
    }


    public static File readFile(String filename){

        File file = null ;
        try{
            file = new File(filename);
        }

        catch (Exception f){
            //throw new FailedToReadExternalFile();
        }
        return file ;

    }

    public String readFileAsResource(String filename ,boolean isSqlFile){


        StringBuilder stringBuilder = new StringBuilder("/sql/");
        if(!isSqlFile){
            stringBuilder = new StringBuilder("/files/");
        }

        stringBuilder.append(filename);
        String uri = stringBuilder.toString();

        filename = this.getClass().getResource(uri).getFile();
        File file =new File(filename);
        String contents = null ;

        try{
            contents = FileUtils.readFileToString(file , StandardCharsets.UTF_8);
        }

        catch(Exception e){
            e.printStackTrace();
        }
        return contents ;

    }

}
