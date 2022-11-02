/*Created by Sinatra Gunda
  At 1:50 PM on 2/24/2020 */

package com.wese.weseaddons.crb.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "accountNumber",
        "errorCode",
        "errorMessage",
        "fieldName",
        "fieldValue"
})
public class RecordError {

    @JsonProperty("accountNumber")
    private String accountNumber;
    @JsonProperty("errorCode")
    private Integer errorCode;
    @JsonProperty("errorMessage")
    private String errorMessage;
    @JsonProperty("fieldName")
    private String fieldName;
    @JsonProperty("fieldValue")
    private String fieldValue;

    /**
     * No args constructor for use in serialization
     *
     */
    public RecordError() {
    }

    /**
     *
     * @param fieldName
     * @param errorMessage
     * @param errorCode
     * @param accountNumber
     * @param fieldValue
     */
    public RecordError(String accountNumber, Integer errorCode, String errorMessage, String fieldName, String fieldValue) {
        super();
        this.accountNumber = accountNumber;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    @JsonProperty("accountNumber")
    public String getAccountNumber() {
        return accountNumber;
    }

    @JsonProperty("accountNumber")
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @JsonProperty("errorCode")
    public Integer getErrorCode() {
        return errorCode;
    }

    @JsonProperty("errorCode")
    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    @JsonProperty("errorMessage")
    public String getErrorMessage() {
        return errorMessage;
    }

    @JsonProperty("errorMessage")
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @JsonProperty("fieldName")
    public String getFieldName() {
        return fieldName;
    }

    @JsonProperty("fieldName")
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @JsonProperty("fieldValue")
    public String getFieldValue() {
        return fieldValue;
    }

    @JsonProperty("fieldValue")
    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

}


