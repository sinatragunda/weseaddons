/*Created by Sinatra Gunda
  At 02:18 on 9/26/2020 */

package com.wese.weseaddons.employeerelations.pojo;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.interfaces.PojoInterface;
import com.wese.weseaddons.pojo.Client;

public class EmployeeClient implements PojoInterface{

    private Long id ;
    private Client client ;
    private Employer employer ;

    public EmployeeClient(){}

    public EmployeeClient(Long id){
        this.id = id ;
    }

    public EmployeeClient(Client client) {
        this.client = client;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }


    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }


    @Override
    public String getSchema() {
        return "m_app_employee_client_mapper";
    }


    public ObjectNode objectNode(){

        ObjectNode objectNode = Helper.createObjectNode();
        objectNode.put("id" ,id);
        objectNode.put("name" ,client.getDisplayName());
        objectNode.put("accountNumber",client.getAccountNumber());
        return objectNode ;
    }
}
