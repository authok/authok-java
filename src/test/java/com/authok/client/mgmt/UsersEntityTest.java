package com.authok.client.mgmt;

import static com.authok.client.MockServer.*;
import static com.authok.client.RecordedRequestMatcher.hasHeader;
import static com.authok.client.RecordedRequestMatcher.hasMethodAndPath;
import static com.authok.client.RecordedRequestMatcher.hasQueryParameter;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import com.authok.client.mgmt.filter.FieldsFilter;
import com.authok.client.mgmt.filter.LogEventFilter;
import com.authok.client.mgmt.filter.PageFilter;
import com.authok.client.mgmt.filter.UserFilter;
import com.authok.json.mgmt.Permission;
import com.authok.json.mgmt.PermissionsPage;
import com.authok.json.mgmt.RolesPage;
import com.authok.json.mgmt.guardian.Enrollment;
import com.authok.json.mgmt.logevents.LogEvent;
import com.authok.json.mgmt.logevents.LogEventsPage;
import com.authok.json.mgmt.organizations.OrganizationsPage;
import com.authok.json.mgmt.users.Identity;
import com.authok.json.mgmt.users.RecoveryCode;
import com.authok.json.mgmt.users.User;
import com.authok.json.mgmt.users.UsersPage;
import com.authok.net.Request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import okhttp3.mockwebserver.RecordedRequest;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

public class UsersEntityTest extends BaseMgmtEntityTest {

    @Test
    public void shouldListUsersByEmail() throws Exception {
        Request<List<User>> request = api.users().listByEmail("johndoe@authok.cn", null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USERS_LIST, 200);
        List<User> response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/users-by-email"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("email", "johndoe@authok.cn"));

        assertThat(response, is(notNullValue()));
        assertThat(response, hasSize(2));
    }

    @Test
    public void shouldListUsersByEmailWithFields() throws Exception {
        FieldsFilter filter = new FieldsFilter().withFields("some,random,fields", true);
        Request<List<User>> request = api.users().listByEmail("johndoe@authok.cn", filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USERS_LIST, 200);
        List<User> response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/users-by-email"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("email", "johndoe@authok.cn"));
        assertThat(recordedRequest, hasQueryParameter("fields", "some,random,fields"));
        assertThat(recordedRequest, hasQueryParameter("include_fields", "true"));

        assertThat(response, is(notNullValue()));
        assertThat(response, hasSize(2));
    }

    @Test
    public void shouldReturnEmptyUsersByEmail() throws Exception {
        Request<List<User>> request = api.users().listByEmail("missing@authok.cn", null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_EMPTY_LIST, 200);
        List<User> response = request.execute();

        assertThat(response, is(notNullValue()));
        assertThat(response, is(emptyCollectionOf(User.class)));
    }

    @Test
    public void shouldListUsers() throws Exception {
        Request<UsersPage> request = api.users().list(null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USERS_LIST, 200);
        UsersPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/users"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        MatcherAssert.assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldListUsersWithPage() throws Exception {
        UserFilter filter = new UserFilter().withPage(23, 5);
        Request<UsersPage> request = api.users().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USERS_LIST, 200);
        UsersPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/users"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("page", "23"));
        assertThat(recordedRequest, hasQueryParameter("per_page", "5"));

        assertThat(response, is(notNullValue()));
        MatcherAssert.assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldListUsersWithTotals() throws Exception {
        UserFilter filter = new UserFilter().withTotals(true);
        Request<UsersPage> request = api.users().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USERS_PAGED_LIST, 200);
        UsersPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/users"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("include_totals", "true"));

        assertThat(response, is(notNullValue()));
        MatcherAssert.assertThat(response.getItems(), hasSize(2));
        MatcherAssert.assertThat(response.getStart(), is(0));
        MatcherAssert.assertThat(response.getLength(), is(14));
        MatcherAssert.assertThat(response.getTotal(), is(14));
        MatcherAssert.assertThat(response.getLimit(), is(50));
    }

    @Test
    public void shouldListUsersWithSort() throws Exception {
        UserFilter filter = new UserFilter().withSort("date:1");
        Request<UsersPage> request = api.users().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USERS_LIST, 200);
        UsersPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/users"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("sort", "date:1"));

        assertThat(response, is(notNullValue()));
        MatcherAssert.assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldListUsersWithQuery() throws Exception {
        UserFilter filter = new UserFilter().withQuery("email:\\*@gmail.com");
        Request<UsersPage> request = api.users().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USERS_LIST, 200);
        UsersPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/users"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, not(hasQueryParameter("search_engine")));
        assertThat(recordedRequest, hasQueryParameter("q", "email:\\*@gmail.com"));

        assertThat(response, is(notNullValue()));
        MatcherAssert.assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldListUsersWithFields() throws Exception {
        UserFilter filter = new UserFilter().withFields("some,random,fields", true);
        Request<UsersPage> request = api.users().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USERS_LIST, 200);
        UsersPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/users"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("fields", "some,random,fields"));
        assertThat(recordedRequest, hasQueryParameter("include_fields", "true"));

        assertThat(response, is(notNullValue()));
        MatcherAssert.assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldReturnEmptyUsers() throws Exception {
        Request<UsersPage> request = api.users().list(null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_EMPTY_LIST, 200);
        UsersPage response = request.execute();

        assertThat(response, is(notNullValue()));
        MatcherAssert.assertThat(response.getItems(), is(emptyCollectionOf(User.class)));
    }

    @Test
    public void shouldThrowOnGetUserWithNullId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'user id' cannot be null!");
        api.users().get(null, null);
    }

    @Test
    public void shouldGetUser() throws Exception {
        Request<User> request = api.users().get("1", null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USER, 200);
        User response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/users/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldGetUserWithFields() throws Exception {
        UserFilter filter = new UserFilter().withFields("some,random,fields", true);
        Request<User> request = api.users().get("1", filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USER, 200);
        User response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/users/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("fields", "some,random,fields"));
        assertThat(recordedRequest, hasQueryParameter("include_fields", "true"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnCreateUserWithNullData() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'user' cannot be null!");
        api.users().create(null);
    }

    @Test
    public void shouldCreateUser() throws Exception {
        Request<User> request = api.users().create(new User("authok"));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USER, 200);
        User response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("POST", "/api/v2/users"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasEntry("connection", "authok"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnDeleteUserWithNullId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'user id' cannot be null!");
        api.users().delete(null);
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        Request<Void> request = api.users().delete("1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USER, 200);
        request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("DELETE", "/api/v2/users/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }

    @Test
    public void shouldThrowOnUpdateUserWithNullId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'user id' cannot be null!");
        api.users().update(null, new User("authok"));
    }

    @Test
    public void shouldThrowOnUpdateUserWithNullData() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'user' cannot be null!");
        api.users().update("1", null);
    }

    @Test
    public void shouldUpdateUser() throws Exception {
        Request<User> request = api.users().update("1", new User("authok"));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USER, 200);
        User response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("PATCH", "/api/v2/users/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasEntry("connection", "authok"));

        assertThat(response, is(notNullValue()));
    }


    @Test
    public void shouldThrowOnGetUserGuardianEnrollmentsWithNullId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'user id' cannot be null!");
        api.users().getEnrollments(null);
    }

    @Test
    public void shouldGetUserGuardianEnrollments() throws Exception {
        Request<List<Enrollment>> request = api.users().getEnrollments("1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_GUARDIAN_ENROLLMENTS_LIST, 200);
        List<Enrollment> response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/users/1/enrollments"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldReturnEmptyUserGuardianEnrollments() throws Exception {
        Request<List<Enrollment>> request = api.users().getEnrollments("1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_EMPTY_LIST, 200);
        List<Enrollment> response = request.execute();

        assertThat(response, is(notNullValue()));
        assertThat(response, is(emptyCollectionOf(Enrollment.class)));
    }

    @Test
    public void shouldThrowOnGetUserLogEventsWithNullId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'user id' cannot be null!");
        api.users().getLogEvents(null, null);
    }

    @Test
    public void shouldGetUserLogEvents() throws Exception {
        Request<LogEventsPage> request = api.users().getLogEvents("1", null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_LOG_EVENTS_LIST, 200);
        LogEventsPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/users/1/logs"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldGetUserLogEventsWithPage() throws Exception {
        LogEventFilter filter = new LogEventFilter().withPage(23, 5);
        Request<LogEventsPage> request = api.users().getLogEvents("1", filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_LOG_EVENTS_LIST, 200);
        LogEventsPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/users/1/logs"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("page", "23"));
        assertThat(recordedRequest, hasQueryParameter("per_page", "5"));

        assertThat(response, is(notNullValue()));
        MatcherAssert.assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldGetUserLogEventsWithTotals() throws Exception {
        LogEventFilter filter = new LogEventFilter().withTotals(true);
        Request<LogEventsPage> request = api.users().getLogEvents("1", filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_LOG_EVENTS_PAGED_LIST, 200);
        LogEventsPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/users/1/logs"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("include_totals", "true"));

        assertThat(response, is(notNullValue()));
        MatcherAssert.assertThat(response.getItems(), hasSize(2));
        MatcherAssert.assertThat(response.getStart(), is(0));
        MatcherAssert.assertThat(response.getLength(), is(14));
        MatcherAssert.assertThat(response.getTotal(), is(14));
        MatcherAssert.assertThat(response.getLimit(), is(50));
    }

    @Test
    public void shouldGetUserLogEventsWithSort() throws Exception {
        LogEventFilter filter = new LogEventFilter().withSort("date:1");
        Request<LogEventsPage> request = api.users().getLogEvents("1", filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_LOG_EVENTS_LIST, 200);
        LogEventsPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/users/1/logs"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("sort", "date:1"));

        assertThat(response, is(notNullValue()));
        MatcherAssert.assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldGetUserLogEventsWithFields() throws Exception {
        LogEventFilter filter = new LogEventFilter().withFields("some,random,fields", true);
        Request<LogEventsPage> request = api.users().getLogEvents("1", filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_LOG_EVENTS_LIST, 200);
        LogEventsPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/users/1/logs"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("fields", "some,random,fields"));
        assertThat(recordedRequest, hasQueryParameter("include_fields", "true"));

        assertThat(response, is(notNullValue()));
        MatcherAssert.assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldReturnEmptyUserLogEvents() throws Exception {
        Request<LogEventsPage> request = api.users().getLogEvents("1", null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_EMPTY_LIST, 200);
        LogEventsPage response = request.execute();

        assertThat(response, is(notNullValue()));
        MatcherAssert.assertThat(response.getItems(), Matchers.is(emptyCollectionOf(LogEvent.class)));
    }

    @Test
    public void shouldThrowOnDeleteUserMultifactorProviderWithNullId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'user id' cannot be null!");
        api.users().deleteMultifactorProvider(null, "duo");
    }

    @Test
    public void shouldThrowOnDeleteUserMultifactorProviderWithNullProvider() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'provider' cannot be null!");
        api.users().deleteMultifactorProvider("1", null);
    }

    @Test
    public void shouldDeleteUserMultifactorProvider() throws Exception {
        Request<Void> request = api.users().deleteMultifactorProvider("1", "duo");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_EMAIL_PROVIDER, 200);
        request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("DELETE", "/api/v2/users/1/multifactor/duo"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }

    @Test
    public void shouldThrowOnRotateUserRecoveryCodeWithNullId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'user id' cannot be null!");
        api.users().rotateRecoveryCode(null);
    }

    @Test
    public void shouldRotateUserRecoveryCode() throws Exception {
        Request<RecoveryCode> request = api.users().rotateRecoveryCode("1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_RECOVERY_CODE, 200);
        RecoveryCode response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("POST", "/api/v2/users/1/recovery-code-regeneration"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnLinkUserIdentityWithNullPrimaryId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'primary user id' cannot be null!");
        api.users().linkIdentity(null, "2", "authok", null);
    }

    @Test
    public void shouldThrowOnLinkUserIdentityWithNullSecondaryId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'secondary user id' cannot be null!");
        api.users().linkIdentity("1", null, "authok", null);
    }

    @Test
    public void shouldThrowOnLinkUserIdentityWithNullSecondaryToken() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'secondary id token' cannot be null!");
        api.users().linkIdentity("1", null);
    }

    @Test
    public void shouldThrowOnLinkUserIdentityWithNullUserId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'primary user id' cannot be null!");
        api.users().linkIdentity(null, "2");
    }

    @Test
    public void shouldThrowOnLinkUserIdentityWithNullProvider() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'provider' cannot be null!");
        api.users().linkIdentity("1", "2", null, null);
    }

    @Test
    public void shouldLinkUserIdentity() throws Exception {
        Request<List<Identity>> request = api.users().linkIdentity("1", "2", "authok", null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_IDENTITIES_LIST, 200);
        List<Identity> response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("POST", "/api/v2/users/1/identities"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(2));
        assertThat(body, hasEntry("provider", "authok"));
        assertThat(body, hasEntry("user_id", "2"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldLinkUserIdentityBySecondaryIdToken() throws Exception {
        Request<List<Identity>> request = api.users().linkIdentity("1", "2");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_IDENTITIES_LIST, 200);
        List<Identity> response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("POST", "/api/v2/users/1/identities"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasEntry("link_with", "2"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldLinkUserIdentityWithConnectionId() throws Exception {
        Request<List<Identity>> request = api.users().linkIdentity("1", "2", "authok", "123456790");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_IDENTITIES_LIST, 200);
        List<Identity> response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("POST", "/api/v2/users/1/identities"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(3));
        assertThat(body, hasEntry("provider", "authok"));
        assertThat(body, hasEntry("user_id", "2"));
        assertThat(body, hasEntry("connection_id", "123456790"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnUnlinkUserIdentityWithNullPrimaryId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'primary user id' cannot be null!");
        api.users().unlinkIdentity(null, "2", "authok");
    }

    @Test
    public void shouldThrowOnUnlinkUserIdentityWithNullSecondaryId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'secondary user id' cannot be null!");
        api.users().unlinkIdentity("1", null, "authok");
    }

    @Test
    public void shouldThrowOnUnlinkUserIdentityWithNullProvider() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'provider' cannot be null!");
        api.users().unlinkIdentity("1", "2", null);
    }

    @Test
    public void shouldUnlinkUserIdentity() throws Exception {
        Request<List<Identity>> request = api.users().unlinkIdentity("1", "2", "authok");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_IDENTITIES_LIST, 200);
        List<Identity> response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("DELETE", "/api/v2/users/1/identities/authok/2"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnListRolesWithNullId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'user id' cannot be null!");
        api.users().listRoles(null, null);
    }

    @Test
    public void shouldListRolesWithoutFilter() throws Exception {
        Request<RolesPage> request = api.users().listRoles("1", null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USER_ROLES_PAGED_LIST, 200);
        RolesPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/users/1/roles"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldListRolesWithPage() throws Exception {
        PageFilter filter = new PageFilter().withPage(23, 5);
        Request<RolesPage> request = api.users().listRoles("1", filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USER_ROLES_PAGED_LIST, 200);
        RolesPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/users/1/roles"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("page", "23"));
        assertThat(recordedRequest, hasQueryParameter("per_page", "5"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldListRolesWithTotals() throws Exception {
        PageFilter filter = new PageFilter().withTotals(true);
        Request<RolesPage> request = api.users().listRoles("1", filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USER_ROLES_PAGED_LIST, 200);
        RolesPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/users/1/roles"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("include_totals", "true"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
        assertThat(response.getStart(), is(0));
        assertThat(response.getLength(), is(14));
        assertThat(response.getTotal(), is(14));
        assertThat(response.getLimit(), is(50));
    }

    @Test
    public void shouldThrowOnAddRolesWithNullId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'user id' cannot be null!");
        api.users().addRoles(null, Collections.emptyList());
    }

    @Test
    public void shouldThrowOnAddRolesWithNullList() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'role ids' cannot be null!");
        api.users().addRoles("1", null);
    }

    @Test
    public void shouldThrowOnAddRolesWithEmptyList() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'role ids' cannot be empty!");
        api.users().addRoles("1", Collections.emptyList());
    }

    @Test
    public void shouldAddRoles() throws Exception {
        Request<Void> request = api.users().addRoles("1",  Collections.singletonList("roleId"));
        assertThat(request, is(notNullValue()));

        server.emptyResponse(200);
        Object response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("POST", "/api/v2/users/1/roles"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasKey("roles"));

        assertThat(response, is(nullValue()));
    }

    @Test
    public void shouldThrowOnRemoveRolesWithNullId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'user id' cannot be null!");
        api.users().removeRoles(null, Collections.emptyList());
    }

    @Test
    public void shouldThrowOnRemoveRolesWithNullList() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'role ids' cannot be null!");
        api.users().removeRoles("1", null);
    }

    @Test
    public void shouldThrowOnRemoveRolesWithEmptyList() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'role ids' cannot be empty!");
        api.users().removeRoles("1", Collections.emptyList());
    }

    @Test
    public void shouldRemoveRoles() throws Exception {
        Request<Void> request = api.users().removeRoles("1", Collections.singletonList("roleId"));
        assertThat(request, is(notNullValue()));

        server.emptyResponse( 200);
        Object response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("DELETE", "/api/v2/users/1/roles"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasKey("roles"));

        assertThat(response, is(nullValue()));
    }

    @Test
    public void shouldListPermissionsWithoutFilter() throws Exception {
        Request<PermissionsPage> request = api.users().listPermissions("1", null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USER_PERMISSIONS_PAGED_LIST, 200);
        PermissionsPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/users/1/permissions"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldListPermissionsWithPage() throws Exception {
        PageFilter filter = new PageFilter().withPage(23, 5);
        Request<PermissionsPage> request = api.users().listPermissions("1", filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USER_PERMISSIONS_PAGED_LIST, 200);
        PermissionsPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/users/1/permissions"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("page", "23"));
        assertThat(recordedRequest, hasQueryParameter("per_page", "5"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldListPermissionsWithTotal() throws Exception {
        PageFilter filter = new PageFilter().withTotals(true);
        Request<PermissionsPage> request = api.users().listPermissions("1", filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_USER_PERMISSIONS_PAGED_LIST, 200);
        PermissionsPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/users/1/permissions"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("include_totals", "true"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
        assertThat(response.getStart(), is(0));
        assertThat(response.getLength(), is(14));
        assertThat(response.getTotal(), is(14));
        assertThat(response.getLimit(), is(50));
    }

    @Test
    public void shouldThrowOnAddPermissionsWithNullId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'user id' cannot be null!");
        api.users().addPermissions(null, Collections.emptyList());
    }

    @Test
    public void shouldThrowOnAddPermissionsWithNullList() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'permissions' cannot be null!");
        api.users().addPermissions("1", null);
    }

    @Test
    public void shouldThrowOnAddPermissionsWithEmptyList() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'permissions' cannot be empty!");
        api.users().addPermissions("1", Collections.emptyList());
    }

    @Test
    public void shouldAddPermissions() throws Exception {
        List<Permission> permissions = new ArrayList<>();
        Permission permission = new Permission();
        permission.setName("permissionName");
        permissions.add(permission);
        Request<Void> request = api.users().addPermissions("1", permissions);
        assertThat(request, is(notNullValue()));

        server.emptyResponse(200);
        Object response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("POST", "/api/v2/users/1/permissions"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasKey("permissions"));

        assertThat(response, is(nullValue()));
    }

    @Test
    public void shouldThrowOnRemovePermissionsWithNullId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'user id' cannot be null!");
        api.users().removePermissions(null, Collections.emptyList());
    }

    @Test
    public void shouldThrowOnRemovePermissionsWithNullList() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'permissions' cannot be null!");
        api.users().removePermissions("roleId", null);
    }

    @Test
    public void shouldThrowOnRemovePermissionsWithEmptyList() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'permissions' cannot be empty!");
        api.users().removePermissions("roleId", Collections.emptyList());
    }

    @Test
    public void shouldRemovePermissions() throws Exception {
        List<Permission> permissions = new ArrayList<>();
        Permission permission = new Permission();
        permission.setName("permissionName");
        permissions.add(permission);
        Request<Void> request = api.users().removePermissions("1", permissions);
        assertThat(request, is(notNullValue()));

        server.emptyResponse(200);
        Object response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("DELETE", "/api/v2/users/1/permissions"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasKey("permissions"));

        assertThat(response, is(nullValue()));
    }

    @Test
    public void shouldThrowOnGetOrganizationsWithNullUserId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("user ID");
        api.users().getOrganizations(null, null);
    }

    @Test
    public void shouldGetUserOrganizationsWithoutFilter() throws Exception {
        Request<OrganizationsPage> request = api.users().getOrganizations("1", null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(ORGANIZATIONS_LIST, 200);
        OrganizationsPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/users/1/organizations"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldGetUserOrganizationsWithPaging() throws Exception {
        Request<OrganizationsPage> request = api.users().getOrganizations("1",
            new PageFilter().withPage(0, 20));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(ORGANIZATIONS_LIST, 200);
        OrganizationsPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/users/1/organizations"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("page", "0"));
        assertThat(recordedRequest, hasQueryParameter("per_page", "20"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldGetUserOrganizationsWithTotals() throws Exception {
        Request<OrganizationsPage> request = api.users().getOrganizations("1",
            new PageFilter().withTotals(true));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(ORGANIZATIONS_PAGED_LIST, 200);
        OrganizationsPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/users/1/organizations"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("include_totals", "true"));

        assertThat(response, is(notNullValue()));
        MatcherAssert.assertThat(response.getItems(), hasSize(2));
        MatcherAssert.assertThat(response.getStart(), is(0));
        MatcherAssert.assertThat(response.getTotal(), is(2));
        MatcherAssert.assertThat(response.getLimit(), is(20));
    }
}
