package com.wese.weseaddons.bereaudechange.dao;

import com.wese.weseaddons.bereaudechange.helper.ForeignKeyHelper;
import com.wese.weseaddons.bereaudechange.mapper.FxTellerAllocationsMapper;
import com.wese.weseaddons.helper.TimeHelper;
import com.wese.weseaddons.pojo.AppUser;
import com.wese.weseaddons.bereaudechange.pojo.FxCashier;
import com.wese.weseaddons.bereaudechange.pojo.FxTellerAllocations;
import com.wese.weseaddons.bereaudechange.pojo.StandardCurrency;
import com.wese.weseaddons.bereaudechange.session.FxSession;
import com.wese.weseaddons.jdbc.JdbcTemplateInit;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FxTellerAllocationsDAO {

    private String tenant ;
    private JdbcTemplate jdbcTemplate ;

    public FxTellerAllocationsDAO(String s){
        this.tenant = s ;
        this.jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenant);

    }

    public List<FxTellerAllocations> findAll(){

        String sql = "SELECT * FROM m_fx_teller_allocations";

        List<FxTellerAllocations> fxTellerAllocationsList = jdbcTemplate.query(sql ,new FxTellerAllocationsMapper());

        if(fxTellerAllocationsList.isEmpty()){
            return new ArrayList<>();
        }

        for(FxTellerAllocations fxTellerAllocations : fxTellerAllocationsList){
            StandardCurrency standardCurrency = (StandardCurrency) ForeignKeyHelper.get(new StandardCurrencyDAO(tenant) ,fxTellerAllocations.getStandardCurrency().getId());

            fxTellerAllocations.setStandardCurrency(standardCurrency);

            AppUser appUser = FxSession.getInstance().getAppUser(tenant ,fxTellerAllocations.getAuthoriser().getId());
            fxTellerAllocations.setAuthoriser(appUser);


            FxCashier fxCashier = (FxCashier)ForeignKeyHelper.get(new FxCashierDAO(tenant) ,fxTellerAllocations.getFxCashier().getId());
            fxTellerAllocations.setFxCashier(fxCashier);
        }

        return fxTellerAllocationsList;

    }

    public FxTellerAllocations find(long id){

        String sql = "SELECT * FROM m_fx_teller_allocations where id = ?";
        Object object[] = new Object[]{
                id
        };


        List<FxTellerAllocations> fxTellerAllocationsList = jdbcTemplate.query(sql ,object ,new FxTellerAllocationsMapper());

        if(fxTellerAllocationsList.isEmpty()){
            return null;
        }

        for(FxTellerAllocations fxTellerAllocations : fxTellerAllocationsList){
            StandardCurrency standardCurrency = (StandardCurrency) ForeignKeyHelper.get(new StandardCurrencyDAO(tenant) ,fxTellerAllocations.getStandardCurrency().getId());

            fxTellerAllocations.setStandardCurrency(standardCurrency);

            AppUser appUser = FxSession.getInstance().getAppUser(tenant ,fxTellerAllocations.getAuthoriser().getId());
            fxTellerAllocations.setAuthoriser(appUser);

            FxCashier fxCashier = (FxCashier)ForeignKeyHelper.get(new FxCashierDAO(tenant) ,fxTellerAllocations.getFxCashier().getId());
            fxTellerAllocations.setFxCashier(fxCashier);
        }

        return fxTellerAllocationsList.get(0);

    }

    public void create(FxTellerAllocations fxTellerAllocations){

        String sql= "insert into m_fx_TellerAllocations(t_account ,amount ,authoriser_id ,date ,standard_currency_id ,financial_instrument_type ,fx_cashier_id) values (? ,? ,? ,? ,? ,? ,?)";
        try{

            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setInt(1,fxTellerAllocations.gettAccount().ordinal());
                    statement.setDouble(2,fxTellerAllocations.getAmount());
                    statement.setLong(3 ,fxTellerAllocations.getAuthoriser().getId());
                    statement.setLong(4 , TimeHelper.epochNow());
                    statement.setLong(5 , fxTellerAllocations.getStandardCurrency().getId());
                    statement.setInt(6,fxTellerAllocations.getFinancialInstrumentType().ordinal());
                    statement.setLong(7 ,fxTellerAllocations.getFxCashier().getId());
                    return statement;
                }
            }, holder);

        }

        catch (Exception e) {
            e.printStackTrace();
            return ;
        }

    }

}
