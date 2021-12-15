package com.authok.json.mgmt.organizations;

import com.authok.client.mgmt.OrganizationsEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Represents the request body when adding or deleting roles from a member of an organization.
 * @see OrganizationsEntity
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Roles {

    @JsonProperty("roles")
    private List<String> roles;

    /**
     * Create a new instance.
     *
     * @param roles the list of Role IDs to associate with the member.
     */
    public Roles(List<String> roles) {
        this.roles = roles;
    }

    /**
     * @return the list of Role IDs associated with the member.
     */
    public List<String> getRoles() {
        return roles;
    }

}
