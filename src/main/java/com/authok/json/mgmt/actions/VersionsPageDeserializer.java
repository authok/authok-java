package com.authok.json.mgmt.actions;

import com.authok.json.mgmt.PageDeserializer;

import java.util.List;

/**
 * Parses a paged response into its {@linkplain VersionsPage} representation.
 */
public class VersionsPageDeserializer extends PageDeserializer<VersionsPage, Version> {

    protected VersionsPageDeserializer() {
        super(Version.class, "items");
    }

    @Override
    protected VersionsPage createPage(List<Version> items) {
        return new VersionsPage(items);
    }

    @Override
    protected VersionsPage createPage(Integer start, Integer length, Integer total, Integer limit, List<Version> items) {
        return new VersionsPage(start, length, total, limit, items);
    }
}
