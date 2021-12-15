package com.authok.json.mgmt.organizations;

import com.authok.client.mgmt.OrganizationsEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the Invitee object for an Invitation.
 * @see Invitation
 * @see OrganizationsEntity
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Invitee {

    @JsonProperty("email")
    private String email;

    /**
     * Create a new instance.
     *
     * @param email the email of the Invitee.
     */
    public Invitee(@JsonProperty("email") String email) {
        this.email = email;
    }

    /**
     * @return the email of this Invitee.
     */
    public String getEmail() {
        return email;
    }
}
