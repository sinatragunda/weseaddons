package com.wese.weseaddons.weselicense.dao;

import com.wese.weseaddons.jdbc.JdbcTemplateInit;
import com.wese.weseaddons.weselicense.mapper.BillMapper;
import com.wese.weseaddons.weselicense.pojo.Bill;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class BillDAO {

    private JdbcTemplate jdbcTemplate ;


    public BillDAO(){
        init();
    }

    public void init(){

        jdbcTemplate = JdbcTemplateInit.getJdbcTemplate("billing");
    }


    public long createBill(Bill bill){


        String sql = "INSERT INTO m_billing(date ,service_id ,tenant_id ,state ,creation_date ,amount,duration) values(? ,?,?,?,?)";

        if(jdbcTemplate==null) {

            System.out.println("Jdbc template is null");
        }

        long billId = 0 ;

        try {

            /// to do ,match values


            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setDouble(1,bill.getAmount());
                    statement.setLong(2 ,bill.getDate());
                    statement.setInt(2 ,bill.getBillState().ordinal());

                    statement.setLong(4 ,bill.getTenant().getId());
                    statement.setLong(5 ,bill.getService().getId());
                    return statement;
                }
            }, holder);

            billId = holder.getKey().longValue();
        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return 0;
        }

        return billId ;
    }

    public Bill getBill(long id) {

        String sql = "SELECT * FROM m_bills where id=?";

        Object args[] = new Object[]{
                id
        };

        List<Bill> billList = jdbcTemplate.query(sql ,args ,new BillMapper());

        if (billList.isEmpty()){
            return null;
        }

        return billList.get(0);

    }
}
