

package com.wese.weseaddons.helper ;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.enumerations.CLASS_TYPE;
import com.wese.weseaddons.errors.WeseErrorService;
import com.wese.weseaddons.interfaces.ExecutableClass;
import com.wese.weseaddons.interfaces.ListingFamily;

import java.util.List;
import java.util.Map;

public class ObjectNodeHelper{


	public static ObjectNode array(List<ExecutableClass> executableClassList,ListingFamily listingFamily){

	    String className = executableClassList.get(0).getClass().getName();
		ObjectNode objectNode = Helper.createObjectNode();

        ArrayNode arrayNode = Helper.createArrayNode();

        Map<String ,String> map = FunctionNameListing.get(listingFamily);

        for(ExecutableClass executableClass: executableClassList){

            ObjectNode objectNode1 = Helper.createObjectNode();

            for(Map.Entry k : map.entrySet()){

                String variable = k.getKey().toString();
                String functionName = k.getValue().toString();

                if(FunctionNameListing.isClassGetter(variable)){

                    ExecutableClass executableClass1 = (ExecutableClass) ExecuteMethod.classCasting(variable ,CLASS_TYPE.EXECUTABLE);
                    ListingFamily listingFamily1 = (ListingFamily) ExecuteMethod.classCasting(variable , CLASS_TYPE.LISTING);


                    executableClass1 = (ExecutableClass)ExecuteMethod.invoke(executableClass ,className ,functionName);

                    ObjectNode objectNode2 = object(executableClass1 ,listingFamily1);

                    objectNode1.putPOJO(functionName ,objectNode2);

                    continue;

                }

                String value = (String)ExecuteMethod.invoke(executableClass ,className ,functionName);

                objectNode1.put(variable ,value);

            }

            arrayNode.addPOJO(objectNode1);
        }


        objectNode.putPOJO("pageItems" ,arrayNode);

        return objectNode ;

	}

	public static ObjectNode objectNodeFromStringClassName(String className){

	    Class<?> classObject = null ;
	    ExecutableClass executableClass = null ;

	    try{

	        classObject = Class.forName(className);
	        executableClass = (ExecutableClass)classObject.cast(executableClass);

	        return object(executableClass ,(ListingFamily)executableClass);

        }

        catch (ClassNotFoundException a){
	        a.printStackTrace();
        }

        return null ;

    }

    public static ObjectNode object(ExecutableClass executableClass ,ListingFamily listingFamily){

        String className = executableClass.getClass().getName();

        ObjectNode objectNode = Helper.createObjectNode();

        Map<String ,String> map = FunctionNameListing.get(listingFamily);


        for(Map.Entry k : map.entrySet()){

            String variable = k.getKey().toString();
            String functionName = k.getValue().toString();

            if(FunctionNameListing.isClassGetter(variable)){
                continue;
            }

            String value = (String)ExecuteMethod.invoke(executableClass ,className ,functionName);
            objectNode.put(variable ,value);

        }
        return objectNode ;

    }

    public static ObjectNode controllerAdviceError(WeseErrorService weseErrorService){

        ObjectNode objectNode = Helper.createObjectNode();
        objectNode.put("status" ,false);
        objectNode.put("message" ,weseErrorService.getMessage());
        return objectNode ;

    }

    public static ObjectNode objectNodeFromString(String arg){

        ObjectMapper objectMapper = new ObjectMapper();
        try{
            return (ObjectNode) objectMapper.readTree(arg);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null ;
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