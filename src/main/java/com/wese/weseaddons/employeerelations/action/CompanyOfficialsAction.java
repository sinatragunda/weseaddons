/*Created by Sinatra Gunda
  At 07:20 on 9/27/2020 */

package com.wese.weseaddons.employeerelations.action;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.employeerelations.dao.CompanyOfficialsDAO;
import com.wese.weseaddons.employeerelations.pojo.CompanyOfficials;
import com.wese.weseaddons.helper.Helper;

import java.util.List;

public class CompanyOfficialsAction {

    private CompanyOfficialsDAO companyOfficialsDAO;


    public ObjectNode find(Long id){
        return  null ;
    }

    public CompanyOfficialsAction(String tenantIdentifier){

        this.companyOfficialsDAO = new CompanyOfficialsDAO(tenantIdentifier);
    }

    public ObjectNode findWhere(Long id){

        List<CompanyOfficials> companyOfficialsList = companyOfficialsDAO.findWhere("employer_id" ,id.toString());

        ArrayNode arrayNode = Helper.createArrayNode();

        for(CompanyOfficials companyOfficials : companyOfficialsList){
            arrayNode.add(companyOfficials.objectNode());
        }

        ObjectNode objectNode = Helper.createObjectNode();
        objectNode.putPOJO("pageItems" ,arrayNode);
        return objectNode ;
    }
}
