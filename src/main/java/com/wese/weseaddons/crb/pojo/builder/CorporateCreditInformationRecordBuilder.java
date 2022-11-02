/*Created by Sinatra Gunda
  At 12:07 PM on 2/24/2020 */

package com.wese.weseaddons.crb.pojo.builder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.wese.weseaddons.crb.enumerations.RECORD_TYPE;
import com.wese.weseaddons.crb.pojo.CorporateCreditInformationRecord;
import com.wese.weseaddons.crb.pojo.CreditInformationRecord;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "corporateCreditInformationRecord",
        "recordType"
})
public class CorporateCreditInformationRecordBuilder  extends CreditInformationRecord {

    @JsonProperty("corporateCreditInformationRecord")
    private CorporateCreditInformationRecord corporateCreditInformationRecord;
    @JsonProperty("recordType")
    private RECORD_TYPE recordType;

    /**
     * No args constructor for use in serialization
     *
     */
    public CorporateCreditInformationRecordBuilder() {
    }

    /**
     *
     * @param recordType
     * @param corporateCreditInformationRecord
     */
    public CorporateCreditInformationRecordBuilder(CorporateCreditInformationRecord corporateCreditInformationRecord, RECORD_TYPE recordType) {
        super();
        this.corporateCreditInformationRecord = corporateCreditInformationRecord;
        this.recordType = recordType;
    }

    @JsonProperty("corporateCreditInformationRecord")
    public CorporateCreditInformationRecord getCorporateCreditInformationRecord() {
        return corporateCreditInformationRecord;
    }

    @JsonProperty("corporateCreditInformationRecord")
    public void setCorporateCreditInformationRecord(CorporateCreditInformationRecord corporateCreditInformationRecord) {
        this.corporateCreditInformationRecord = corporateCreditInformationRecord;
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

