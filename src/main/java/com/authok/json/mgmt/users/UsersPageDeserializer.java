package com.authok.json.mgmt.users;

import com.authok.json.mgmt.PageDeserializer;
import com.authok.client.mgmt.UsersEntity;

import java.util.List;

/**
 * Parses a given paged response into their {@link UsersPage} representation.
 * <p>
 * This class is thread-safe.
 *
 * @see PageDeserializer
 * @see UsersEntity
 */
@SuppressWarnings({"unused", "WeakerAccess"})
class UsersPageDeserializer extends PageDeserializer<UsersPage, User> {

    UsersPageDeserializer() {
        super(User.class, "items");
    }

    @Override
    protected UsersPage createPage(List<User> items) {
        return new UsersPage(items);
    }

    @Override
    @SuppressWarnings("deprecation")
    protected UsersPage createPage(Integer start, Integer length, Integer total, Integer limit, List<User> items) {
        return new UsersPage(start, length, total, limit, items);
    }

    @Override
    protected UsersPage createPage(Integer start, Integer length, Integer total, Integer limit, String next, List<User> items) {
        return new UsersPage(start, length, total, limit, next, items);
    }
}
