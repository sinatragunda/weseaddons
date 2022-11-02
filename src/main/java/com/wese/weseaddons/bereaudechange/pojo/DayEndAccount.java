package com.wese.weseaddons.bereaudechange.pojo;

import com.wese.weseaddons.bereaudechange.enumerations.FINANCIAL_INSTRUMENT_TYPE;
import com.wese.weseaddons.bereaudechange.pojo.StandardCurrency;

public class DayEndAccount {

    private StandardCurrency standardCurrency ;
    private FinancialInstrument financialInstrument ;
    private double amount ;


    public DayEndAccount(StandardCurrency standardCurrency, FinancialInstrument financialInstrument, double amount) {
        this.standardCurrency = standardCurrency;
        this.financialInstrument = financialInstrument;
        this.amount = amount;
    }

    public StandardCurrency getStandardCurrency() {
        return standardCurrency;
    }

    public void setStandardCurrency(StandardCurrency standardCurrency) {
        this.standardCurrency = standardCurrency;
    }

    public FinancialInstrument getFinancialInstrument() {
        return financialInstrument;
    }

    public void setFinancialInstrument(FinancialInstrument financialInstrument) {
        this.financialInstrument = financialInstrument;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
