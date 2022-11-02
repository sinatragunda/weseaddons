/*Created by Sinatra Gunda
  At 7:36 AM on 2/26/2020 */

package com.wese.weseaddons.crb.dao;

import com.wese.weseaddons.crb.mapper.SyncWithCrbMapper;
import com.wese.weseaddons.crb.pojo.SyncWithCrb;
import com.wese.weseaddons.enumerations.TYPE_OF_RESOURCE;
import com.wese.weseaddons.jdbc.JdbcTemplateInit;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SyncWithCrbDAO {

    private JdbcTemplate jdbcTemplate ;


    public SyncWithCrbDAO(String tenantIdentifier) {

        this.jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenantIdentifier);

    }
//
//    public ObjectNode update(Token token){
//
//        if(token==null){
//
//            return Helper.statusNodes(false);
//        }
//
//        String sql = "update m_crb_token set token = ? ,expires_after= ? ,status = ? where id = ?";
//        Object args[]= new Object[]{
//                token.getToken(),
//                token.getExpiresAfter() ,
//                token.getStatus() ,
//                1
//        };
//
//        int rows = jdbcTemplate.update(sql ,args);
//
//        if(rows > 0){
//            return Helper.statusNodes(true);
//        }
//
//        return Helper.statusNodes(false);
//    }

    public SyncWithCrb findCrbRecord(long resourceId, TYPE_OF_RESOURCE typeOfResource){

        String sql = "SELECT * FROM m_crb_sync where resource_id = ? AND type_of_resource = ?";

        Object args[]= new Object[]{
                resourceId ,
                typeOfResource.ordinal()
        };

        List<SyncWithCrb> syncWithCrbList = jdbcTemplate.query(sql ,args ,new SyncWithCrbMapper());

        if (syncWithCrbList.isEmpty()){
            return null;
        }

        return syncWithCrbList.get(0);

    }




    public void create(SyncWithCrb syncWithCrb){

        String sql = "INSERT INTO m_crb_sync(type_of_resource ,resource_id) VALUES(? , ?)";

        long id = 0 ;
        try {

            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setInt(1,syncWithCrb.getTypeOfResource().ordinal());
                    statement.setLong(2,syncWithCrb.getResourceId());


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
