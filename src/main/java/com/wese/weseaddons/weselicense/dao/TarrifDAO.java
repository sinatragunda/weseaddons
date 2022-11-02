package com.wese.weseaddons.weselicense.dao;

import com.wese.weseaddons.jdbc.JdbcService;

import com.wese.weseaddons.jdbc.JdbcTemplateInit;
import com.wese.weseaddons.weselicense.mapper.TarrifMapper;
import com.wese.weseaddons.weselicense.pojo.Tarrif;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class TarrifDAO implements JdbcService {


    private String tenantIdentifier ;
    private JdbcTemplate jdbcTemplate ;

    public TarrifDAO(){

        this.tenantIdentifier = "billing" ;
        init();

    }

    @Override
    public void init(){

        this.jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenantIdentifier);

    }

    public Tarrif getTarrif(long id) {

        String sql = "SELECT * FROM m_tariffs where id=?";

        Object args[] = new Object[]{
                id
        };

        List<Tarrif> tarrifList = jdbcTemplate.query(sql ,args ,new TarrifMapper());

        if (tarrifList.isEmpty()){
            return null;
        }

        return tarrifList.get(0);

    }

    public List<Tarrif> getAllTarrifs() {

        String sql = "SELECT * FROM m_tariffs";

        List<Tarrif> tarrifList = jdbcTemplate.query(sql ,new TarrifMapper());

        if (tarrifList.isEmpty()){
            return new ArrayList<>();
        }

        return tarrifList;

    }

    public void createTarrif(){

    }
}
