package com.authok.json.mgmt.organizations;

import com.authok.json.JsonTest;

import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MembersPageTest extends JsonTest<MembersPage> {
    private static final String jsonWithoutTotals = "[\n" +
        "  {\n" +
        "    \"user_id\": \"authok|605a1f57cbeb2c0070fdf123\",\n" +
        "    \"email\": \"dave@domain.com\",\n" +
        "    \"picture\": \"https://domain.com/img.png\",\n" +
        "    \"name\": \"dave\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"user_id\": \"authok|605a0fc1bef67f006851a123\",\n" +
        "    \"email\": \"eric@domain.com\",\n" +
        "    \"picture\": \"https://domain.com/img.png\",\n" +
        "    \"name\": \"eric\"\n" +
        "  }\n" +
        "]\n";

    private static final String jsonWithTotals = "{\n" +
        "  \"members\": [\n" +
        "    {\n" +
        "      \"user_id\": \"authok|605a1f57cbeb2c0070fdf123\",\n" +
        "      \"email\": \"dave@domain.com\",\n" +
        "      \"picture\": \"https://domain.com/img.png\",\n" +
        "      \"name\": \"dave\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"user_id\": \"authok|605a0fc1bef67f006851a123\",\n" +
        "      \"email\": \"eric@domain.com\",\n" +
        "      \"picture\": \"https://domain.com/img.png\",\n" +
        "      \"name\": \"eric\"\n" +
        "    }\n" +
        "  ],\n" +
        "  \"start\": 0,\n" +
        "  \"limit\": 20,\n" +
        "  \"total\": 2\n" +
        "}\n";

    private static final String jsonWithCheckpointPageResponse = "{\n" +
        "  \"members\": [\n" +
        "    {\n" +
        "      \"user_id\": \"authok|605a1f57cbeb2c0070fdf123\",\n" +
        "      \"email\": \"dave@domain.com\",\n" +
        "      \"picture\": \"https://domain.com/img.png\",\n" +
        "      \"name\": \"dave\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"user_id\": \"authok|605a0fc1bef67f006851a123\",\n" +
        "      \"email\": \"eric@domain.com\",\n" +
        "      \"picture\": \"https://domain.com/img.png\",\n" +
        "      \"name\": \"eric\"\n" +
        "    }\n" +
        "  ],\n" +
        "  \"next\": \"MjAyMS0wMy0yOSAxNjo1MDo09s44NDYxODcrMDAsb3JnX2Y0VXZUbG1iSWd2005zTGw\"\n" +
        "}\n";

    @Test
    public void shouldDeserializeWithoutTotals() throws Exception {
        MembersPage page = fromJSON(jsonWithoutTotals, MembersPage.class);

        assertThat(page, is(notNullValue()));
        MatcherAssert.assertThat(page.getStart(), is(nullValue()));
        MatcherAssert.assertThat(page.getLength(), is(nullValue()));
        MatcherAssert.assertThat(page.getTotal(), is(nullValue()));
        MatcherAssert.assertThat(page.getLimit(), is(nullValue()));
        MatcherAssert.assertThat(page.getNext(), is(nullValue()));
        MatcherAssert.assertThat(page.getItems(), is(notNullValue()));
        MatcherAssert.assertThat(page.getItems().size(), is(2));
    }

    @Test
    public void shouldDeserializeWithTotals() throws Exception {
        MembersPage page = fromJSON(jsonWithTotals, MembersPage.class);

        assertThat(page, is(notNullValue()));
        MatcherAssert.assertThat(page.getStart(), is(0));
        MatcherAssert.assertThat(page.getTotal(), is(2));
        MatcherAssert.assertThat(page.getLimit(), is(20));
        MatcherAssert.assertThat(page.getNext(), is(nullValue()));
        MatcherAssert.assertThat(page.getItems(), is(notNullValue()));
        MatcherAssert.assertThat(page.getItems().size(), is(2));
    }

    @Test
    public void shouldDeserializeWithNextItemPointer() throws Exception {
        MembersPage page = fromJSON(jsonWithCheckpointPageResponse, MembersPage.class);

        assertThat(page, is(notNullValue()));

        MatcherAssert.assertThat(page.getStart(), is(nullValue()));
        MatcherAssert.assertThat(page.getTotal(), is(nullValue()));
        MatcherAssert.assertThat(page.getLimit(), is(nullValue()));
        MatcherAssert.assertThat(page.getNext(), is("MjAyMS0wMy0yOSAxNjo1MDo09s44NDYxODcrMDAsb3JnX2Y0VXZUbG1iSWd2005zTGw"));
        MatcherAssert.assertThat(page.getItems(), is(notNullValue()));
        MatcherAssert.assertThat(page.getItems().size(), is(2));
    }

    @Test
    public void shouldBeCreatedWithoutNextField() {
        MembersPage page = new MembersPageDeserializer().createPage(0, 5, 20, 50, new ArrayList<>());

        MatcherAssert.assertThat(page.getStart(), is(0));
        MatcherAssert.assertThat(page.getLength(), is(5));
        MatcherAssert.assertThat(page.getTotal(), is(20));
        MatcherAssert.assertThat(page.getLimit(), is(50));
        MatcherAssert.assertThat(page.getItems(), is(notNullValue()));
    }
}

