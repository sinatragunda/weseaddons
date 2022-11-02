package com.wese.weseaddons.mapper;

import com.wese.weseaddons.pojo.WeseAppUser;
import org.springframework.jdbc.core.RowMapper;


import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<WeseAppUser> {

    public WeseAppUser mapRow(ResultSet resultSet ,int rowNum){

        WeseAppUser weseAppUser = null ;

        try{

            weseAppUser = new WeseAppUser();
            weseAppUser.setUsername(resultSet.getString("username"));

        }

        catch (SQLException sqlException){

            System.out.println("Some exception thrown here");
        }

        return weseAppUser ;
    }


}
