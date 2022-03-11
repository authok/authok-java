package com.authok.client.auth;

/**
 * email免密登录请求.
 */
public enum PasswordlessEmailType {

    /**
     * Send a link.
     */
    LINK("link"),

    /**
     * Send a code.
     */
    CODE("code");

    private final String type;

    PasswordlessEmailType(String type) {
        this.type = type;
    }

    /**
     * Gets the type of Passwordless email request.
     *
     * @return the type of Passwordless email request.
     */
    public String getType() {
        return type;
    }
}
