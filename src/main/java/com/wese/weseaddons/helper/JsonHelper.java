package com.wese.weseaddons.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class JsonHelper {


    public static String objectToJson(Object object){

        String json = null ;

        try{
            json = new ObjectMapper().writeValueAsString(object);
        }

        catch (JsonProcessingException j){
            j.printStackTrace();
        }

        return json ;
    }

    public static <T> T stringToObject(String value ,T t){
        T object = null ;
        try{
            object = (T) new ObjectMapper().readValue(value ,t.getClass());
        }
        catch (IOException i){
            i.printStackTrace();
        }

        return object ;
    }

    public static <T> T serializeFromHttpResponse(T object ,String arg){

        boolean isPresent = Optional.ofNullable(arg).isPresent();
        T genericData = null ;
        if (isPresent) {
            ObjectMapper objectMapper = new ObjectMapper();
            //objectMapper.enable(De)
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNRESOLVED_OBJECT_IDS);
            objectMapper.disable(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE);
            try {
                genericData = (T) objectMapper.readValue(arg, object.getClass());
            } catch (IOException io) {
                io.printStackTrace();
            }
        }

        return genericData ;
    }

    public static List serializeListFromHttpResponse(Object object ,String arg){

        boolean isPresent = Optional.ofNullable(arg).isPresent();

        List genericList = null ;
        if (isPresent) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNRESOLVED_OBJECT_IDS);
            objectMapper.disable(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE);
            CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class ,object.getClass());
            try {
                genericList = objectMapper.readValue(arg, collectionType);
            } catch (IOException io) {
                io.printStackTrace();
            }
        }

        return genericList ;
    }

}
