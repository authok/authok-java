package com.authok.client.mgmt.filter;

import com.authok.client.mgmt.UsersEntity;

/**
 * Class used to filter the results received when calling the Users endpoint. Related to the {@link UsersEntity} entity.
 * <p>
 * This class is not thread-safe.
 *
 * @see BaseFilter
 */
public class UserFilter extends QueryFilter {

    /**
     * Creates a new instance using the latest search engine version.
     * <p>
     * Since version 1.12.0 this SDK no longer specifies a search engine version by default. If you need to use
     * a version different than the latest one, please set it explicitly by calling {@link #withSearchEngine(String)}.
     * See the latest user search doc: https://authok.cn/docs/users/search/
     */
    public UserFilter() {
    }

    @Override
    public UserFilter withTotals(boolean includeTotals) {
        super.withTotals(includeTotals);
        return this;
    }

    /**
     * Selects which Search Engine version to use when querying for users.
     * <p>
     * See the latest user search doc: https://authok.cn/docs/users/search/
     *
     * @param searchEngineVersion the search engine version to use on queries.
     * @return this filter instance
     */
    public UserFilter withSearchEngine(String searchEngineVersion) {
        parameters.put("search_engine", searchEngineVersion);
        return this;
    }

    /**
     * Filter by a query
     *
     * @param query the query expression to use following the syntax defined at https://authok.cn/docs/users/search/v3/query-syntax
     * @return this filter instance
     */
    @Override
    public UserFilter withQuery(String query) {
        super.withQuery(query);
        return this;
    }

    @Override
    public UserFilter withSort(String sort) {
        super.withSort(sort);
        return this;
    }

    @Override
    public UserFilter withPage(int pageNumber, int amountPerPage) {
        super.withPage(pageNumber, amountPerPage);
        return this;
    }

    @Override
    public UserFilter withFields(String fields, boolean includeFields) {
        super.withFields(fields, includeFields);
        return this;
    }
}
