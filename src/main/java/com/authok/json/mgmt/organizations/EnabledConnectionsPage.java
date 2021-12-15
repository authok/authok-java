package com.authok.json.mgmt.organizations;

import com.authok.json.mgmt.Page;
import com.authok.client.mgmt.OrganizationsEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

/**
 * Represents a page of the enabled connections for an organization.
 * @see EnabledConnection
 * @see OrganizationsEntity
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = ConnectionsPageDeserializer.class)
public class EnabledConnectionsPage extends Page<EnabledConnection> {

    public EnabledConnectionsPage(List<EnabledConnection> items) {
        super(items);
    }

    public EnabledConnectionsPage(Integer start, Integer length, Integer total, Integer limit, List<EnabledConnection> items) {
        super(start, length, total, limit, items);
    }
}
