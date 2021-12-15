package com.authok.json.mgmt.client;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("WeakerAccess")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Addon {

    @JsonUnwrapped
    @JsonIgnore
    private final Map<String, Object> properties;

    public Addon() {
        this.properties = new HashMap<>();
    }

    @JsonAnySetter
    public void setProperty(String name, Object value) {
        properties.put(name, value);
    }

    @JsonAnyGetter
    public Map<String, Object> getProperties() {
        return properties;
    }

    public Object getProperty(String name) {
        return properties.get(name);
    }

}
