package com.authok.client.mgmt.filter;

import com.authok.client.mgmt.JobsEntity;

/**
 * Class used to change the behavior of a Users Imports Job. Related to the {@link JobsEntity} entity.
 * <p>
 * This class is not thread-safe.
 *
 * @see BaseFilter
 */
@SuppressWarnings("UnusedReturnValue")
public class UsersImportOptions extends BaseFilter {

    /**
     * When set to true, sends a completion email to all tenant owners when the import job is finished.
     * If you do not want emails sent, you must explicitly set this parameter to false.
     *
     * @param sendCompletionEmail whether to send a completion email to all tenant owners when the import
     *                            job is finished. Defaults to true.
     * @return this filter instance
     */
    public UsersImportOptions withSendCompletionEmail(boolean sendCompletionEmail) {
        parameters.put("send_completion_email", sendCompletionEmail);
        return this;
    }

    /**
     * Sets an optional user-defined string that can be used to correlate multiple jobs.
     *
     * @param externalId the external ID to use.
     * @return this filter instance
     */
    public UsersImportOptions withExternalId(String externalId) {
        parameters.put("external_id", externalId);
        return this;
    }

    /**
     * When set to false, pre-existing users that match on email address, user ID, or username will fail.
     * When set to true, pre-existing users that match on any of these fields will be updated, but only
     * with upsertable attributes. For a list of user profile fields that can be upserted during import,
     * see the following article.
     * <p>
     * https://authok.cn/docs/users/references/user-profile-structure#user-profile-attributes
     *
     * @param upsert Whether to update users if they already exist (true) or to ignore them (false). Defaults to false.
     * @return this filter instance
     */
    public UsersImportOptions withUpsert(boolean upsert) {
        parameters.put("upsert", upsert);
        return this;
    }

}
