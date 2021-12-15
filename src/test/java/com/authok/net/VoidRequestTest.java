package com.authok.net;

import com.authok.client.MockServer;
import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Before;
import org.junit.Test;

import static com.authok.client.MockServer.AUTH_TOKENS;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class VoidRequestTest {
    private OkHttpClient client;
    private MockServer server;

    @Before
    public void setUp() throws Exception {
        client = new OkHttpClient();
        server = new MockServer();
    }

    @Test
    public void shouldCreateGETRequest() throws Exception {
        VoidRequest request = new VoidRequest(client, server.getBaseUrl(), "GET");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(AUTH_TOKENS, 200);
        Void execute = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getMethod(), is("GET"));
        assertThat(execute, is(nullValue()));
    }

    @Test
    public void shouldCreatePOSTRequest() throws Exception {
        VoidRequest request = new VoidRequest(client, server.getBaseUrl(), "POST");
        assertThat(request, is(notNullValue()));
        request.addParameter("non_empty", "body");

        server.jsonResponse(AUTH_TOKENS, 200);
        Void execute = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getMethod(), is("POST"));
        assertThat(execute, is(nullValue()));
    }

}
