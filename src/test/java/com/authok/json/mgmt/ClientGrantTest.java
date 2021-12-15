package com.authok.json.mgmt;

import com.authok.json.JsonMatcher;
import com.authok.json.JsonTest;

import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ClientGrantTest extends JsonTest<ClientGrant> {

    private static final String json = "{\"client_id\":\"clientId\",\"audience\":\"aud\",\"scope\":[\"one\",\"two\"]}";
    private static final String readOnlyJson = "{\"id\":\"grantId\"}";

    @Test
    public void shouldSerialize() throws Exception {
        ClientGrant grant = new ClientGrant();
        grant.setAudience("aud");
        grant.setClientId("clientId");
        grant.setScope(Arrays.asList("one", "two"));

        String serialized = toJSON(grant);
        assertThat(serialized, is(notNullValue()));
        MatcherAssert.assertThat(serialized, JsonMatcher.hasEntry("client_id", "clientId"));
        assertThat(serialized, JsonMatcher.hasEntry("audience", "aud"));
        assertThat(serialized, JsonMatcher.hasEntry("scope", Arrays.asList("one", "two")));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        ClientGrant grant = fromJSON(json, ClientGrant.class);

        assertThat(grant, is(notNullValue()));

        assertThat(grant.getAudience(), is("aud"));
        assertThat(grant.getClientId(), is("clientId"));
        assertThat(grant.getScope(), contains("one", "two"));
    }

    @Test
    public void shouldIncludeReadOnlyValuesOnDeserialize() throws Exception {
        ClientGrant grant = fromJSON(readOnlyJson, ClientGrant.class);
        assertThat(grant, is(notNullValue()));

        assertThat(grant.getId(), is("grantId"));
    }
}
