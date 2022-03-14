package com.authok.client.mgmt.filter;

import com.authok.client.mgmt.GrantsEntity;

/**
 * Class used to filter the results received when calling the Grants endpoint. Related to the {@link GrantsEntity} entity.
 * <p>
 * This class is not thread-safe.
 *
 * @see BaseFilter
 */
public class GrantsFilter extends BaseFilter {

    /**
     * Filter by client id
     *
     * @param clientId only retrieve items with this client id.
     * @return this filter instance
     */
    public GrantsFilter withClientId(String clientId) {
        parameters.put("client_id", clientId);
        return this;
    }

    /**
     * Filter by audience
     *
     * @param audience only retrieve the item with this audience.
     * @return this filter instance
     */
    public GrantsFilter withAudience(String audience) {
        parameters.put("audience", audience);
        return this;
    }

    /**
     * Filter by page
     *
     * @param pageNumber    the page number to retrieve.
     * @param amountPerPage the amount of items per page to retrieve.
     * @return this filter instance
     */
    public GrantsFilter withPage(int pageNumber, int amountPerPage) {
        parameters.put("page", pageNumber);
        parameters.put("page_size", amountPerPage);
        return this;
    }

    /**
     * Include the query summary
     *
     * @param includeTotals whether to include or not the query summary.
     * @return this filter instance
     */
    public GrantsFilter withTotals(boolean includeTotals) {
        parameters.put("include_totals", includeTotals);
        return this;
    }

}
