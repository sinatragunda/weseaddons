package com.wese.weseaddons.bereaudechange.mapper;

import com.wese.weseaddons.bereaudechange.pojo.CurrencyPair;
import com.wese.weseaddons.bereaudechange.pojo.Settings;
import com.wese.weseaddons.bereaudechange.pojo.StandardCurrency;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SettingsMapper implements RowMapper<Settings> {

    @Override
    public Settings mapRow(ResultSet rs , int rownNum){

        Settings settings =null;

        try{
            settings = new Settings();
            settings.setId(rs.getLong("id"));
            settings.setDailyLimit(rs.getDouble("daily_limit"));
            settings.setOpeningTime(rs.getInt("opening_time"));
            settings.setClosingTime(rs.getInt("closing_time"));
            settings.setCompanyName(rs.getString("company_name"));
            settings.setCompanyCity(rs.getString("company_city"));
            settings.setCompanyAddress(rs.getString("company_address"));

            long id = rs.getLong("accounting_currency_id");
            settings.setAccountingCurrency(new StandardCurrency(id));

            int revalType = rs.getInt("auto_reval_type");
            settings.setAutoRevalType(revalType);

            settings.setDistrict(rs.getString("district"));

            long id2 = rs.getLong("currency_pair_id");
            settings.setCurrencyPair(new CurrencyPair(id2));

            long id3 = rs.getLong("local_currency_id");
            settings.setLocalCurrency(new StandardCurrency(id3));
            
            int profitCalculationMethodInt = rs.getInt("profit_calculation_method");
            settings.setProfitCalculationMethod(profitCalculationMethodInt);

        }
        catch (SQLException s){
            s.printStackTrace();
        }

        return settings ;

    }
}
