/*Created by Sinatra Gunda
  At 16:54 on 9/26/2020 */

package com.wese.weseaddons.employeerelations.pojo;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.interfaces.PojoInterface;

public class CompanyOfficials implements PojoInterface{

    private Long id ;
    private String name ;
    private String email ;
    private Employer employer ;

    public CompanyOfficials(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    @Override
    public String getSchema() {
        return "m_app_company_official";
    }

    public ObjectNode objectNode(){

        ObjectNode objectNode = Helper.createObjectNode();
        objectNode.put("id",id);
        objectNode.put("name" ,name);
        objectNode.put("email",email);
        return objectNode ;
    }
}
