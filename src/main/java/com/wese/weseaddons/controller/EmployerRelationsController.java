/*Created by Sinatra Gunda
  At 16:44 on 9/26/2020 */

package com.wese.weseaddons.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.employeerelations.action.BankAccountAction;
import com.wese.weseaddons.employeerelations.action.EmployeeClientActions;
import com.wese.weseaddons.employeerelations.action.EmployerAction;
import com.wese.weseaddons.employeerelations.action.CompanyOfficialsAction;
import com.wese.weseaddons.employeerelations.pojo.BankAccount;
import com.wese.weseaddons.employeerelations.pojo.DisbursedLoanBankAccount;
import com.wese.weseaddons.employeerelations.pojo.Employer;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value ="/employerrelations")
public class EmployerRelationsController {



    @RequestMapping(method = RequestMethod.POST)
    public ObjectNode createEmployer(@RequestBody Employer employer , @RequestParam("tenantIdentifier")String tenantIdentifier){

        EmployerAction employerAction = new EmployerAction(tenantIdentifier);
        return employerAction.create(employer);
       // return EmployeeClientActions.getInstance().createEmployer(employer ,tenantIdentifier);

    }

    @RequestMapping(method = RequestMethod.GET ,params = {"tenantIdentifier","id"})
    public ObjectNode findEmpoyer(@RequestParam("tenantIdentifier")String tenantIdentifier ,@RequestParam("id")Long employerId){

        EmployerAction employerAction = new EmployerAction(tenantIdentifier);
        return employerAction.find(employerId);

    }

    @RequestMapping(method = RequestMethod.GET)
    public ObjectNode findAll(@RequestParam("tenantIdentifier")String tenantIdentifier){

        EmployerAction employerAction = new EmployerAction(tenantIdentifier);
        return employerAction.findAll();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ObjectNode edit(@RequestParam("tenantIdentifier")String tenantIdentifier ,@RequestBody Employer employer){

        EmployerAction employerAction = new EmployerAction(tenantIdentifier);
        return employerAction.edit(employer);

    }


    @RequestMapping(value = "/employees", method = RequestMethod.POST)
    public ObjectNode addEmployees(@RequestParam("tenantIdentifier")String tenantIdentifier ,@RequestBody Employer employer){

        EmployerAction employerAction = new EmployerAction(tenantIdentifier);
        return employerAction.addEmployees(employer);

    }


    @RequestMapping(value = "/employee", method = RequestMethod.PUT)
    public ObjectNode findCompanyOfficials(@RequestParam("tenantIdentifier")String tenantIdentifier ,@RequestParam("id")Long id){

        CompanyOfficialsAction companyOfficialsAction = new CompanyOfficialsAction(tenantIdentifier);
        return companyOfficialsAction.findWhere(id);

    }



    @RequestMapping(value = "/clientofficials", method = RequestMethod.GET)
    public ObjectNode clientOfficials(@RequestParam("tenantIdentifier")String tenantIdentifier ,@RequestParam("clientId")Long clientId){

        EmployeeClientActions employeeClientActions = new EmployeeClientActions(tenantIdentifier);
        return employeeClientActions.findEmployerOfficials(clientId);

    }

    @RequestMapping(value = "/bankaccount", method = RequestMethod.GET)
    public ObjectNode findBankAccountsForClient(@RequestParam("tenantIdentifier")String tenantIdentifier ,@RequestParam("clientId")Long clientId){

        BankAccountAction bankAccountAction = new BankAccountAction(tenantIdentifier);
        return bankAccountAction.find(clientId);

    }


    @RequestMapping(value = "/bankaccount", method = RequestMethod.POST)
    public ObjectNode createBankAccount(@RequestParam("tenantIdentifier")String tenantIdentifier ,@RequestBody BankAccount bankAccount){

        BankAccountAction bankAccountAction = new BankAccountAction(tenantIdentifier);
        return bankAccountAction.create(bankAccount);

    }

    @RequestMapping(value = "/bankaccount", method = RequestMethod.PUT)
    public ObjectNode deactivateBankAccount(@RequestParam("tenantIdentifier")String tenantIdentifier ,@RequestBody BankAccount bankAccount){

        BankAccountAction bankAccountAction = new BankAccountAction(tenantIdentifier);
        return bankAccountAction.deactivateBankAccount(bankAccount);

    }

    @RequestMapping(value = "/bankaccount/loan", method = RequestMethod.POST)
    public ObjectNode createBankAccountLoanMap(@RequestParam("tenantIdentifier")String tenantIdentifier , @RequestBody DisbursedLoanBankAccount disbursedLoanBankAccount){

        BankAccountAction bankAccountAction = new BankAccountAction(tenantIdentifier);
        return bankAccountAction.createDisbusedBankAccount(disbursedLoanBankAccount);

    }

    @RequestMapping(value = "/bankaccount/loan", method = RequestMethod.GET)
    public ObjectNode findLinkedBankAccount(@RequestParam("tenantIdentifier")String tenantIdentifier ,@RequestParam("loanId")Long loanId){

        //BankAccountAction bankAccountAction = new BankAccountAction(tenantIdentifier);
        //return bankAccountAction.findDisbursedBankAccount(loanId);
        return null ;

    }

}
