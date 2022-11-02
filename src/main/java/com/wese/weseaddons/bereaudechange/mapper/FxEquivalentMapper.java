package com.wese.weseaddons.bereaudechange.mapper;

import com.wese.weseaddons.bereaudechange.pojo.FxDeal;
import com.wese.weseaddons.bereaudechange.pojo.FxEquivalent;
import com.wese.weseaddons.bereaudechange.pojo.TradingRates;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FxEquivalentMapper implements RowMapper<FxEquivalent> {

    @Override
    public FxEquivalent mapRow(ResultSet rs, int i) throws SQLException {

        FxEquivalent fxEquivalant = null ;
        try{

            fxEquivalant = new FxEquivalent();
            fxEquivalant.setId(rs.getLong("id"));

            long id = rs.getLong("fx_deal_id");
            fxEquivalant.setFxDeal(new FxDeal(id));

            long id1 = rs.getLong("conversion_trading_rates_id");
            fxEquivalant.setConversionTradingRates(new TradingRates(id1));

            long id2 = rs.getLong("deal_trading_rates_id");
            fxEquivalant.setDealTradingRates(new TradingRates(id2));

            fxEquivalant.setLocalAmount(rs.getDouble("local_amount"));
            fxEquivalant.setEquivalantAmount(rs.getDouble("equivalant_amount"));

        }

        catch (SQLException s){
            s.printStackTrace();
        }

        return fxEquivalant;

    }
}
