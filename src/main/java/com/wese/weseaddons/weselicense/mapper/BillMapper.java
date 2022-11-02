package com.wese.weseaddons.weselicense.mapper;

import com.wese.weseaddons.weselicense.dao.ServiceDAO;
import com.wese.weseaddons.weselicense.dao.TenantDAO;
import com.wese.weseaddons.weselicense.pojo.Bill;
import com.wese.weseaddons.weselicense.pojo.Service;
import com.wese.weseaddons.weselicense.pojo.Tenant;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BillMapper implements RowMapper<Bill> {

    public Bill mapRow(ResultSet resultSet ,int rowNum){

        Bill bill= null ;
        try{
            bill = new Bill();

            TenantDAO tenantDAO = new TenantDAO();
            Tenant tenant = tenantDAO.getTenant(resultSet.getLong("tenant_id"));

            bill.setId(resultSet.getLong("id"));
            bill.setTenant(tenant);

            ServiceDAO serviceDAO = new ServiceDAO();
            Service service = serviceDAO.getService(resultSet.getLong("service_id"));

            bill.setService(service);
            bill.setAmount(resultSet.getFloat("amount"));
        }

        catch (SQLException s){
            s.printStackTrace();
        }

        return bill ;

    }
}
