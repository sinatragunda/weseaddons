package com.wese.weseaddons.bereaudechange.fxrequest;

import com.wese.weseaddons.bereaudechange.mapper.OrganisationCurrencyMapper;
import com.wese.weseaddons.bereaudechange.pojo.OrganisationCurrency;
import com.wese.weseaddons.bereaudechange.pojo.StandardCurrency;
import com.wese.weseaddons.jdbc.JdbcTemplateInit;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class OrganisationCurrencyRequest {

    private String tenantIdentifier ;
    private JdbcTemplate jdbcTemplate ;

    public OrganisationCurrencyRequest(String s){
        this.tenantIdentifier = s ;
        this.jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenantIdentifier);
   

    }

    public boolean codeExists(String code){

        String sql = "SELECT * FROM m_organisation_currency WHERE code = ?";
        Object args[] = new Object[]{
                code
        };

        List<OrganisationCurrency> organisationCurrencyList = jdbcTemplate.query(sql ,args ,new OrganisationCurrencyMapper());

        if(organisationCurrencyList.isEmpty()){
            return false;
        }

        return true ;

    }


    public void create(StandardCurrency standardCurrency){

        if(codeExists(standardCurrency.getName())){
            return;
        }

        String sql = "INSERT INTO m_organisation_currency(code ,decimal_places,name ,internationalized_name_code) VALUES(? ,? ,?,?)";

        try{

            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setString(1,standardCurrency.getName());
                    statement.setLong(2,2);
                    statement.setString(3 , standardCurrency.getCountry());
                    statement.setString(4 ,standardCurrency.getInternationalCode());
                    return statement;
                }
            }, holder);

        }

        catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static void staticCreate(StandardCurrency standardCurrency,String tenant){

        OrganisationCurrencyRequest organisationCurrencyRequest = new OrganisationCurrencyRequest(tenant);
        organisationCurrencyRequest.create(standardCurrency);

    }


}

