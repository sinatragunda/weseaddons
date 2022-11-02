
package com.wese.weseaddons.crb.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"infinityCode",
"password",
"username"
})
public class TokenRequest {

@JsonProperty("infinityCode")
private String infinityCode;
@JsonProperty("password")
private String password;
@JsonProperty("username")
private String username;

/**
* No args constructor for use in serialization
*
*/
public TokenRequest() {
}

/**
*
* @param password
* @param infinityCode
* @param username
*/
public TokenRequest(String infinityCode, String password, String username) {
super();
this.infinityCode = infinityCode;
this.password = password;
this.username = username;
}

@JsonProperty("infinityCode")
public String getInfinityCode() {
return infinityCode;
}

@JsonProperty("infinityCode")
public void setInfinityCode(String infinityCode) {
this.infinityCode = infinityCode;
}

@JsonProperty("password")
public String getPassword() {
return password;
}

@JsonProperty("password")
public void setPassword(String password) {
this.password = password;
}

@JsonProperty("username")
public String getUsername() {
return username;
}

@JsonProperty("username")
public void setUsername(String username) {
this.username = username;
}

}
