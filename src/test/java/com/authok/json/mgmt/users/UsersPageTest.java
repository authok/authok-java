package com.authok.json.mgmt.users;

import com.authok.json.JsonTest;

import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UsersPageTest extends JsonTest<UsersPage> {
    private static final String jsonWithTotals = "{\"start\":0,\"length\":10,\"total\":14,\"limit\":50,\"users\":[{\"connection\":\"authok\",\"client_id\":\"client123\",\"password\":\"pwd\",\"verify_password\":true,\"username\":\"usr\",\"email\":\"me@authok.cn\",\"email_verified\":true,\"verify_email\":true,\"phone_number\":\"1234567890\",\"phone_verified\":true,\"verify_phone_number\":true,\"picture\":\"https://pic.ture/12\",\"name\":\"John\",\"nickname\":\"Johny\",\"given_name\":\"John\",\"family_name\":\"Walker\",\"created_at\":\"2016-02-23T19:57:29.532Z\",\"updated_at\":\"2016-02-23T19:57:29.532Z\",\"identities\":[],\"app_metadata\":{},\"user_metadata\":{},\"last_ip\":\"10.0.0.1\",\"last_login\":\"2016-02-23T19:57:29.532Z\",\"logins_count\":10,\"blocked\":true}]}";
    private static final String jsonWithoutTotals = "[{\"connection\":\"authok\",\"client_id\":\"client123\",\"password\":\"pwd\",\"verify_password\":true,\"username\":\"usr\",\"email\":\"me@authok.cn\",\"email_verified\":true,\"verify_email\":true,\"phone_number\":\"1234567890\",\"phone_verified\":true,\"verify_phone_number\":true,\"picture\":\"https://pic.ture/12\",\"name\":\"John\",\"nickname\":\"Johny\",\"given_name\":\"John\",\"family_name\":\"Walker\",\"created_at\":\"2016-02-23T19:57:29.532Z\",\"updated_at\":\"2016-02-23T19:57:29.532Z\",\"identities\":[],\"app_metadata\":{},\"user_metadata\":{},\"last_ip\":\"10.0.0.1\",\"last_login\":\"2016-02-23T19:57:29.532Z\",\"logins_count\":10,\"blocked\":true}]";
    private static final String jsonWithCheckpointPageResponse = "{\"users\":[{\"connection\":\"authok\",\"client_id\":\"client123\",\"password\":\"pwd\",\"verify_password\":true,\"username\":\"usr\",\"email\":\"me@authok.cn\",\"email_verified\":true,\"verify_email\":true,\"phone_number\":\"1234567890\",\"phone_verified\":true,\"verify_phone_number\":true,\"picture\":\"https://pic.ture/12\",\"name\":\"John\",\"nickname\":\"Johny\",\"given_name\":\"John\",\"family_name\":\"Walker\",\"created_at\":\"2016-02-23T19:57:29.532Z\",\"updated_at\":\"2016-02-23T19:57:29.532Z\",\"identities\":[],\"app_metadata\":{},\"user_metadata\":{},\"last_ip\":\"10.0.0.1\",\"last_login\":\"2016-02-23T19:57:29.532Z\",\"logins_count\":10,\"blocked\":true}],\"next\":\"MjAyMS0wMy0yOSAxNjo1MDo09s44NDYxODcrMDAsb3JnX2Y0VXZUbG1iSWd2005zTGw\"}";

    @Test
    public void shouldDeserializeWithoutTotals() throws Exception {
        UsersPage page = fromJSON(jsonWithoutTotals, UsersPage.class);

        assertThat(page, is(notNullValue()));
        MatcherAssert.assertThat(page.getStart(), is(nullValue()));
        MatcherAssert.assertThat(page.getLength(), is(nullValue()));
        MatcherAssert.assertThat(page.getTotal(), is(nullValue()));
        MatcherAssert.assertThat(page.getLimit(), is(nullValue()));
        MatcherAssert.assertThat(page.getNext(), is(nullValue()));
        MatcherAssert.assertThat(page.getItems(), is(notNullValue()));
        MatcherAssert.assertThat(page.getItems().size(), is(1));
    }

    @Test
    public void shouldDeserializeWithTotals() throws Exception {
        UsersPage page = fromJSON(jsonWithTotals, UsersPage.class);

        assertThat(page, is(notNullValue()));
        MatcherAssert.assertThat(page.getStart(), is(0));
        MatcherAssert.assertThat(page.getLength(), is(10));
        MatcherAssert.assertThat(page.getTotal(), is(14));
        MatcherAssert.assertThat(page.getLimit(), is(50));
        MatcherAssert.assertThat(page.getNext(), is(nullValue()));
        MatcherAssert.assertThat(page.getItems(), is(notNullValue()));
        MatcherAssert.assertThat(page.getItems().size(), is(1));
    }

    @Test
    public void shouldDeserializeWithCheckpointResponse() throws Exception {
        UsersPage page = fromJSON(jsonWithCheckpointPageResponse, UsersPage.class);

        MatcherAssert.assertThat(page.getStart(), is(nullValue()));
        MatcherAssert.assertThat(page.getLength(), is(nullValue()));
        MatcherAssert.assertThat(page.getTotal(), is(nullValue()));
        MatcherAssert.assertThat(page.getLimit(), is(nullValue()));
        MatcherAssert.assertThat(page.getItems(), is(notNullValue()));
        MatcherAssert.assertThat(page.getNext(), is("MjAyMS0wMy0yOSAxNjo1MDo09s44NDYxODcrMDAsb3JnX2Y0VXZUbG1iSWd2005zTGw"));
        MatcherAssert.assertThat(page.getItems().size(), is(1));
    }

    @Test
    public void shouldBeCreatedWithoutNextField() {
        UsersPage page = new UsersPageDeserializer().createPage(0, 5, 20, 50, new ArrayList<>());

        MatcherAssert.assertThat(page.getStart(), is(0));
        MatcherAssert.assertThat(page.getLength(), is(5));
        MatcherAssert.assertThat(page.getTotal(), is(20));
        MatcherAssert.assertThat(page.getLimit(), is(50));
        MatcherAssert.assertThat(page.getItems(), is(notNullValue()));
    }
}
