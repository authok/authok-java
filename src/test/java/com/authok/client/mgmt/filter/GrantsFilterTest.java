package com.authok.client.mgmt.filter;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class GrantsFilterTest {

    private GrantsFilter filter;

    @Before
    public void setUp() {
        filter = new GrantsFilter();
    }

    @Test
    public void shouldFilterByAudience() {
        GrantsFilter instance = filter.withAudience("https://myapi.authok.cn");

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("audience", "https://myapi.authok.cn"));
    }

    @Test
    public void shouldFilterByClientId() {
        GrantsFilter instance = filter.withClientId("n3roinr32i23iron23nr");

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("client_id", "n3roinr32i23iron23nr"));
    }

    @Test
    public void shouldFilterByPage() {
        GrantsFilter instance = filter.withPage(5, 10);

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("per_page", 10));
        assertThat(filter.getAsMap(), Matchers.hasEntry("page", 5));
    }

    @Test
    public void shouldIncludeTotals() {
        GrantsFilter instance = filter.withTotals(true);

        assertThat(filter, is(instance));
        assertThat(filter.getAsMap(), is(notNullValue()));
        assertThat(filter.getAsMap(), Matchers.hasEntry("include_totals", true));
    }

}
