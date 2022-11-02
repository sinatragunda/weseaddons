package com.wese.weseaddons.bereaudechange.enumerations;

public enum PURPOSE {

	EXTERNAL_TERTIARY_EDUCATION_EXPENSES("External Education and Expenses"),
	FOREGIN_MEDICAL_EXPENSE("Foreign Medical Exepnses"),
	AIR_TICKETS("Air Tickets"),
	HOLIDAY("Holiday"),
	MACHINERY("Machinery Importation"),
	SOFTWARE("Software Services"),
	PERSONAL("Personal");

	private String code ;

	PURPOSE(String code){
		this.code = code ;
	}

	public String getCode(){
		return this.code ;
	}

	public static PURPOSE fromInt(int arg){
		for(PURPOSE purpose : PURPOSE.values()){
			if(purpose.ordinal()==arg){
				return purpose;
			}
		}
		return null ;
	}
}
