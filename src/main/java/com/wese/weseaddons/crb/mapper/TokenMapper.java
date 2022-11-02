package com.wese.weseaddons.crb.mapper;

import com.wese.weseaddons.crb.pojo.Token;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TokenMapper implements RowMapper<Token> {


    @Override
    public Token mapRow(ResultSet rs , int rowNum){

        Token token =null ;

        try{
            token = new Token();
            token.setToken(rs.getString("token"));
            token.setExpiresAfter(rs.getString("expires_after"));
            token.setStatus(rs.getString("status"));
            
        }

        catch (SQLException s){
            s.printStackTrace();
        }

        return token ;
    }
}
