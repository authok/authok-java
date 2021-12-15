package com.authok.json.mgmt;

import com.authok.client.mgmt.ConnectionsEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

/**
 * Class that represents a given page of Grants. Related to the {@link ConnectionsEntity} entity.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = ConnectionsPageDeserializer.class)
public class ConnectionsPage extends Page<Connection> {

    public ConnectionsPage(List<Connection> items) {
        super(items);
    }

    public ConnectionsPage(Integer start, Integer length, Integer total, Integer limit, List<Connection> items) {
        super(start, length, total, limit, items);
    }

}
