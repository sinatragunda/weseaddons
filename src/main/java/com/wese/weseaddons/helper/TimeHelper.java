/*Created by Sinatra Gunda
  At 9:57 AM on 2/25/2020 */

package com.wese.weseaddons.helper;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date ;
import java.util.concurrent.TimeUnit;

public class TimeHelper {


	public static String dateToStringWithFormat(Date date,String pattern){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String val = simpleDateFormat.format(date);
        return val ;

    }

    public static Date dateNow(){

        Instant instant = Instant.now();
        Date date = Date.from(instant);
        return date ;

    }

    public static String timestampToString(long epoch){
        return timestampToString("dd MMM yyy" ,epoch);
    }

    public static String timestampToString(String pattern ,long epoch){

        Date date = timestampToDate(epoch);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String val = simpleDateFormat.format(date);
        return val ;
    }

    public static String timestampToStringWithPrecision(long epoch){
        return timestampToString("dd MMM yyyy : hhmm:ss" ,epoch);
    }

    public static String timestampToStringWithTime(long epoch){

        ZoneId zoneId = ZoneId.of(Constants.zimbabweTimeZone);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(epoch) ,zoneId);
        String pattern = "dd MMM yyyy : hhmm:ss";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return dateTimeFormatter.toString();
    }


    public static String dateToString(Date date ,String format){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);

    }

    public static String timestampToStringWithTimeForSecond(long epoch){

        ZoneId zoneId = ZoneId.of(Constants.zimbabweTimeZone);
        //ZoneId zoneId = ZoneId.of("Africa/Harare");
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(epoch) ,zoneId);

        String pattern = "dd MMM yyyy : HHmm:ss";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(dateTimeFormatter);

    }

    public static String timestampToStringEx(long epoch){

        Instant instant = Instant.ofEpochSecond(epoch);
        Date date = Date.from(instant);
        String pattern = "dd MMM yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String val = simpleDateFormat.format(date);
        return val ;

    }


    public static Date timestampToDate(long epoch){
        Instant instant = Instant.ofEpochMilli(epoch);
        Date date = Date.from(instant);
        return date ;
    }


    public static Date timestampToDateSecond(long epoch){
        Instant instant = Instant.ofEpochSecond(epoch);
        Date date = Date.from(instant);
        return date ;
    }

    public static long angularStringToDate(String value){

        if(value==null){

            Date date = Helper.dateNow();
            return Instant.ofEpochSecond(date.getTime()).getEpochSecond();

        }

        LocalDateTime localDateTime = rawDateToStringWithTime(value);
        return localDateTime.toEpochSecond(ZoneOffset.UTC);

    }

    public static LocalDateTime rawDateToStringWithTime(String stringDate){


        LocalDateTime localDateTime = LocalDateTime.now();
        int hour = localDateTime.getHour();

        String hourString = String.valueOf(hour);
        String minuteString = String.valueOf(localDateTime.getMinute());

        if(hour < 10){

            hourString = String.format("0%d",hour);
        }

        if(localDateTime.getMinute() < 10){

            minuteString = String.format("0%d",localDateTime.getMinute());
        }


        String fullDateTime = String.format("%s : %s%s",stringDate ,hourString ,minuteString);

        String pattern = "dd MMM yyyy : HHmm";

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);


        try{

            Instant instant  = simpleDateFormat.parse(fullDateTime).toInstant();
            localDateTime = LocalDateTime.ofInstant(instant ,getZoneId(null));

        }

        catch (ParseException parseException){
            return localDateTime(Constants.zimbabweTimeZone);
        }

        return localDateTime;

    }


    public static LocalDateTime localDateTime(String locale){

        ZoneId zoneId = ZoneId.of(locale);
        Clock clock = Clock.system(zoneId);
        LocalDateTime localDateTime = LocalDateTime.now(clock);
        return localDateTime ;
    }

    public static Date dateNow(String locale){

        ZoneId zoneId = ZoneId.of(locale);
        Clock clock = Clock.system(zoneId);
        Date date = Date.from(Instant.now(clock));
        return date ;
    }

    public static ZoneId getZoneId(String locale){
        if(locale==null){
            locale = "Africa/Harare";
        }
        return ZoneId.of(locale);
    }

    public static long dateToLong(Date date){
        return date.toInstant().toEpochMilli();
    }



    public static String timestampToStringStrokeFormat(long epoch){

        Date date = timestampToDate(epoch);
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String val = simpleDateFormat.format(date);
        return val ;

    }


    public static String timestampToStringTime(long epoch){

        Instant instant = Instant.ofEpochSecond(epoch);
        Date date = Date.from(instant);
        String pattern = "HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String val = simpleDateFormat.format(date);
        return val ;

    }


    public static Date timestampToDateEx(long epoch){
        Instant instant = Instant.ofEpochSecond(epoch);
        Date date = Date.from(instant);
        return date ;
    }


    public static Date date(){

        Instant instant = dateNow().toInstant();
        Date date = Date.from(instant);
        return date ;

    }

    public static java.sql.Date timestampToSqlDate(long epoch){

        return new java.sql.Date(epoch);

    }

    public static LocalDateTime timestampToDateWithTime(long epoch){

        Instant instant = Instant.ofEpochSecond(epoch);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant ,getZoneId("Africa/Harare"));
        return localDateTime ;
    }

    public static long stringToTimestamp(String stringDate){

        Date date = stringToDate(stringDate);
        return date.getTime() ;

    }

    public static boolean compareDates(Date left ,Date right){

        if(left.getYear()==right.getYear()){
            if(left.getMonth()==right.getMonth()){
                if(left.getDay()==right.getDay()){
                    return true;
                }
            }
        }

        return false ;
    }

    public static Long epochNow(){
        return dateNow().toInstant().toEpochMilli();
    }

    // added as a counter function since the instant not quick enough to generate new values with each iteration
    public static Long nanoTime(){
        return System.nanoTime();
    }

    public static Date stringToDate(String stringDate){

        String pattern = "dd MMM yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = null ;
        try{

            date = simpleDateFormat.parse(stringDate);
        }

        catch (ParseException | NullPointerException parseException){

            System.out.println("Parse exception");
            Date dateNow = dateNow("Africa/Harare");
            return dateNow ;
        }

        return date;

    }


    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit){

        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    public static Date dateFromArray(int []dateArray){

        /**
         * Date must be minused by 1900
         */
        int year = dateArray[0] - 1900;
        int month = dateArray[1]-1;
        int day = dateArray[2];

        Date date = new Date(year ,month ,day);
        return date;
    }



}
