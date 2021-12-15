package com.authok.json.mgmt.client;

import com.authok.json.mgmt.Page;
import com.authok.client.mgmt.ClientsEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

/**
 * Class that represents a given page of Clients. Related to the {@link ClientsEntity} entity.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = ClientsPageDeserializer.class)
public class ClientsPage extends Page<Client> {

    public ClientsPage(List<Client> items) {
        super(items);
    }

    public ClientsPage(Integer start, Integer length, Integer total, Integer limit, List<Client> items) {
        super(start, length, total, limit, items);
    }

}
