/*Created by Sinatra Gunda
  At 17:26 on 9/26/2020 */

package com.wese.weseaddons.employeerelations.action;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.employeerelations.cache.EmployeeRelationsCache;
import com.wese.weseaddons.employeerelations.dao.CompanyOfficialsDAO;
import com.wese.weseaddons.employeerelations.dao.EmployeeClientDAO;
import com.wese.weseaddons.employeerelations.pojo.CompanyOfficials;
import com.wese.weseaddons.employeerelations.pojo.EmployeeClient;
import com.wese.weseaddons.helper.Helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeClientActions {

   private String tenantIdentifier ;
   private EmployeeClientDAO employeeClientDAO ;

   public EmployeeClientActions(String tenantIdentifier){
      this.tenantIdentifier = tenantIdentifier ;
      this.employeeClientDAO = new EmployeeClientDAO(tenantIdentifier);
   }

   public ObjectNode create(EmployeeClient employeeClient){

      Long id = employeeClientDAO.create(employeeClient);

      if(id!=null){
         return Helper.statusNodes(true).put("id" ,id);
      }

      return Helper.statusNodes(false).put("message" ,"Failed to create relations");
   }

   public ObjectNode findEmployerOfficials(Long clientId){

      ///first find client company

      List<EmployeeClient> employeeClientList = employeeClientDAO.findWhere("client_id" ,clientId.toString() ,false);
      if(!employeeClientList.isEmpty()){

         EmployeeClient employeeClient = employeeClientList.get(0);
         Long employerId = employeeClient.getEmployer().getId();

         CompanyOfficialsDAO companyOfficialsDAO = new CompanyOfficialsDAO(tenantIdentifier);
         List<CompanyOfficials> companyOfficialsList = companyOfficialsDAO.findWhere("employer_id" ,employerId.toString());

         if(!companyOfficialsList.isEmpty()){

            ArrayNode arrayNode = Helper.createArrayNode();
            for(CompanyOfficials companyOfficials : companyOfficialsList){
               arrayNode.add(companyOfficials.objectNode());
            }

            return Helper.statusNodes(true).putPOJO("pageItems" ,arrayNode);
         }
      }

      return Helper.statusNodes(false);
   }

   public ObjectNode findAll(Long employerId){

      List<EmployeeClient> employeeClientList = employeeClientDAO.findWhere("employer_id",employerId.toString(),true);

      ArrayNode arrayNode = Helper.createArrayNode();
      boolean status = false ;
      if(!employeeClientList.isEmpty()){

         for(EmployeeClient employeeClient : employeeClientList){
            arrayNode.add(employeeClient.objectNode());
         }
         status = true ;
      }

      return Helper.statusNodes(status).putPOJO("pageItems",arrayNode);

   }


}
