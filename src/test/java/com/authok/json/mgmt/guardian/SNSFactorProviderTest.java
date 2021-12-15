package com.authok.json.mgmt.guardian;

import com.authok.json.JsonMatcher;
import com.authok.json.JsonTest;

import org.hamcrest.MatcherAssert;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class SNSFactorProviderTest extends JsonTest<SNSFactorProvider> {

    private static final String json = "{\"aws_access_key_id\":\"akey\",\"aws_secret_access_key\":\"secretakey\",\"aws_region\":\"ar\",\"sns_apns_platform_application_arn\":\"arn1\",\"sns_gcm_platform_application_arn\":\"arn2\"}";

    @SuppressWarnings("deprecation")
    @Test
    public void shouldSerializeWithDeprecatedSetters() throws Exception {
        SNSFactorProvider provider = new SNSFactorProvider();
        provider.setAWSRegion("ar");
        provider.setAWSAccessKeyId("akey");
        provider.setAWSSecretAccessKey("secretakey");
        provider.setSNSAPNSPlatformApplicationARN("arn1");
        provider.setSNSGCMPlatformApplicationARN("arn2");

        String serialized = toJSON(provider);
        assertThat(serialized, is(notNullValue()));
        MatcherAssert.assertThat(serialized, JsonMatcher.hasEntry("aws_access_key_id", "akey"));
        assertThat(serialized, JsonMatcher.hasEntry("aws_secret_access_key", "secretakey"));
        assertThat(serialized, JsonMatcher.hasEntry("aws_region", "ar"));
        assertThat(serialized, JsonMatcher.hasEntry("sns_apns_platform_application_arn", "arn1"));
        assertThat(serialized, JsonMatcher.hasEntry("sns_gcm_platform_application_arn", "arn2"));
    }

    @Test
    public void shouldSerialize() throws Exception {
        SNSFactorProvider provider = new SNSFactorProvider("akey", "secretakey", "ar", "arn1", "arn2");

        String serialized = toJSON(provider);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("aws_access_key_id", "akey"));
        assertThat(serialized, JsonMatcher.hasEntry("aws_secret_access_key", "secretakey"));
        assertThat(serialized, JsonMatcher.hasEntry("aws_region", "ar"));
        assertThat(serialized, JsonMatcher.hasEntry("sns_apns_platform_application_arn", "arn1"));
        assertThat(serialized, JsonMatcher.hasEntry("sns_gcm_platform_application_arn", "arn2"));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        SNSFactorProvider provider = fromJSON(json, SNSFactorProvider.class);

        assertThat(provider, is(notNullValue()));
        assertThat(provider.getAWSRegion(), is("ar"));
        assertThat(provider.getAWSAccessKeyId(), is("akey"));
        assertThat(provider.getAWSSecretAccessKey(), is("secretakey"));
        assertThat(provider.getSNSAPNSPlatformApplicationARN(), is("arn1"));
        assertThat(provider.getSNSGCMPlatformApplicationARN(), is("arn2"));
    }

}
