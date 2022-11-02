package com.wese.weseaddons.daofactory;

import com.wese.weseaddons.interfaces.PojoInterface;
import org.springframework.jdbc.core.RowMapper;
import com.wese.weseaddons.interfaces.MapperInterface;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class MapperFactory implements RowMapper {

    private MapperInterface mapperInterface ;

    public MapperFactory(MapperInterface mapperInterface){
        this.mapperInterface = mapperInterface ;
    }

    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        return mapperInterface.mapRow(resultSet ,i);
    }

    public static MapperFactory getMapperFactory(PojoInterface pojoInterface , String className){

        Class<?> mapperClass = null;
        MapperInterface mapperInterface = null;
        try{
            mapperClass = Class.forName(className);
            mapperInterface = (MapperInterface) mapperClass.newInstance();
        }

        catch (InstantiationException | ClassNotFoundException | IllegalAccessException c){
            c.printStackTrace();
        }

        return new MapperFactory(mapperInterface);

    }
}
