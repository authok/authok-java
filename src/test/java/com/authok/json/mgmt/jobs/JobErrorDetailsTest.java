package com.authok.json.mgmt.jobs;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import com.authok.json.JsonTest;
import com.authok.json.mgmt.users.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Collections;

import org.hamcrest.MatcherAssert;
import org.junit.Test;

public class JobErrorDetailsTest extends JsonTest<JobErrorDetails> {

    String json = "{\"user\":{\"email\":\"john.doe@gmail.com\"},\"errors\":[{\"code\":\"code\",\"message\":\"message\",\"path\":\"path\"}]}";

    @Test
    public void shouldDeserialize() throws Exception {
        JobErrorDetails errorDetails = fromJSON(json, JobErrorDetails.class);

        assertThat(errorDetails, is(notNullValue()));
        assertThat(errorDetails.getUser(), is(notNullValue()));
        MatcherAssert.assertThat(errorDetails.getUser().getEmail(), is(equalTo("john.doe@gmail.com")));
        assertThat(errorDetails.getErrors(), hasSize(1));

        assertThat(errorDetails.getErrors().get(0).getCode(), is(equalTo("code")));
        assertThat(errorDetails.getErrors().get(0).getMessage(), is(equalTo("message")));
        assertThat(errorDetails.getErrors().get(0).getPath(), is(equalTo("path")));
    }

    @Test
    public void shouldSerialize() throws JsonProcessingException {
        User user = new User();
        user.setEmail("john.doe@gmail.com");
        JobError error = new JobError("code", "message", "path");
        JobErrorDetails errorDetails = new JobErrorDetails(user, Collections.singletonList(error));
        String serialized = toJSON(errorDetails);

        assertThat(serialized, is(equalTo(json)));
    }
}
