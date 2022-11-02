package com.wese.weseaddons.bereaudechange.helper;

import com.wese.weseaddons.bereaudechange.dao.TradingRatesDAO;
import com.wese.weseaddons.bereaudechange.enumerations.FINANCIAL_INSTRUMENT_TYPE;
import com.wese.weseaddons.bereaudechange.enumerations.SETTLE_TYPE;
import com.wese.weseaddons.bereaudechange.enumerations.TRADE_TYPE;
import com.wese.weseaddons.bereaudechange.pojo.*;
import com.wese.weseaddons.enumerations.REQUEST_METHOD;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.helper.TimeHelper;
import com.wese.weseaddons.http.HttpClientBridge;
import com.wese.weseaddons.jdbc.JdbcTemplateInit;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class SettleHelper {

    private String tenant ;
    private JdbcTemplate jdbcTemplate ;


    public SettleHelper(String s){

        this.tenant = s ;
        this.jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(s);
    }

    public boolean settle(FxDeal fxDeal , Blotter blotter ,FxCashier fxCashier){

        TradingRatesDAO tradingRatesDAO = new TradingRatesDAO(tenant);
        TradingRates tradingRates = tradingRatesDAO.find(fxDeal.getTradingRates().getId());
        fxDeal.setTradingRates(tradingRates);

        CurrencyPair currencyPair = tradingRates.getCurrencyPair();
        
        MoneyAccount baseMoneyAccount = currencyPair.getBaseCurrencyMoneyAccount();
        MoneyAccount quoteMoneyAccount = currencyPair.getQuoteCurrencyMoneyAccount();
        FinancialInstrument baseFinancialInstrument = baseMoneyAccount.getTradingCurrency().getFinancialInstrument();
        FinancialInstrument quoteFinancialInstrument = quoteMoneyAccount.getTradingCurrency().getFinancialInstrument();

        final long subResourceId ;

        if(baseFinancialInstrument.getFinancialInstrumentType()==FINANCIAL_INSTRUMENT_TYPE.CASH){

            String url = String.format("/tellers/%d/cashiers/%d/settle?command=settle",fxCashier.getSystemTellerId() ,fxCashier.getSystemCashierId());
            JSONObject jsonObject = getJsonObject(fxDeal ,blotter);

            HttpClientBridge httpClientBridge = new HttpClientBridge(REQUEST_METHOD.POST);
            httpClientBridge.setPostObject(jsonObject);
            String response = httpClientBridge.makeRequest(url);

            JSONObject jsonObject1 = new JSONObject(response);

            try{

                subResourceId = jsonObject1.getLong("subResourceId");

                Runnable runnable = ()->{
                	settleWithdrawUsingJdbc(subResourceId);
                };

                OffshoreThread.run(runnable);
            }

            catch (JSONException j){

                j.printStackTrace();
                return false ;
            }
        }

        if(quoteFinancialInstrument.getFinancialInstrumentType()==FINANCIAL_INSTRUMENT_TYPE.CASH){

             OffshoreThread.run(settleDepositUsingJdbc(fxDeal ,fxCashier ,blotter));

        }

        return true;
    }

    public JSONObject getJsonObject(FxDeal fxDeal ,Blotter blotter){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("currencyCode",fxDeal.getTradingRates().getCurrencyPair().getBaseCurrencyMoneyAccount().getTradingCurrency().getStandardCurrency().getName());

        if(fxDeal.getTradeType()==TRADE_TYPE.SELL){

            jsonObject.put("currencyCode",fxDeal.getTradingRates().getCurrencyPair().getQuoteCurrencyMoneyAccount().getTradingCurrency().getStandardCurrency().getName());

        }
        jsonObject.put("txnAmount",blotter.getNetBaseAmount());
        jsonObject.put("txnNote","Fx Cash Out Transaction");
        jsonObject.put("locale","en");
        jsonObject.put("dateFormat","dd MMMM yyyy");
        jsonObject.put("txnDate", TimeHelper.timestampToStringEx(fxDeal.getDate()));

        return jsonObject ;

    }


    public Runnable settleDepositUsingJdbc(FxDeal fxDeal ,FxCashier fxCashier ,Blotter blotter){


        Runnable runnable = ()->{

            SETTLE_TYPE settleType = SETTLE_TYPE.DEPOSIT ;
            java.util.Date javaDate = TimeHelper.timestampToDateEx(fxDeal.getDate());
            java.time.LocalDateTime strokeDate = TimeHelper.timestampToDateWithTime(fxDeal.getDate());


            String sql = "INSERT INTO m_cashier_transactions(cashier_id, txn_type, txn_amount, txn_date, created_date, txn_note, currency_code) VALUES(? ,?,?,?,?,?,?)";

            try{

                GeneratedKeyHolder holder = new GeneratedKeyHolder();
                jdbcTemplate.update(new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                        statement.setLong(1,fxCashier.getSystemCashierId());
                        statement.setInt(2,Integer.parseInt(settleType.getCode()));
                        statement.setDouble(3,blotter.getNetQuoteAmount());
                        statement.setDate(4 , Helper.localDateTimeToSqlDate(strokeDate));
                        statement.setTimestamp(5 ,Helper.localDateTimeToSqlTimestamp(strokeDate));
                        statement.setString(6 ,"Fx Cash In Transactions");


                        String currencyCode = fxDeal.getTradingRates().getCurrencyPair().getQuoteCurrencyMoneyAccount().getTradingCurrency().getStandardCurrency().getName();

                        if(fxDeal.getTradeType() == TRADE_TYPE.SELL){

                            currencyCode = fxDeal.getTradingRates().getCurrencyPair().getBaseCurrencyMoneyAccount().getTradingCurrency().getStandardCurrency().getName();

                        }

                        statement.setString(7 ,currencyCode);

                        return statement;
                    }
                }, holder);

            }

            catch (Exception e) {
                e.printStackTrace();

            }
        };

        return runnable ;

    }

    public boolean settleWithdrawUsingJdbc(long id){

        SETTLE_TYPE settleType = SETTLE_TYPE.WITHDRAW ;

        Runnable runnable = ()->{

            int txnType = Integer.parseInt(settleType.getCode());

            String sql= "UPDATE m_cashier_transactions SET txn_type = ? WHERE id =?";

            Object args[] = new Object[]{
                    txnType ,
                    id
            };

            int affectedRows = jdbcTemplate.update(sql ,args);

        };

        OffshoreThread.run(runnable);
        return true ;
    }

}
 