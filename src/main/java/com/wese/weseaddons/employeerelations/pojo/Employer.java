/*Created by Sinatra Gunda
  At 02:19 on 9/26/2020 */

package com.wese.weseaddons.employeerelations.pojo;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.interfaces.PojoInterface;

import java.util.ArrayList;
import java.util.List;

public class Employer implements PojoInterface{


    private Long id ;
    private String name ;
    private String accountNumber ;
    private String address;
    private String email ;
    private List<EmployeeClient> employeeClientList;
    private List<CompanyOfficials> companyOfficialsList;


    public Employer(){
        employeeClientList = new ArrayList<>();
        companyOfficialsList = new ArrayList<>();
    }

    public Employer(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CompanyOfficials> getCompanyOfficialsList() {
        return companyOfficialsList;
    }

    public void setCompanyOfficialsList(List<CompanyOfficials> companyOfficialsList) {
        this.companyOfficialsList = companyOfficialsList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<EmployeeClient> getEmployeeClientList() {
        return employeeClientList;
    }

    public void setEmployeeClientList(List<EmployeeClient> employeeClientList) {
        this.employeeClientList = employeeClientList;
    }


    @Override
    public String getSchema() {
        return "m_app_employer";
    }

    public ObjectNode objectNode(){

        ObjectNode objectNode = Helper.createObjectNode();
        objectNode.put("id" ,id);
        objectNode.put("name" ,name);
        objectNode.put("email",email);
        objectNode.put("address" ,address);
        objectNode.put("accountNumber" ,accountNumber);

        objectNode.put("hasEmployees" ,!employeeClientList.isEmpty());
        objectNode.put("hasOfficials",!companyOfficialsList.isEmpty());

        ArrayNode clientsArrayNode = Helper.createArrayNode();       
        if(!employeeClientList.isEmpty()){
            for(EmployeeClient employeeClient : employeeClientList){
                clientsArrayNode.add(employeeClient.objectNode());
            }

        }

        objectNode.putPOJO("clients" ,clientsArrayNode);

        ArrayNode companyOfficialsNode = Helper.createArrayNode();           
        if(!companyOfficialsList.isEmpty()){
            for(CompanyOfficials companyOfficials : companyOfficialsList){
                companyOfficialsNode.add(companyOfficials.objectNode());
            }
        }

        objectNode.putPOJO("companyOfficials" ,companyOfficialsNode);

        return objectNode ;
    }
}
