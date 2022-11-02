/*Created by Sinatra Gunda
  At 4:37 PM on 2/23/2020 */

package com.wese.weseaddons.crb.pojo.builder;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.wese.weseaddons.crb.enumerations.RECORD_TYPE;
import com.wese.weseaddons.crb.pojo.GuarantorInformationRecord;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "guarantorInformationRecord",
        "recordType"
})
public class GuarantorInformationRecordBuilder {

    @JsonProperty("guarantorInformationRecord")
    private GuarantorInformationRecord guarantorInformationRecord;
    @JsonProperty("recordType")
    private RECORD_TYPE recordType;

    /**
     * No args constructor for use in serialization
     *
     */
    public GuarantorInformationRecordBuilder() {
    }

    /**
     *
     * @param recordType
     * @param guarantorInformationRecord
     */
    public GuarantorInformationRecordBuilder(GuarantorInformationRecord guarantorInformationRecord, RECORD_TYPE recordType) {
        super();
        this.guarantorInformationRecord = guarantorInformationRecord;
        this.recordType = recordType;
    }

    @JsonProperty("guarantorInformationRecord")
    public GuarantorInformationRecord getGuarantorInformationRecord() {
        return guarantorInformationRecord;
    }

    @JsonProperty("guarantorInformationRecord")
    public void setGuarantorInformationRecord(GuarantorInformationRecord guarantorInformationRecord) {
        this.guarantorInformationRecord = guarantorInformationRecord;
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
