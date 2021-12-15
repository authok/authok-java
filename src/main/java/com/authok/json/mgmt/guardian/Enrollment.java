package com.authok.json.mgmt.guardian;

import com.authok.client.mgmt.GuardianEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Class that represents an Authok Guardian Enrollment object. Related to the {@link GuardianEntity} entity.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Enrollment {

    @JsonProperty("id")
    private String id;
    @JsonProperty("status")
    private String status;
    @JsonProperty("type")
    private String type;
    @JsonProperty("name")
    private String name;
    @JsonProperty("identifier")
    private String identifier;
    @JsonProperty("phone_number")
    private String phoneNumber;
    @JsonProperty("auth_method")
    private String authMethod;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("enrolled_at")
    private Date enrolledAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("last_auth")
    private Date lastAuth;

    /**
     * Getter for the enrollment ID
     *
     * @return the id.
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * Getter for the enrollment status. Either 'pending' or 'confirmed'.
     *
     * @return the status.
     */
    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    /**
     * Getter for the enrollment type.
     *
     * @return the type.
     */
    @JsonProperty("type")
    public String getType() {
        return type;
    }

    /**
     * Getter for the enrollment name. This is usually the phone name. Only for 'guardian' enrollments.
     *
     * @return the name.
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * Getter for the device identifier. This is usually a unique phone identifier. Only for 'guardian' enrollments.
     *
     * @return the identifier.
     */
    @JsonProperty("identifier")
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Getter for the phone number. Only for 'sms' enrollments.
     *
     * @return the phone number.
     */
    @JsonProperty("phone_number")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Getter for the authentication method.
     *
     * @return the authentication method.
     */
    @JsonProperty("auth_method")
    public String getAuthMethod() {
        return authMethod;
    }

    /**
     * Getter for the enrolled at.
     *
     * @return the enrolled at.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("enrolled_at")
    public Date getEnrolledAt() {
        return enrolledAt;
    }

    /**
     * Getter for the last authentication.
     *
     * @return the last authentication.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("last_auth")
    public Date getLastAuth() {
        return lastAuth;
    }
}
