package com.authok.json.mgmt.organizations;

import com.authok.json.mgmt.PageDeserializer;

import java.util.List;

/**
 * Parses a paged response into its {@linkplain EnabledConnectionsPage} representation.
 */
public class ConnectionsPageDeserializer  extends PageDeserializer<EnabledConnectionsPage, EnabledConnection> {
    protected ConnectionsPageDeserializer() {
        super(EnabledConnection.class, "items");
    }

    @Override
    protected EnabledConnectionsPage createPage(List<EnabledConnection> items) {
        return new EnabledConnectionsPage(items);
    }

    @Override
    protected EnabledConnectionsPage createPage(Integer start, Integer length, Integer total, Integer limit, List<EnabledConnection> items) {
        return new EnabledConnectionsPage(start, length, total, limit, items);
    }
}
