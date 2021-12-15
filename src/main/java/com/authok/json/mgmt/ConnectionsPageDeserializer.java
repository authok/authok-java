package com.authok.json.mgmt;

import com.authok.client.mgmt.ConnectionsEntity;

import java.util.List;

/**
 * Parses a given paged response into their {@link ConnectionsPage} representation.
 * <p>
 * This class is thread-safe.
 *
 * @see PageDeserializer
 * @see ConnectionsEntity
 */
@SuppressWarnings({"unused", "WeakerAccess"})
class ConnectionsPageDeserializer extends PageDeserializer<ConnectionsPage, Connection> {

    ConnectionsPageDeserializer() {
        super(Connection.class, "connections");
    }

    @Override
    protected ConnectionsPage createPage(List<Connection> items) {
        return new ConnectionsPage(items);
    }

    @Override
    protected ConnectionsPage createPage(Integer start, Integer length, Integer total, Integer limit, List<Connection> items) {
        return new ConnectionsPage(start, length, total, limit, items);
    }

}
