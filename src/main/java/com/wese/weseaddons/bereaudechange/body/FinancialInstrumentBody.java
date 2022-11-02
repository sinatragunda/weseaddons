package com.wese.weseaddons.bereaudechange.body;

public class FinancialInstrumentBody {

    private String name ;
    private String tenant ;
    private String description ;
    private int instrumentType ;

    public FinancialInstrumentBody() {
    }

    public String getName() {
        return name;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public String getTenant() {
        return tenant;
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

    public int getInstrumentType() {
        return instrumentType;
    }

    public void setInstrumentType(int instrumentType) {
        this.instrumentType = instrumentType;
    }
}
