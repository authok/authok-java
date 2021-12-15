package com.authok.client.mgmt.filter;

import com.authok.client.mgmt.RolesEntity;

/**
 * Class used to filter the results received when calling the Grants endpoint. Related to the {@link RolesEntity} entity.
 * <p>
 * This class is not thread-safe.
 *
 * @see BaseFilter
 */
public class RolesFilter extends PageFilter {

    /**
     * Filter by name
     *
     * @param name only retrieve items with this name.
     * @return this filter instance
     */
    public RolesFilter withName(String name) {
        parameters.put("name_filter", name);
        return this;
    }

    /**
     * Filter by page
     *
     * @param pageNumber    the page number to retrieve.
     * @param amountPerPage the amount of items per page to retrieve.
     * @return this filter instance
     */
    @Override
    public RolesFilter withPage(int pageNumber, int amountPerPage) {
        super.withPage(pageNumber, amountPerPage);
        return this;
    }

    /**
     * Include the query summary
     *
     * @param includeTotals whether to include or not the query summary.
     * @return this filter instance
     */
    @Override
    public RolesFilter withTotals(boolean includeTotals) {
        super.withTotals(includeTotals);
        return this;
    }

}
