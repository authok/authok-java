package com.authok.json.mgmt.organizations;

import com.authok.json.JsonMatcher;
import com.authok.json.JsonTest;

import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class OrganizationsTest extends JsonTest<Organization> {

    @Test
    public void shouldDeserialize() throws Exception {
        String orgJson = "{\n" +
            "        \"id\": \"org_abc\",\n" +
            "        \"name\": \"org-name\",\n" +
            "        \"display_name\": \"display name\",\n" +
            "        \"branding\": {\n" +
            "            \"logo_url\": \"https://some-url.com/\",\n" +
            "            \"colors\": {\n" +
            "                \"primary\": \"#FF0000\",\n" +
            "                \"page_background\": \"#FF0000\"\n" +
            "            }\n" +
            "        },\n" +
            "        \"metadata\": {\n" +
            "            \"key1\": \"val1\"\n" +
            "        }\n" +
            "    }";

        Organization org = fromJSON(orgJson, Organization.class);
        assertThat(org, is(notNullValue()));
        assertThat(org.getId(), is("org_abc"));
        assertThat(org.getName(), is("org-name"));
        assertThat(org.getDisplayName(), is("display name"));
        assertThat(org.getBranding(), is(notNullValue()));
        assertThat(org.getBranding().getLogoUrl(), is("https://some-url.com/"));
        assertThat(org.getBranding().getColors(), is(notNullValue()));
        assertThat(org.getBranding().getColors().getPrimary(), is("#FF0000"));
        assertThat(org.getBranding().getColors().getPageBackground(), is("#FF0000"));
        assertThat(org.getMetadata(), is(notNullValue()));
        assertThat(org.getMetadata().get("key1"), is("val1"));
    }

    @Test
    public void shouldSerialize() throws Exception {
        Colors colors = new Colors();
        colors.setPrimary("#FF0000");
        colors.setPageBackground("#DD0000");

        Branding branding = new Branding();
        branding.setLogoUrl("https://some-url.com");
        branding.setColors(colors);

        Map<String, Object> metadata = Collections.singletonMap("key1", "val1");
        Organization organization = new Organization("org-name");
        organization.setDisplayName("display name");
        organization.setBranding(branding);
        organization.setMetadata(metadata);

        String serialized = toJSON(organization);
        assertThat(serialized, is(notNullValue()));
        MatcherAssert.assertThat(serialized, JsonMatcher.hasEntry("name", "org-name"));
        assertThat(serialized, JsonMatcher.hasEntry("display_name", "display name"));
        assertThat(serialized, JsonMatcher.hasEntry("metadata", metadata));
        assertThat(serialized, JsonMatcher.hasEntry("branding", is(notNullValue())));
    }
}
