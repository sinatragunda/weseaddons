package com.wese.weseaddons.bereaudechange.fxrequest;

import com.wese.weseaddons.bereaudechange.pojo.FxCashier;
import com.wese.weseaddons.bereaudechange.pojo.Staff;
import com.wese.weseaddons.helper.TimeHelper;
import com.wese.weseaddons.jdbc.JdbcTemplateInit;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class TellerRequest {

    private String tenantIdentifier ;

    public TellerRequest(String s){
        this.tenantIdentifier = s ;
    }


    public long createTeller(Staff staff ,FxCashier fxCashier){

        long id= 0 ;

        JdbcTemplate jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenantIdentifier);

        String sql = "INSERT INTO m_tellers(office_id ,name ,description ,valid_from ,valid_to ,state) VALUES(? ,? ,?,?,? ,?)";

        try{

            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setLong(1,staff.getOfficeId());
                    statement.setString(2,fxCashier.getTellerName());
                    statement.setString(3,fxCashier.getDescription());
                    statement.setString(4 , TimeHelper.timestampToStringStrokeFormat(fxCashier.getActiveFromDate()));
                    statement.setString(5 ,TimeHelper.timestampToStringStrokeFormat(fxCashier.getActiveToDate()));
                    statement.setInt(6 ,300);
                    return statement;
                }
            }, holder);

            id = holder.getKey().longValue() ;

        }

        catch (Exception e) {
            e.printStackTrace();

        }

        return id ;
    }
}
