package com.authok.net;

import com.authok.json.auth.TokenHolder;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.OkHttpClient;

public class TokenRequest extends CustomRequest<TokenHolder> implements AuthRequest {

    public TokenRequest(OkHttpClient client, String url) {
        super(client, url, "POST", new TypeReference<TokenHolder>() {
        });
    }

    @Override
    public TokenRequest setRealm(String realm) {
        super.addParameter("realm", realm);
        return this;
    }

    @Override
    public TokenRequest setAudience(String audience) {
        super.addParameter("audience", audience);
        return this;
    }

    @Override
    public TokenRequest setScope(String scope) {
        super.addParameter("scope", scope);
        return this;
    }
}
