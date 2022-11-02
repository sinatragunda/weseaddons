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

public class CashierRequest {

    private String tenantIdentifier ;
    private JdbcTemplate jdbcTemplate ;

    public CashierRequest(String s){

        this.tenantIdentifier = s ;
        this.jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenantIdentifier);

    }

    public long createCashier(Staff staff ,FxCashier fxCashier ,long tellerId){

        long id= 0 ;


        String sql = "INSERT INTO m_cashiers(staff_id ,teller_id ,start_date ,end_date ,full_day ,description) VALUES(? ,? ,?,?,? , ?)";

        try{

            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setLong(1,staff.getId());
                    statement.setLong(2,tellerId);
                    statement.setString(3 , TimeHelper.timestampToStringStrokeFormat(fxCashier.getActiveFromDate()));
                    statement.setString(4 ,TimeHelper.timestampToStringStrokeFormat(fxCashier.getActiveToDate()));
                    statement.setInt(5 ,1);
                    statement.setString(6 ,fxCashier.getDescription());
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
