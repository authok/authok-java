package com.authok.json.mgmt;

import com.authok.client.mgmt.StatsEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Class that represents an Authok Daily Stats object. Related to the {@link StatsEntity} entity.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DailyStats {

    @JsonProperty("logins")
    private Integer logins;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("date")
    private Date date;

    /**
     * Getter for the amount of logins on the date
     *
     * @return the amount of logins
     */
    @JsonProperty("logins")
    public Integer getLogins() {
        return logins;
    }

    /**
     * Getter for the date to which the stats belong
     *
     * @return the date to which the stats belong
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("date")
    public Date getDate() {
        return date;
    }
}
