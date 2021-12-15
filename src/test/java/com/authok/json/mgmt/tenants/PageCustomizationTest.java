package com.authok.json.mgmt.tenants;

import com.authok.json.JsonMatcher;
import com.authok.json.JsonTest;

import org.hamcrest.MatcherAssert;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class PageCustomizationTest extends JsonTest<PageCustomization> {

    private static final String json = "{\"enabled\":true,\"html\":\"thewebpage\"}";

    @Test
    public void shouldSerialize() throws Exception {
        PageCustomization customization = new PageCustomization();
        customization.setEnabled(true);
        customization.setHTML("thewebpage");

        String serialized = toJSON(customization);
        assertThat(serialized, is(notNullValue()));
        MatcherAssert.assertThat(serialized, JsonMatcher.hasEntry("enabled", true));
        assertThat(serialized, JsonMatcher.hasEntry("html", "thewebpage"));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        PageCustomization customization = fromJSON(json, PageCustomization.class);

        assertThat(customization, is(notNullValue()));
        assertThat(customization.isEnabled(), is(true));
        assertThat(customization.getHTML(), is("thewebpage"));
    }

}
