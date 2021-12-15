package com.authok.json.mgmt.guardian;

import com.authok.json.JsonMatcher;
import com.authok.json.JsonTest;

import org.hamcrest.MatcherAssert;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class FactorTest extends JsonTest<Factor> {

    private static final String readOnlyJson = "{\"enabled\":true,\"name\":\"sms\",\"trial_expired\":true}";

    @Test
    public void shouldSerialize() throws Exception {
        Factor factor = new Factor(true);

        String serialized = toJSON(factor);
        assertThat(serialized, is(notNullValue()));
        MatcherAssert.assertThat(serialized, JsonMatcher.hasEntry("enabled", true));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        Factor factor = fromJSON(readOnlyJson, Factor.class);

        assertThat(factor, is(notNullValue()));
        assertThat(factor.isEnabled(), is(true));
        assertThat(factor.getName(), is("sms"));
        assertThat(factor.isTrialExpired(), is(true));
    }
}
