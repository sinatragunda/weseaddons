package com.wese.weseaddons.bereaudechange.dao;

import com.wese.weseaddons.bereaudechange.helper.ForeignKeyHelper;
import com.wese.weseaddons.bereaudechange.impl.FxDAOService;
import com.wese.weseaddons.bereaudechange.mapper.TransactionChargeMapper;
import com.wese.weseaddons.bereaudechange.pojo.Charges;
import com.wese.weseaddons.bereaudechange.pojo.MoneyAccountTransactions;
import com.wese.weseaddons.bereaudechange.pojo.TransactionCharge;
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


public class TransactionChargeDAO implements FxDAOService {

    private String tenant ;
    private JdbcTemplate jdbcTemplate ;


    public TransactionChargeDAO(String tenant){
        this.tenant = tenant ;
        this.jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenant);
    }

    public List<TransactionCharge> findAll(){

        String sql = "SELECT * FROM m_fx_transaction_charge_mapping";

        List<TransactionCharge> transactionChargeList = jdbcTemplate.query(sql ,new TransactionChargeMapper());

        if(transactionChargeList.isEmpty()){
            return new ArrayList<>();
        }

        for(TransactionCharge t : transactionChargeList){

            Charges charges = (Charges) ForeignKeyHelper.get(new ChargesDAO(tenant) ,t.getCharges().getId());
            MoneyAccountTransactions moneyAccountTransactions = (MoneyAccountTransactions)ForeignKeyHelper.get(new MoneyAccountTransactionsDAO(tenant) ,t.getMoneyAccountTransactions().getId());

            t.setCharges(charges);
            t.setMoneyAccountTransactions(moneyAccountTransactions);
        }


        return transactionChargeList;

    }

    public List<TransactionCharge> find(long id ,String column){

        String sql = String.format("SELECT * FROM m_fx_transaction_charge_mapping where %s=?" ,column);

        Object args[] = new Object[]{
                id
        };

        List<TransactionCharge> transactionChargeList = jdbcTemplate.query(sql ,args ,new TransactionChargeMapper());

        for(TransactionCharge t : transactionChargeList){

        	Charges charges = (Charges) ForeignKeyHelper.get(new ChargesDAO(tenant) ,t.getCharges().getId());
        	MoneyAccountTransactions moneyAccountTransactions = (MoneyAccountTransactions)ForeignKeyHelper.get(new MoneyAccountTransactionsDAO(tenant) ,t.getMoneyAccountTransactions().getId());

        	if(charges!=null){
        	    System.out.println("Print something here "+charges.getAmount());
            }

        	t.setCharges(charges);
        	t.setMoneyAccountTransactions(moneyAccountTransactions);
        }

        return transactionChargeList ;

    }


    public long create(TransactionCharge transactionCharge){

        String sql ="insert into m_fx_transaction_charge_mapping(transaction_id ,charge_id) values(? ,?)";
        long id = 0 ;
        try {

            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setLong(1,transactionCharge.getMoneyAccountTransactions().getId());
                    statement.setLong(2 ,transactionCharge.getCharges().getId());
                    return statement;
                }
            }, holder);

            id = holder.getKey().longValue();
        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return id ;
    }

    @Override
    public Object find(long id) {
        return null;
    }
}


