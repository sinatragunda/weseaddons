package com.wese.weseaddons.ussd.helper;

import com.wese.weseaddons.pojo.LoanProducts;
import com.wese.weseaddons.ussd.enumerations.EXECUTE_FLAG;
import com.wese.weseaddons.ussd.enumerations.INPUT_FLAG;
import com.wese.weseaddons.ussd.enumerations.PHONE_NUMBER_FLAG;
import com.wese.weseaddons.ussd.enumerations.PREREASON_FLAG;
import com.wese.weseaddons.ussd.menu.USSDMenuRouter;
import com.wese.weseaddons.ussd.session.Session;
import com.wese.weseaddons.ussd.session.SessionDetails;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class Constants {


    public static int backOption = 10 ;
    public static int drawdownSavingsId = 1;
    public static String dummy = "acc123";
    public static String connection = "CON\n";
    public static String endConnection = "END";
    public static String locale="en_ZW";
    public static String dateFormat="dd MMM yyyy";
    public static String invalidInput = "You have selected invalid request \n :10 : Back\n";
    public static String invalidBackRequest = "Invalid back request";
    public static String noValidAccount = "No data found";
    public static String backRequest  = "10 : Back \n";
    public static String failedUssdTransaction = "Failed to do a transaction .Please try again";
    public static String genericError ="System error";
    public static String arrayOutOfBounds = "You have selected an invalid option";
    public static String unregisteredUser = "END\nYou are an unregistered customer  ,this platform only valid for Altus client ,our team will contact you";
    public static String underConstruction = "Class still under construction";
    public static String timeoutSession = "END\nYour session has timed out .Please restart";
    public static String transactionTerminated = "END\nYour Transaction has been terminated please try again ,system busy";
    public static String errorRaised = "Some error has been raised during execution";
    public static String amountIsAboveMax = "CON\nSorry the amount you have entered is above the maximum allowed\nEND";
    public static String amountIsBelowMax = "CON\nSorry the amount you have entered is below the minimum allowed\nEND";
    public static String inElligibleClient = "CON\nWe are sorry ,you are not elligible to take a loan ,you have monies in arrears\nEND";
    public static String password = "Taat@wesecbs2019";
    public static String username = "wese";
    //public static String apiUrl = "https://160.119.102.66/fineract-provider/api/v1";
    public static String apiUrl = "https://localhost:8445/fineract-provider/api/v1";
    public static long allowedDifference = 15 ;
    public static String tenantIdentifier = "demo";
    public static String keystoreFileLocations[] = {"C:\\Users\\HP\\Desktop\\.keystore","//root//Desktop//wese-UI//.keystore" ,"C:\\Users\\keys\\.keystore"};


    public static void setInputFlag(Session session ,boolean status ,int executeIndex){

        session.setInputFlag(INPUT_FLAG.OFF);

        if(status){

            session.setInputFlag(INPUT_FLAG.ON);
        }

        session.setExecuteIndex(executeIndex);
        SessionDetails.getInstance().updateSessionObject(session);

    }

    public static String stripPhoneNumber(String phoneNumber) {

        String strippedNumber = phoneNumber.substring(4);
        return strippedNumber ;

    }


    public static void setExecuteFlag(Session session ,boolean status ,int executeIndex){

        session.setExecuteFlag(EXECUTE_FLAG.OFF);

        if(status){

            session.setExecuteFlag(EXECUTE_FLAG.ON);
        }

        session.setExecuteIndex(executeIndex);
        SessionDetails.getInstance().updateSessionObject(session);

    }

    public static void setFlagsOff(Session session){

        session.setExecuteFlag(EXECUTE_FLAG.OFF);
        session.setInputFlag(INPUT_FLAG.OFF);
        session.setPhoneNumberFlag(PHONE_NUMBER_FLAG.OFF);
        session.setPreReasonFlag(PREREASON_FLAG.OFF);
        SessionDetails.getInstance().updateSessionObject(session);

    }

    public static String getTodayDate(){

        ZoneId zoneId = ZoneId.of("Africa/Harare");
        Clock clock = Clock.system(zoneId);
        Instant instant = Instant.now(clock);
        LocalDateTime localDataTime = LocalDateTime.ofInstant(instant ,zoneId);
        String pattern = "dd MMM yyyy";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        String time = localDataTime.format(dateTimeFormatter);
        return time ;


    }

    public static String  isLoanAmountWithinLimits(LoanProducts loanProducts , double principal ){

        if(principal > loanProducts.getMaxPrincipal()){

            return amountIsAboveMax ;
        }

        if(principal < loanProducts.getMinPrincipal()){

            return amountIsBelowMax ;
        }

        return null ;
    }

    public static  List<String> getCancelLoanReasons(){

        List<String> list = new ArrayList<>();
        list.add("Response too long");
        list.add("Got money from other lender");
        list.add("Personal reason");

        return list ;
    }

    public static boolean timeoutSession(long timestamp){

        long currentTimestamp = Instant.now().toEpochMilli();
        boolean timeout = (currentTimestamp - timestamp) > allowedDifference ? true : false ;
        return timeout ;

    }

    public static String concatOption(String option){

        int index = option.lastIndexOf("*");
        String value = option.substring(index);

        return value ;

    }

}
