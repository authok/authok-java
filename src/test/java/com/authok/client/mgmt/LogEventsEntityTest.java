package com.authok.client.mgmt;

import com.authok.client.mgmt.filter.LogEventFilter;
import com.authok.json.mgmt.logevents.LogEvent;
import com.authok.json.mgmt.logevents.LogEventsPage;
import com.authok.net.Request;

import okhttp3.mockwebserver.RecordedRequest;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import static com.authok.client.MockServer.*;
import static com.authok.client.RecordedRequestMatcher.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class LogEventsEntityTest extends BaseMgmtEntityTest {

    @Test
    public void shouldListEventLogs() throws Exception {
        Request<LogEventsPage> request = api.logEvents().list(null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_LOG_EVENTS_LIST, 200);
        LogEventsPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v1/logs"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        MatcherAssert.assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldListLogEventsWithPage() throws Exception {
        LogEventFilter filter = new LogEventFilter().withPage(23, 5);
        Request<LogEventsPage> request = api.logEvents().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_LOG_EVENTS_LIST, 200);
        LogEventsPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v1/logs"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("page", "23"));
        assertThat(recordedRequest, hasQueryParameter("page_size", "5"));

        assertThat(response, is(notNullValue()));
        MatcherAssert.assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldListLogEventsWithTotals() throws Exception {
        LogEventFilter filter = new LogEventFilter().withTotals(true);
        Request<LogEventsPage> request = api.logEvents().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_LOG_EVENTS_PAGED_LIST, 200);
        LogEventsPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v1/logs"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("include_totals", "true"));

        assertThat(response, is(notNullValue()));
        MatcherAssert.assertThat(response.getItems(), hasSize(2));
        MatcherAssert.assertThat(response.getStart(), is(0));
        MatcherAssert.assertThat(response.getLength(), is(14));
        MatcherAssert.assertThat(response.getTotal(), is(14));
        MatcherAssert.assertThat(response.getLimit(), is(50));
    }

    @Test
    public void shouldListLogEventsWithSort() throws Exception {
        LogEventFilter filter = new LogEventFilter().withSort("date:1");
        Request<LogEventsPage> request = api.logEvents().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_LOG_EVENTS_LIST, 200);
        LogEventsPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v1/logs"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("sort", "date:1"));

        assertThat(response, is(notNullValue()));
        MatcherAssert.assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldListLogEventsWithQuery() throws Exception {
        LogEventFilter filter = new LogEventFilter().withQuery("email:\\*@gmail.com");
        Request<LogEventsPage> request = api.logEvents().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_LOG_EVENTS_LIST, 200);
        LogEventsPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v1/logs"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("q", "email:\\*@gmail.com"));

        assertThat(response, is(notNullValue()));
        MatcherAssert.assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldListLogEventsWithCheckpoint() throws Exception {
        LogEventFilter filter = new LogEventFilter().withCheckpoint("id3", 5);
        Request<LogEventsPage> request = api.logEvents().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_LOG_EVENTS_LIST, 200);
        LogEventsPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v1/logs"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("from", "id3"));
        assertThat(recordedRequest, hasQueryParameter("take", "5"));

        assertThat(response, is(notNullValue()));
        MatcherAssert.assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldListLogEventsWithFields() throws Exception {
        LogEventFilter filter = new LogEventFilter().withFields("some,random,fields", true);
        Request<LogEventsPage> request = api.logEvents().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_LOG_EVENTS_LIST, 200);
        LogEventsPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v1/logs"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("fields", "some,random,fields"));
        assertThat(recordedRequest, hasQueryParameter("include_fields", "true"));

        assertThat(response, is(notNullValue()));
        MatcherAssert.assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldReturnEmptyLogEvents() throws Exception {
        Request<LogEventsPage> request = api.logEvents().list(null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_EMPTY_LIST, 200);
        LogEventsPage response = request.execute();

        assertThat(response, is(notNullValue()));
        MatcherAssert.assertThat(response.getItems(), Matchers.is(emptyCollectionOf(LogEvent.class)));
    }

    @Test
    public void shouldThrowOnGetLogEventWithNullId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'log event id' cannot be null!");
        api.logEvents().get(null);
    }

    @Test
    public void shouldGetLogEvent() throws Exception {
        Request<LogEvent> request = api.logEvents().get("1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_LOG_EVENT, 200);
        LogEvent response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v1/logs/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }
}
