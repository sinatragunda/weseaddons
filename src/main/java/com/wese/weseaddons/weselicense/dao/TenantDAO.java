package com.wese.weseaddons.weselicense.dao;

import com.wese.weseaddons.jdbc.JdbcService;
import com.wese.weseaddons.jdbc.JdbcTemplateInit;
import com.wese.weseaddons.weselicense.mapper.TenantMapper;

import com.wese.weseaddons.weselicense.pojo.Tenant;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;


public class TenantDAO implements JdbcService {

    private String tenantIdentifier ;
    private JdbcTemplate jdbcTemplate ;

    public TenantDAO(){

        this.tenantIdentifier="billing" ;
        init();

    }

    @Override
    public void init(){

        this.jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenantIdentifier);

    }

    public Tenant getTenant(long id) {

        String sql = "SELECT * FROM m_tenant where id=?";

        Object args[] = new Object[]{
                id
        };

        List<Tenant> tenantList = jdbcTemplate.query(sql ,args ,new TenantMapper());

        if (tenantList.isEmpty()){
            return null;
        }

        return tenantList.get(0);

    }

    public Tenant getTenant(String tenantIdentifier) {

        String sql = "SELECT * FROM m_tenant where name=?";

        Object args[] = new Object[]{
                tenantIdentifier
        };

        List<Tenant> tenantList = jdbcTemplate.query(sql ,args ,new TenantMapper());

        if (tenantList.isEmpty()){
            return null;
        }

        return tenantList.get(0);

    }

    public List<Tenant> getAllTenants(){

        String sql = "SELECT * FROM m_tenant";

        List<Tenant> tenantList = jdbcTemplate.query(sql ,new TenantMapper());

        if (tenantList.isEmpty()){
            return new ArrayList<>();
        }

        return tenantList;
    }
}
