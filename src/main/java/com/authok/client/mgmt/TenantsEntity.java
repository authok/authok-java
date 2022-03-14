package com.authok.client.mgmt;

import com.authok.client.mgmt.filter.FieldsFilter;
import com.authok.json.mgmt.tenants.Tenant;
import com.authok.net.CustomRequest;
import com.authok.net.Request;
import com.authok.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import java.util.Map;

/**
 * 管理API: 租户设置 https://docs.authok.cn/api/management/v1#!/Tenants
 * <p>
 * 不是线程安全的.
 *
 * @see ManagementAPI
 */
@SuppressWarnings("WeakerAccess")
public class TenantsEntity extends BaseManagementEntity {

    TenantsEntity(OkHttpClient client, HttpUrl baseUrl, String apiToken) {
        super(client, baseUrl, apiToken);
    }

    /**
     * 请求租户设置. 需要包含 read:tenant_settings 作用域的令牌.
     * 参考 https://docs.authok.cn/api/management/v1#!/Tenants/get_settings
     *
     * @param filter 过滤条件. 可为空.
     * @return 待执行的请求.
     */
    public Request<Tenant> get(FieldsFilter filter) {
        HttpUrl.Builder builder = baseUrl
                .newBuilder()
                .addPathSegments("api/v1/tenants/settings");
        if (filter != null) {
            for (Map.Entry<String, Object> e : filter.getAsMap().entrySet()) {
                builder.addQueryParameter(e.getKey(), String.valueOf(e.getValue()));
            }
        }
        String url = builder.build().toString();
        CustomRequest<Tenant> request = new CustomRequest<>(client, url, "GET", new TypeReference<Tenant>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * 更新租户设置. 需要有 update:tenant_settings 作用域的令牌.
     * 参考 https://docs.authok.cn/api/management/v1#!/Tenants/patch_settings
     *
     * @param tenant 待更新的租户数据.
     * @return 返回可执行的请求.
     */
    public Request<Tenant> update(Tenant tenant) {
        Asserts.assertNotNull(tenant, "tenant");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v1/tenants/settings")
                .build()
                .toString();

        CustomRequest<Tenant> request = new CustomRequest<>(client, url, "PATCH", new TypeReference<Tenant>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        request.setBody(tenant);
        return request;
    }
}
