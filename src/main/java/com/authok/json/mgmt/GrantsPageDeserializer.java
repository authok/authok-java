package com.authok.json.mgmt;

import com.authok.client.mgmt.GrantsEntity;

import java.util.List;

/**
 * Parses a given paged response into their {@link GrantsPage} representation.
 * <p>
 * This class is thread-safe.
 *
 * @see PageDeserializer
 * @see GrantsEntity
 */
@SuppressWarnings({"unused", "WeakerAccess"})
class GrantsPageDeserializer extends PageDeserializer<GrantsPage, Grant> {

    GrantsPageDeserializer() {
        super(Grant.class, "grants");
    }

    @Override
    protected GrantsPage createPage(List<Grant> items) {
        return new GrantsPage(items);
    }

    @Override
    protected GrantsPage createPage(Integer start, Integer length, Integer total, Integer limit, List<Grant> items) {
        return new GrantsPage(start, length, total, limit, items);
    }

}
