/*Created by Sinatra Gunda
  At 9:24 AM on 2/26/2020 */

package com.wese.weseaddons.crb.helper;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.pojo.MakerCheckerEntry;


public class CrbMakerCheckerEntryHelper {


    public static ObjectNode upload(long commandId , String tenantIdentifier) {

        MakerCheckerEntry makerCheckerEntry = SyncWithCrbHelper.makerCheckerProperty(commandId, tenantIdentifier);

        String entityName = makerCheckerEntry.getEntityName();
        String actionName = makerCheckerEntry.getActionName();

        if (entityName.equalsIgnoreCase("loan")) {

            if (actionName.equalsIgnoreCase("approve")) {

                return CreditApplicationHelper.upload(makerCheckerEntry.getResourceId(), tenantIdentifier);
            }

            else if(actionName.equalsIgnoreCase("disbursement")){

                //return CreditInformationRecordHelper.upload(makerCheckerEntry.getResourceId() ,tenantIdentifier);

            }
        }

        return null;

    }
}
