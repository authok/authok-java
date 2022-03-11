package com.authok.json.mgmt;

import com.authok.client.mgmt.RolesEntity;

import java.util.List;

/**
 * Parses a given paged response into their {@link RolesPage} representation.
 * <p>
 * This class is thread-safe.
 *
 * @see PageDeserializer
 * @see RolesEntity
 */
@SuppressWarnings({"unused", "WeakerAccess"})
class RolesPageDeserializer extends PageDeserializer<RolesPage, Role> {

    RolesPageDeserializer() {
        super(Role.class, "items");
    }

    @Override
    protected RolesPage createPage(List<Role> items) {
        return new RolesPage(items);
    }

    @Override
    protected RolesPage createPage(Integer start, Integer length, Integer total, Integer limit, List<Role> items) {
        return new RolesPage(start, length, total, limit, items);
    }

}
