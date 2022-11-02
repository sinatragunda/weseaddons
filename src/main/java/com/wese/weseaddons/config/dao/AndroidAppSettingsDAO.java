/*Created by Sinatra Gunda
  At 04:59 on 10/5/2020 */

package com.wese.weseaddons.config.dao;

import com.wese.weseaddons.config.mapper.AndroidAppSettingsMapper;
import com.wese.weseaddons.config.pojo.AndroidAppSettings;
import com.wese.weseaddons.employeerelations.dao.CompanyOfficialsDAO;
import com.wese.weseaddons.employeerelations.pojo.CompanyOfficials;
import com.wese.weseaddons.helper.ListHelper;
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
import java.util.StringTokenizer;
import java.util.function.Consumer;

public class AndroidAppSettingsDAO {

    private String tenantIdentifier ;
    private JdbcTemplate jdbcTemplate ;
    private String schema ="m_app_settings";


    private Consumer<AndroidAppSettings> activityNotificationConsumer = (e)->{

        CompanyOfficialsDAO companyOfficialsDAO = new CompanyOfficialsDAO(tenantIdentifier);
        List<CompanyOfficials> companyOfficialsList = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(e.getActivityNotification() ,",");
        while(stringTokenizer.hasMoreTokens()){
            String id = stringTokenizer.nextToken();
            companyOfficialsList = companyOfficialsDAO.findWhere("employer_id" ,id);
            //EmployerDAO employerDAO = new EmployerDAO()
        }

        e.setEmailNotificationList(companyOfficialsList);

    };

    public AndroidAppSettingsDAO(String t){
        this.tenantIdentifier = t  ;
        this.jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenantIdentifier);

    }

    public AndroidAppSettings find(String tenantIdentifier){

        String sql = String.format("Select * from %s WHERE tenant = ?",schema);
        Object args[] = new Object[]{
                tenantIdentifier
        };

        List<AndroidAppSettings> androidAppsSettingsList = jdbcTemplate.query(sql ,args ,new AndroidAppSettingsMapper());
        androidAppsSettingsList.stream().forEach(activityNotificationConsumer);
        return ListHelper.get(androidAppsSettingsList ,0);
    }

    public boolean update(AndroidAppSettings androidAppsSettings){

        AndroidAppSettings a = find(tenantIdentifier);

        if(a==null){
            Long id = create(androidAppsSettings);
            if(id!=null){
                return true ;
            }
            return false ;
        }


        String sql  = String.format("UPDATE %s SET loan_products = ? ,activity_notification = ? ,address =? WHERE tenant =?",schema);

        Object args[] = new Object[]{
                androidAppsSettings.getLoanProducts(),
                androidAppsSettings.getActivityNotification(),
                androidAppsSettings.getAddress(),
                tenantIdentifier

        };

        int rows = jdbcTemplate.update(sql ,args);
        if(rows > 0){
            return true ;
        }
        return false;
    }
    public Long create(AndroidAppSettings androidAppSettings){

        String sql = String.format("INSERT INTO %s(tenant ,loan_products ,activity_notification ,address) VALUES(? , ? ,? ,?)" ,schema);
        Long id = null ;
        try {

            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setString(1,tenantIdentifier);
                    statement.setString(2,androidAppSettings.getLoanProducts());
                    statement.setString(3,androidAppSettings.getActivityNotification());
                    statement.setString(4,androidAppSettings.getAddress());

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
}
