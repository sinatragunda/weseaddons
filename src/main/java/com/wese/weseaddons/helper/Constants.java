package com.wese.weseaddons.helper;

import com.wese.weseaddons.utility.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.boot.autoconfigure.mail.MailProperties;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class Constants {

    public static boolean homeTest = true ;

//    public static String password = "Taat@wesecbs2019";
//    public static String username = "wese";

    public static String apiUrlLive = "https://66.45.238.189/fineract-provider/api/v1";

    public static String apiUrlProduction = "https://localhost:8445/fineract-provider/api/v1";

    //public static String apiUrlProduction = "https://66.45.238.189/fineract-provider/api/v1";


    public static String keystoreFilename = homeTest ?"C:\\Users\\HP\\Desktop\\.keystore":"C:\\Users\\Sinatra Gunda\\Desktop\\.keystore";
    public static String dateFormat = "d MMM yyyy";
    public static String database = "jdbc:mysql://localhost:3306/";
    public static String defaultUsername = "root";
    public static String defaultPassword = "mysql";
    public static String defaultDriverClassName = "com.mysql.cj.jdbc.Driver";
    public static String zimbabweTimeZone = "Africa/Harare";
    public static String keystoreFileLocations[] = {"C:\\Users\\HP\\Desktop\\.keystore","//root//Desktop//wese-UI//.keystore" ,"C:\\Users\\keys\\.keystore" ,"//usr//share//tomcat7//webapps//.keystore" ,"C:\\Users\\user\\Desktop\\.keystore" ,"//home//taat//.keystore"};
    public static String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
    public static String nonTradingHours = "This transaction can only be made during trading hours .Please wait or review your settings";
    public static String invalidDate = "From date comes before after date ,please try to correct";
    public static String isNotCashier = "Please note we have detected that you are perfoming a transaction from a non registered Cashier .Transactions are done through cashiers only";
    public static String hasSettled = "Transaction can't be perfomed as the requested amount is greater than balance in your till";
    public static String invalidTick = "You have created a currency with invalid tick or market symbol please try this notation EUR/USD .Exactly 3 letters or characters";
    public static String noLocalCurrency = "You have not set your local currency .Please go to Settings and set first as it is a requirement";
    public static String noMainTrade ="Main currency trade pair is not set ,please go to settings and select where written main trade trade";
    public static String failedUpdate ="Failed to update a record to the database";
    public static String failedCreate = "Failed to create new record ,please try again and check input data";
    public static String validateDealFailed = "Failed to validate this fx deal ,please make sure you have provided data for all the input fields editable .Common field is Purpose ,kindly check that";
    public static String dailyLimitReached = "Your organisation has reached it's daily fx trade limit .Please try again tomorrow";
    public static String failedAuthorization = "Failed to authorise this action ,your credentials are invalid";
    public static String noAvailableData ="No available data";
    public static String noValidSavingsAccount ="No valid savings account";
    public static String bdxDatabaseList[] = {"deal" ,"currency_pair" ,"trading_rates" ,"live_rates" ,"trading_currency","money_account" ,"account_charge_mapping" ,"blotter" ,"bulk_deal_mapper","cashier","charges","daily_tracker","equivalant","financial_instruments","funds_action" ,"live_rates","revaluation","settings","transactions","transaction_charge_mapping"};
    public static String  bdxClassNames[] = {"FxDeal","Blotter","CurrencyPair","Charges","FinancialInstrument","FundsAction","FxCashier","FxDailyTracker","FxEquivalent","FxHistory" ,"LiveRates","MoneyAccount" ,"MoneyAccountTransactions"  ,"MoneyAccountChargesRM","FxMarkUp","Settings","StandardCurrency","TradingCurrency","TradingRates","TransactionCharge"};
    public static String bdxTenantsList[] = {"demo"};
    public static String insufficientFunds = "Transaction can't be perfomed as the requested amount is greater than balance in your till";
    public static String failedTransaction = "Transaction has failed ,please check all parameters";
    public static String transactionNotFound = "Transaction has not been found ,please check the authenticity of your key";


    public static String taskNote ="Task Note Updated";
    public static String crbInfinityCode = "zm123456789";
    public static String crbUsername = "ZMDU_VS";
    public static String crbPassword = "K2MhiHYe6dXu77U";
    public static String crbDateFormat = "ddMMyyyy";
    public static String crbSecureUrl = "https://secure3.crbafrica.com/duv2/data/zm/update/";

    public static String bereauDeChangeClients[] = {"demo"};
    public static String crbClients[] = {"demo"};
    public static String dataNotCollected = "Data Not Collected";
    public static String separator = "---------------------------------------------------------------------------------------------------------------------------------------------------------------";


    public static String basePackage = "com.wese.weseaddons.controller";
    public static Map<String ,String[]> passwordMap = initMap();

    @Value("${app.live}")
    static boolean isLive = true;

    public synchronized static Map<String ,String[]> initMap(){

        boolean isInit = Optional.ofNullable(passwordMap).isPresent();

        if(!isInit){

            passwordMap = new HashMap<>();
            String[] demo = {"wesecbs" ,"demo123"};
            String[] altus = {"wese" ,"Taat@wesecbs2019"};
            String[] all = {"wesecbs" ,"password"};

            passwordMap.put("demo" ,demo);
            passwordMap.put("all" ,all);
            passwordMap.put("altus" ,altus);
        }
        return passwordMap;
    }

    public synchronized static String[] credentialsMap(){

        String tenantIdentifier = ThreadContext.getTenant();

        if(passwordMap.containsKey(tenantIdentifier)){
            return passwordMap.get(tenantIdentifier);
        }
        return passwordMap.get("all");
    }

    public static String apiUrl(String endpoint){

        StringBuilder stringBuilder = new StringBuilder(apiUrlProduction);

        if(isLive){
            System.err.println("------------------------is live ? "+isLive);
            stringBuilder = new StringBuilder(apiUrlLive);
        }

        stringBuilder.append("/"+endpoint);
        return stringBuilder.toString();
    }

}
