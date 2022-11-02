package com.wese.weseaddons.crb.pojo.builder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.wese.weseaddons.crb.enumerations.RECORD_TYPE;
import com.wese.weseaddons.crb.pojo.MobileLoansInformation;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"data",
"recordType"
})
public class MobileLoansInformationBuilder {

@JsonProperty("data")
private MobileLoansInformation mobileLoansInformation;
@JsonProperty("recordType")
private RECORD_TYPE recordType;

/**
* No args constructor for use in serialization
*
*/
public MobileLoansInformationBuilder() {
}

/**
*
* @param mobileLoansInformation
* @param recordType
*/
public MobileLoansInformationBuilder(MobileLoansInformation mobileLoansInformation, RECORD_TYPE recordType) {
super();
this.mobileLoansInformation = mobileLoansInformation;
this.recordType = recordType;
}

@JsonProperty("mobileLoansInformation")
public MobileLoansInformation getMobileLoansInformation() {
return mobileLoansInformation;
}

@JsonProperty("mobileLoansInformation")
public void setMobileLoansInformation(MobileLoansInformation mobileLoansInformation) {
this.mobileLoansInformation = mobileLoansInformation;
}

@JsonProperty("recordType")
public String getRecordType() {
return recordType.name();
}

@JsonProperty("recordType")
public void setRecordType(RECORD_TYPE recordType) {
this.recordType = recordType;
}

}