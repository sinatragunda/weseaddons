/*Created by Sinatra Gunda
  At 04:24 on 9/26/2020 */

package com.wese.weseaddons.employeerelations.dao;

import com.wese.weseaddons.employeerelations.mapper.EmployeeClientMapper;
import com.wese.weseaddons.employeerelations.pojo.EmployeeClient;
import com.wese.weseaddons.employeerelations.pojo.Employer;
import com.wese.weseaddons.jdbc.JdbcTemplateInit;
import com.wese.weseaddons.networkrequests.ClientRequest;
import com.wese.weseaddons.pojo.Client;
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

public class EmployeeClientDAO {

    private String tenantIdentifier ;
    private EmployeeClient employeeClient;
    private JdbcTemplate jdbcTemplate ;


    public EmployeeClientDAO(String tenantIdentifier){
        this.employeeClient = new EmployeeClient();
        this.tenantIdentifier= tenantIdentifier ;
        this.jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenantIdentifier);
    }

    private Consumer<EmployeeClient> linkClient = (e)->{

        String clientId = String.valueOf(e.getClient().getId());
        ClientRequest clientRequest = new ClientRequest(tenantIdentifier);
        Client client = clientRequest.findClient(clientId);

        if(client!=null){
            e.setClient(client);
        }
    };

    public List<EmployeeClient> findWhere(String column ,String id ,boolean isLinkCustomer){

        String sql = String.format("SELECT * FROM %s WHERE %s = ?",employeeClient.getSchema() ,column);
        Object args[] = new Object[]{
                id
        };

        List<EmployeeClient> employeeClientList = jdbcTemplate.query(sql ,args ,new EmployeeClientMapper());
        if(!employeeClientList.isEmpty()){
            if(isLinkCustomer){
                employeeClientList.stream().forEach(linkClient);
            }
        }
        return employeeClientList;

    }


    public Long create(EmployeeClient employeeClient){

        String sql = String.format("INSERT INTO %s(client_id ,employer_id) VALUES(? , ?)", employeeClient.getSchema());
        Long id = null ;
        try {

            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setLong(1, employeeClient.getId());
                    statement.setLong(2, employeeClient.getEmployer().getId());

                    return statement;
                }
            }, holder);

            id = holder.getKey().longValue();

        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return  id ;

    }



}
