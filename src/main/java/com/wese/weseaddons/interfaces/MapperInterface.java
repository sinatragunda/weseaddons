package com.wese.weseaddons.interfaces;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;

public interface MapperInterface{

    PojoInterface mapRow(ResultSet resultSet , int i);

}
