package com.authok.json.mgmt.client;

import com.authok.json.JsonMatcher;
import com.authok.json.JsonTest;

import org.hamcrest.MatcherAssert;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class MobileTest extends JsonTest<Mobile> {

    private static final String json = "{\"android\":{},\"ios\":{}}";

    @Test
    public void shouldSerialize() throws Exception {
        Android android = new Android(null, null);
        IOS ios = new IOS(null, null);
        Mobile mobile = new Mobile(android, ios);

        String serialized = toJSON(mobile);
        assertThat(serialized, is(notNullValue()));
        MatcherAssert.assertThat(serialized, JsonMatcher.hasEntry("android", notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("ios", notNullValue()));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        Mobile mobile = fromJSON(json, Mobile.class);

        assertThat(mobile, is(notNullValue()));
        assertThat(mobile.getAndroid(), is(notNullValue()));
        assertThat(mobile.getIOS(), is(notNullValue()));
    }
}
