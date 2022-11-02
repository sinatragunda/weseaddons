package com.wese.weseaddons.weselicense.dao;

import com.wese.weseaddons.jdbc.JdbcService;
import com.wese.weseaddons.jdbc.JdbcTemplateInit;
import com.wese.weseaddons.weselicense.mapper.ServiceMapper;
import com.wese.weseaddons.weselicense.pojo.Service;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ServiceDAO implements JdbcService {


    private String tenantIdentifier ;
    private JdbcTemplate jdbcTemplate ;

    public ServiceDAO(){

        this.tenantIdentifier = "billing" ;
        init();

    }

    @Override
    public void init(){

        this.jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenantIdentifier);

    }

    public long createService(Service service){


        String sql = "INSERT INTO m_services(date ,service_id ,tenant_id ,state ,creation_date ,amount,duration) values(? ,?,?,?,?)";

        if(jdbcTemplate==null) {

            System.out.println("Jdbc template is null");
        }

        long serviceId = 0 ;

        try {

            /// to do ,match values


            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//                    statement.setDouble(1,service.get);
//
//                    statement.setLong(4 ,bill.getTenant().getId());
//                    statement.setLong(5 ,bill.getService().getId());
                    return statement;
                }
            }, holder);

            serviceId = holder.getKey().longValue();
        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return 0;
        }

        return serviceId ;
    }

    public Service getService(long id) {

        String sql = "SELECT * FROM m_service where id=?";

        Object args[] = new Object[]{
                id
        };

        List<Service> serviceList = jdbcTemplate.query(sql ,new ServiceMapper());

        if (serviceList.isEmpty()){
            return null;
        }

        return serviceList.get(0);

    }

    public List<Service> getAllServices() {

        String sql = "SELECT * FROM m_service";

        List<Service> serviceList = jdbcTemplate.query(sql ,new ServiceMapper());

        if (serviceList.isEmpty()){
            return new ArrayList<>();
        }

        return serviceList;

    }

    public void createTarrif(){

    }
}
