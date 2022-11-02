package com.wese.weseaddons.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {

    public static File getFile(){

        File file = new File("C:\\Users\\weseaddons");

        if(!file.isDirectory()){

            file.mkdir();
        }

        File file1 = new File("C:\\Users\\weseaddons\\error.log");

        if(!file1.exists()){
            try{
                file1.createNewFile();
            }

            catch (IOException i){
                i.printStackTrace();
                System.out.println("File not found "+i.getMessage());
                return null ;
            }
        }

        return file1 ;

    }

    public static void log(String message){

        File file = getFile();

        if(file==null){
            return ;
        }

        FileWriter fileWriter =null;
        try{
            fileWriter = new FileWriter(file);
            fileWriter.append(message);

        }

        catch (IOException io){
            io.printStackTrace();
            System.out.println("Error when writing file");
        }

    }
}
