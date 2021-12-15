package com.authok.json.mgmt.organizations;

import com.authok.json.mgmt.Page;
import com.authok.client.mgmt.OrganizationsEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

/**
 * Represents a page of Invitations.
 * @see Invitation
 * @see OrganizationsEntity
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = InvitationsPageDeserializer.class)
public class InvitationsPage extends Page<Invitation> {

    public InvitationsPage(List<Invitation> items) {
        super(items);
    }

    public InvitationsPage(Integer start, Integer length, Integer total, Integer limit, List<Invitation> items) {
        super(start, length, total, limit, items);
    }
}
