package com.authok.net;

import com.authok.exception.AuthokException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public abstract class BaseRequest<T> implements Request<T> {

    private final OkHttpClient client;

    BaseRequest(OkHttpClient client) {
        this.client = client;
    }

    protected abstract okhttp3.Request createRequest() throws AuthokException;

    protected abstract T parseResponse(Response response) throws AuthokException;

    /**
     * Executes this request.
     *
     * @return the response body JSON decoded as T
     * @throws AuthokException if the request execution fails.
     */
    @Override
    public T execute() throws AuthokException {
        okhttp3.Request request = createRequest();
        try (Response response = client.newCall(request).execute()) {
            return parseResponse(response);
        } catch (AuthokException e) {
            throw e;
        } catch (IOException e) {
            throw new AuthokException("Failed to execute request", e);
        }
    }

    @Override
    public CompletableFuture<T> executeAsync() {
        final CompletableFuture<T> future = new CompletableFuture<T>();

        okhttp3.Request request;
        try {
            request = createRequest();
        } catch (AuthokException e) {
            future.completeExceptionally(e);
            return future;
        }

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                future.completeExceptionally(new AuthokException("Failed to execute request", e));
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    T parsedResponse = parseResponse(response);
                    future.complete(parsedResponse);
                } catch (AuthokException e) {
                    future.completeExceptionally(e);
                }
            }
        });

        return future;
    }
}
