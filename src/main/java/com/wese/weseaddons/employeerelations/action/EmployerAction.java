/*Created by Sinatra Gunda
  At 04:16 on 9/26/2020 */

package com.wese.weseaddons.employeerelations.action;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.employeerelations.dao.EmployerDAO;
import com.wese.weseaddons.employeerelations.dao.CompanyOfficialsDAO;
import com.wese.weseaddons.employeerelations.pojo.EmployeeClient;
import com.wese.weseaddons.employeerelations.pojo.Employer;
import com.wese.weseaddons.employeerelations.pojo.CompanyOfficials;
import com.wese.weseaddons.helper.Helper;

import java.util.List;

public class EmployerAction {


    private String tenantIdientifer ;
    private EmployerDAO employerDAO ;
    private EmployeeClientActions employeeClientActions ;

    public EmployerAction(String tenantIdientifer){
        this.tenantIdientifer = tenantIdientifer;
        this.employerDAO = new EmployerDAO(tenantIdientifer);
        this.employeeClientActions = new EmployeeClientActions(tenantIdientifer);
    }


    public ObjectNode addEmployees(Employer employer){

        List<EmployeeClient> employeeClientList = employer.getEmployeeClientList();
        for(EmployeeClient employeeClient : employeeClientList){
            employeeClient.setEmployer(employer);
            employeeClientActions.create(employeeClient);
        }

        return Helper.statusNodes(true);

    }

    public ObjectNode edit(Employer employer){

        boolean status = employerDAO.edit(employer);
        return Helper.statusNodes(status);
    }


    public ObjectNode create(Employer employer){

        Long id = employerDAO.create(employer);
        if(id!=null){

            employer.setId(id);
            CompanyOfficialsDAO companyOfficialsDAO = new CompanyOfficialsDAO(tenantIdientifer);
            try{
                for(CompanyOfficials companyOfficials : employer.getCompanyOfficialsList()){
                    companyOfficials.setEmployer(employer);
                    companyOfficialsDAO.create(companyOfficials);

                }
            }
            catch (NullPointerException n){

            }
            return Helper.statusNodes(true).put("id" ,id);

        }

        return Helper.statusNodes(false).put("message" ,"Failed to create employer ,please try again");
    }


    public ObjectNode find(Long id){

        Employer employer = employerDAO.find(id);

        if(employer!=null){
            return employer.objectNode().put("status",true);
        }

        return Helper.statusNodes(false);

    }


    public ObjectNode findAll(){

        List<Employer> employerList = employerDAO.findAll();
        ArrayNode arrayNode = Helper.createArrayNode();
        boolean status = false ;
        if(!employerList.isEmpty()){
            status = true ;
            for(Employer employer :employerList){
                arrayNode.add(employer.objectNode());
            }
        }
        return Helper.statusNodes(status).putPOJO("pageItems",arrayNode);

    }


}
