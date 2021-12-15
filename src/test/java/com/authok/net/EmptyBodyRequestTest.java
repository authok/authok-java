package com.authok.net;

import com.authok.client.MockServer;
import com.authok.json.auth.TokenHolder;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static com.authok.client.MockServer.AUTH_TOKENS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;

public class EmptyBodyRequestTest {

    private MockServer server;
    private OkHttpClient client;

    private TypeReference<TokenHolder> tokenHolderType;

    @Before
    public void setUp() throws Exception {
        server = new MockServer();
        client = new OkHttpClient();
        tokenHolderType = new TypeReference<TokenHolder>() {
        };
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void shouldCreateEmptyRequestBody() throws Exception {
        EmptyBodyRequest<TokenHolder> request = new EmptyBodyRequest<>(client, server.getBaseUrl(), "POST", tokenHolderType);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(AUTH_TOKENS, 200);
        request.execute();
        RecordedRequest recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getMethod(), is("POST"));
        assertThat(recordedRequest.getBodySize(), is(0L));
    }

    @Test
    public void shouldNotAddParameters() throws Exception {
        EmptyBodyRequest<TokenHolder> request = new EmptyBodyRequest<>(client, server.getBaseUrl(), "POST", tokenHolderType);
        Map mapValue = mock(Map.class);
        request.addParameter("key", "value");
        request.addParameter("map", mapValue);

        server.jsonResponse(AUTH_TOKENS, 200);
        request.execute();
        RecordedRequest recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getMethod(), is("POST"));
        assertThat(recordedRequest.getBodySize(), is(0L));
    }
}
