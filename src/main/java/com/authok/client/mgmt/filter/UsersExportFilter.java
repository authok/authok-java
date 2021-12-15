package com.authok.client.mgmt.filter;

import com.authok.client.mgmt.JobsEntity;
import com.authok.json.mgmt.jobs.UsersExportField;

import java.util.List;

/**
 * Class used to filter the results received when a Users Exports Job is completed. Related to the {@link JobsEntity} entity.
 * <p>
 * This class is not thread-safe.
 *
 * @see BaseFilter
 */
@SuppressWarnings("UnusedReturnValue")
public class UsersExportFilter extends BaseFilter {

    /**
     * Changes the output format of the exports file
     *
     * @param format the format of the exports file. Typically 'csv' or 'json'.
     * @return this filter instance
     */
    public UsersExportFilter withFormat(String format) {
        parameters.put("format", format);
        return this;
    }

    /**
     * Limits the amount of results
     *
     * @param limit the amount of results to include
     * @return this filter instance
     */
    public UsersExportFilter withLimit(int limit) {
        parameters.put("limit", limit);
        return this;
    }

    /**
     * Selects the fields to include in each user export
     *
     * @param fields the list of fields to include in each user export
     * @return this filter instance
     */
    public UsersExportFilter withFields(List<UsersExportField> fields) {
        parameters.put("fields", fields);
        return this;
    }

}
