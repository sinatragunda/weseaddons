package com.wese.weseaddons.bereaudechange.body;

public class TradingCurrencyBody {

    private String tenant ;
    private String name ;
    private String symbol ;
    private String region ;
    private String country ;
    private long financialInstrumentId ;

    public TradingCurrencyBody() {

    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }



    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public long getFinancialInstrumentId() {
        return financialInstrumentId;
    }

    public void setFinancialInstrumentId(long financialInstrumentId) {
        this.financialInstrumentId = financialInstrumentId;
    }
}
