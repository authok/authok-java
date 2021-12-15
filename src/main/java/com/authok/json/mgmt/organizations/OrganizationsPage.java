package com.authok.json.mgmt.organizations;

import com.authok.json.mgmt.Page;
import com.authok.client.mgmt.OrganizationsEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

/**
 * Represents a page of an Organization.
 * @see Organization
 * @see OrganizationsEntity
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = OrganizationsPageDeserializer.class)
public class OrganizationsPage extends Page<Organization> {
    public OrganizationsPage(List<Organization> items) {
        super(items);
    }

    /**
     * @deprecated use {@linkplain OrganizationsPage#OrganizationsPage(Integer, Integer, Integer, Integer, String, List)} instead.
     */
    @Deprecated
    public OrganizationsPage(Integer start, Integer length, Integer total, Integer limit, List<Organization> items) {
        super(start, length, total, limit, items);
    }

    public OrganizationsPage(Integer start, Integer length, Integer total, Integer limit, String next, List<Organization> items) {
        super(start, length, total, limit, next, items);
    }
}
