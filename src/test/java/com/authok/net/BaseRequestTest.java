package com.authok.net;

import com.authok.exception.APIException;
import com.authok.exception.AuthokException;
import com.authok.exception.RateLimitException;
import okhttp3.Request;
import okhttp3.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;

// FIXME: These test require mocking of the final class okhttp3.Response. To do so
//  an opt-in incubating Mockito feature is used, for more information see:
//  https://github.com/mockito/mockito/wiki/What%27s-new-in-Mockito-2#mock-the-unmockable-opt-in-mocking-of-final-classesmethods
//  This issue can be tracked to see if/when this feature will become standard for Mockito (perhaps Mockito 4):
//  https://github.com/mockito/mockito/issues/1728
public class BaseRequestTest {

    private Response response;
    private Call call;
    private OkHttpClient client;

    @Before
    public void setUp() throws Exception {
        response = mock(Response.class);

        call = mock(Call.class);
        when(call.execute()).thenReturn(response);

        client = mock(OkHttpClient.class);
        when(client.newCall(any())).thenReturn(call);
    }

    @Test
    public void alwaysCloseResponseOnSuccessfulRequest() {
        Exception exception = null;
        try {
            new MockBaseRequest<String>(client) {
                @Override
                protected String parseResponse(Response response) {
                    return "";
                }
            }.execute();
        } catch (Exception e) {
            exception = e;
        }

        assertThat(exception, is(nullValue()));
        verify(response, times(1)).close();
    }

    @Test
    public void alwaysCloseResponseOnRateLimitException() {
        Exception exception = null;
        try {
            new MockBaseRequest<String>(client) {
                @Override
                protected String parseResponse(Response response) throws AuthokException {
                    throw new RateLimitException(-1, -1, -1);
                }
            }.execute();
        } catch (Exception e) {
            exception = e;
        }

        assertThat(exception, is(notNullValue()));
        assertThat(exception, is(instanceOf(RateLimitException.class)));
        assertThat(exception.getMessage(), is("Request failed with status code 429: Rate limit reached"));
        verify(response, times(1)).close();
    }

    @Test
    public void alwaysCloseResponseOnAPIException() {
        Exception exception = null;
        try {
            new MockBaseRequest<String>(client) {
                @Override
                protected String parseResponse(Response response) throws AuthokException {
                    throw new APIException("APIException", 500, null);
                }
            }.execute();
        } catch (Exception e) {
            exception = e;
        }

        assertThat(exception, is(notNullValue()));
        assertThat(exception, is(instanceOf(APIException.class)));
        assertThat(exception.getMessage(), is("Request failed with status code 500: APIException"));
        verify(response, times(1)).close();
    }

    @Test
    public void alwaysCloseResponseOnAuthokException() {
        Exception exception = null;
        try {
            new MockBaseRequest<String>(client) {
                @Override
                protected String parseResponse(Response response) throws AuthokException {
                    throw new AuthokException("AuthokException");
                }
            }.execute();
        } catch (Exception e) {
            exception = e;
        }

        assertThat(exception, is(notNullValue()));
        assertThat(exception, is(instanceOf(AuthokException.class)));
        assertThat(exception.getMessage(), is("AuthokException"));
        verify(response, times(1)).close();
    }

    @Test
    public void asyncCompletesWithExceptionWhenRequestCreationFails() throws Exception {
        CompletableFuture<?> request = new MockBaseRequest<String>(client) {
            @Override
            protected Request parseResponse(Response response) throws AuthokException {
                throw new AuthokException("Response Parsing Error");
            }
            @Override
            protected Request createRequest() throws AuthokException {
                throw new AuthokException("Create Request Error");
            }
        }.executeAsync();

        Exception exception = null;
        Object result = null;

        try {
            result = request.get();
        } catch (Exception e) {
            exception = e;
        }

        assertThat(result, is(nullValue()));
        assertThat(exception, is(notNullValue()));
        assertThat(exception.getCause(), is(instanceOf(AuthokException.class)));
        assertThat(exception.getCause().getMessage(), is("Create Request Error"));
    }

    @Test
    public void asyncCompletesWithExceptionWhenRequestFails() throws Exception {
        doReturn(call).when(client).newCall(any());

        doAnswer(invocation -> {
            ((Callback) invocation.getArgument(0)).onFailure(call, new IOException("Error!"));
            return null;
        }).when(call).enqueue(any());

        CompletableFuture<?> request = new MockBaseRequest<String>(client) {
            @Override
            protected String parseResponse(Response response) throws AuthokException {
                throw new AuthokException("Response Parsing Error");
            }
        }.executeAsync();

        Exception exception = null;
        Object result = null;

        try {
            result = request.get();
        } catch (Exception e) {
            exception = e;
        }

        assertThat(exception, is(notNullValue()));
        assertThat(result, is(nullValue()));
        assertThat(exception.getCause(), is(instanceOf(AuthokException.class)));
        assertThat(exception.getCause().getMessage(), is("Failed to execute request"));
        assertThat(exception.getCause().getCause(), is(instanceOf(IOException.class)));
        assertThat(exception.getCause().getCause().getMessage(), is("Error!"));
    }

    @Test
    public void asyncCompletesWithExceptionWhenResponseParsingFails() throws Exception {
        doReturn(call).when(client).newCall(any());

        doAnswer(invocation -> {
            ((Callback) invocation.getArgument(0)).onResponse(call, response);
            return null;
        }).when(call).enqueue(any());

        CompletableFuture<?> request = new MockBaseRequest<String>(client) {
            @Override
            protected String parseResponse(Response response) throws AuthokException {
                throw new AuthokException("Response Parsing Error");
            }
        }.executeAsync();

        Exception exception = null;
        Object result = null;

        try {
            result = request.get();
        } catch (Exception e) {
            exception = e;
        }

        assertThat(result, is(nullValue()));
        assertThat(exception, is(notNullValue()));
        assertThat(exception.getCause(), is(instanceOf(AuthokException.class)));
        assertThat(exception.getCause().getMessage(), is("Response Parsing Error"));
    }

    @Test
    public void asyncCompletesSuccessfully() {
        doReturn(call).when(client).newCall(any());

        doAnswer(invocation -> {
            ((Callback) invocation.getArgument(0)).onResponse(call, response);
            return null;
        }).when(call).enqueue(any());

        CompletableFuture<?> request = new MockBaseRequest<String>(client) {
            @Override
            protected String parseResponse(Response response) throws AuthokException {
                return "Success";
            }
        }.executeAsync();

        Exception exception = null;
        Object result = null;

        try {
            result = request.get();
        } catch (Exception e) {
            exception = e;
        }

        assertThat(exception, is(nullValue()));
        assertThat(result, is(instanceOf(String.class)));
        assertThat(result, is("Success"));
    }

    private abstract static class MockBaseRequest<String> extends BaseRequest {
        MockBaseRequest(OkHttpClient client) {
            super(client);
        }

        @Override
        protected Request createRequest() throws AuthokException {
            return null;
        }
    }

}
