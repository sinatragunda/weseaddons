/*Created by Sinatra Gunda
  At 04:16 on 9/26/2020 */

package com.wese.weseaddons.employeerelations.action;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.employeerelations.dao.BankAccountDAO;
import com.wese.weseaddons.employeerelations.pojo.BankAccount;
import com.wese.weseaddons.employeerelations.pojo.DisbursedLoanBankAccount;
import com.wese.weseaddons.helper.Helper;

import java.util.List;

public class BankAccountAction {


    private String tenantIdentifier ;
    private BankAccountDAO bankAccountDAO;


    public BankAccountAction(String tenantIdientifer){
        this.tenantIdentifier = tenantIdientifer;
        this.bankAccountDAO = new BankAccountDAO(tenantIdientifer);
    }


    public ObjectNode create(BankAccount bankAccount){

        Long id = bankAccountDAO.create(bankAccount);
        if(id!=null){
            return Helper.statusNodes(true);

        }
        return Helper.statusNodes(false).put("message" ,"Failed to add bank account");

    }


    public ObjectNode createDisbusedBankAccount(DisbursedLoanBankAccount disbursedLoanBankAccount){

        Long id = bankAccountDAO.createDisbursedLoanBankAccount(disbursedLoanBankAccount);
        if(id!=null){
            return Helper.statusNodes(true);

        }
        return Helper.statusNodes(false);

    }


    public ObjectNode deactivateBankAccount(BankAccount bankAccount){

        boolean status = bankAccountDAO.deactivateBankAccount(bankAccount);

        return Helper.statusNodes(status);

    }


    public ObjectNode findDisbursedBankAccount(Long loanId){

        DisbursedLoanBankAccount disbursedLoanBankAccount = bankAccountDAO.findDisbursedLoanBankAccount(loanId);
        return disbursedLoanBankAccount.objectNode();

    }


    public ObjectNode find(Long clientId){

        List<BankAccount> bankAccountList = bankAccountDAO.findWhere("client_id",clientId);

        ArrayNode arrayNode = Helper.createArrayNode();
        boolean status = false ;
        if(!bankAccountList.isEmpty()){
            for(BankAccount bankAccount : bankAccountList){
                if(bankAccount.isActive()){
                    arrayNode.add(bankAccount.objectNode());
                }
            }
            status = true ;
        }

        return Helper.statusNodes(status).putPOJO("pageItems" ,arrayNode);

    }
}
