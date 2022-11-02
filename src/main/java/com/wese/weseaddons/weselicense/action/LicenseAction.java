package com.wese.weseaddons.weselicense.action;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.logger.Logger;
import com.wese.weseaddons.weselicense.dao.DaoSession;
import com.wese.weseaddons.weselicense.body.TenantRequestBody;
import com.wese.weseaddons.weselicense.dao.TenantDAO;
import com.wese.weseaddons.weselicense.helper.ExpirationTime;
import com.wese.weseaddons.weselicense.helper.ObjectNodeBuilder;
import com.wese.weseaddons.weselicense.pojo.Tenant;

public class LicenseAction {

    private TenantRequestBody tenantRequestBody ;

    public  LicenseAction(TenantRequestBody tenantRequestBody){
        this.tenantRequestBody = tenantRequestBody ;
    }

    public ObjectNode checkLicenseStatus(){

        DaoSession daoSession = new DaoSession();
        TenantDAO tenantDAO = new TenantDAO();

        Tenant tenant = tenantDAO.getTenant(tenantRequestBody.getTenantIdentifier());

        if(tenant==null){
            Logger.log("Tenant "+tenantRequestBody.getTenantIdentifier()+" is not found ,data null");
            return ObjectNodeBuilder.accountStatusNode(false ,0 ,false);

        }

        if(tenant.isExemption()){

           return ObjectNodeBuilder.accountStatusNode(true ,0 ,false);

        }

        ExpirationTime expirationTime = new ExpirationTime(tenant ,daoSession);
        long daysRemaining = expirationTime.getCurrentLicenseDaysToExpiration();

        long gracePeriod = tenant.getGracePeriod();

        if(daysRemaining <= gracePeriod && daysRemaining > 0 ){

            ///now show the time time to repay warning
            return ObjectNodeBuilder.accountStatusNode(true ,daysRemaining ,true);

        }

        System.out.println("What can be here "+daysRemaining);

        if(daysRemaining <=0){

            // here lock the system
            return ObjectNodeBuilder.accountStatusNode(false ,0,false);

        }


        System.out.println("What can be here also ? "+daysRemaining);

        return ObjectNodeBuilder.accountStatusNode(true ,daysRemaining ,false);
    }
}
