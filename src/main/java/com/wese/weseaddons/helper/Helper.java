package com.wese.weseaddons.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.json.JSONArray;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Helper{

	public static Date makeDateOutOfObject(List<Integer> list){

		SimpleDateFormat simpleDateFormatter = new SimpleDateFormat("yyyy MM dd");
		StringBuilder stringBuilder = new StringBuilder();

		for(int i : list){
			stringBuilder.append(String.format("%d " ,i));
		}

		Date date = null ;
		try{

			date = simpleDateFormatter.parse(stringBuilder.toString());
		}

		catch (ParseException p){

			System.out.println(p.getMessage());
		}

		return date ;

	}

	public static Date makeDateOutOfJsonArrayObject(JSONArray jsonArray){

		List<Integer> list = new ArrayList<>();

		for(int i =0 ;i < jsonArray.length() ;++i){

			list.add(jsonArray.getInt(i));
		}

		return makeDateOutOfObject(list);
	}

	public static Date dateNow(){

        Instant instant = Instant.ofEpochMilli(System.currentTimeMillis());
        Date date = Date.from(instant);
		return date ;
    }

    public static java.sql.Date sqlDateNow(){

		return new java.sql.Date(dateNow().getTime());

	}

	public static java.sql.Date dateToSqlDate(Date date){

    	return new java.sql.Date(date.getTime());

	}

	public static java.sql.Date  localDateTimeToSqlDate(LocalDateTime localDateTime){
		java.sql.Date d = java.sql.Date.valueOf(localDateTime.toLocalDate());
		return d ;
	}

	public static java.sql.Timestamp localDateTimeToSqlTimestamp(LocalDateTime localDateTime){
		java.sql.Timestamp d = java.sql.Timestamp.valueOf(localDateTime);
		return d ;
	}




	public static long timestampNow(){

		ZoneId zoneId = ZoneId.of("Africa/Harare");
		Clock clock = Clock.system(zoneId);
		Instant instant = Instant.now(clock);
		return instant.getEpochSecond();
	}

    public static ObjectNode createObjectNode(){

		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode objectNode = objectMapper.createObjectNode();
		return objectNode ;

	}

	public static ArrayNode createArrayNode(){

		ObjectMapper objectMapper = new ObjectMapper();
		ArrayNode arrayNode = objectMapper.createArrayNode();
		return arrayNode ;

	}

	public static ObjectNode statusNodes(boolean status){
    	return createObjectNode().put("status" ,status);

	}
}