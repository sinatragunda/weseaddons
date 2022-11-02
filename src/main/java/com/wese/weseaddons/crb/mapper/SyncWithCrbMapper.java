/*Created by Sinatra Gunda
  At 7:50 AM on 2/25/2020 */

package com.wese.weseaddons.crb.mapper;

import com.wese.weseaddons.crb.pojo.SyncWithCrb;
import com.wese.weseaddons.crb.pojo.Token;
import com.wese.weseaddons.enumerations.TYPE_OF_RESOURCE;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SyncWithCrbMapper implements RowMapper<SyncWithCrb> {


    @Override
    public SyncWithCrb mapRow(ResultSet rs , int rowNum){

        SyncWithCrb syncWithCrb =null ;

        try{
            syncWithCrb = new SyncWithCrb();
            int resourceType = rs.getInt("type_of_resource");

            syncWithCrb.setTypeOfResource(TYPE_OF_RESOURCE.fromInt(resourceType));

            syncWithCrb.setResourceId(rs.getLong("resource_id"));


        }

        catch (SQLException s){
            s.printStackTrace();
        }

        return syncWithCrb ;
    }

}
