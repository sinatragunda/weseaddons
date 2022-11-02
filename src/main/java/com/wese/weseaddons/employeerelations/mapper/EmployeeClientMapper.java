/*Created by Sinatra Gunda
  At 05:17 on 9/26/2020 */

package com.wese.weseaddons.employeerelations.mapper;


import com.wese.weseaddons.employeerelations.pojo.Employer;
import com.wese.weseaddons.employeerelations.pojo.EmployeeClient;
import com.wese.weseaddons.interfaces.MapperInterface;
import com.wese.weseaddons.pojo.Client;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeClientMapper implements RowMapper<EmployeeClient> ,MapperInterface {


    @Override
    public EmployeeClient mapRow(ResultSet resultSet, int i){

        EmployeeClient employeeClient = null ;
        try{
            employeeClient = new EmployeeClient();
            employeeClient.setId(resultSet.getLong("id"));
            Long clientId = resultSet.getLong("client_id");
            employeeClient.setClient(new Client(clientId));

            Long employerId = resultSet.getLong("employer_id");
            employeeClient.setEmployer(new Employer(employerId));

        }
        catch (SQLException s){
            s.printStackTrace();
        }

        return employeeClient;
    }
}
