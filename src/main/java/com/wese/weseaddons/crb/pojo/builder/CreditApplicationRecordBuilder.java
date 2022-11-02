package com.wese.weseaddons.crb.pojo.builder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.wese.weseaddons.crb.enumerations.RECORD_TYPE;
import com.wese.weseaddons.crb.pojo.CreditApplicationRecord;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"creditApplicationRecord",
"recordType"
})
public class CreditApplicationRecordBuilder {

@JsonProperty("creditApplicationRecord")
private CreditApplicationRecord creditApplicationRecord;
@JsonProperty("recordType")
private RECORD_TYPE recordType;

/**
* No args constructor for use in serialization
*
*/
public CreditApplicationRecordBuilder() {
}

/**
*
* @param recordType
* @param creditApplicationRecord
*/
public CreditApplicationRecordBuilder(CreditApplicationRecord creditApplicationRecord, RECORD_TYPE recordType) {
super();
this.creditApplicationRecord = creditApplicationRecord;
this.recordType = recordType;
}

@JsonProperty("creditApplicationRecord")
public CreditApplicationRecord getCreditApplicationRecord() {
return creditApplicationRecord;
}

@JsonProperty("creditApplicationRecord")
public void setCreditApplicationRecord(CreditApplicationRecord creditApplicationRecord) {
this.creditApplicationRecord = creditApplicationRecord;
}

@JsonProperty("recordType")
public RECORD_TYPE getRecordType() {
return recordType;
}

@JsonProperty("recordType")
public void setRecordType(RECORD_TYPE recordType) {
this.recordType = recordType;
}

}
