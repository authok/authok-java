package com.authok.json.mgmt.tickets;

import com.authok.json.JsonMatcher;
import com.authok.json.JsonTest;

import org.hamcrest.MatcherAssert;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class PasswordChangeTicketTest extends JsonTest<PasswordChangeTicket> {

    private static final String readOnlyJson = "{\"ticket\":\"https://page.authok.cn/tickets/123\"}";

    @SuppressWarnings("deprecation")
    @Test
    public void shouldSerialize() throws Exception {
        PasswordChangeTicket ticket = new PasswordChangeTicket("usr123");
        ticket.setResultUrl("https://page.authok.cn/result");
        ticket.setTTLSeconds(36000);
        ticket.setConnectionId("12");
        ticket.setEmail("me@authok.cn");
        ticket.setNewPassword("pass123");
        ticket.setMarkEmailAsVerified(true);
        ticket.setOrganizationId("org_abc");
        ticket.setClientId("client_abc");
        ticket.setIncludeEmailInRedirect(false);

        String serialized = toJSON(ticket);
        assertThat(serialized, is(notNullValue()));
        MatcherAssert.assertThat(serialized, JsonMatcher.hasEntry("user_id", "usr123"));
        assertThat(serialized, JsonMatcher.hasEntry("result_url", "https://page.authok.cn/result"));
        assertThat(serialized, JsonMatcher.hasEntry("ttl_sec", 36000));
        assertThat(serialized, JsonMatcher.hasEntry("new_password", "pass123"));
        assertThat(serialized, JsonMatcher.hasEntry("connection_id", "12"));
        assertThat(serialized, JsonMatcher.hasEntry("email", "me@authok.cn"));
        assertThat(serialized, JsonMatcher.hasEntry("mark_email_as_verified", true));
        assertThat(serialized, JsonMatcher.hasEntry("organization_id", "org_abc"));
        assertThat(serialized, JsonMatcher.hasEntry("client_id", "client_abc"));
        assertThat(serialized, JsonMatcher.hasEntry("includeEmailInRedirect", false));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void shouldSerializeWithCustomizedConnection() throws Exception {
        PasswordChangeTicket ticket = new PasswordChangeTicket("user@emailprovider.com", "connid123");
        ticket.setResultUrl("https://page.authok.cn/result");
        ticket.setTTLSeconds(36000);
        ticket.setEmail("me@authok.cn");
        ticket.setNewPassword("pass123");
        ticket.setUserId("usr123");

        String serialized = toJSON(ticket);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("user_id", "usr123"));
        assertThat(serialized, JsonMatcher.hasEntry("result_url", "https://page.authok.cn/result"));
        assertThat(serialized, JsonMatcher.hasEntry("ttl_sec", 36000));
        assertThat(serialized, JsonMatcher.hasEntry("new_password", "pass123"));
        assertThat(serialized, JsonMatcher.hasEntry("connection_id", "connid123"));
        assertThat(serialized, JsonMatcher.hasEntry("email", "me@authok.cn"));
    }
    
    @Test
    public void shouldIncludeReadOnlyValuesOnDeserialize() throws Exception {
        PasswordChangeTicket ticket = fromJSON(readOnlyJson, PasswordChangeTicket.class);
        assertThat(ticket, is(notNullValue()));

        assertThat(ticket.getTicket(), is("https://page.authok.cn/tickets/123"));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void shouldHandleNullPasswordString() {
        PasswordChangeTicket ticket = new PasswordChangeTicket("userId");
        ticket.setNewPassword((String) null);

        assertThat(ticket, is(notNullValue()));
    }

    @Test
    public void shouldHandleNullPasswordCharArray() {
        PasswordChangeTicket ticket = new PasswordChangeTicket("userId");
        ticket.setNewPassword((char[]) null);

        assertThat(ticket, is(notNullValue()));
    }
}
