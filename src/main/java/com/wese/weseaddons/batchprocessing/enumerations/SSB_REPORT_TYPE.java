/*

    Created by Sinatra Gunda
    At 3:42 PM on 6/19/2021

*/
package com.wese.weseaddons.batchprocessing.enumerations;

import java.util.ArrayList;
import java.util.List;

public enum SSB_REPORT_TYPE {

    NONE("None"),
    INVALID_SEARCH_DATA("Invalid Search Data"),
    FAILED_REPAYMENTS("Failed Loan Repayments"),
    NO_ACTIVE_LOANS("No Active Loans"),
    NO_MATCHING_LOANS("No Matching Loans"),
    LOAN_REPAYMENT_WAITING_APPROVAL("Loan Repayment Waiting Approval"),
    REPAID_LOANS("Loans Repayment Successfull"),
    ADJUSTED_DEPOSIT("Adjusted Deposit"),
    INVALID_NRC_SEARCH_NUMBER("Client Not Found"),
    FAILED_DEPOSITS("Failed Deposits"),
    NO_VALID_SAVINGS_ACCOUNT("No Valid Savings Account"),
    SAVINGS_ACCOUNT_PRELOADED("Savings Account Preloaded"),
    SHARES_TRANSACTION_SUCCESSFULL("Shares Transaction Successfull"),
    SHARES_TRANSACTION_PENDING("Shares Transaction Pending"),
    SHARES_MAXIMUM_REACHED("Shares Maximum Reached"),
    NO_ACTIVE_SHARES_ACCOUNT("No Active Shares Account"),
    SHARES_INTERNAL_ERROR("Shares Internal Error"),
    EXCESS_BALANCE_MOVED("Excess Drawdown Balance Moved"),
    SHORTFALL_BALANCE("Shortfall Balance Payment"),
    SUCCESSFULL_DEPOSIT("Successfull deposit"),
    SUCCESSFULL_SAVINGS_LOAN_TRANSFER("Succesfull Savings To Loan Transfer"),
    SUCCESSFULL_SAVINGS_SHARE_TRANSFER("Successfull Savings to Share Transfer"),
    SUCCESSFULL_SAVINGS_TO_SAVINGS_TRANSFER("Successfull Savings to Savings Transfer"),
    ZERO_BALANCE_DEPOSIT("Zero Balance Deposit ,operation skipped"),
    DDA_FUND_DEPOSIT_SUCCESS("DDA Fund deposited succesfully"),
    DDA_FUND_DEPOSIT_FAILED("DDA Fund deposit failed ,possible cause zero balance deposit"),
    DDA_FUND_TO_CLIENT_SUCCESS("DDA Fund to client DDA transfer successful"),
    DDA_FUNDER_NOT_VALID("DDA Funder for client not valid ,or failed to link"),
    DDA_FUND_TO_CLIENT_FAILED("DDA Fund to client account transfer failed ,possible cause insufficient funds in DDA Account"),
    FAILED("Failed"),
    SUCCESS("Success"),
    SKIPPED("Skipped"),
    ALL("All");

    String code ;

    SSB_REPORT_TYPE(String arg){
        this.code = arg ;
    }

    public String getCode() {
        return code;
    }

    public static SSB_REPORT_TYPE fromString(String arg){

        for(SSB_REPORT_TYPE s : values()){
            if(s.getCode().equalsIgnoreCase(arg)){
                return s;
            }
        }
        return ALL;
    }

    public static List<String> template(){

        List list = new ArrayList();
        for(SSB_REPORT_TYPE s : values()){
            list.add(s.getCode());
        }
        return list ;
    }
}
