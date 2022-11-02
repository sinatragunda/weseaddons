package com.wese.weseaddons.remittance.helper;


import com.wese.weseaddons.bereaudechange.enumerations.SETTLE_TYPE;
import com.wese.weseaddons.bereaudechange.helper.OffshoreThread;
import com.wese.weseaddons.bereaudechange.pojo.*;
import com.wese.weseaddons.enumerations.REQUEST_METHOD;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.helper.TimeHelper;
import com.wese.weseaddons.http.HttpClientBridge;
import com.wese.weseaddons.remittance.pojo.RemittanceTransaction;
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

public class RemittanceSettleHelper {

    private String tenant ;
    private JdbcTemplate jdbcTemplate ;


    public RemittanceSettleHelper(String s){

        this.tenant = s ;
        this.jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(s);

    }

    public boolean settle(RemittanceTransaction remittanceTransaction , FxCashier fxCashier , boolean isDeposit){

        final long subResourceId ;

        if(!isDeposit){

            String url = String.format("/tellers/%d/cashiers/%d/settle?command=settle",fxCashier.getSystemTellerId() ,fxCashier.getSystemCashierId());
            
            JSONObject jsonObject = getJsonObject(remittanceTransaction);

            HttpClientBridge httpClientBridge = new HttpClientBridge(REQUEST_METHOD.POST);
            httpClientBridge.setPostObject(jsonObject);
            String response = httpClientBridge.makeRequest(url);

            JSONObject jsonObject1 = new JSONObject(response);

            System.err.println(response);

            try{

                subResourceId = jsonObject1.getLong("subResourceId");

                Runnable runnable = ()->{
                	settleWithdrawUsingJdbc(subResourceId);
                };

                OffshoreThread.run(runnable);
                return true ;
            }

            catch (JSONException j){

                j.printStackTrace();
                return false ;
            }
        }

        OffshoreThread.run(settleDepositUsingJdbc(remittanceTransaction ,fxCashier));
        return true;

    }

    public JSONObject getJsonObject(RemittanceTransaction remittanceTransaction){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("currencyCode",remittanceTransaction.getStandardCurrency().getName());

        jsonObject.put("txnAmount",remittanceTransaction.getAmount());
        jsonObject.put("txnNote","Rx Cash Out Remittance");
        jsonObject.put("locale","en");
        jsonObject.put("dateFormat","dd MMMM yyyy");
        jsonObject.put("txnDate", TimeHelper.timestampToStringEx(remittanceTransaction.getTransactionDate()));

        return jsonObject ;

    }


    public Runnable settleDepositUsingJdbc(RemittanceTransaction remittanceTransaction ,FxCashier fxCashier){

        Runnable runnable = ()->{

            SETTLE_TYPE settleType = SETTLE_TYPE.DEPOSIT ;
            java.util.Date javaDate = TimeHelper.timestampToDateEx(remittanceTransaction.getTransactionDate());
            java.time.LocalDateTime strokeDate = TimeHelper.timestampToDateWithTime(remittanceTransaction.getTransactionDate());


            String sql = "INSERT INTO m_cashier_transactions(cashier_id, txn_type, txn_amount, txn_date, created_date, txn_note, currency_code) VALUES(? ,?,?,?,?,?,?)";

            try{

                GeneratedKeyHolder holder = new GeneratedKeyHolder();
                jdbcTemplate.update(new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                        statement.setLong(1,fxCashier.getSystemCashierId());
                        statement.setInt(2,Integer.parseInt(settleType.getCode()));
                        statement.setDouble(3,remittanceTransaction.getAmount());
                        statement.setDate(4 , Helper.localDateTimeToSqlDate(strokeDate));
                        statement.setTimestamp(5 ,Helper.localDateTimeToSqlTimestamp(strokeDate));
                        statement.setString(6 ,"Rx Cash In Remittance");

                        String currencyCode = remittanceTransaction.getStandardCurrency().getName();

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
 