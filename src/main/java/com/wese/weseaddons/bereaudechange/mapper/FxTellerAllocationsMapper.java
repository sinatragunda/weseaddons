package com.wese.weseaddons.bereaudechange.mapper;

import com.wese.weseaddons.bereaudechange.enumerations.FINANCIAL_INSTRUMENT_TYPE;
import com.wese.weseaddons.bereaudechange.enumerations.T_ACCOUNT;
import com.wese.weseaddons.pojo.AppUser;
import com.wese.weseaddons.bereaudechange.pojo.FxCashier;
import com.wese.weseaddons.bereaudechange.pojo.FxTellerAllocations;
import com.wese.weseaddons.bereaudechange.pojo.StandardCurrency;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FxTellerAllocationsMapper implements RowMapper<FxTellerAllocations> {


    @Override
    public FxTellerAllocations mapRow(ResultSet rs , int rowNum){

        FxTellerAllocations fxTellerTransactiona =null ;
        
        try{
            fxTellerTransactiona = new FxTellerAllocations();
            fxTellerTransactiona.setId(rs.getLong("id"));
            fxTellerTransactiona.setAmount(rs.getDouble("amount"));

            long id = rs.getLong("authoriser_id");
            fxTellerTransactiona.setAuthoriser(new AppUser(id));

            long id2= rs.getLong("standard_currency_id");
            fxTellerTransactiona.setStandardCurrency(new StandardCurrency(id2));

            fxTellerTransactiona.setDate(rs.getLong("date"));

            int financialInstrumentType = rs.getInt("financial_instrument_type");

            fxTellerTransactiona.setFinancialInstrumentType(FINANCIAL_INSTRUMENT_TYPE.fromInt(financialInstrumentType));

            int tAccount = rs.getInt("t_account");
            fxTellerTransactiona.settAccount(T_ACCOUNT.fromInt(tAccount));

            long id3 = rs.getLong("fx_cashier_id");
            fxTellerTransactiona.setFxCashier(new FxCashier(id3));

        }

        catch (SQLException s){
            s.printStackTrace();
        }

        return fxTellerTransactiona ;
    }
}
