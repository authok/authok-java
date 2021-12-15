package com.authok.json.mgmt.users;

import com.authok.json.mgmt.Page;
import com.authok.client.mgmt.UsersEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

/**
 * Class that represents a given Page of Users. Related to the {@link UsersEntity} entity.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = UsersPageDeserializer.class)
public class UsersPage extends Page<User> {

    public UsersPage(List<User> items) {
        super(items);
    }

    /**
     * @deprecated Use {@linkplain UsersPage#UsersPage(Integer, Integer, Integer, Integer, String, List)} instead.
     */
    @Deprecated
    public UsersPage(Integer start, Integer length, Integer total, Integer limit, List<User> items) {
        super(start, length, total, limit, items);
    }

    public UsersPage(Integer start, Integer length, Integer total, Integer limit, String next, List<User> items) {
        super(start, length, total, limit, next, items);
    }
}
