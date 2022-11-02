package com.wese.weseaddons.bereaudechange.mapper;

import com.wese.weseaddons.bereaudechange.pojo.Blotter;
import com.wese.weseaddons.bereaudechange.pojo.FxBulkDeal;
import com.wese.weseaddons.bereaudechange.pojo.FxDeal;
import com.wese.weseaddons.pojo.Client;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class FxBulkDealMapper implements RowMapper<FxBulkDeal> {

    @Override
    public FxBulkDeal mapRow(ResultSet rs , int rownNum){

        FxBulkDeal fxBulkDeal =null;

        try{
            
            fxBulkDeal = new FxBulkDeal();
            fxBulkDeal.setId(rs.getLong("id"));

            int alloc = rs.getInt("allocation_type");
            fxBulkDeal.setAllocationType(alloc);


            long id = rs.getLong("blotter_id");
            fxBulkDeal.setBlotter(new Blotter(id));


            long id1 = rs.getLong("client_id");
            fxBulkDeal.setClient(new Client(id1));

            long id2 = rs.getLong("fx_deal_id");
            fxBulkDeal.setFxDeal(new FxDeal(id2));

            fxBulkDeal.setRootNode(rs.getLong("root_node_id"));

        }
        catch (SQLException s){
            s.printStackTrace();
        }

        return fxBulkDeal ;

    }
}
