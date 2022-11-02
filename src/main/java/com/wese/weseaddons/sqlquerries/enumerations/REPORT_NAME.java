/*Created by Sinatra Gunda
  At 09:45 on 2/16/2021 */

package com.wese.weseaddons.sqlquerries.enumerations;

public enum REPORT_NAME {

    GENERAL_LEDGER("GeneralLedgerReport"),
    ACTIVE_LOANS("Active Loans Details");

    private String code ;

    REPORT_NAME(String code){
        this.code = code ;
    }

    public static REPORT_NAME fromString(String value){

        for(REPORT_NAME reportName : REPORT_NAME.values()){
            if(reportName.getCode().equalsIgnoreCase(value)){
                return reportName ;
            }
        }
        return null ;
    }

    public String getCode() {
        return code;
    }
}
