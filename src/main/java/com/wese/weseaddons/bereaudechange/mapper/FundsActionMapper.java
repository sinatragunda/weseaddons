package com.wese.weseaddons.bereaudechange.mapper;


import com.wese.weseaddons.pojo.AppUser;
import com.wese.weseaddons.bereaudechange.pojo.FundsAction;
import com.wese.weseaddons.bereaudechange.pojo.MoneyAccount;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FundsActionMapper implements RowMapper<FundsAction> {

    @Override
    public FundsAction mapRow(ResultSet rs , int rownNum){

        FundsAction fundsAction =null;

        try{
            fundsAction = new FundsAction();
            fundsAction.setId(rs.getLong("id"));
            fundsAction.setAmount(rs.getDouble("amount"));
            fundsAction.setAuthoriser(new AppUser(rs.getLong("authoriser_id")));
            fundsAction.setCharges(rs.getDouble("charges"));

            long id = rs.getLong("source_account_id");
            fundsAction.setSourceMoneyAccount(new MoneyAccount(id));


            long id1 = rs.getLong("destination_account_id");
            fundsAction.setDestinationMoneyAccount(new MoneyAccount(id1));

            int actionType = rs.getInt("action_type");
            fundsAction.setFundsActionType(actionType);
        }
        catch (SQLException s){
            s.printStackTrace();
        }

        return fundsAction ;

    }
}
