/*Created by Sinatra Gunda
  At 10:47 AM on 2/24/2020 */

package com.wese.weseaddons.crb.pojo.builder;


import com.wese.weseaddons.crb.enumerations.RECORD_TYPE;
import com.wese.weseaddons.crb.pojo.ConsumerCreditInformationRecord;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.wese.weseaddons.crb.pojo.CreditInformationRecord;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "personalInformationRecord",
        "recordType"
})

public class ConsumerCreditInformationRecordBuilder{

    @JsonProperty("personalInformationRecord")
    private ConsumerCreditInformationRecord consumerCreditInformationRecord;
    @JsonProperty("recordType")
    private RECORD_TYPE recordType;

    /**
     * No args constructor for use in serialization
     *
     */
    public ConsumerCreditInformationRecordBuilder() {
    }

    /**
     *
     * @param recordType
     * @param consumerCreditInformationRecord
     */
    public ConsumerCreditInformationRecordBuilder(ConsumerCreditInformationRecord consumerCreditInformationRecord, RECORD_TYPE recordType) {
        this.consumerCreditInformationRecord = consumerCreditInformationRecord;
        this.recordType = recordType;
    }

    @JsonProperty("personalInformationRecord")
    public ConsumerCreditInformationRecord getConsumerCreditInformationRecord() {
        return consumerCreditInformationRecord;
    }

    @JsonProperty("personalInformationRecord")
    public void setConsumerCreditInformationRecord(ConsumerCreditInformationRecord consumerCreditInformationRecord) {
        this.consumerCreditInformationRecord = consumerCreditInformationRecord;
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

