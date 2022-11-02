
package com.wese.weseaddons.crb.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "expiresAfter",
        "status",
        "token"
})

public class Token implements Serializable {

    @JsonProperty("expiresAfter")
    private String expiresAfter;
    @JsonProperty("status")
    private String status;
    @JsonProperty("token")
    private String token;

    /**
     * No args constructor for use in serialization
     *
     */
    public Token() {
    }

    /**
     *
     * @param expiresAfter
     * @param status
     * @param token
     */
    public Token(String expiresAfter, String status, String token) {
        super();
        this.expiresAfter = expiresAfter;
        this.status = status;
        this.token = token;
    }

    @JsonProperty("expiresAfter")
    public String getExpiresAfter() {
        return expiresAfter;
    }

    @JsonProperty("expiresAfter")
    public void setExpiresAfter(String expiresAfter) {
        this.expiresAfter = expiresAfter;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("token")
    public String getToken() {
        return token;
    }

    @JsonProperty("token")
    public void setToken(String token) {
        this.token = token;
    }

}
