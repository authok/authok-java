package com.authok.json.mgmt.actions;

import com.authok.json.mgmt.Page;
import com.authok.client.mgmt.ActionsEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

/**
 * Represents a page of an action's versions.
 * @see Version
 * @see ActionsEntity
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = VersionsPageDeserializer.class)
public class VersionsPage extends Page<Version> {

    public VersionsPage(List<Version> items) {
        super(items);
    }

    public VersionsPage(Integer start, Integer length, Integer total, Integer limit, List<Version> items) {
        super(start, length, total, limit, items);
    }

    public VersionsPage(Integer start, Integer length, Integer total, Integer limit, String next, List<Version> items) {
        super(start, length, total, limit, next, items);
    }
}
