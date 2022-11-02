package com.wese.weseaddons.bereaudechange.dao;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.body.FxCashierBody;
import com.wese.weseaddons.bereaudechange.fxrequest.CashierRequest;
import com.wese.weseaddons.bereaudechange.fxrequest.StaffRequest;
import com.wese.weseaddons.bereaudechange.fxrequest.TellerRequest;
import com.wese.weseaddons.bereaudechange.impl.FxDAOService;
import com.wese.weseaddons.bereaudechange.mapper.FxCashierMapper;
import com.wese.weseaddons.pojo.AppUser;
import com.wese.weseaddons.bereaudechange.pojo.FxCashier;
import com.wese.weseaddons.bereaudechange.pojo.Staff;
import com.wese.weseaddons.bereaudechange.session.FxReload;
import com.wese.weseaddons.bereaudechange.session.FxSession;
import com.wese.weseaddons.helper.Helper;
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

public class FxCashierDAO implements FxDAOService{

    private String tenant ;
    private JdbcTemplate jdbcTemplate ;
    private FxCashier fxCashier ;

    public FxCashierDAO(){}

    public FxCashierDAO(String tenant){
        this.tenant = tenant ;
        this.jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenant);
    }

    public FxCashier find(long id){

        String sql = "SELECT * FROM m_fx_cashier where id=?";

        Object args[] = new Object[]{
                id
        };

        List<FxCashier> fxCashierList = jdbcTemplate.query(sql ,args ,new FxCashierMapper());

        if (fxCashierList.isEmpty()){
            return null ;
        }

        FxCashier fxCashier = fxCashierList.get(0);
        AppUser appUser = findForeignKey(fxCashier);
        fxCashier.setAppUser(appUser);
        return fxCashier;

    }

    public FxCashier findWhere(long id ,String column){

        String sql = String.format("SELECT * FROM m_fx_cashier where %s=?",column);

        Object args[] = new Object[]{
                id
        };

        List<FxCashier> fxCashierList = jdbcTemplate.query(sql ,args ,new FxCashierMapper());

        if (fxCashierList.isEmpty()){
            return null ;
        }

        FxCashier fxCashier = fxCashierList.get(0);
        //AppUser appUser = findForeignKey(fxCashier);
        //fxCashier.setAppUser(appUser);
        return fxCashier;

    }

    public List<FxCashier> findAll(){

        String sql = "SELECT * FROM m_fx_cashier";

        List<FxCashier> fxCashierList = jdbcTemplate.query(sql ,new FxCashierMapper());

        if(fxCashierList.isEmpty()){
            return new ArrayList<>();
        }

        for(FxCashier fxCashier : fxCashierList){
            AppUser appUser = findForeignKey(fxCashier);
            fxCashier.setAppUser(appUser);
        }

        return fxCashierList;

    }

    public AppUser findForeignKey(FxCashier fxCashier){
        return FxSession.instance.getAppUser(tenant ,fxCashier.getAppUser().getId());
    }



    public ObjectNode create(FxCashierBody fxCashierBody){
        
        FxReload.getInstance().start();

        AppUser appUser = FxSession.getInstance().getAppUser(tenant ,fxCashierBody.getAppUser().getId());
        fxCashierBody.setAppUser(appUser);
        Staff staff = new Staff(fxCashierBody);
        FxCashier fxCashier = new FxCashier(fxCashierBody);
        StaffRequest staffRequest = new StaffRequest(tenant);
        staffRequest.createStaff(staff);

        if(staff.getId()==0){

            return Helper.statusNodes(false).put("message" ,"Failed to create teller ,some parts of this data already exist elsewhere in the system");
        }

        TellerRequest tellerRequest = new TellerRequest(tenant);
        long tellerId = tellerRequest.createTeller(staff ,fxCashier);

        if(tellerId==0){

            return Helper.statusNodes(false).put("message" ,"Failed to create teller ,some parts of this data already exist elsewhere in the system");
        }


        CashierRequest cashierRequest = new CashierRequest(tenant);
        long cashierId = cashierRequest.createCashier(staff ,fxCashier ,tellerId);

        if(cashierId==0){

            return Helper.statusNodes(false).put("message" ,"Failed to create teller ,some parts of this data already exist elsewhere in the system");
        }

        String sql= "insert into m_fx_cashier(teller_name ,description ,app_user_id ,active_from_date ,active_to_date ,system_cashier_id ,system_teller_id ,system_staff_id) values (? ,? ,? ,? ,? ,? ,? ,?)";
        try{

            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setString(1,fxCashier.getTellerName());
                    statement.setString(2,fxCashier.getDescription());
                    statement.setLong(3,fxCashier.getAppUser().getId());
                    statement.setLong(4 ,fxCashier.getActiveFromDate());
                    statement.setLong(5 ,fxCashier.getActiveToDate());
                    statement.setLong(6 ,cashierId);
                    statement.setLong(7 ,tellerId);
                    statement.setLong(8,staff.getId());
                    return statement;
                }
            }, holder);

        }

        catch (Exception e) {
            e.printStackTrace();
            return Helper.statusNodes(false).put("message" ,"Failed to create teller ,unkwown error");
        }

        return Helper.statusNodes(true);
    }
}
