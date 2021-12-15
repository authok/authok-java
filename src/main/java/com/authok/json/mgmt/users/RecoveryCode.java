package com.authok.json.mgmt.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("unused")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecoveryCode {

    @JsonProperty("recovery_code")
    private String code;

    @JsonProperty("recovery_code")
    public String getCode() {
        return code;
    }
}
