package com.authok.json.mgmt;

import com.authok.json.JsonTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class DailyStatsTest extends JsonTest<DailyStats> {
    private static final String json = "{\"logins\":123,\"date\":\"2017-01-18T17:45:08.328Z\"}";

    @Test
    public void shouldDeserialize() throws Exception {
        DailyStats stats = fromJSON(json, DailyStats.class);

        assertThat(stats, is(notNullValue()));
        assertThat(stats.getDate(), is(equalTo(parseJSONDate("2017-01-18T17:45:08.328Z"))));
        assertThat(stats.getLogins(), is(123));
    }

}
