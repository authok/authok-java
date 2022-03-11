package com.authok.client.mgmt;

import com.authok.client.mgmt.filter.FieldsFilter;
import com.authok.json.mgmt.tenants.Tenant;
import com.authok.net.Request;

import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Test;

import static com.authok.client.MockServer.MGMT_TENANT;
import static com.authok.client.RecordedRequestMatcher.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class TenantsEntityTest extends BaseMgmtEntityTest {

    @Test
    public void shouldGetTenantSettings() throws Exception {
        Request<Tenant> request = api.tenants().get(null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_TENANT, 200);
        Tenant response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v1/tenants/settings"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldGetTenantSettingsWithFields() throws Exception {
        FieldsFilter filter = new FieldsFilter().withFields("some,random,fields", true);
        Request<Tenant> request = api.tenants().get(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_TENANT, 200);
        Tenant response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v1/tenants/settings"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("fields", "some,random,fields"));
        assertThat(recordedRequest, hasQueryParameter("include_fields", "true"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnUpdateTenantSettingsWithNullData() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'tenant' cannot be null!");
        api.tenants().update(null);
    }

    @Test
    public void shouldUpdateTenantSettings() throws Exception {
        Request<Tenant> request = api.tenants().update(new Tenant());
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_TENANT, 200);
        Tenant response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("PATCH", "/api/v1/tenants/settings"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }
}
