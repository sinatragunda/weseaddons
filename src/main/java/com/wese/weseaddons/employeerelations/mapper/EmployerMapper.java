/*Created by Sinatra Gunda
  At 02:22 on 9/26/2020 */

package com.wese.weseaddons.employeerelations.mapper;

import com.wese.weseaddons.employeerelations.pojo.Employer;
import com.wese.weseaddons.interfaces.MapperInterface;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployerMapper implements RowMapper<Employer> ,MapperInterface {


    @Override
    public Employer mapRow(ResultSet resultSet, int i) {

        Employer employer = null ;
        try{
            employer = new Employer();
            employer.setId(resultSet.getLong("id"));
            employer.setName(resultSet.getString("name"));
            employer.setEmail(resultSet.getString("email"));
            employer.setAccountNumber(resultSet.getString("account_number"));
            employer.setAddress(resultSet.getString("address"));
        }
        catch (SQLException s){
            s.printStackTrace();
        }

        return employer;
    }
}
