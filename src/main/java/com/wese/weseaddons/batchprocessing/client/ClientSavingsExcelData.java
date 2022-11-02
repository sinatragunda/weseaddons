package com.wese.weseaddons.batchprocessing.client;

import com.wese.weseaddons.batchprocessing.pojo.ExcelClientData;
import com.wese.weseaddons.pojo.SavingsAccount;
import com.wese.weseaddons.pojo.Client;

public class ClientSavingsExcelData {

    private Client client ;
    private SavingsAccount savingsAccount ;
    private ExcelClientData excelClientData ;

    public ClientSavingsExcelData(ExcelClientData excelClientData ,SavingsAccount savingsAccount ,Client client){
        this.client = client ;
        this.excelClientData = excelClientData ;
        this.savingsAccount = savingsAccount ;

    }

    public Client getClient() {
        return client;
    }

    public ExcelClientData getExcelClientData() {
        return excelClientData;
    }

    public SavingsAccount getSavingsAccount() {
        return savingsAccount;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setSavingsAccount(SavingsAccount savingsAccount) {
        this.savingsAccount = savingsAccount;
    }

    public void setExcelClientData(ExcelClientData excelClientData) {
        this.excelClientData = excelClientData;
    }
}
