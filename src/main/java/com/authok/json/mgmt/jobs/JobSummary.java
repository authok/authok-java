package com.authok.json.mgmt.jobs;

import com.authok.client.mgmt.JobsEntity;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class that represents the summary of a completed Authok Job object. Related to the {@link JobsEntity} entity.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobSummary {

    @JsonProperty("failed")
    private final Integer failed;
    @JsonProperty("updated")
    private final Integer updated;
    @JsonProperty("inserted")
    private final Integer inserted;
    @JsonProperty("total")
    private final Integer total;

    @JsonCreator
    private JobSummary(@JsonProperty("failed") Integer failed, @JsonProperty("updated") Integer updated, @JsonProperty("inserted") Integer inserted, @JsonProperty("total") Integer total) {
        this.failed = failed;
        this.updated = updated;
        this.inserted = inserted;
        this.total = total;
    }

    @JsonProperty("failed")
    public Integer getFailed() {
        return failed;
    }

    @JsonProperty("updated")
    public Integer getUpdated() {
        return updated;
    }

    @JsonProperty("inserted")
    public Integer getInserted() {
        return inserted;
    }

    @JsonProperty("total")
    public Integer getTotal() {
        return total;
    }
}
