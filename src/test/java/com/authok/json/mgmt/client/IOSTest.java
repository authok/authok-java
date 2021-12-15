package com.authok.json.mgmt.client;

import com.authok.json.JsonMatcher;
import com.authok.json.JsonTest;

import org.hamcrest.MatcherAssert;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class IOSTest extends JsonTest<IOS> {

    private static final String json = "{\"team_id\":\"team\",\"app_bundle_identifier\":\"identifier\"}";

    @Test
    public void shouldSerialize() throws Exception {
        IOS ios = new IOS("team", "identifier");

        String serialized = toJSON(ios);
        assertThat(serialized, is(notNullValue()));
        MatcherAssert.assertThat(serialized, JsonMatcher.hasEntry("team_id", "team"));
        assertThat(serialized, JsonMatcher.hasEntry("app_bundle_identifier", "identifier"));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        IOS ios = fromJSON(json, IOS.class);

        assertThat(ios, is(notNullValue()));

        assertThat(ios.getTeamId(), is("team"));
        assertThat(ios.getAppBundleIdentifier(), is("identifier"));
    }
}
