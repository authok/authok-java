package com.authok.json.mgmt.guardian;

import com.authok.json.JsonMatcher;
import com.authok.json.JsonTest;

import org.hamcrest.MatcherAssert;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class EnrollmentTicketTest extends JsonTest<EnrollmentTicket> {

    private static final String readOnlyJson = "{\"ticket_id\":\"ticket123\",\"ticket_url\":\"https://authok.cn/guardian/tickets/123\"}";

    @Test
    public void shouldSerializeDataToBeSent() throws Exception {
        EnrollmentTicket ticket = new EnrollmentTicket("1", true, "me@authok.cn");

        String serialized = toJSON(ticket);
        assertThat(serialized, is(notNullValue()));
        MatcherAssert.assertThat(serialized, JsonMatcher.hasEntry("user_id", "1"));
        assertThat(serialized, JsonMatcher.hasEntry("send_mail", true));
        assertThat(serialized, JsonMatcher.hasEntry("email", "me@authok.cn"));
    }

    @Test
    public void shouldSerializeDataToBeSentOnlyUserId() throws Exception {
        EnrollmentTicket ticket = new EnrollmentTicket("1");

        String serialized = toJSON(ticket);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("user_id", "1"));
    }

    @Test
    public void shouldSerializeDataToBeSentOnlyUserIdAndSendEmail() throws Exception {
        EnrollmentTicket ticket = new EnrollmentTicket("1", true);

        String serialized = toJSON(ticket);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("user_id", "1"));
        assertThat(serialized, JsonMatcher.hasEntry("send_mail", true));
    }

    @Test
    public void shouldIncludeReadOnlyValuesOnDeserialize() throws Exception {
        EnrollmentTicket ticket = fromJSON(readOnlyJson, EnrollmentTicket.class);
        assertThat(ticket, is(notNullValue()));

        assertThat(ticket.getTicketId(), is("ticket123"));
        assertThat(ticket.getTicketUrl(), is("https://authok.cn/guardian/tickets/123"));
    }
}
