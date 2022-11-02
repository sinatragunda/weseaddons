package com.wese.weseaddons.bereaudechange.mapper;

import com.wese.weseaddons.bereaudechange.dao.FxCashierDAO;
import com.wese.weseaddons.pojo.AppUser;
import com.wese.weseaddons.bereaudechange.pojo.FxCashier;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FxCashierMapper implements RowMapper<FxCashier> {

    @Override
    public FxCashier mapRow(ResultSet rs , int rowNum){

        FxCashier fxCashier = null ;

        try{

            fxCashier = new FxCashier();
            fxCashier.setId(rs.getLong("id"));
            fxCashier.setTellerName(rs.getString("teller_name"));
            fxCashier.setDescription(rs.getString("description"));

            long id = rs.getLong("app_user_id");
            fxCashier.setAppUser(new AppUser(id));

            fxCashier.setActiveToDate(rs.getLong("active_to_date"));
            fxCashier.setActiveFromDate(rs.getLong("active_from_date"));

            fxCashier.setSystemCashierId(rs.getLong("system_cashier_id"));
            fxCashier.setSystemTellerId(rs.getLong("system_teller_id"));
            fxCashier.setSystemStaffId(rs.getLong("system_staff_id"));
        }

        catch (SQLException s){
            s.printStackTrace();
        }

        return fxCashier ;

    }


}
