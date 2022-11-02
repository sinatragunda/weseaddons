package com.wese.weseaddons.bereaudechange.dao;

import com.wese.weseaddons.bereaudechange.mapper.FxBulkDealMapper;
import com.wese.weseaddons.bereaudechange.pojo.Blotter;
import com.wese.weseaddons.bereaudechange.pojo.FxBulkDeal;
import com.wese.weseaddons.bereaudechange.pojo.FxDeal;
import com.wese.weseaddons.pojo.Client;
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

public class FxBulkDealDAO {

    private String tenant ;
    private JdbcTemplate jdbcTemplate ;


    public FxBulkDealDAO(String tenant){
        this.tenant = tenant ;
        this.jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenant);
    }

//    public ObjectNode create(List<FxDeal> fxDealList ,int allocationType){
//
//        for(FxDeal fxDeal : fxDealList){
//
//            Client client = fxDeal.getClient() ;
//            Blotter blotter = fxDeal.getBlotter() ;
//
//            create(fxDeal ,client ,blotter ,allocationType);
//        }
//
//        return Helper.statusNodes(true);
//
//    }

    public List<FxBulkDeal> find(long id){

        String sql = "SELECT * FROM m_fx_bulk_deal_mapper where fx_deal_id = ?";

        Object object[] = new Object[]{
                id
        };

        List<FxBulkDeal> fxBulkDealList = jdbcTemplate.query(sql ,object ,new FxBulkDealMapper());

        if(fxBulkDealList.isEmpty()){
            return new ArrayList<>();
        }

        long rootId = fxBulkDealList.get(0).getRootNode();

        Object object1[] = new Object[]{
                rootId
        };

        fxBulkDealList.clear();

        sql = "SELECT * FROM m_fx_bulk_deal_mapper where root_node_id = ?";

        fxBulkDealList = jdbcTemplate.query(sql ,object1 ,new FxBulkDealMapper());

        return fxBulkDealList ;
    }

    public void create(long rootNode ,FxDeal fxDeal ,Client client ,Blotter blotter ,int allocationType){

        String sql ="INSERT INTO m_fx_bulk_deal_mapper(blotter_id ,client_id ,fx_deal_id ,allocation_type ,root_node_id) VALUES(? ,? ,? ,? ,?)";

        long id = 0 ;

        try{
            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setLong(1, blotter.getId());
                    statement.setLong(2 ,client.getId());
                    statement.setLong(3 ,fxDeal.getId());
                    statement.setInt(4 ,allocationType);
                    statement.setLong(5 ,rootNode);

                    return statement;
                }
            }, holder);

            id = holder.getKey().longValue();
        }

        catch (Exception e) {
            e.printStackTrace();
            return ;
        }

    }

}
