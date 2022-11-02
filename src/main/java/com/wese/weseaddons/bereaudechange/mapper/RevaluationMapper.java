package com.wese.weseaddons.bereaudechange.mapper;

import com.wese.weseaddons.bereaudechange.pojo.FxDeal;
import com.wese.weseaddons.bereaudechange.pojo.Revaluation;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RevaluationMapper implements RowMapper<Revaluation> {

    @Override
    public Revaluation mapRow(ResultSet rs , int rownNum){

        Revaluation revaluation =null;

        try{
            revaluation = new Revaluation();
            revaluation.setId(rs.getLong("id"));

            long id = rs.getLong("fx_deal_id");
            revaluation.setFxDeal(new FxDeal(id));

            revaluation.setFcy(rs.getDouble("fcy"));
            revaluation.setPositionRevaluation(rs.getDouble("position_revaluation"));
            revaluation.setRevaluationProfit(rs.getDouble("revaluation_profit"));
            revaluation.setRevalRate(rs.getDouble("reval_rate"));
            revaluation.setAccruedDate(rs.getLong("accrued_date"));


        }
        catch (SQLException s){
            s.printStackTrace();
        }

        return revaluation ;

    }
}
