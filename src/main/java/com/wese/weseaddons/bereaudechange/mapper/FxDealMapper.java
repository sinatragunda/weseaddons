package com.wese.weseaddons.bereaudechange.mapper;

import com.wese.weseaddons.bereaudechange.enumerations.PURPOSE;
import com.wese.weseaddons.bereaudechange.enumerations.TRADE_TYPE;
import com.wese.weseaddons.bereaudechange.pojo.*;
import com.wese.weseaddons.pojo.Client;
import com.wese.weseaddons.pojo.AppUser ;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FxDealMapper implements RowMapper<FxDeal> {

	@Override
	public FxDeal mapRow(ResultSet rs , int rowNum){
		FxDeal fxDeal = null ;
		try{
			fxDeal = new FxDeal();
			fxDeal.setId(rs.getLong("id"));
			fxDeal.setDate(rs.getLong("date"));
			fxDeal.setReversed(rs.getBoolean("is_reversed"));

			long id = rs.getLong("trading_rates_id");
			TradingRates tradingRates = new TradingRates(id);
			fxDeal.setTradingRates(tradingRates);

			long id1 = rs.getLong(("blotter_id"));
			Blotter blotter = new Blotter(id1);
			fxDeal.setBlotter(blotter);

			int fxdealType = rs.getInt("fx_deal_type");
			fxDeal.setFxDealType(fxdealType);

			fxDeal.setIncludeCharges(rs.getBoolean("include_charges"));

			long id2 = rs.getLong("client_id");
			Client client = new Client(id2);
			fxDeal.setClient(client);

			fxDeal.setAppUser(new AppUser(rs.getLong("officer_id")));

			int purpose = rs.getInt("purpose");
			fxDeal.setPurpose(purpose);

			fxDeal.setAccrued(rs.getBoolean("accrued"));

			int tradeType = rs.getInt("trade_type");
			fxDeal.setTradeType(tradeType);

//			long id3 = rs.getLong("fx_cashier_id");
//			fxDeal.setFxCashier(new FxCashier(id3));
		}

		catch(SQLException s){
			s.printStackTrace();
		}

		return fxDeal ;
	}
}