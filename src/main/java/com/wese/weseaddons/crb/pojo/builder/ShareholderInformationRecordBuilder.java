/*Created by Sinatra Gunda
  At 3:29 PM on 2/23/2020 */

package com.wese.weseaddons.crb.pojo.builder;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.wese.weseaddons.crb.enumerations.RECORD_TYPE;
import com.wese.weseaddons.crb.pojo.ShareholderInformationRecord;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "recordType",
        "shareholderInformationRecord"
})
public class ShareholderInformationRecordBuilder {

    @JsonProperty("recordType")
    private RECORD_TYPE recordType;
    @JsonProperty("shareholderInformationRecord")
    private ShareholderInformationRecord shareholderInformationRecord;

    /**
     * No args constructor for use in serialization
     *
     */
    public ShareholderInformationRecordBuilder() {
    }

    /**
     *
     * @param recordType
     * @param shareholderInformationRecord
     */
    public ShareholderInformationRecordBuilder(RECORD_TYPE recordType, ShareholderInformationRecord shareholderInformationRecord) {
        super();
        this.recordType = recordType;
        this.shareholderInformationRecord = shareholderInformationRecord;
    }

    @JsonProperty("recordType")
    public String getRecordType() {
        return recordType.name();
    }

    @JsonProperty("recordType")
    public void setRecordType(RECORD_TYPE recordType) {
        this.recordType = recordType;
    }

    @JsonProperty("shareholderInformationRecord")
    public ShareholderInformationRecord getShareholderInformationRecord() {
        return shareholderInformationRecord;
    }

    @JsonProperty("shareholderInformationRecord")
    public void setShareholderInformationRecord(ShareholderInformationRecord shareholderInformationRecord) {
        this.shareholderInformationRecord = shareholderInformationRecord;
    }
}
