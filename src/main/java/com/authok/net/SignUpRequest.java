package com.authok.net;

import com.authok.json.auth.CreatedUser;

import java.util.Map;

/**
 * Class that represents a Create User call.
 */
@SuppressWarnings("UnusedReturnValue")
public interface SignUpRequest extends Request<CreatedUser> {

    /**
     * Setter for the additional fields to set when creating a user.
     *
     * @param customFields the list of custom fields.
     * @return this request instance.
     */
    SignUpRequest setCustomFields(Map<String, String> customFields);
}
