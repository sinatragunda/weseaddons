/*Created by Sinatra Gunda
  At 7:44 AM on 2/26/2020 */

package com.wese.weseaddons.crb.helper;

import com.wese.weseaddons.bereaudechange.helper.OffshoreThread;
import com.wese.weseaddons.crb.dao.SyncWithCrbDAO;
import com.wese.weseaddons.crb.pojo.SyncWithCrb;
import com.wese.weseaddons.enumerations.REQUEST_METHOD;
import com.wese.weseaddons.enumerations.TYPE_OF_RESOURCE;
import com.wese.weseaddons.http.HttpClientBridge;
import com.wese.weseaddons.pojo.MakerCheckerEntry;
import org.json.JSONException;
import org.json.JSONObject;

public class SyncWithCrbHelper {

    public static boolean sync(String tenantIdentier , long resourceId , TYPE_OF_RESOURCE typeOfResource){

        SyncWithCrbDAO syncWithCrbDAO = new SyncWithCrbDAO(tenantIdentier);

        SyncWithCrb syncWithCrb = syncWithCrbDAO.findCrbRecord(resourceId ,typeOfResource);

        if(syncWithCrb==null){

            Runnable runnable = ()->{
                syncWithCrbDAO.create(new SyncWithCrb(0 ,resourceId ,typeOfResource));
            };

            OffshoreThread.run(runnable);

            return false ;
        }

        return true ;

    }

    public static MakerCheckerEntry makerCheckerProperty(long commandId,String tenantIdentifier){

        HttpClientBridge httpClientBridge = new HttpClientBridge(REQUEST_METHOD.GET);

        String response = httpClientBridge.makeRequest("/audits/"+commandId);

        JSONObject jsonObject = null ;

        MakerCheckerEntry makerCheckerEntry = null ;

        try{

            jsonObject = new JSONObject(response);

            long resourceId = jsonObject.getLong("resourceId");
            String actionName = jsonObject.getString("actionName");
            String entityName = jsonObject.getString("entityName");

            makerCheckerEntry = new MakerCheckerEntry(resourceId ,entityName ,actionName);

        }

        catch (JSONException j){
            j.printStackTrace();
            return null;
        }

        return makerCheckerEntry ;



    }

}
