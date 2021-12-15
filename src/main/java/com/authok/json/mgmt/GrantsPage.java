package com.authok.json.mgmt;

import com.authok.client.mgmt.GrantsEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

/**
 * Class that represents a given page of Grants. Related to the {@link GrantsEntity} entity.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = GrantsPageDeserializer.class)
public class GrantsPage extends Page<Grant> {

    public GrantsPage(List<Grant> items) {
        super(items);
    }

    public GrantsPage(Integer start, Integer length, Integer total, Integer limit, List<Grant> items) {
        super(start, length, total, limit, items);
    }

}
