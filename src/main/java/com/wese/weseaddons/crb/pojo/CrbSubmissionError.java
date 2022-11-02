/*Created by Sinatra Gunda
  At 1:48 PM on 2/24/2020 */

package com.wese.weseaddons.crb.pojo;


import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "callbackId",
        "message",
        "recordErrors",
        "responseCode"
})
public class CrbSubmissionError {

    @JsonProperty("callbackId")
    private String callbackId;
    @JsonProperty("message")
    private String message;
    @JsonProperty("recordErrors")
    private List<RecordError> recordErrors = null;
    @JsonProperty("responseCode")
    private Integer responseCode;

    /**
     * No args constructor for use in serialization
     *
     */
    public CrbSubmissionError() {
    }

    /**
     *
     * @param recordErrors
     * @param callbackId
     * @param message
     * @param responseCode
     */
    public CrbSubmissionError(String callbackId, String message, List<RecordError> recordErrors, Integer responseCode) {
        super();
        this.callbackId = callbackId;
        this.message = message;
        this.recordErrors = recordErrors;
        this.responseCode = responseCode;
    }

    @JsonProperty("callbackId")
    public String getCallbackId() {
        return callbackId;
    }

    @JsonProperty("callbackId")
    public void setCallbackId(String callbackId) {
        this.callbackId = callbackId;
    }

    @JsonProperty("message")
    public String getMessage() {
        return errorMessage();
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty("recordErrors")
    public List<RecordError> getRecordErrors() {
        return recordErrors;
    }

    @JsonProperty("recordErrors")
    public void setRecordErrors(List<RecordError> recordErrors) {
        this.recordErrors = recordErrors;
    }

    @JsonProperty("responseCode")
    public Integer getResponseCode() {
        return responseCode;
    }

    @JsonProperty("responseCode")
    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String errorMessage(){
        String message = null ;
        for(RecordError r : recordErrors){
            message = r.getErrorMessage();
            break;
        }
        return message ;

    }

}

