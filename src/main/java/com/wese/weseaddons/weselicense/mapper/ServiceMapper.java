package com.wese.weseaddons.weselicense.mapper;

import com.wese.weseaddons.weselicense.dao.TarrifDAO;
import com.wese.weseaddons.weselicense.dao.TenantDAO;
import com.wese.weseaddons.weselicense.enumeration.SERVICE_TYPE;
import com.wese.weseaddons.weselicense.pojo.Service;
import com.wese.weseaddons.weselicense.pojo.Tarrif;
import com.wese.weseaddons.weselicense.pojo.Tenant;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceMapper implements RowMapper<Service>{


    public Service mapRow(ResultSet resultSet ,int rowNum){

        Service service = null ;
        try{
            service = new Service();

            int tarrifId = resultSet.getInt(("tarrif_id"));
            int serviceType =  resultSet.getInt("service_type");

            TarrifDAO tarrifDAO = new TarrifDAO();
            Tarrif tarrif = tarrifDAO.getTarrif(tarrifId);



            service.setId(resultSet.getLong("id"));
            service.setServiceType(SERVICE_TYPE.fromInt(serviceType));
            service.setTarrif(tarrif);

        }

        catch (SQLException s){

            s.printStackTrace();
        }

        return service ;
    }

}
