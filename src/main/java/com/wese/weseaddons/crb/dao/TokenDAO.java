
package com.wese.weseaddons.crb.dao;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.crb.mapper.TokenMapper;
import com.wese.weseaddons.crb.pojo.Token;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.jdbc.JdbcTemplateInit;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class TokenDAO {

    private JdbcTemplate jdbcTemplate ;


    public TokenDAO(String tenantIdentifier) {

        this.jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenantIdentifier);

    }

    public ObjectNode update(Token token){

        if(token==null){
            return Helper.statusNodes(false);
        }

        String sql = "update m_crb_token set token = ? ,expires_after= ? ,status = ? where id = ?";
        Object args[]= new Object[]{
                token.getToken(),
                token.getExpiresAfter() ,
                token.getStatus() ,
                1
        };

        int rows = jdbcTemplate.update(sql ,args);

        if(rows > 0){
            return Helper.statusNodes(true);
        }

        return Helper.statusNodes(false);
    }

    public Token find(){

        String sql = "SELECT * FROM m_crb_token";

        List<Token> tokenList = jdbcTemplate.query(sql ,new TokenMapper());

        if (tokenList.isEmpty()){
            return null;
        }

        return tokenList.get(0);

    }

    public void create(Token token){

        System.err.println("------------------------token length ----------------"+token.getToken().length());

        String sql = "INSERT INTO m_crb_token(token,expires_after,status) VALUES(? , ? ,?)";
        long id = 0 ;
        try {

            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setString(1,token.getToken());
                    statement.setString(2,token.getExpiresAfter());
                    statement.setString(3,token.getStatus());

                    return statement;
                }
            }, holder);

            id = holder.getKey().longValue();

        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}
