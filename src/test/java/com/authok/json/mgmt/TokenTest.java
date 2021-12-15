package com.authok.json.mgmt;

import com.authok.json.JsonMatcher;
import com.authok.json.JsonTest;

import org.hamcrest.MatcherAssert;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class TokenTest extends JsonTest<Token> {
    private static final String json = "{\"jti\":\"id\",\"aud\":\"myapi\"}";

    @Test
    public void shouldSerialize() throws Exception {
        Token token = new Token("id");
        token.setAud("myapi");

        String serialized = toJSON(token);
        assertThat(serialized, is(notNullValue()));
        MatcherAssert.assertThat(serialized, JsonMatcher.hasEntry("jti", "id"));
        assertThat(serialized, JsonMatcher.hasEntry("aud", "myapi"));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        Token token = fromJSON(json, Token.class);

        assertThat(token, is(notNullValue()));
        assertThat(token.getAud(), is("myapi"));
        assertThat(token.getJTI(), is("id"));
    }

}
