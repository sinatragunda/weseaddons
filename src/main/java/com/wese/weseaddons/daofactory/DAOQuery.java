package com.wese.weseaddons.daofactory;


import com.wese.weseaddons.helper.ListHelper;
import com.wese.weseaddons.interfaces.MapperInterface;
import com.wese.weseaddons.interfaces.PojoInterface;
import com.wese.weseaddons.jdbc.JdbcTemplateInit;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class DAOQuery {

    public static List<PojoInterface> findAll(PojoInterface pojoInterface ,String tenant){

        String tableName = pojoInterface.getSchema();
        JdbcTemplate jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenant);

        String sql = String.format("SELECT * FROM %s",tableName);

        MapperFactory mapperFactory = getMapperFactory(pojoInterface);
        List<PojoInterface> pojoInterfaceList = jdbcTemplate.query(sql ,mapperFactory);

        if(pojoInterfaceList.isEmpty()){
            return new ArrayList<>();
        }

        return pojoInterfaceList ;

    }

    public static void reloadSingleTable(PojoInterface pojoInterface,String tenant){

        String tableName = pojoInterface.getSchema();
        JdbcTemplate jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenant);

        String sql = String.format("SELECT * FROM %s",tableName);

        MapperFactory mapperFactory = getMapperFactory(pojoInterface);
        List<PojoInterface> pojoInterfaceList = jdbcTemplate.query(sql ,mapperFactory);

        if(pojoInterfaceList.isEmpty()){
            return;
        }

        String className = pojoInterface.getClass().getSimpleName();

//        synchronized (DAOQuery.class){
//
//            boolean hasLock = bdxTenantData.getBdxCache().getWriteLockMap().get(className);
//
//            System.err.println("------------------ has lock to update this field "+hasLock);
//
//            while(hasLock) {
//                try {
//
//                    Thread.sleep(5000);
//                }
//
//                catch (InterruptedException i) {
//                    // TODO: handle exception
//                    i.printStackTrace();
//                }
//            }
//
//            bdxTenantData.getBdxCache().getWriteLockMap().replace(className,true);
//            bdxTenantData.getBdxCache().getBdxCacheMap().replace(className ,pojoInterfaceList);
//            bdxTenantData.getBdxCache().getWriteLockMap().replace(className,false);
        //}
    }



    public static PojoInterface find(PojoInterface pojoInterface ,String tenant ,long id){

        String tableName = pojoInterface.getSchema();
        JdbcTemplate jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenant);

        String sql = String.format("SELECT * FROM %s WHERE id = ?",tableName);

        Object args[] = new Object[]{
                id
        };

        MapperFactory mapperFactory = getMapperFactory(pojoInterface);
        List<PojoInterface> pojoInterfaceList = jdbcTemplate.query(sql ,args , mapperFactory);
        return ListHelper.get(pojoInterfaceList ,0);

    }

    public static List<PojoInterface> findWhere(PojoInterface pojoInterface ,String tenant ,String columnName, String id){

        String tableName = pojoInterface.getSchema();

        JdbcTemplate jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenant);

        String sql = String.format("SELECT * FROM %s WHERE %s = ?",tableName ,columnName);

        Object args[] = new Object[]{
                id
        };

        MapperFactory mapperFactory = getMapperFactory(pojoInterface);
        List<PojoInterface> pojoInterfaceList = jdbcTemplate.query(sql ,args , mapperFactory);

        return pojoInterfaceList ;


    }

    public static PojoInterface find(List<? extends PojoInterface> pojoInterfaceList ,long id){

        if(pojoInterfaceList.isEmpty()){
            return null ;
        }

        List<PojoInterface> immutablePojoList = Collections.unmodifiableList(pojoInterfaceList);

        Iterator<PojoInterface> pojoInterfaceIterator = immutablePojoList.iterator();

        while(pojoInterfaceIterator.hasNext()){

            PojoInterface pojoInterface = pojoInterfaceIterator.next();
            Long pojoId = pojoInterface.getId();

            if(pojoId==id){
                return pojoInterface ;
            }
        }

        return null;

    }

    public static MapperFactory getMapperFactory(PojoInterface pojoInterface){

        Class<?> mapperClass = null;
        MapperInterface mapperInterface = null;
        try{
            mapperClass = Class.forName(DAOHelper.getClassMapperName(pojoInterface));
            mapperInterface = (MapperInterface) mapperClass.newInstance();
        }

        catch (InstantiationException | ClassNotFoundException | IllegalAccessException c){
            c.printStackTrace();
        }

        return new MapperFactory(mapperInterface);

    }
}
