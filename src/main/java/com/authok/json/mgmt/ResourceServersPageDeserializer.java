package com.authok.json.mgmt;

import com.authok.client.mgmt.ResourceServerEntity;

import java.util.List;

/**
 * Parses a given paged response into their {@link ResourceServersPage} representation.
 * <p>
 * This class is thread-safe.
 *
 * @see PageDeserializer
 * @see ResourceServerEntity
 */
@SuppressWarnings({"unused", "WeakerAccess"})
class ResourceServersPageDeserializer extends PageDeserializer<ResourceServersPage, ResourceServer> {

    ResourceServersPageDeserializer() {
        super(ResourceServer.class, "resource_servers");
    }

    @Override
    protected ResourceServersPage createPage(List<ResourceServer> items) {
        return new ResourceServersPage(items);
    }

    @Override
    protected ResourceServersPage createPage(Integer start, Integer length, Integer total, Integer limit, List<ResourceServer> items) {
        return new ResourceServersPage(start, length, total, limit, items);
    }

}
