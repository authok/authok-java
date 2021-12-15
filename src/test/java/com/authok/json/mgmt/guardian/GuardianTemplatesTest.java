package com.authok.json.mgmt.guardian;

import com.authok.json.JsonMatcher;
import com.authok.json.JsonTest;

import org.hamcrest.MatcherAssert;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class GuardianTemplatesTest extends JsonTest<GuardianTemplates> {
    private static final String json = "{\"enrollment_message\":\"enrollme\",\"verification_message\":\"verifyme\"}";

    @Test
    public void shouldSerialize() throws Exception {
        GuardianTemplates templates = new GuardianTemplates("enrollme", "verifyme");

        String serialized = toJSON(templates);
        assertThat(serialized, is(notNullValue()));
        MatcherAssert.assertThat(serialized, JsonMatcher.hasEntry("enrollment_message", "enrollme"));
        assertThat(serialized, JsonMatcher.hasEntry("verification_message", "verifyme"));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        GuardianTemplates templates = fromJSON(json, GuardianTemplates.class);

        assertThat(templates, is(notNullValue()));
        assertThat(templates.getEnrollmentMessage(), is("enrollme"));
        assertThat(templates.getVerificationMessage(), is("verifyme"));
    }

}
