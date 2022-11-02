package com.wese.weseaddons.sqlquerries.helper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.errors.springexceptions.SqlQueryParameterNotFound;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractAndReplaceParam {


    public static String extract(ObjectNode objectNode ,String sql ,String[] params){

        String newSql = sql ;
        int index = params.length;

        for(int i= 0 ; i < index ;++i){


            String regex = String.format("\\$\\{%s}",params[i]);
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(newSql);

            if(matcher.find()){

                String arg = params[i];
                String rarg = "R_"+params[i];
                if(objectNode.has(arg)){
                    JsonNode jsonNode = objectNode.get(arg);
                    newSql = matcher.replaceAll(jsonNode.asText());
                    continue ;
                }

                else if(objectNode.has(rarg)){
                    JsonNode jsonNode = objectNode.get(rarg);
                    newSql = matcher.replaceAll(jsonNode.asText());
                    continue ;
                }

                throw new SqlQueryParameterNotFound(arg);
            }
        }
        return newSql ;

    }

}
