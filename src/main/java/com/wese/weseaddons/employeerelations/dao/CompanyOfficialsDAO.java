/*Created by Sinatra Gunda
  At 16:57 on 9/26/2020 */

package com.wese.weseaddons.employeerelations.dao;

import com.wese.weseaddons.daofactory.DAOQuery;
import com.wese.weseaddons.employeerelations.mapper.CompanyOfficialsMapper;
import com.wese.weseaddons.employeerelations.pojo.CompanyOfficials;
import com.wese.weseaddons.jdbc.JdbcTemplateInit;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class CompanyOfficialsDAO {


    private JdbcTemplate jdbcTemplate ;
    private String tenantIdentifier ;
    private CompanyOfficials companyOfficials;

    public CompanyOfficialsDAO(String tenantIdentifier){
        this.tenantIdentifier = tenantIdentifier;
        this.companyOfficials = new CompanyOfficials();
        jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenantIdentifier);
    }

    public CompanyOfficials find(Long id){

        return (CompanyOfficials) DAOQuery.find(new CompanyOfficials() ,tenantIdentifier ,id);

    }

    public List<CompanyOfficials> findWhere(String columnName , String id){

        String sql = String.format("SELECT * FROM %s WHERE %s=?",companyOfficials.getSchema() ,columnName);
        Object args[] = new Object[]{
                id
        };

        List<CompanyOfficials> companyOfficialsList = jdbcTemplate.query(sql ,args ,new CompanyOfficialsMapper());
        return companyOfficialsList;

    }


    public Long create(CompanyOfficials companyOfficials){

        String sql = String.format("INSERT INTO %s(name ,email ,employer_id) VALUES(? , ? ,?)", companyOfficials.getSchema());
        Long id = null ;
        try {

            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setString(1, companyOfficials.getName());
                    statement.setString(2, companyOfficials.getEmail());
                    statement.setLong(3 ,companyOfficials.getEmployer().getId());
                    return statement;
                }
            }, holder);
            id = holder.getKey().longValue();
        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return id ;
    }


    public List<CompanyOfficials> findAll(){

        String sql = String.format("SELECT * FROM %s", companyOfficials.getSchema());
        List<CompanyOfficials> companyOfficialsList = jdbcTemplate.query(sql ,new CompanyOfficialsMapper());

        // code needs change here
        //List<Employer> employerList = (List<Employer> extends PojoInterface) DAOQuery.findAll(new Employer() ,tenantIdentifier);
        return companyOfficialsList;

    }
}
