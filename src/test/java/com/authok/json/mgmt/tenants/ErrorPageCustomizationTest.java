package com.authok.json.mgmt.tenants;

import com.authok.json.JsonMatcher;
import com.authok.json.JsonTest;

import org.hamcrest.MatcherAssert;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class ErrorPageCustomizationTest extends JsonTest<ErrorPageCustomization> {

    private static final String json = "{\"enabled\":true,\"html\":\"thewebpage\",\"show_log_link\":true,\"url\":\"https://page.authok.com/main\"}";

    @Test
    public void shouldSerialize() throws Exception {
        ErrorPageCustomization customization = new ErrorPageCustomization();
        customization.setEnabled(true);
        customization.setHTML("thewebpage");
        customization.setShowLogLink(true);
        customization.setUrl("https://page.authok.com/main");

        String serialized = toJSON(customization);
        assertThat(serialized, is(notNullValue()));
        MatcherAssert.assertThat(serialized, JsonMatcher.hasEntry("enabled", true));
        assertThat(serialized, JsonMatcher.hasEntry("html", "thewebpage"));
        assertThat(serialized, JsonMatcher.hasEntry("show_log_link", true));
        assertThat(serialized, JsonMatcher.hasEntry("url", "https://page.authok.com/main"));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        ErrorPageCustomization customization = fromJSON(json, ErrorPageCustomization.class);

        assertThat(customization, is(notNullValue()));
        assertThat(customization.isEnabled(), is(true));
        assertThat(customization.getHTML(), is("thewebpage"));
        assertThat(customization.willShowLogLink(), is(true));
        assertThat(customization.getUrl(), is("https://page.authok.com/main"));
    }

}
