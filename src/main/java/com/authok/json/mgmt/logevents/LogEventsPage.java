package com.authok.json.mgmt.logevents;

import com.authok.json.mgmt.Page;
import com.authok.client.mgmt.LogEventsEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

/**
 * Class that represents a given page of Log Events. Related to the {@link LogEventsEntity} entity.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = LogEventsPageDeserializer.class)
public class LogEventsPage extends Page<LogEvent> {

    public LogEventsPage(List<LogEvent> items) {
        super(items);
    }

    public LogEventsPage(Integer start, Integer length, Integer total, Integer limit, List<LogEvent> items) {
        super(start, length, total, limit, items);
    }

}
