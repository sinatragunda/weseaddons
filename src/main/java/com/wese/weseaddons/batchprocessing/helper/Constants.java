
package com.wese.weseaddons.batchprocessing.helper;

import com.wese.weseaddons.pojo.SavingsAccount;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class Constants {


    public static boolean homeTest = true ;
    public static int backOption = 10 ;
    public static int drawdownSavingsId = 1;
    public static String dummy = "acc123";
    public static String password ="Taat@wesecbs2019";
    public static String username ="wese";
    public static String apiUrl = "https://160.119.102.66/fineract-provider/api/v1";
    //public static String apiUrl = "https://localhost:8445/fineract-provider/api/v1";
    public static String tenantIdentifier;
    public static String keystoreFilename = homeTest ?"C:\\Users\\HP\\Desktop\\.keystore":"C:\\Users\\Sinatra Gunda\\Desktop\\.keystore";
    public static List<SavingsAccount> savingsAccountList = new ArrayList<>();
    public static String dateFormat = "dd MMM yyyy";
    public static String nameColumn ;
    public static String keystoreFileLocations[] = {"C:\\Users\\HP\\Desktop\\.keystore","//root//Desktop//wese-UI//.keystore"};



    public static String getTodayDate(){

        ZoneId zoneId = ZoneId.of("Africa/Harare");
        Clock clock = Clock.system(zoneId);
        Instant instant = Instant.now(clock);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant ,zoneId);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat);
        String time = localDateTime.format(dateTimeFormatter);
        return time ; 

    }

    public static int validateDate(String arg){
        
       
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat);
        LocalDate localDate= null ;
        try {
            
            localDate = LocalDate.parse(arg,dateTimeFormatter);
            
        }  
        catch(DateTimeParseException e) {
            
            e.printStackTrace();
            return -1 ;
        }

        if(localDate != null){

            LocalDate dateNow = LocalDate.parse(getTodayDate() ,dateTimeFormatter);
            if(localDate.isAfter(dateNow)){
                return 0 ;
            }

        }

        return 1 ;
    }

    public static boolean isNrcNumber(String externalId){

        String delim = "/";
        StringTokenizer stringTokenizer = new StringTokenizer(externalId ,delim);
        while(stringTokenizer.hasMoreElements()){

            return true ;
        }

        return false ;

    }

 

}
