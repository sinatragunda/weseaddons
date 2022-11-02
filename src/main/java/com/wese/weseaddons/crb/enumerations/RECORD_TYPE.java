package com.wese.weseaddons.crb.enumerations;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RECORD_TYPE {

	BCI("Bounced cheque information for individual"),
	BCC("Bounced cheque information for Corporate"),
	CA("Credit Application information for individual"),
	AC("Credit Application information for corporate"),
	CR("Collateral Information"),
	DDI("Direct Debit Information for Individual"),
	DDC("Direct Debit Information for Corporate"),
	DI("Director Information"),
	GID("Gurantor Information for Corporate – Directors Guarantee"),
	GIP("Guarantor Information for Individual – Personal Guarantee"),
	GIC("Guarantor Information for Corporate – Corporate Guarantee"),
	GIB("Guarantor Information for Corporate – Bank Guarantee"),
	GIS("Guarantor Information for Corporate – Sovereign Guarantee"),
	SII("Shareholder Information for Individual"),
	SIC("Shareholder Information for Corporate"),
	PA("Corporate Credit Information"),
	PI("Consumer Credit Information"),
	ML("Mobile Loan Information");


	private String code ;

    RECORD_TYPE(String c){
        this.code =c ;
    }

    public String getCode() {
        return code;
    }

    public static RECORD_TYPE fromInt(int i){

        for(RECORD_TYPE c : RECORD_TYPE.values()){
            if(c.ordinal()==i){
                return c ;
            }
        }
        return null ;
    }

	@JsonValue
	public String value(){
		return name() ;
	}


}
