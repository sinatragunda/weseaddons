package com.wese.weseaddons.jdbc;

import com.wese.weseaddons.daofactory.MapperFactory;
import com.wese.weseaddons.helper.Constants;
import com.wese.weseaddons.interfaces.PojoInterface;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class JdbcTemplateInit {


    public static JdbcTemplate getJdbcTemplate(String tenantIdentifier){

        StringBuilder stringBuilder = new StringBuilder(Constants.database);
        stringBuilder.append(tenantIdentifier);
        String url = stringBuilder.toString();

        JdbcConnectionProperties jdbcConnectionProperties = new JdbcConnectionProperties(url);
        JdbcInit jdbcInit = new JdbcInit(jdbcConnectionProperties);
        JdbcTemplate jdbcTemplate = jdbcInit.getJdbcTemplate();

        return jdbcTemplate ;

    }

    public static <T> List<T> template(PojoInterface pojoInterface, JdbcTemplate jdbcTemplate, String sql , boolean hasArg ,Object[] args){

        String className = getClassMapperNameTaskManager(pojoInterface);
        MapperFactory mapperFactory = MapperFactory.getMapperFactory(pojoInterface ,className);

        List<T> tList =null;

        if(hasArg){
            tList = jdbcTemplate.query(sql ,args ,mapperFactory);
        }
        else{
            tList = jdbcTemplate.query(sql,mapperFactory);
        }

        if (tList.isEmpty()){
            return new ArrayList<>();
        }
        return tList ;

    }

    public static String getClassMapperNameTaskManager(PojoInterface pojoInterface){

        String className = pojoInterface.getClass().getSimpleName();
        StringBuilder stringBuilder = new StringBuilder("com.wese.weseaddons.taskmanager.mapper.");
        stringBuilder.append(className+"Mapper");
        return stringBuilder.toString();
    }



}
