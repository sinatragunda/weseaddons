
package com.wese.weseaddons.crb.pojo.builder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.wese.weseaddons.crb.enumerations.RECORD_TYPE;
import com.wese.weseaddons.crb.pojo.CollateralInformationRecord;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"collateralInformationRecord",
"recordType"
})
public class CollateralInformationRecordBuilder {

    @JsonProperty("collateralInformationRecord")
    private CollateralInformationRecord collateralInformationRecord;
    @JsonProperty("recordType")
    private RECORD_TYPE recordType;

    /**
     * No args constructor for use in serialization
     */
    public CollateralInformationRecordBuilder() {
    }

    /**
     * @param collateralInformationRecord
     * @param recordType
     */
    public CollateralInformationRecordBuilder(CollateralInformationRecord collateralInformationRecord, RECORD_TYPE recordType) {
        super();
        this.collateralInformationRecord = collateralInformationRecord;
        this.recordType = recordType;
    }

    @JsonProperty("collateralInformationRecord")
    public CollateralInformationRecord getCollateralInformationRecord() {
        return collateralInformationRecord;
    }

    @JsonProperty("collateralInformationRecord")
    public void setCollateralInformationRecord(CollateralInformationRecord collateralInformationRecord) {
        this.collateralInformationRecord = collateralInformationRecord;
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
