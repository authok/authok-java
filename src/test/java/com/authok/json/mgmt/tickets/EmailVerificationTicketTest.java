package com.authok.json.mgmt.tickets;

import com.authok.json.JsonMatcher;
import com.authok.json.JsonTest;
import com.authok.json.mgmt.EmailVerificationIdentity;

import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class EmailVerificationTicketTest extends JsonTest<EmailVerificationTicket> {

    private static final String readOnlyJson = "{\"ticket\":\"https://page.authok.cn/tickets/123\"}";

    @Test
    public void shouldSerialize() throws Exception {
        EmailVerificationTicket ticket = new EmailVerificationTicket("usr123");
        ticket.setResultUrl("https://page.authok.cn/result");
        ticket.setTTLSeconds(36000);
        ticket.setIncludeEmailInRedirect(true);
        ticket.setClientId("client_abc");
        ticket.setOrganizationId("org_abc");

        String serialized = toJSON(ticket);
        assertThat(serialized, is(notNullValue()));
        MatcherAssert.assertThat(serialized, JsonMatcher.hasEntry("user_id", "usr123"));
        assertThat(serialized, JsonMatcher.hasEntry("result_url", "https://page.authok.cn/result"));
        assertThat(serialized, JsonMatcher.hasEntry("ttl_sec", 36000));
        assertThat(serialized, JsonMatcher.hasEntry("includeEmailInRedirect", true));
        assertThat(serialized, JsonMatcher.hasEntry("client_id", "client_abc"));
        assertThat(serialized, JsonMatcher.hasEntry("organization_id", "org_abc"));
    }

    @Test
    public void shouldSerializeWithIdentity() throws Exception {
        EmailVerificationTicket ticket = new EmailVerificationTicket("usr123");
        ticket.setResultUrl("https://page.authok.cn/result");
        ticket.setTTLSeconds(36000);
        EmailVerificationIdentity identity = new EmailVerificationIdentity("some-provider", "some-user-id");
        ticket.setIdentity(identity);

        String serialized = toJSON(ticket);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("user_id", "usr123"));
        assertThat(serialized, JsonMatcher.hasEntry("result_url", "https://page.authok.cn/result"));
        assertThat(serialized, JsonMatcher.hasEntry("ttl_sec", 36000));

        Map<String, String> identityMap = new HashMap<>();
        identityMap.put("provider", "some-provider");
        identityMap.put("user_id", "some-user-id");
        assertThat(serialized, JsonMatcher.hasEntry("identity", identityMap));
    }

    @Test
    public void shouldIncludeReadOnlyValuesOnDeserialize() throws Exception {
        EmailVerificationTicket ticket = fromJSON(readOnlyJson, EmailVerificationTicket.class);
        assertThat(ticket, is(notNullValue()));

        assertThat(ticket.getTicket(), is("https://page.authok.cn/tickets/123"));
    }

}
