package com.authok.json.mgmt.client;

import com.authok.json.JsonMatcher;
import com.authok.json.JsonTest;

import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class AddonsTest extends JsonTest<Addons> {

    private static final String json = "{\"rms\":{\"key\":\"value\"},\"mscrm\":{\"key\":\"value\"},\"slack\":{\"key\":\"value\"},\"layer\":{\"key\":\"value\"},\"other\":{\"key\":\"value\"}}";

    @Test
    public void shouldSerialize() throws Exception {
        Addon addon = new Addon();
        addon.setProperty("key", "value");
        Addons addons = new Addons(addon, addon, addon, addon);
        addons.setAdditionalAddon("other", addon);

        String serialized = toJSON(addons);
        assertThat(serialized, is(notNullValue()));
        MatcherAssert.assertThat(serialized, JsonMatcher.hasEntry("rms", Collections.singletonMap("key", "value")));
        assertThat(serialized, JsonMatcher.hasEntry("mscrm", Collections.singletonMap("key", "value")));
        assertThat(serialized, JsonMatcher.hasEntry("slack", Collections.singletonMap("key", "value")));
        assertThat(serialized, JsonMatcher.hasEntry("layer", Collections.singletonMap("key", "value")));
        assertThat(serialized, JsonMatcher.hasEntry("other", Collections.singletonMap("key", "value")));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        Addons addons = fromJSON(json, Addons.class);

        assertThat(addons, is(notNullValue()));

        assertThat(addons.getRMS(), is(notNullValue()));
        assertThat(addons.getMSCRM(), is(notNullValue()));
        assertThat(addons.getSlack(), is(notNullValue()));
        assertThat(addons.getLayer(), is(notNullValue()));
        assertThat(addons.getAdditionalAddons(), is(notNullValue()));
        assertThat(addons.getAdditionalAddons().size(), is(1));
        assertThat(addons.getAdditionalAddons().get("other"), is(notNullValue()));
    }
}
