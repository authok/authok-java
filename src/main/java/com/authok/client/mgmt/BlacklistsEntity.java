package com.authok.client.mgmt;

import com.authok.json.mgmt.Token;
import com.authok.net.CustomRequest;
import com.authok.net.Request;
import com.authok.net.VoidRequest;
import com.authok.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import java.util.List;

/**
 * Class that provides an implementation of the Blacklists methods of the Management API as defined in https://authok.cn/docs/api/management/v1#!/Blacklists
 * <p>
 * This class is not thread-safe.
 *
 * @see ManagementAPI
 */
@SuppressWarnings("WeakerAccess")
public class BlacklistsEntity extends BaseManagementEntity {

    BlacklistsEntity(OkHttpClient client, HttpUrl baseUrl, String apiToken) {
        super(client, baseUrl, apiToken);
    }

    /**
     * Request all the Blacklisted Tokens with a given audience. A token with scope blacklist:tokens is needed.
     * See https://authok.cn/docs/api/management/v1#!/Blacklists/get_tokens.
     *
     * @param audience the token audience (aud).
     * @return a Request to execute.
     */
    public Request<List<Token>> getBlacklist(String audience) {
        Asserts.assertNotNull(audience, "audience");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v1/blacklists/tokens")
                .addQueryParameter("aud", audience)
                .build()
                .toString();
        CustomRequest<List<Token>> request = new CustomRequest<>(client, url, "GET", new TypeReference<List<Token>>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Add a Token to the Blacklist. A token with scope blacklist:tokens is needed.
     * See https://authok.cn/docs/api/management/v1#!/Blacklists/post_tokens.
     *
     * @param token the token to blacklist.
     * @return a Request to execute.
     */
    public Request blacklistToken(Token token) {
        Asserts.assertNotNull(token, "token");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v1/blacklists/tokens")
                .build()
                .toString();
        VoidRequest request = new VoidRequest(client, url, "POST");
        request.addHeader("Authorization", "Bearer " + apiToken);
        request.setBody(token);
        return request;
    }
}
