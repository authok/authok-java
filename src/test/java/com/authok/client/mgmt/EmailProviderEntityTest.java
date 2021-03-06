package com.authok.client.mgmt;

import com.authok.client.mgmt.filter.FieldsFilter;
import com.authok.json.mgmt.emailproviders.EmailProvider;
import com.authok.net.Request;

import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Test;

import java.util.Map;

import static com.authok.client.MockServer.MGMT_EMAIL_PROVIDER;
import static com.authok.client.MockServer.bodyFromRequest;
import static com.authok.client.RecordedRequestMatcher.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class EmailProviderEntityTest extends BaseMgmtEntityTest {
    @Test
    public void shouldGetEmailProvider() throws Exception {
        Request<EmailProvider> request = api.emailProvider().get(null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_EMAIL_PROVIDER, 200);
        EmailProvider response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v1/emails/provider"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldGetEmailProviderWithFields() throws Exception {
        FieldsFilter filter = new FieldsFilter().withFields("some,random,fields", true);
        Request<EmailProvider> request = api.emailProvider().get(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_EMAIL_PROVIDER, 200);
        EmailProvider response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v1/emails/provider"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("fields", "some,random,fields"));
        assertThat(recordedRequest, hasQueryParameter("include_fields", "true"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnSetupEmailProviderWithNullData() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'email provider' cannot be null!");
        api.emailProvider().setup(null);
    }

    @Test
    public void shouldSetupEmailProvider() throws Exception {
        Request<EmailProvider> request = api.emailProvider().setup(new EmailProvider("provider"));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_EMAIL_PROVIDER, 200);
        EmailProvider response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("POST", "/api/v1/emails/provider"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasEntry("name", "provider"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldDeleteEmailProvider() throws Exception {
        Request<Void> request = api.emailProvider().delete();
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_EMAIL_PROVIDER, 200);
        request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("DELETE", "/api/v1/emails/provider"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }

    @Test
    public void shouldThrowOnUpdateEmailProviderWithNullData() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'email provider' cannot be null!");
        api.emailProvider().update(null);
    }

    @Test
    public void shouldUpdateEmailProvider() throws Exception {
        Request<EmailProvider> request = api.emailProvider().update(new EmailProvider("name"));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_EMAIL_PROVIDER, 200);
        EmailProvider response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("PATCH", "/api/v1/emails/provider"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasEntry("name", "name"));

        assertThat(response, is(notNullValue()));
    }
}
