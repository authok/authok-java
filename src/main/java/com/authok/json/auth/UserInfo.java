package com.authok.json.auth;

import com.authok.client.auth.AuthAPI;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that holds the Information related to a User's Access Token. Obtained after a call to {@link AuthAPI#userInfo(String)},
 * {@link AuthAPI#signUp(String, String, String)} or {@link AuthAPI#signUp(String, String, String, String)}.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfo implements Serializable {

    private final Map<String, Object> values;

    UserInfo() {
        values = new HashMap<>();
    }

    @JsonAnySetter
    void setValue(String key, Object value) {
        values.put(key, value);
    }

    /**
     * Getter for the values contained in this object
     *
     * @return the values contained in the object.
     */
    @JsonAnyGetter
    public Map<String, Object> getValues() {
        return values;
    }
}
