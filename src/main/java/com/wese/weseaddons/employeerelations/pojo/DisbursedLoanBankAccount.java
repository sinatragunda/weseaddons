/*Created by Sinatra Gunda
  At 10:15 on 9/30/2020 */

package com.wese.weseaddons.employeerelations.pojo;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.employeerelations.action.BankAccountAction;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.pojo.Loan;

public class DisbursedLoanBankAccount {

    private Long id;
    private Loan loan ;
    private BankAccount bankAccount;

    public DisbursedLoanBankAccount(){}

    public DisbursedLoanBankAccount(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }{}


    public String getSchema(){
        return "m_app_disbursed_loan_bank_account";
    }

    public ObjectNode objectNode(){

        ObjectNode objectNode = Helper.createObjectNode();
        objectNode.put("id" ,id);
        objectNode.put("loanId" ,loan.getId());
        objectNode.putPOJO("bankAccount" ,bankAccount.objectNode());

        return objectNode;
    }
}
