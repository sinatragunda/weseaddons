package com.wese.weseaddons.bereaudechange.dao;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.helper.*;
import com.wese.weseaddons.bereaudechange.impl.FxDAOService;
import com.wese.weseaddons.bereaudechange.mapper.FxDealMapper;
import com.wese.weseaddons.bereaudechange.pojo.*;
import com.wese.weseaddons.bereaudechange.session.FxSession;
import com.wese.weseaddons.helper.Constants;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.helper.TimeHelper;
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
import java.util.function.Consumer;

public class FxDealDAO implements FxDAOService {

	private String tenant;
	private FxCashier fxCashier;
	private JdbcTemplate jdbcTemplate;
	private Settings settings;
	private FxConversionHelper fxConversionHelper;

	private Consumer<FxDeal> fxDealConsumer = (fxDeal) -> {

		Blotter blotter = (Blotter) getForeignKey(new BlotterDAO(tenant), fxDeal.getBlotter().getId());
		fxDeal.setBlotter(blotter);

		TradingRates tradingRates = (TradingRates) getForeignKey(new TradingRatesDAO(tenant),
				fxDeal.getTradingRates().getId());
		fxDeal.setTradingRates(tradingRates);
	};

	public FxDealDAO(String s) {
		this.tenant = s;
		this.jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenant);
		this.settings = FxSession.getInstance().getSettings(tenant);
	}

	public FxDeal find(long id) {

		String sql = "SELECT * FROM m_fx_deal where id=?";

		Object args[] = new Object[] { id };

		List<FxDeal> fxDealList = jdbcTemplate.query(sql, args, new FxDealMapper());

		if (fxDealList.isEmpty()) {
			return null;
		}

		fxDealList.stream().forEach(fxDealConsumer);
		return fxDealList.get(0);

	}

	public List<FxDeal> findAll() {

		String sql = "SELECT * FROM m_fx_deal";

		List<FxDeal> fxDealList = jdbcTemplate.query(sql, new FxDealMapper());

		if (fxDealList.isEmpty()) {
			return new ArrayList<>();
		}

		fxDealList.forEach(fxDealConsumer);

		return fxDealList;

	}

	public List<FxDeal> findWhere(long clientId, String column) {

		String sql = String.format("SELECT * FROM m_fx_deal where %s=?", column);

		Object args[] = new Object[] { clientId };

		List<FxDeal> fxDealList = jdbcTemplate.query(sql, args, new FxDealMapper());

		if (fxDealList.isEmpty()) {
			return new ArrayList<>();
		}

		fxDealList.stream().forEach(fxDealConsumer);
		return fxDealList;

	}

	public List<FxDeal> findWhereDates(long clientId, String column, long from, long to) {

		String sql = String.format("SELECT * FROM m_fx_deal where %s=? AND date BETWEEN %d AND %d", column, from, to);

		Object args[] = new Object[] { clientId };

		List<FxDeal> fxDealList = jdbcTemplate.query(sql, args, new FxDealMapper());

		if (fxDealList.isEmpty()) {
			return new ArrayList<>();
		}

		fxDealList.stream().forEach(fxDealConsumer);

		return fxDealList;

	}

	public List<FxDeal> findWhereDateBefore(long to) {

		String sql = "SELECT * FROM m_fx_deal where date < ?";

		Object args[] = new Object[] { to };

		List<FxDeal> fxDealList = jdbcTemplate.query(sql, args, new FxDealMapper());

		if (fxDealList.isEmpty()) {
			return new ArrayList<>();
		}

		fxDealList.stream().forEach(fxDealConsumer);

		return fxDealList;

	}

	public boolean update(long id) {

		String sql = "UPDATE m_fx_deal SET accrued = ? WHERE id = ?";

		Object object[] = new Object[] { true, id };

		int affectedRows = jdbcTemplate.update(sql, object);

		if (affectedRows > 0) {
			return true;
		}

		return false;
	}

	public boolean reverse(long id) {

		String sql = "UPDATE m_fx_deal SET is_reversed = ? WHERE id = ?";
		Object object[] = new Object[] { true, id };

		FxDeal fxDeal = find(id);

		MoneyAccount quoteMoneyAccount = fxDeal.getTradingRates().getCurrencyPair().getQuoteCurrencyMoneyAccount();
		MoneyAccount baseMoneyAccount = fxDeal.getTradingRates().getCurrencyPair().getBaseCurrencyMoneyAccount();

		int affectedRows = jdbcTemplate.update(sql, object);

		if (affectedRows > 0) {

			TransactionHelper transactionHelper = new TransactionHelper();
			transactionHelper.setTenant(tenant);

			switch (fxDeal.getTradeType()) {

			case SELL:
				transactionHelper.depositFunds(fxDeal, quoteMoneyAccount);
				transactionHelper.withdrawFunds(fxDeal, baseMoneyAccount);
				break;
			case BUY:

				transactionHelper.depositFunds(fxDeal, baseMoneyAccount);
				transactionHelper.withdrawFunds(fxDeal, quoteMoneyAccount);

				break;
			}

			return true;
		}

		return false;
	}

	public ObjectNode create(FxDeal fxDeal) {

		if (!FxValidationHelper.fxDealValiation(fxDeal)) {
			return Helper.statusNodes(false).put("message", Constants.validateDealFailed);
		}

		if (!isCashier(fxDeal)) {
			return Helper.statusNodes(false).put("message", Constants.isNotCashier);
		}

		if (!isLocalCurrencySet()) {

			return Helper.statusNodes(false).put("message", Constants.noLocalCurrency);
		}

		if (!isMainTradeSet()) {
			return Helper.statusNodes(false).put("message", Constants.noMainTrade);

		}

		if (!hasSettled(fxDeal)) {
			return Helper.statusNodes(false).put("message", Constants.hasSettled);
		}

		if (dailyLimitReached(fxDeal)) {
			return Helper.statusNodes(false).put("message", Constants.dailyLimitReached);

		}

        String sql= "insert into m_fx_deal(date ,trading_rates_id ,blotter_id ,is_reversed ,client_id ,officer_id,fx_deal_type ,purpose ,trade_type ,purpose_string ,date_string ,include_charges) values (? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,?)";
        long id = 0 ;

        final long timeNow = fxDeal.getDate();

        System.err.println("\nDate used here is "+timeNow);

        try{
            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator(){
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setLong(1, timeNow);
                    statement.setLong(2 ,fxDeal.getTradingRates().getId());
                    statement.setLong(3 ,fxDeal.getBlotter().getId());
                    statement.setBoolean(4 ,false);
                    statement.setLong(5 ,fxDeal.getClient().getId());
                    statement.setLong(6 ,fxDeal.getAppUser().getId());
                    statement.setInt(7 ,fxDeal.getFxDealType().ordinal());
                    statement.setInt(8 ,fxDeal.getPurpose().ordinal());
                    statement.setInt(9 ,fxDeal.getTradeType().ordinal());
                    statement.setString(10 ,fxDeal.getPurpose().getCode());
                    statement.setString(11  , TimeHelper.timestampToStringWithTimeForSecond(timeNow));
                    statement.setBoolean(12 ,fxDeal.isIncludeCharges());
                    return statement;
                }

			}, holder);

			id = holder.getKey().longValue();

		}

		catch (Exception e) {
			e.printStackTrace();
			// FxReverseDeal.reverse(fxDeal.getBlotter().getId());
			return Helper.statusNodes(false).put("message", "Error processing transaction ,try again");
		}

		FxDeal fxDeal1 = find(id);

		MoneyAccount baseMoneyAccount = fxDeal1.getTradingRates().getCurrencyPair().getBaseCurrencyMoneyAccount();
		MoneyAccount quoteMoneyAccount = fxDeal1.getTradingRates().getCurrencyPair().getQuoteCurrencyMoneyAccount();

		TransactionHelper transactionHelper = new TransactionHelper();
		transactionHelper.setTenant(tenant);

		switch (fxDeal.getTradeType()) {
		case BUY:
			transactionHelper.depositFunds(fxDeal1, quoteMoneyAccount);
			transactionHelper.withdrawFunds(fxDeal1, baseMoneyAccount);
			break;
		case SELL:
			transactionHelper.depositFunds(fxDeal1, baseMoneyAccount);
			transactionHelper.withdrawFunds(fxDeal1, quoteMoneyAccount);
			break;
		}

		Runnable runnable = fxConversionHelper.convertCurrency(id);
		OffshoreThread.run(runnable);

		OffshoreThread.run(new FxWeightedAverageHelper(fxDeal ,tenant));


		return Helper.statusNodes(true).put("id", id);

	}

	public Object getForeignKey(FxDAOService daoService, long id) {

		Object object = daoService.find(id);
		return object;

	}


	public boolean isCashier(FxDeal fxDeal) {

		long userId = fxDeal.getAppUser().getId();
		FxCashierDAO fxCashierDAO = new FxCashierDAO(tenant);
		this.fxCashier = fxCashierDAO.findWhere(userId, "app_user_id");

		if (fxCashier == null) {
			return false;
		}

		return true;
	}

	public boolean hasSettled(FxDeal fxDeal) {

		Blotter blotter = fxDeal.getBlotter();
		SettleHelper settleHelper = new SettleHelper(tenant);
		return settleHelper.settle(fxDeal, blotter, fxCashier);
	}

	public boolean dailyLimitReached(FxDeal fxDeal) {

		this.fxConversionHelper = new FxConversionHelper(settings, fxDeal, tenant);
		return fxConversionHelper.dailyLimitReached();

	}

	public boolean isLocalCurrencySet() {

		if (settings.getLocalCurrency().getId() == 0) {
			return false;
		}

		return true;
	}

	public boolean isMainTradeSet() {

		if (settings.getCurrencyPair().getId() == 0) {
			return false;
		}

		return true;
	}
}