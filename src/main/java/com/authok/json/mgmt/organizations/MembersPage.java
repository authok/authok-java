package com.authok.json.mgmt.organizations;

import com.authok.json.mgmt.Page;
import com.authok.client.mgmt.OrganizationsEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

/**
 * Represents a page of Members of an organization.
 * @see Member
 * @see OrganizationsEntity
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = MembersPageDeserializer.class)
public class MembersPage extends Page<Member> {
    public MembersPage(List<Member> items) {
        super(items);
    }

    /**
     * @deprecated Use {@linkplain MembersPage#MembersPage(Integer, Integer, Integer, Integer, String, List)} instead.
     */
    @Deprecated
    public MembersPage(Integer start, Integer length, Integer total, Integer limit, List<Member> items) {
        super(start, length, total, limit, items);
    }

    public MembersPage(Integer start, Integer length, Integer total, Integer limit, String next, List<Member> items) {
        super(start, length, total, limit, next, items);
    }
}
