package com.wese.weseaddons.bereaudechange.dao;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.helper.ForeignKeyHelper;
import com.wese.weseaddons.bereaudechange.helper.OffshoreThread;
import com.wese.weseaddons.bereaudechange.mapper.SettingsMapper;
import com.wese.weseaddons.bereaudechange.pojo.CurrencyPair;
import com.wese.weseaddons.bereaudechange.pojo.Settings;
import com.wese.weseaddons.bereaudechange.pojo.StandardCurrency;
import com.wese.weseaddons.bereaudechange.session.FxReload;
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


public class SettingsDAO {

    private String tenant ;
    private JdbcTemplate jdbcTemplate ;

    public SettingsDAO(String tenant){

        this.tenant = tenant ;
        this.jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenant);
    }

    public Settings find(){

        String sql = "SELECT * FROM m_fx_settings";
        List<Settings> settingssList = jdbcTemplate.query(sql ,new SettingsMapper());
        if(settingssList.isEmpty()){
            return null ;
        }

        Settings settings = settingssList.get(0);
        
        try{
            
            StandardCurrency standardCurrency = (StandardCurrency) ForeignKeyHelper.get(new StandardCurrencyDAO(tenant),settings.getAccountingCurrency().getId());
            StandardCurrency localCurrency = (StandardCurrency) ForeignKeyHelper.get(new StandardCurrencyDAO(tenant),settings.getLocalCurrency().getId());
            CurrencyPair currencyPair = (CurrencyPair)ForeignKeyHelper.get(new CurrencyPairDAO(tenant) ,settings.getCurrencyPair().getId());            
            
            if(standardCurrency!=null){
                settings.setAccountingCurrency(standardCurrency);
            }

            if(localCurrency !=null){
                settings.setLocalCurrency(localCurrency);
            }

            if(currencyPair !=null){
                settings.setCurrencyPair(currencyPair);
            }
        }
        
        catch(NullPointerException n) {
            
        }
        return settings ;

    }


    public Settings setDefaults(Settings settings){
        
        System.out.println("Set defaults here son ");

        if(settings.getOpeningTime()==0){
            settings.setOpeningTime(7);
        }

        if(settings.getClosingTime()==0){
            settings.setClosingTime(17);
        }

        if(settings.getDailyLimit()==0){
            settings.setDailyLimit(10000);
        }

        return settings ;

    }

    public ObjectNode update(Settings settings){

        Settings defaultSettings = find();

        if(settings.getLocalCurrency().getId()==0){
            settings.setLocalCurrency(defaultSettings.getLocalCurrency());
        }
        if(settings.getCurrencyPair().getId()==0){
            settings.setCurrencyPair(defaultSettings.getCurrencyPair());
        }

        if(settings.getAccountingCurrency().getId()==0){
            settings.setAccountingCurrency(defaultSettings.getAccountingCurrency());
        }

        String sql ="UPDATE m_fx_settings SET opening_time= ? ,closing_time=? ,daily_limit=?,accounting_currency_id =?,district =?,auto_reval_type =? ,company_name = ? ,company_address = ? , company_city = ? ,local_currency_id = ? ,currency_pair_id = ?";

        Object args[] = new Object[]{
                settings.getOpeningTime() ,
                settings.getClosingTime() ,
                settings.getDailyLimit(),
                settings.getAccountingCurrency().getId(),
                settings.getDistrict(),
                settings.getAutoRevalInt() ,
                settings.getCompanyName() ,
                settings.getCompanyAddress(),
                settings.getCompanyCity(),
                settings.getLocalCurrency().getId(),
                settings.getCurrencyPair().getId(),
              //  settings.getProfitCalculationMethod().ordinal()
        };

        int affectedRows = jdbcTemplate.update(sql ,args);

        if(affectedRows > 0){

            return Helper.statusNodes(true);
        }

        return Helper.statusNodes(false);

    }

    public ObjectNode create(Settings s){

        Settings settings = setDefaults(s);

        String sql ="INSERT INTO m_fx_settings(opening_time ,closing_time ,daily_limit ,accounting_currency_id ,district ,auto_reval_type ,local_currency_id ,currency_pair_id ,company_name ,company_address ,company_city) VALUES(? ,? ,? ,? ,?,? ,? ,? ,? ,? ,?)";

        long id = 0 ;

        try{
            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setInt(1, settings.getOpeningTime());
                    statement.setInt(2 ,settings.getClosingTime());
                    statement.setDouble(3,settings.getDailyLimit());
                    statement.setLong(4 ,settings.getAccountingCurrency().getId());
                    statement.setString(5 ,settings.getDistrict());
                    statement.setInt(6 ,settings.getAutoRevalType().ordinal());
                    statement.setLong(7 ,settings.getLocalCurrency().getId());
                    statement.setLong(8 ,settings.getCurrencyPair().getId());
                    statement.setString(9 ,settings.getCompanyName());
                    statement.setString(10 ,settings.getCompanyAddress());
                    statement.setString(11 ,settings.getCompanyCity());
                    //statement.setInt(12 ,settings.getProfitCalculationMethod().ordinal());

                    return statement;
                }
            }, holder);

            id = holder.getKey().longValue();
            settings.setId(id);

        }

        catch (Exception e) {
            e.printStackTrace();
            return Helper.statusNodes(false).put("fx_error_message","Failed to run ,some data fields contain no values try again");
        }
        
        Runnable runnable = ()->{
          
            FxReload.getInstance().start();
        };
        
        OffshoreThread.run(runnable);

        return Helper.statusNodes(true);

    }

}