/*Created by Sinatra Gunda
  At 02:34 on 9/26/2020 */

package com.wese.weseaddons.employeerelations.dao;

import com.wese.weseaddons.cache.services.Cachable;
import com.wese.weseaddons.daofactory.DAOQuery;
import com.wese.weseaddons.employeerelations.cache.EmployeeRelationsCache;
import com.wese.weseaddons.employeerelations.mapper.EmployerMapper;
import com.wese.weseaddons.employeerelations.pojo.EmployeeClient;
import com.wese.weseaddons.employeerelations.pojo.Employer;
import com.wese.weseaddons.employeerelations.pojo.CompanyOfficials;
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
import java.util.function.Consumer;

public class EmployerDAO implements Cachable {


    private JdbcTemplate jdbcTemplate;
    private String tenantIdentifier;
    private Employer employer;
    private List<Employer> employerList;

    private Consumer<Employer> clientsConsumer = (e) -> {

        Long id = e.getId();
        EmployeeClientDAO employeeClientDAO = new EmployeeClientDAO(tenantIdentifier);
        List<EmployeeClient> employeeClientList = employeeClientDAO.findWhere("client_id",id.toString(),true);
        e.setEmployeeClientList(employeeClientList);

    };


    private Consumer<Employer> companyOfficialsConsumer = (e) -> {

        Long id = e.getId();
        CompanyOfficialsDAO companyOfficialsDAO = new CompanyOfficialsDAO(tenantIdentifier);
        List<CompanyOfficials> companyOfficialsList = companyOfficialsDAO.findWhere("employer_id", id.toString());
        e.setCompanyOfficialsList(companyOfficialsList);

    };

    public EmployerDAO(String tenantIdentifier) {
        this.tenantIdentifier = tenantIdentifier;
        jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenantIdentifier);
        employerList = new ArrayList<>();
        employer = new Employer();
    }

    public Employer find(Long id) {

        Employer employer = (Employer) DAOQuery.find(new Employer(), tenantIdentifier, id);
        List<Employer> employerList = new ArrayList<>();

        if (employer != null) {
            employerList.add(employer);
            employerList.stream().forEach(clientsConsumer);
            employerList.stream().forEach(companyOfficialsConsumer);

        }

        return employer;

//
//        return (Employer)DAOQuery.find(employerList ,id);

    }


    public boolean edit(Employer employer) {

        String sql = String.format("UPDATE %s SET name = ? ,email= ? ,address=? WHERE id = ?", employer.getSchema());

        Object args[] = new Object[]{
                employer.getName(),
                employer.getEmail(),
                employer.getAddress(),
                employer.getId()
        };

        int rows = jdbcTemplate.update(sql, args);

        if (rows > 0){

            List<CompanyOfficials> companyOfficialsList = employer.getCompanyOfficialsList();
            CompanyOfficialsDAO companyOfficialsDAO = new CompanyOfficialsDAO(tenantIdentifier);

            for(CompanyOfficials companyOfficials : companyOfficialsList){
                companyOfficials.setEmployer(employer);
                companyOfficialsDAO.create(companyOfficials);
            }
            return true;
        }

        return false;

    }

    public Long create(Employer employer){

        String sql = String.format("INSERT INTO %s(name ,account_number ,email ,address) VALUES(? , ? ,? ,?)",employer.getSchema());
        Long id = null ;
        try {

            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setString(1,employer.getName());
                    statement.setString(2,employer.getAccountNumber());
                    statement.setString(3,employer.getEmail());
                    statement.setString(4,employer.getAddress());

                    return statement;
                }
            }, holder);

            id = holder.getKey().longValue();

        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        if(id!=null){
          //  EmployeeRelationsCache.getInstance().writeFlag(true);
        }

        return id ;
    }


    public List<Employer> findAll(){

        String sql = String.format("SELECT * FROM %s",employer.getSchema());
        List<Employer> employerList = jdbcTemplate.query(sql ,new EmployerMapper());
        return employerList ;

    }



    @Override
    public void reload(){
        findAll();
    }
}
