package com.wese.weseaddons.weselicense.action;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.weselicense.dao.DaoSession;
import com.wese.weseaddons.weselicense.dao.TenantDAO;
import com.wese.weseaddons.weselicense.pojo.Tenant;


import java.util.List;

public class TenantAction {

    public ObjectNode getAllTenants(){

        ArrayNode arrayNode = Helper.createArrayNode();

        TenantDAO tenantDAO = new TenantDAO();
        List<Tenant> tenantList = tenantDAO.getAllTenants();

        if(tenantList.isEmpty()){
            return Helper.statusNodes(false);
        }

        for(Tenant tenant : tenantList){

            arrayNode.addPOJO(tenantNode(tenant));

        }

        ObjectNode objectNode = Helper.statusNodes(true);
        objectNode.putPOJO("pageItems",objectNode);

        return objectNode ;
    }

    public ObjectNode getTenant(long id ,String name){

        TenantDAO tenantDAO = new TenantDAO();
        Tenant tenant = null ;

        if(name==null){
            tenant = tenantDAO.getTenant(id);
        }
        else{
            tenant = tenantDAO.getTenant(name);
        }


        if(tenant==null){
            return Helper.statusNodes(false);
        }


        ObjectNode objectNode = Helper.statusNodes(true);
        objectNode.putPOJO("tenant",tenantNode(tenant));

        return objectNode ;
    }

    public ObjectNode tenantNode(Tenant tenant){

        ObjectNode objectNode = Helper.createObjectNode();
        objectNode.put("id" ,tenant.getId());
        objectNode.put("gracePeriod" ,tenant.getGracePeriod());
        objectNode.put("name" ,tenant.getTenantName());
        objectNode.put("exemption" ,tenant.isExemption());

        return  objectNode ;
    }
}
