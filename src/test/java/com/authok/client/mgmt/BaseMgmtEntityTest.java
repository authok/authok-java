package com.authok.client.mgmt;

import com.authok.client.MockServer;
import com.authok.client.auth.AuthAPI;
import com.authok.json.auth.TokenHolder;
import com.authok.net.TokenRequest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class BaseMgmtEntityTest {

    private static final String API_TOKEN = "apiToken";

    protected MockServer server;
    protected ManagementAPI api;

    @SuppressWarnings("deprecation")
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        server = new MockServer();
        api = new ManagementAPI(server.getBaseUrl(), API_TOKEN);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }
}
