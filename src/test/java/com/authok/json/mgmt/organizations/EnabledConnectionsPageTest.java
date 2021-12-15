package com.authok.json.mgmt.organizations;

import com.authok.json.JsonTest;

import org.hamcrest.MatcherAssert;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class EnabledConnectionsPageTest extends JsonTest<EnabledConnectionsPage> {

    private static String jsonWithoutTotals = "[\n" +
        "    {\n" +
        "        \"connection_id\": \"con_1\",\n" +
        "        \"assign_membership_on_login\": false,\n" +
        "        \"connection\": {\n" +
        "            \"name\": \"google-oauth2\",\n" +
        "            \"strategy\": \"google-oauth2\"\n" +
        "        }\n" +
        "    },\n" +
        "    {\n" +
        "        \"connection_id\": \"con_2\",\n" +
        "        \"assign_membership_on_login\": true,\n" +
        "        \"connection\": {\n" +
        "            \"name\": \"Username-Password-Authentication\",\n" +
        "            \"strategy\": \"authok\"\n" +
        "        }\n" +
        "    }\n" +
        "]";

    private static String jsonWithTotals = "{\n" +
        "    \"enabled_connections\": [\n" +
        "        {\n" +
        "            \"connection_id\": \"con_1\",\n" +
        "            \"assign_membership_on_login\": false,\n" +
        "            \"connection\": {\n" +
        "                \"name\": \"google-oauth2\",\n" +
        "                \"strategy\": \"google-oauth2\"\n" +
        "            }\n" +
        "        },\n" +
        "        {\n" +
        "            \"connection_id\": \"con_2\",\n" +
        "            \"assign_membership_on_login\": true,\n" +
        "            \"connection\": {\n" +
        "                \"name\": \"Username-Password-Authentication\",\n" +
        "                \"strategy\": \"authok\"\n" +
        "            }\n" +
        "        }\n" +
        "    ],\n" +
        "    \"start\": 0,\n" +
        "    \"limit\": 2,\n" +
        "    \"total\": 2\n" +
        "}";

    @Test
    public void shouldDeserializeWithoutTotals() throws Exception {
        EnabledConnectionsPage page = fromJSON(jsonWithoutTotals, EnabledConnectionsPage.class);

        assertThat(page, is(notNullValue()));
        MatcherAssert.assertThat(page.getStart(), is(nullValue()));
        MatcherAssert.assertThat(page.getLength(), is(nullValue()));
        MatcherAssert.assertThat(page.getTotal(), is(nullValue()));
        MatcherAssert.assertThat(page.getLimit(), is(nullValue()));
        MatcherAssert.assertThat(page.getItems(), is(notNullValue()));
        MatcherAssert.assertThat(page.getItems().size(), is(2));
    }

    @Test
    public void shouldDeserializeWithTotals() throws Exception {
        EnabledConnectionsPage page = fromJSON(jsonWithTotals, EnabledConnectionsPage.class);

        assertThat(page, is(notNullValue()));
        assertThat(page, is(notNullValue()));
        MatcherAssert.assertThat(page.getStart(), is(0));
        MatcherAssert.assertThat(page.getTotal(), is(2));
        MatcherAssert.assertThat(page.getLimit(), is(2));
        MatcherAssert.assertThat(page.getItems(), is(notNullValue()));
        MatcherAssert.assertThat(page.getItems().size(), is(2));
    }
}
