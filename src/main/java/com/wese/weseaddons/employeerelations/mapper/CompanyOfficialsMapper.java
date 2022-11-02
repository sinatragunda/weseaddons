/*Created by Sinatra Gunda
  At 17:01 on 9/26/2020 */

package com.wese.weseaddons.employeerelations.mapper;

import com.wese.weseaddons.employeerelations.pojo.CompanyOfficials;
import com.wese.weseaddons.employeerelations.pojo.Employer;
import com.wese.weseaddons.interfaces.MapperInterface;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyOfficialsMapper implements RowMapper<CompanyOfficials> ,MapperInterface {


    @Override
    public CompanyOfficials mapRow(ResultSet resultSet, int i){

        CompanyOfficials companyOfficials = null ;
        try{
            companyOfficials = new CompanyOfficials();
            companyOfficials.setId(resultSet.getLong("id"));
            companyOfficials.setName(resultSet.getString("name"));
            companyOfficials.setEmail(resultSet.getString("email"));

            Long employerId = resultSet.getLong("employer_id");
            companyOfficials.setEmployer(new Employer(employerId));
        }
        catch (SQLException s){
            s.printStackTrace();
        }

        return companyOfficials;
    }
}

