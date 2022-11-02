/*Created by Sinatra Gunda
  At 08:17 on 10/5/2020 */

package com.wese.weseaddons.config.mapper;

import com.wese.weseaddons.config.pojo.AndroidAppSettings;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AndroidAppSettingsMapper implements RowMapper<AndroidAppSettings>{

    @Override
    public AndroidAppSettings mapRow(ResultSet resultSet, int i) {

        AndroidAppSettings androidAppSettings = null;
        try{
            androidAppSettings = new AndroidAppSettings();
            androidAppSettings.setAddress(resultSet.getString("address"));
            androidAppSettings.setTenant(resultSet.getString("tenant"));
            androidAppSettings.setLoanProducts(resultSet.getString("loan_products"));
            androidAppSettings.setActivityNotification(resultSet.getString("activity_notification"));
            androidAppSettings.setAppVersion(resultSet.getDouble("app_version"));

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return androidAppSettings;
    }
}
