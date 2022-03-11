package com.authok.json.mgmt;

import com.authok.client.mgmt.ClientGrantsEntity;

import java.util.List;

/**
 * Parses a given paged response into their {@link ClientGrantsPage} representation.
 * <p>
 * This class is thread-safe.
 *
 * @see PageDeserializer
 * @see ClientGrantsEntity
 */
@SuppressWarnings({"unused", "WeakerAccess"})
class ClientGrantsPageDeserializer extends PageDeserializer<ClientGrantsPage, ClientGrant> {

    ClientGrantsPageDeserializer() {
        super(ClientGrant.class, "items");
    }

    @Override
    protected ClientGrantsPage createPage(List<ClientGrant> items) {
        return new ClientGrantsPage(items);
    }

    @Override
    protected ClientGrantsPage createPage(Integer start, Integer length, Integer total, Integer limit, List<ClientGrant> items) {
        return new ClientGrantsPage(start, length, total, limit, items);
    }

}
