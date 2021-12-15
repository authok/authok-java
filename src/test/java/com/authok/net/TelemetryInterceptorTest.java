package com.authok.net;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TelemetryInterceptorTest {
    private Telemetry telemetry;

    @Before
    public void setUp() {
        telemetry = mock(Telemetry.class);
        when(telemetry.getValue()).thenReturn("ClientInfo");
    }

    @Test
    public void shouldBeEnabledByDefault() {
        TelemetryInterceptor interceptor = new TelemetryInterceptor(telemetry);
        assertThat(interceptor.isEnabled(), is(true));
    }

    @Test
    public void shouldDisable() {
        TelemetryInterceptor interceptor = new TelemetryInterceptor(telemetry);
        interceptor.setEnabled(false);
        assertThat(interceptor.isEnabled(), is(false));
    }

    @Test
    public void shouldEnable() {
        TelemetryInterceptor interceptor = new TelemetryInterceptor(telemetry);
        interceptor.setEnabled(true);
        assertThat(interceptor.isEnabled(), is(true));
    }

    @Test
    public void shouldGetTelemetry() {
        TelemetryInterceptor interceptor = new TelemetryInterceptor(telemetry);
        assertThat(interceptor.getTelemetry(), CoreMatchers.is(telemetry));
    }

    @Test
    public void shouldAllowToModifyTelemetryOnceSet() throws Exception {
        TelemetryInterceptor interceptor = new TelemetryInterceptor(telemetry);
        Telemetry updatedTelemetry = mock(Telemetry.class);
        when(updatedTelemetry.getValue()).thenReturn("UpdatedClientInfo");
        interceptor.setTelemetry(updatedTelemetry);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        MockWebServer server = new MockWebServer();
        server.start();
        server.enqueue(new MockResponse());

        Request request = new Request.Builder()
                .get()
                .url(server.url("/"))
                .build();
        client.newCall(request).execute();

        RecordedRequest finalRequest = server.takeRequest();
        assertThat(finalRequest.getHeader("Authok-Client"), is("UpdatedClientInfo"));

        server.shutdown();
    }

    @Test
    public void shouldAddTelemetryHeaderIfEnabled() throws Exception {
        TelemetryInterceptor interceptor = new TelemetryInterceptor(telemetry);
        interceptor.setEnabled(true);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        MockWebServer server = new MockWebServer();
        server.start();
        server.enqueue(new MockResponse());

        Request request = new Request.Builder()
                .get()
                .url(server.url("/"))
                .build();
        client.newCall(request).execute();

        RecordedRequest finalRequest = server.takeRequest();
        assertThat(finalRequest.getHeader("Authok-Client"), is("ClientInfo"));

        server.shutdown();
    }

    @Test
    public void shouldNotAddTelemetryHeaderIfDisabled() throws Exception {
        TelemetryInterceptor interceptor = new TelemetryInterceptor(telemetry);
        interceptor.setEnabled(false);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        MockWebServer server = new MockWebServer();
        server.start();
        server.enqueue(new MockResponse());

        Request request = new Request.Builder()
                .get()
                .url(server.url("/"))
                .build();
        client.newCall(request).execute();

        RecordedRequest finalRequest = server.takeRequest();
        assertThat(finalRequest.getHeader("Authok-Client"), is(nullValue()));

        server.shutdown();
    }


}
