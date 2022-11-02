package com.wese.weseaddons.bereaudechange.mapper;
import com.wese.weseaddons.bereaudechange.enumerations.ROUNDING_RULE;
import com.wese.weseaddons.bereaudechange.pojo.CurrencyPair;
import com.wese.weseaddons.bereaudechange.pojo.MoneyAccount;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CurrencyPairMapper implements  RowMapper<CurrencyPair> {

	@Override
	public CurrencyPair mapRow(ResultSet rs , int rowNum){
		
		CurrencyPair currencyPair = null ;
		
		try{
			currencyPair = new CurrencyPair();
			currencyPair.setId(rs.getLong("id"));
			currencyPair.setTick(rs.getString("tick"));

			long id1 = rs.getLong("base_currency_id");
			MoneyAccount moneyAccount = new MoneyAccount(id1);
			currencyPair.setBaseCurrencyMoneyAccount(moneyAccount);

			long id2 = rs.getLong("quote_currency_id");
			MoneyAccount moneyAccount1 = new MoneyAccount(id2);
			currencyPair.setQuoteCurrencyMoneyAccount(moneyAccount1);


			int rule = rs.getInt("rounding_rule");
			ROUNDING_RULE roundingRule = ROUNDING_RULE.fromInt(rule);

			currencyPair.setRoundingRule(roundingRule);
			currencyPair.setMarkUp(rs.getDouble("mark_up"));

			int marketType = rs.getInt("market_type");
			currencyPair.setMarketType(marketType);

		}

		catch(SQLException s){

			s.printStackTrace();
		}

		return currencyPair ;
	}
}