package com.authok.json.mgmt.users;

import com.fasterxml.jackson.annotation.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"unused", "WeakerAccess"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileData implements Serializable {

    @JsonProperty("email")
    private String email;
    @JsonProperty("email_verified")
    private Boolean emailVerified;
    @JsonProperty("name")
    private String name;
    @JsonProperty("username")
    private String username;
    @JsonProperty("given_name")
    private String givenName;
    @JsonProperty("phone_number")
    private String phoneNumber;
    @JsonProperty("phone_verified")
    private Boolean phoneVerified;
    @JsonProperty("family_name")
    private String familyName;
    private final Map<String, Object> values;

    public ProfileData() {
        values = new HashMap<>();
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email_verified")
    public Boolean isEmailVerified() {
        return emailVerified;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    @JsonProperty("given_name")
    public String getGivenName() {
        return givenName;
    }

    @JsonProperty("phone_number")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @JsonProperty("phone_verified")
    public Boolean isPhoneVerified() {
        return phoneVerified;
    }

    @JsonProperty("family_name")
    public String getFamilyName() {
        return familyName;
    }

    @JsonAnySetter
    void setValue(String key, Object value) {
        values.put(key, value);
    }

    @JsonAnyGetter
    public Map<String, Object> getValues() {
        return values;
    }
}
