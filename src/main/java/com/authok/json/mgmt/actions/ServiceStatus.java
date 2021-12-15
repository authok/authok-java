package com.authok.json.mgmt.actions;

import com.authok.client.mgmt.ActionsEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the status of the actions service.
 * @see ActionsEntity
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceStatus {

    @JsonProperty("status")
    private String status;

    /**
     * @return the status of the actions service.
     */
    public String getStatus() {
        return status;
    }
}
