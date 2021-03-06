package com.authok.client.mgmt;

import com.authok.json.mgmt.EmailTemplate;
import com.authok.net.Request;

import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Test;

import java.util.Map;

import static com.authok.client.MockServer.*;
import static com.authok.client.RecordedRequestMatcher.hasHeader;
import static com.authok.client.RecordedRequestMatcher.hasMethodAndPath;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@SuppressWarnings("RedundantThrows")
public class EmailTemplatesEntityTest extends BaseMgmtEntityTest {
    @Test
    public void shouldGetEmailTemplate() throws Exception {
        Request<EmailTemplate> request = api.emailTemplates().get("welcome_email");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_EMAIL_TEMPLATE, 200);
        EmailTemplate response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v1/email-templates/welcome_email"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnGetEmailTemplateWithNullName() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'template name' cannot be null!");
        api.emailTemplates().get(null);
    }

    @Test
    public void shouldCreateEmailTemplate() throws Exception {
        EmailTemplate template = new EmailTemplate();
        template.setName("welcome_email");
        template.setBody("Welcome!!");
        template.setFrom("authok.cn");
        template.setSubject("Welcome");
        template.setSyntax("liquid");
        template.setEnabled(true);

        Request<EmailTemplate> request = api.emailTemplates().create(template);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_EMAIL_PROVIDER, 200);
        EmailTemplate response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("POST", "/api/v1/email-templates"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(6));
        assertThat(body, hasEntry("template", "welcome_email"));
        assertThat(body, hasEntry("body", "Welcome!!"));
        assertThat(body, hasEntry("from", "authok.cn"));
        assertThat(body, hasEntry("syntax", "liquid"));
        assertThat(body, hasEntry("subject", "Welcome"));
        assertThat(body, hasEntry("enabled", true));
        assertThat(body, not(hasKey("resultUrl")));
        assertThat(body, not(hasKey("urlLifetimeInSeconds")));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnCreateEmailTemplateWithNullData() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'template' cannot be null!");
        api.emailTemplates().create(null);
    }

    @Test
    public void shouldPatchEmailTemplate() throws Exception {
        EmailTemplate template = new EmailTemplate();
        template.setBody("<html>New</html>");
        template.setUrlLifetimeInSeconds(123);
        template.setResultUrl("https://somewhere.com");

        Request<EmailTemplate> request = api.emailTemplates().update("welcome_email", template);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_EMAIL_PROVIDER, 200);
        EmailTemplate response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("PATCH", "/api/v1/email-templates/welcome_email"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(4));
        assertThat(body, hasEntry("resultUrl", "https://somewhere.com"));
        assertThat(body, hasEntry("body", "<html>New</html>"));
        assertThat(body, hasEntry("syntax", "liquid"));
        assertThat(body, hasEntry("urlLifetimeInSeconds", 123));
        assertThat(body, not(hasKey("template")));
        assertThat(body, not(hasKey("from")));
        assertThat(body, not(hasKey("enabled")));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnPatchEmailTemplateWithNullData() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'template' cannot be null!");
        api.emailTemplates().update("welcome_email", null);
    }

    @Test
    public void shouldThrowOnPatchEmailTemplateWithNullName() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'template name' cannot be null!");
        api.emailTemplates().update(null, new EmailTemplate());
    }
}
