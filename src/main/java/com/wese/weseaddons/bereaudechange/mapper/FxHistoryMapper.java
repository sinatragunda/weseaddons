package com.wese.weseaddons.bereaudechange.mapper;
import com.wese.weseaddons.pojo.AppUser;
import com.wese.weseaddons.bereaudechange.pojo.FxHistory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FxHistoryMapper implements RowMapper<FxHistory>{

        @Override
        public FxHistory mapRow(ResultSet rs , int rowNum){

                FxHistory history = null ;
                try{

                        history = new FxHistory();
                        history.setId(rs.getLong("id"));

                        history.setResourceId(rs.getLong("resource_id"));
                        history.setAuditTrail(rs.getInt("audit_trail"));
                        history.setDate(rs.getLong("date"));

                        long id = rs.getLong("authoriser_id");

                        history.setAuthorizer(new AppUser(id));

                }

                catch(SQLException s){
                        s.printStackTrace();
                }

                return history ;
        }
}