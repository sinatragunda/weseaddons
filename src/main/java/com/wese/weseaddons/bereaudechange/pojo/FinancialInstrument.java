package com.wese.weseaddons.bereaudechange.pojo;

import com.wese.weseaddons.bereaudechange.enumerations.FINANCIAL_INSTRUMENT_TYPE;

public class FinancialInstrument {

    private long id ;
    private String name ;
    private String description ;
    private FINANCIAL_INSTRUMENT_TYPE financialInstrumentType ;


    public FinancialInstrument() {
    }

    public FinancialInstrument(long id) {
        this.id = id;
    }

    public FinancialInstrument(String name, String description, FINANCIAL_INSTRUMENT_TYPE financialInstrumentType) {
        this.name = name;
        this.description = description;
        this.financialInstrumentType = financialInstrumentType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FINANCIAL_INSTRUMENT_TYPE getFinancialInstrumentType() {
        return financialInstrumentType;
    }

    public void setFinancialInstrumentType(int type) {

        for(FINANCIAL_INSTRUMENT_TYPE t : FINANCIAL_INSTRUMENT_TYPE.values()){
            if(t.ordinal()==type){
                financialInstrumentType = t ;
            }
        }

        this.financialInstrumentType = financialInstrumentType;
    }
}
