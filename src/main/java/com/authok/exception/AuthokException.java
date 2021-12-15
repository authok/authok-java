package com.authok.exception;

import java.io.IOException;

/**
 * Class that represents an error captured when executing an http request to the Authok Server.
 */
@SuppressWarnings("WeakerAccess")
public class AuthokException extends IOException {

    public AuthokException(String message) {
        super(message);
    }

    public AuthokException(String message, Throwable cause) {
        super(message, cause);
    }
}
