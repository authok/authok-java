package com.authok.json.mgmt.users;

import com.authok.json.JsonTest;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class IdentityTest extends JsonTest<Identity> {

    private static final String json = "{\"connection\":\"authok\",\"user_id\":\"user|123\",\"isSocial\":true,\"provider\":\"oauth\",\"access_token\":\"aTokEn\",\"profileData\":{},\"access_token_secret\":\"s3cr3t\"}";

    @Test
    public void shouldDeserialize() throws Exception {
        Identity identity = fromJSON(json, Identity.class);

        assertThat(identity, is(notNullValue()));
        assertThat(identity.getConnection(), is("authok"));
        assertThat(identity.getUserId(), is("user|123"));
        assertThat(identity.isSocial(), is(true));
        assertThat(identity.getProvider(), is("oauth"));
        assertThat(identity.getAccessToken(), is("aTokEn"));
        assertThat(identity.getProfileData(), is(notNullValue()));
        assertThat(identity.getValues(), is(notNullValue()));
        assertThat(identity.getValues(), hasEntry("access_token_secret", "s3cr3t"));
    }
}
