package com.authok.json.mgmt.tenants;

import com.authok.json.JsonMatcher;
import com.authok.json.JsonTest;

import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TenantTest extends JsonTest<Tenant> {

    private static final String json = "{\"change_password\":{},\"guardian_mfa_page\":{},\"default_audience\":\"https://domain.authok.cn/myapi\",\"default_directory\":\"Username-Password-Authentication\",\"error_page\":{},\"flags\":{},\"friendly_name\":\"My-Tenant\",\"picture_url\":\"https://pic.to/123\",\"support_email\":\"support@authok.cn\",\"support_url\":\"https://support.authok.cn\",\"allowed_logout_urls\":[\"https://domain.authok.cn/logout\"], \"session_lifetime\":24}";

    @Test
    public void shouldSerialize() throws Exception {
        Tenant tenant = new Tenant();
        tenant.setChangePasswordPage(new PageCustomization());
        tenant.setGuardianMFAPage(new PageCustomization());
        tenant.setDefaultAudience("https://domain.authok.cn/myapi");
        tenant.setDefaultDirectory("Username-Password-Authentication");
        tenant.setErrorPage(new ErrorPageCustomization());
        tenant.setFlags(Collections.emptyMap());
        tenant.setFriendlyName("My-Tenant");
        tenant.setPictureUrl("https://pic.to/123");
        tenant.setSupportEmail("support@authok.cn");
        tenant.setSupportUrl("https://support.authok.cn");
        tenant.setAllowedLogoutUrls(Collections.singletonList("https://domain.authok.cn/logout"));
        tenant.setSessionLifetime(48);

        String serialized = toJSON(tenant);
        assertThat(serialized, is(notNullValue()));

        MatcherAssert.assertThat(serialized, JsonMatcher.hasEntry("change_password", notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("guardian_mfa_page", notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("default_audience", "https://domain.authok.cn/myapi"));
        assertThat(serialized, JsonMatcher.hasEntry("default_directory", "Username-Password-Authentication"));
        assertThat(serialized, JsonMatcher.hasEntry("error_page", notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("flags", notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("friendly_name", "My-Tenant"));
        assertThat(serialized, JsonMatcher.hasEntry("picture_url", "https://pic.to/123"));
        assertThat(serialized, JsonMatcher.hasEntry("support_email", "support@authok.cn"));
        assertThat(serialized, JsonMatcher.hasEntry("support_url", "https://support.authok.cn"));
        assertThat(serialized, JsonMatcher.hasEntry("allowed_logout_urls", Arrays.asList("https://domain.authok.cn/logout")));
        assertThat(serialized, JsonMatcher.hasEntry("session_lifetime", 48));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        Tenant tenant = fromJSON(json, Tenant.class);

        assertThat(tenant, is(notNullValue()));
        assertThat(tenant.getChangePasswordPage(), is(notNullValue()));
        assertThat(tenant.getGuardianMFAPage(), is(notNullValue()));
        assertThat(tenant.getDefaultAudience(), is("https://domain.authok.cn/myapi"));
        assertThat(tenant.getDefaultDirectory(), is("Username-Password-Authentication"));
        assertThat(tenant.getErrorPage(), is(notNullValue()));
        assertThat(tenant.getFlags(), is(notNullValue()));
        assertThat(tenant.getFriendlyName(), is("My-Tenant"));
        assertThat(tenant.getPictureUrl(), is("https://pic.to/123"));
        assertThat(tenant.getSupportEmail(), is("support@authok.cn"));
        assertThat(tenant.getSupportUrl(), is("https://support.authok.cn"));
        assertThat(tenant.getAllowedLogoutUrls(), contains("https://domain.authok.cn/logout"));
        assertThat(tenant.getSessionLifetime(), is(24));
    }

}
