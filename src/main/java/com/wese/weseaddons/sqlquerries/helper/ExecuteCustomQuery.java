package com.wese.weseaddons.sqlquerries.helper;

import com.wese.weseaddons.daofactory.MapperFactory;
import com.wese.weseaddons.interfaces.PojoInterface;
import com.wese.weseaddons.jdbc.JdbcTemplateInit;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class ExecuteCustomQuery {


    public static List<?extends  PojoInterface> execute(MapperFactory mapperFactory, String tenantIdentifier , String query){

        JdbcTemplate jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenantIdentifier);

        List<PojoInterface> pojoInterfaceList = jdbcTemplate.query(query ,mapperFactory);

        return pojoInterfaceList;

    }

}
