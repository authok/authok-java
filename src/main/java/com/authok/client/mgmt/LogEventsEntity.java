package com.authok.client.mgmt;

import com.authok.client.mgmt.filter.LogEventFilter;
import com.authok.json.mgmt.logevents.LogEvent;
import com.authok.json.mgmt.logevents.LogEventsPage;
import com.authok.net.CustomRequest;
import com.authok.net.Request;
import com.authok.utils.Asserts;
import com.authok.client.mgmt.filter.QueryFilter;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import java.util.Map;

/**
 * Class that provides an implementation of the Events methods of the Management API as defined in https://authok.cn/docs/api/management/v2#!/Logs
 * <p>
 * This class is not thread-safe.
 *
 * @see ManagementAPI
 */
@SuppressWarnings("WeakerAccess")
public class LogEventsEntity extends BaseManagementEntity {

    LogEventsEntity(OkHttpClient client, HttpUrl baseUrl, String apiToken) {
        super(client, baseUrl, apiToken);
    }

    /**
     * Request all the Log Events. A token with scope read:logs is needed.
     * See https://authok.cn/docs/api/management/v2#!/Logs/get_logs
     *
     * @param filter the filter to use. Can be null.
     * @return a Request to execute.
     */
    public Request<LogEventsPage> list(LogEventFilter filter) {
        HttpUrl.Builder builder = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/logs");
        if (filter != null) {
            for (Map.Entry<String, Object> e : filter.getAsMap().entrySet()) {
                if (QueryFilter.KEY_QUERY.equals(e.getKey())) {
                    builder.addEncodedQueryParameter(e.getKey(), String.valueOf(e.getValue()));
                } else {
                    builder.addQueryParameter(e.getKey(), String.valueOf(e.getValue()));
                }
            }
        }
        String url = builder.build().toString();
        CustomRequest<LogEventsPage> request = new CustomRequest<>(client, url, "GET", new TypeReference<LogEventsPage>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Request a Log Event. A token with scope read:logs is needed.
     * See https://authok.cn/docs/api/management/v2#!/Logs/get_logs_by_id
     *
     * @param logEventId the id of the connection to retrieve.
     * @return a Request to execute.
     */
    public Request<LogEvent> get(String logEventId) {
        Asserts.assertNotNull(logEventId, "log event id");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/logs")
                .addPathSegment(logEventId)
                .build()
                .toString();
        CustomRequest<LogEvent> request = new CustomRequest<>(client, url, "GET", new TypeReference<LogEvent>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }
}
