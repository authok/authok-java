package com.authok.client.mgmt.filter;

import com.authok.client.mgmt.RulesEntity;

/**
 * Class used to filter the results received when calling the Rules endpoint. Related to the {@link RulesEntity} entity.
 * <p>
 * This class is not thread-safe.
 *
 * @see BaseFilter
 */
public class RulesFilter extends FieldsFilter {

    /**
     * Filter by enabled value
     *
     * @param enabled only retrieve items that are enabled or disabled.
     * @return this filter instance.
     */
    public RulesFilter withEnabled(boolean enabled) {
        parameters.put("enabled", enabled);
        return this;
    }

    @Override
    public RulesFilter withFields(String fields, boolean includeFields) {
        super.withFields(fields, includeFields);
        return this;
    }

    /**
     * Include the query summary
     * Warning: Can only be used with {@link RulesEntity#listAll(RulesFilter)}
     *
     * @param includeTotals whether to include or not the query summary.
     * @return this filter instance
     */
    public RulesFilter withTotals(boolean includeTotals) {
        parameters.put("include_totals", includeTotals);
        return this;
    }

    /**
     * Filter by page
     *
     * @param pageNumber    the page number to retrieve.
     * @param amountPerPage the amount of items per page to retrieve.
     * @return this filter instance
     */
    public RulesFilter withPage(int pageNumber, int amountPerPage) {
        parameters.put("page", pageNumber);
        parameters.put("per_page", amountPerPage);
        return this;
    }

}
