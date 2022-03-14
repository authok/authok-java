package com.authok.client.mgmt.filter;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Class used to filter the results received when calling endpoints that accept a Query.
 * <p>
 * This class is not thread-safe.
 *
 * @see BaseFilter
 */
public class QueryFilter extends FieldsFilter {

    public static final String KEY_QUERY = "q";

    /**
     * Filter by a query
     *
     * @param query the query expression to use
     * @return this filter instance
     */
    public QueryFilter withQuery(String query) {
        try {
            String encodedQuery = urlEncode(query);
            parameters.put(KEY_QUERY, encodedQuery);
            return this;
        } catch (UnsupportedEncodingException ex) {
            //"Every implementation of the Java platform is required to support the following standard charsets [...]: UTF-8"
            //cf. https://docs.oracle.com/javase/7/docs/api/java/nio/charset/Charset.html
            throw new IllegalStateException("UTF-8 encoding not supported by current Java platform implementation.", ex);
        }
    }

    /**
     * Include the query summary
     *
     * @param includeTotals whether to include or not the query summary.
     * @return this filter instance
     */
    public QueryFilter withTotals(boolean includeTotals) {
        parameters.put("include_totals", includeTotals);
        return this;
    }

    /**
     * Sort the query
     *
     * @param sort the field to use for sorting. Use 'field:order' where order is 1 for ascending and -1 for descending.
     * @return this filter instance
     */
    public QueryFilter withSort(String sort) {
        parameters.put("sort", sort);
        return this;
    }

    /**
     * Filter by page
     *
     * @param pageNumber    the page number to retrieve.
     * @param amountPerPage the amount of items per page to retrieve.
     * @return this filter instance
     */
    public QueryFilter withPage(int pageNumber, int amountPerPage) {
        parameters.put("page", pageNumber);
        parameters.put("page_size", amountPerPage);
        return this;
    }

    @Override
    public QueryFilter withFields(String fields, boolean includeFields) {
        super.withFields(fields, includeFields);
        return this;
    }

    //Visible for testing
    String urlEncode(String query) throws UnsupportedEncodingException {
        return URLEncoder.encode(query, "UTF-8");
    }

}
