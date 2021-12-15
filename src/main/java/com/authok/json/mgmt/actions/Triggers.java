package com.authok.json.mgmt.actions;

import com.authok.client.mgmt.ActionsEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Represents the triggers of an action.
 * @see Action
 * @see ActionsEntity
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Triggers {

    @JsonProperty("triggers")
    private List<Trigger> triggers;

    /**
     * @return the list of triggers
     */
    public List<Trigger> getTriggers() {
        return this.triggers;
    }
}
