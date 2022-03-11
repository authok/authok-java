# Authok Java

[![Build-Circle][circle-ci-badge]][circle-ci-url]
[![MIT][mit-badge]][mit-url]
[![Maven][maven-badge]][maven-url]
[![JCenter][jcenter-badge]][jcenter-url]
[![codecov][codecov-badge]][codecov-url]

[Authok](https://authok.cn) 平台的 Java 客户端库.

## 下载

通过 Maven 下载 Authok Java:

```xml
<dependency>
  <groupId>com.authok</groupId>
  <artifactId>authok</artifactId>
  <version>1.35.0</version>
</dependency>
```

或 Gradle:

```gradle
implementation 'com.authok:authok:1.35.0'
```


### Android

Authok 认证API 和 用户管理API 对应的库为 `authok.android`. 了解更多可访问 https://github.com/authok/authok.android.

## Auth API

参考 [认证API文档](https://docs.authok.cn/api/authentication) 实现.

创建 `AuthAPI` 实例 [dashboard](https://mgmt.authok.cn/#/applications). 

```java
AuthAPI auth = new AuthAPI("{YOUR_DOMAIN}", "{YOUR_CLIENT_ID}", "{YOUR_CLIENT_SECRET}");
```

### 授权 - /authorize

创建一个 `AuthorizeUrlBuilder` 实例通过OAuth提供者进行用户认证. `redirectUri` 必须在 应用的 "回调链接" 中预先设置. 参数可以被追加到 URL 后面. 调用 `build()` 方法获得完整URL.

```AuthorizeUrlBuilder authorizeUrl(String redirectUri)```

例子:
```java
String url = auth.authorizeUrl("https://me.cn.authok.cn/callback")
    .withConnection("wechat:pc")
    .withAudience("https://api.me.cn.authok.cn/users")
    .withScope("openid contacts")
    .withState("state123")
    .build();
```

### 退出登录 - /logout
创建一个 `LogoutUrlBuilder` 实例来退登用户. `returnToUrl` 需要在 应用的 "允许的退登链接" 中预先设置, 参数可以被添加到URL后面. 调用 `build()` 方法获得完整URL.

`LogoutUrlBuilder logoutUrl(String returnToUrl, boolean setClientId)`

例子:
```java
String url = auth.logoutUrl("https://me.authok.cn/home", true)
    .useFederated(true)
    .build();
```

### 用户信息 - /userinfo
请求用户信息. 令牌需要有 `openid` 作用域.

`Request<UserInfo> userInfo(String accessToken)`

例子:
```java
Request<UserInfo> request = auth.userInfo("nisd1h9dk.....s1doWJOsaf");
try {
    UserInfo info = request.execute();
    // info.getValues();
} catch (APIException exception) {
    // api error
} catch (AuthokException exception) {
    // request error
}
```

### 重置密码 - /dbconnections/change_password
请求重置用户密码. 只可用于数据库身份源.

`Request resetPassword(String email, String connection)`

例子:
```java
Request request = auth.resetPassword("user@domain.com", "Username-Password-Auth");
try {
    request.execute();
} catch (APIException exception) {
    // api error
} catch (AuthokException exception) {
    // request error
}
```


### 注册 - /dbconnections/signup
创建一个注册请求. 可以最多添加10个额外注册字段. 仅仅能用于数据库身份源.

`SignUpRequest signUp(String email, String username, String password, String connection)`

`SignUpRequest signUp(String email, String password, String connection)`

例子:
```java
Map<String, String> fields = new HashMap<>();
fields.put("age", "25");
fields.put("city", "Buenos Aires");
SignUpRequest request = auth.signUp("user@domain.com", "username", "password123", "Username-Password-Authentication")
    .setCustomFields(fields);
try {
    CreatedUser user = request.execute();
} catch (APIException exception) {
    // api error
} catch (AuthokException exception) {
    // request error
}
```

### 交换授权码 - /oauth/token

用调用 /authorize 端点获取的 `code` 来请求令牌. 必须设置重定向链接.

`AuthRequest exchangeCode(String code, String redirectUri)`

例子:
```java
AuthRequest request = auth.exchangeCode("asdfgh", "https://me.authok.cn/callback")
    .setAudience("https://api.me.authok.cn/users")
    .setScope("openid contacts");
try {
    TokenHolder holder = request.execute();
} catch (APIException exception) {
    // api error
} catch (AuthokException exception) {
    // request error
}
```

### 密码登录 - /oauth/token

通过 `username` 和 `password` 登录. 会通过租户的 "默认数据源" 进行登录.

`AuthRequest login(String emailOrUsername, String password)`

例子:
```java
AuthRequest request = auth.login("me@domain.com", "password123")
    .setAudience("https://api.me.authok.cn/users")
    .setScope("openid contacts");
try {
    TokenHolder holder = request.execute();
} catch (APIException exception) {
    // api error
} catch (AuthokException exception) {
    // request error
}
```

### 通过密码域登录 Realm - /oauth/token

通过进行 `username` 和 `password` 进行 Password Realm 登录.

`AuthRequest login(String emailOrUsername, String password, String realm)`

例子:
```java
AuthRequest request = auth.login("me@domain.com", "password123", "Username-Password-Authentication")
    .setAudience("https://api.me.authok.cn/users")
    .setScope("openid contacts");
try {
    TokenHolder holder = request.execute();
} catch (APIException exception) {
    // api error
} catch (AuthokException exception) {
    // request error
}
```

### 请求 Audience 对应的令牌 - /oauth/token

请求指定Audience的令牌.

`AuthRequest requestToken(String audience)`

例子:
```java
AuthRequest request = auth.requestToken("https://api.me.authok.cn/users")
    .setScope("openid contacts");
try {
    TokenHolder holder = request.execute();
} catch (APIException exception) {
    // api error
} catch (AuthokException exception) {
    // request error
}
```

### 撤销刷新令牌

撤销已经存在的刷新令牌.

`Request<Void> revokeToken(String refreshToken)`

例子:
```java
Request<Void> request = auth.revokeToken("nisd1h9dks1doWJOsaf");
try {
    request.execute();
} catch (APIException exception) {
    // api error
} catch (AuthokException exception) {
    // request error
}
```

### 认证续期

通过一个有效的刷新令牌来进行认证续期.

`AuthRequest renewAuth(String refreshToken)`

例子:
```java
AuthRequest request = auth.renewAuth("nisd1h9dks1doWJOsaf");
try {
    TokenHolder holder = request.execute();
} catch (APIException exception) {
    // api error
} catch (AuthokException exception) {
    // request error
}
```

### 免密认证

本SDK支持 [免密认证](https://docs.authok.cn/connections/passwordless).

邮箱流程同时支持用 验证码 和 链接 两种模式发起登录:

```java
try {
    PasswordlessEmailResponse = auth.startPasswordlessEmailFlow("user@domain.com", PasswordlessEmailType.CODE)
        .execute();
} catch (AuthokException e) {
        // handle request error
}
```

也可以发起短信验证码形式的免密登录:

```java
try {
    PasswordlessSmsResponse result = auth.startPasswordlessSmsFlow("+16511234567")
        .execute();
} catch (AuthokException e) {
    // handle request error
}
```

通过验证码完成免密认证后将会获得令牌:

```java
AuthRequest request = auth.exchangePasswordlessOtp("emailOrPhone", PasswordlessRealmType.EMAIL, new char[]{'c','o','d','e'});
try {
    TokenHolder tokens = request.execute();
} catch (AuthokException e) {
    // handle request error
}
```

### 组织(租户)

[组织](https://docs.authok.cn/organizations) 主要用于 SaaS 系统和 Business-to-Business (B2B) 应用.

#### 登录组织

在构建授权链接时通过设置 `withOrganization()` 来登录组织:

```java
AuthAPI auth = new AuthAPI("{YOUR_DOMAIN}", "{YOUR_CLIENT_ID}", "{YOUR_CLIENT_SECRET}");
String url = auth.authorizeUrl("https://me.authok.cn/callback")
    .withOrganization("{YOUR_ORGANIZATION_ID")
    .build();
```

**重要!** 当登录组织时, 要确保 ID Token 的 `org_id` 声明匹配预期的组织. 可配置 `IdTokenVerifier` 进行 `org_id` 校验.
更多信息, 请参考 [令牌和组织](https://docs.authok.cn/organizations/using-tokens).
```java
IdTokenVerifier.init("{ISSUER}", "{AUDIENCE}", signatureVerifier)
    .withOrganization("{ORG_ID}")
    .build()
    .verify(jwt);
```

### 接受用户邀请

在构建授权链接时通过设置 `withInvitation()` 来接受用户邀请:

```
AuthAPI auth = new AuthAPI("{YOUR_DOMAIN}", "{YOUR_CLIENT_ID}", "{YOUR_CLIENT_SECRET}");
String url = auth.authorizeUrl("https://me.authok.cn/callback")
    .withOrganization("{YOUR_ORGANIZATION_ID")
    .withInvitation("{YOUR_INVITATION_ID}")
    .build();
```

## 管理 API

实现基于 [管理 API 文档](https://docs.authok.cn/api/management/v1). 

创建 `ManagementAPI` 实例, 第一个参数 domain 可在 [应用](https://mgmt.authok.cn/#/applications) 中获取, 第二个参数需提供一个有效令牌.

```java
ManagementAPI mgmt = new ManagementAPI("{YOUR_DOMAIN}", "{YOUR_API_TOKEN}");
```

You can use the Authentication API to obtain a token for a previously authorized Application:

```java
AuthAPI authAPI = new AuthAPI("{YOUR_DOMAIN}", "{YOUR_CLIENT_ID}", "{YOUR_CLIENT_SECRET}");
AuthRequest authRequest = authAPI.requestToken("https://{YOUR_DOMAIN}/api/v1/");
TokenHolder holder = authRequest.execute();
ManagementAPI mgmt = new ManagementAPI("{YOUR_DOMAIN}", holder.getAccessToken());
```

(注意上面的代码需要进行错误处理, 并且最好缓存令牌直到过期，而不要每次 Management API 调用都去请求令牌). 

可以调用 `ManagementAPI` 实例的 `setApiToken` 方法来替换过期令牌.

点击 [这里](https://docs.authok.cn/api/management/v2/tokens) 获取更多关于获取令牌的信息.

管理 API 被划分为不同实体. 每个实体都有 list, create, update, delete 和 update 方法 和一些特定于实体的方法. 所有调用都通过传入 `ManagementAPI` 实例的令牌进行验证，并且每个方法都要携带对应的 `scope` . 参考 javadoc 来了解每个调用对应的 `scope` .

* **Blacklists:**  参考 [文档](https://docs.authok.cn/api/management/v2#!/Blacklists/get_tokens). 调用 `mgmt.blacklists()`.
* **Client Grants:** 参考 [文档](https://docs.authok.cn/api/management/v2#!/Client_Grants/get_client_grants). 调用 `mgmt.clientGrants()`. 支持分页.
* **Clients:** 参考 [文档](https://docs.authok.cn/api/management/v2#!/Clients/get_clients). 调用 `mgmt.clients()`. 支持分页.
* **Connections:** 参考 [文档](https://docs.authok.cn//api/management/v2#!/Connections/get_connections). 调用 `mgmt.connections()`. 支持分页.
* **Device Credentials:** 参考 [文档](https://docs.authok.cn//api/management/v2#!/Device_Credentials/get_device_credentials). 调用 `mgmt.deviceCredentials()`.
* **Email Providers:** 参考 [文档](https://docs.authok.cn/api/management/v2#!/Emails/get_provider). 调用 `mgmt.emailProvider()`.
* **Email Templates:** 参考 [文档](https://docs.authok.cn/api/management/v2#!/Email_Templates/get_email_templates_by_templateName). 调用 `mgmt.emailTemplates()`.
* **Grants:** 参考 [文档](https://docs.authok.cn/api/management/v2#!/Grants/get_grants). 调用 `mgmt.grants()`. 支持分页.
* **Guardian:** 参考 [文档](https://docs.authok.cn/api/management/v2#!/Guardian/get_factors). 调用 `mgmt.guardian()`.
* **Jobs:** 参考 [文档](https://docs.authok.cn/api/management/v2#!/Jobs/get_jobs_by_id). 调用 `mgmt.jobs()`.
* **Logs:** 参考 [文档](https://docs.authok.cn/api/management/v2#!/Logs/get_logs). 调用 `mgmt.logEvents()`. 支持分页.
* **Log Streams:** 参考 [文档](https://docs.authok.cn/api/management/v2#!/Log_Streams/get_log_streams). 调用 `mgmt.logStreams()`.
* **Resource Servers:** 参考 [文档](https://docs.authok.cn/api/management/v2#!/Resource_Servers/get_resource_servers). 调用 `mgmt.resourceServers()`. 支持分页.
* **Roles:** 参考 [文档](https://docs.authok.cn/api/management/v2#!/Roles/get_roles). 调用 `mgmt.roles()`. 支持分页.
* **Rules:** 参考 [文档](https://docs.authok.cn/api/management/v2#!/Rules/get_rules). 调用 `mgmt.rules()`. 支持分页.
* **Stats:** 参考 [文档](https://docs.authok.cn/api/management/v2#!/Stats/get_active_users). 调用 `mgmt.stats()`.
* **Tenants:** 参考 [文档](https://docs.authok.cn/api/management/v2#!/Tenants/get_settings). 调用 `mgmt.tenants()`.
* **Tickets:** 参考 [文档](https://docs.authok.cn/api/management/v2#!/Tickets/post_email_verification). 调用 `mgmt.tickets()`.
* **User Blocks:** 参考 [文档](https://docs.authok.cn/api/management/v2#!/User_Blocks/get_user_blocks). 调用 `mgmt.userBlocks()`.
* **Users:** 参考 [这里](https://docs.authok.cn/api/management/v2#!/Users/get_users) 和 [这里](https://docs.authok.cn/api/management/v2#!/Users_By_Email) doc. 调用 `mgmt.users()`. 支持分页.


> 部分端点支持分页请求. 你可以在 给过滤实例传入 `page` 和 `page_size` 参数, 可选参数 `include_totals` 用于获取结果总数. 详情可参考下面 "分页查找Users" 的例子. 


### Users

#### List by Email

通过邮件来分页查询用户. API令牌中需要 `read:users` scope. 如果你想获取到 identities.access_token 属性, 那么你对应需要 `read:user_idp_tokens` 的 scope.
你可以传递跟多过滤参数进行更精确结果的筛选.

`Request<List<User>> listByEmail(String email, UserFilter filter)`

例子:
```java
FieldsFilter filter = new FieldsFilter();
//...
Request<List<User>> request = mgmt.users().listByEmail("johndoe@authok.cn", filter);
try {
    List<User> response = request.execute();
} catch (APIException exception) {
    // api error
} catch (AuthokException exception) {
    // request error
}
```

#### 列表

获取用户列表. 需要scope `read:users` . 如果需要返回 identities.access_token 属性, 则还需要 `read:user_idp_tokens` scope.
你可以传递跟多过滤参数进行更精确结果的筛选.

`Request<UsersPage> list(UserFilter filter)`

例子:
```java
UserFilter filter = new UserFilter()
    .withPage(0, 20);
//...
Request<UsersPage> request = mgmt.users().list(filter);
try {
    UsersPage response = request.execute();
} catch (APIException exception) {
    // api error
} catch (AuthokException exception) {
    // request error
}
```

#### Get

请求单个用户. API 令牌中需要包含 `read:users` scope. 需要需要包含 identities.access_token 属性, 还需要 `read:user_idp_tokens` scope.
你可以传递跟多过滤参数来指定 包含 与 不包含的字段.

`Request<User> get(String userId, UserFilter filter)`

例子:
```java
UserFilter filter = new UserFilter();
//...
Request<User> request = mgmt.users().get("authok|123", filter);
try {
    User response = request.execute();
} catch (APIException exception) {
    // api error
} catch (AuthokException exception) {
    // request error
}
```

#### Create

创建一个用户. API令牌中需要 `create:users` scope.

`Request<User> create(User user)`

例子:
```java
User data = new User("my-connection");
//...
Request<User> request = mgmt.users().create(data);
try {
    User response = request.execute();
} catch (APIException exception) {
    // api error
} catch (AuthokException exception) {
    // request error
}
```

#### Delete

删除一个用户. API令牌中需要 `delete:users` scope.

`Request delete(String userId)`

例子:
```java
Request request = mgmt.users().delete("authok|123");
try {
    request.execute();
} catch (APIException exception) {
    // api error
} catch (AuthokException exception) {
    // request error
}
```

#### Update

更新一个用户. API令牌中需要 `update:users` scope. 如果要更新 app_metadata 则还需要 `update:users_app_metadata` scope.

`Request<User> update(String userId, User user)`

例子:
```java
User data = new User();
//...
Request request = mgmt.users().update("authok|123", data);
try {
    User response = request.execute();
} catch (APIException exception) {
    // api error
} catch (AuthokException exception) {
    // request error
}
```

#### Get Guardian Enrollments

列出用户的 Guardian Enrollments. API令牌中需要 `read:users` scope.

`Request<List<Enrollment>> getEnrollments(String userId)`

例子:
```java
Request<List<Enrollment>> request = mgmt.users().getEnrollments("authok|123");
try {
    List<Enrollment> response = request.execute();
} catch (APIException exception) {
    // api error
} catch (AuthokException exception) {
    // request error
}
```

#### 获取日志事件

获取用户日志列表. API令牌中需要 `read:logs` scope.
你可以传递跟多过滤参数进行更精确结果的筛选.

`Request<LogEventsPage> getLogEvents(String userId, LogEventFilter filter)`

例子:
```java
LogEventFilter filter = new LogEventFilter();
//...
Request<LogEventsPage> request = mgmt.users().getLogEvents("authok|123", filter);
try {
    LogEventsPage response = request.execute();
} catch (APIException exception) {
    // api error
} catch (AuthokException exception) {
    // request error
}
```

#### 删除多因素提供者

删除用户的 多因素提供者. API令牌中需要 `update:users` scope.

`Request deleteMultifactorProvider(String userId, String provider)`

例子:
```java
Request request = mgmt.users().deleteMultifactorProvider("authok|123", "duo");
try {
    request.execute();
} catch (APIException exception) {
    // api error
} catch (AuthokException exception) {
    // request error
}
```

#### Rotate Recovery Code

轮换用户的 Recovery Code. API令牌需要 `update:users` scope.

`Request<RecoveryCode> rotateRecoveryCode(String userId)`

例子:
```java
Request<RecoveryCode> request = mgmt.users().rotateRecoveryCode("authok|123");
try {
    RecoveryCode response = request.execute();
} catch (APIException exception) {
    // api error
} catch (AuthokException exception) {
    // request error
}
```

#### 关联身份

关联两个用户身份. API令牌中需要 `update:users` scope.

`Request<List<Identity>> linkIdentity(String primaryUserId, String secondaryUserId, String provider, String connectionId)`

例子:
```java
Request<List<Identities>> request = mgmt.users().linkIdentity("authok|123", "124", "facebook", "c90");
try {
    List<Identities> response = request.execute();
} catch (APIException exception) {
    // api error
} catch (AuthokException exception) {
    // request error
}
```

#### 解除身份关联

解除两个用户之间的身份关联. API令牌中需要 `update:users` scope.

`Request<List<Identity>> unlinkIdentity(String primaryUserId, String secondaryUserId, String provider)`

例子:
```java
Request<List<Identities>> request = mgmt.users().unlinkIdentity("authok|123", "124", "facebook");
try {
    List<Identities> response = request.execute();
} catch (APIException exception) {
    // api error
} catch (AuthokException exception) {
    // request error
}
```

## 异步请求

可通过  `executeAsync()` 方法异步执行请求, 会返回一个 `CompletableFuture<T>`. 

## API Clients Recommendations
SDK 基于 **OkHttp** 库实现. [官方推荐](https://square.github.io/okhttp/4.x/okhttp/okhttp3/-ok-http-client/#okhttpclients-should-be-shared) 建议尽可能重用 clients. 但是, 当前不允许传递一个已经存在的 `OkHttpClient` 实例到我们的 `AuthAPI` 和 `ManagementAPI` 客户端. 

`AuthAPI` 和 `ManagementAPI` 的网络客户端可通过 `HttpOptions` 进行设置, 这样便于自定义超时 和 代理设置等:

```java
HttpOptions options = new HttpOptions();

// 配置超时; 默认为10秒钟, 连接超时 和 读取超时都一样:
options.setConnectTimeout(5);
options.setReadTimeout(15);

// 配置代理:
Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("{IP-ADDRESS}", {PORT}));
ProxyOptions proxyOptions = new ProxyOptions(proxy);
options.setProxyOptions(proxyOptions);

// create client
AuthAPI authAPI = new AuthAPI("{CLIENT_ID}", "{CLIENT_SECRET}", options);
```

一旦初始化了 client, 对应会创建一个新的 `OkHttpClient` 实例用于处理请求. This instance is not directly exposed for customization. In order to reduce resource consumption, make use of the _singleton pattern_ to keep a single instance of this SDK's API client during the lifecycle of your application.

For the particular case of the `ManagementAPI` client, if the token you've originally set has expired or you require to change its scopes, you can update the client's token with the `setApiToken(String)` method.    

## 错误处理

如果请求出错，API Clients 会抛出 `AuthokException`, 例如: 网络不可连或者超时.

如果你要处理不同的错误场景，你需要首先捕获 `APIException`, 它包含了出错线索.

APIExplorer 为每个端点保留一个 响应消息列表. 可以读取 Http 状态码: `exception.getStatusCode()` 来获得错误线索. 例如. `status_code=403` 表示令牌没有足够的权限.

错误码用于区分错误类型, 你可以调用 `exception.getError()` 来获得错误码. 如果你想要查看用户可读的描述 `exception.getDescription()`. Finally, if the error response includes additional properties they can be obtained by calling `exception.getValue("{THE_KEY}")`.


```
示范异常数据
{
  statusCode: 400,
  description: "Query validation error: 'String 'users' does not match pattern. Must be a comma separated list of the following values: name,strategy,options,enabled_clients,id,provisioning_ticket_url' on property fields (A comma separated list of fields to include or exclude (depending on include_fields) from the result, empty to retrieve all fields).",
  error: "invalid_query_string"
}
```

## ID Token 验证

此SDK也提供了针对 OIDC-compliant ID Token的验证机制, 参考 [OIDC规范](https://openid.net/specs/openid-connect-core-1_0-final.html#IDTokenValidation).

### 验证通过 RS256 签名算法 签名的 ID Token

为了验证采用 RS256 签名算法签名的 ID Token, 你需要提供一个 
`PublicKeyProvider` 的实现, 返回 用于令牌签名验证的公钥. 下面的例子描述了如何使用 `JwkProvider`  [jwks-rsa-java](https://github.com/authok/jwks-rsa-java):

```java
JwkProvider provider = new JwkProviderBuilder("https://your-domain.authok.cn").build();
SignatureVerifier sigVerifier = SignatureVerifier.forRS256(new PublicKeyProvider() {
    @Override
    public RSAPublicKey getPublicKeyById(String keyId) throws PublicKeyProviderException {
       try {
            return (RSAPublicKey) provider.get(keyId).getPublicKey();
        } catch (JwkException jwke) {
            throw new PublicKeyProviderException("Error obtaining public key", jwke);
        }
    }
}

IdTokenVerifier idTokenVerifier = IdTokenVerifier.init("https://your-domain.authok.cn/","your-client-id", signatureVerifier).build();

try {
    idTokenVerifier.verify("token", "expected-nonce");
} catch(IdTokenValidationException idtve) {
    // Handle invalid token exception
}
```

### 验证通过 HS256 签名算法签名的 ID Token

验证通过 HS256 签名算法签名的 ID Token:

```java
SignatureVerifier signatureVerifier = SignatureVerifier.forHS256("your-client-secret");
IdTokenVerifier idTokenVerifier = IdTokenVerifier.init("https://your-domain.authok.cn/","your-client-id", signatureVerifier).build();

try {
    idTokenVerifier.verify("token", "expected-nonce");
} catch(IdTokenValidationException idtve) {
    // Handle invalid token exception
}
```

### 额外的配置选项

默认情况下, 基于时间的声明如令牌超时 (`exp` 声明) 会允许一个 **60 秒** 的滑动窗口.
你可以在构建 `IdTokenVerifier` 时通过调用 `withLeeway` 来进行自定义:

```java
IdTokenVerifier idTokenVerifier = IdTokenVerifier.init("https://your-domain.authok.cn/","your-client-id", signatureVerifier)
        .withLeeway(120) // two minutes
        .build();
``` 

当进行令牌校验时, 以下方法可用于不同的场景:

```java
// 验证令牌的签名和声明, 不包括 nonce 和 auth_time 声明
idTokenVerifier.verify("token");

// 验证令牌的 签名 和 声明, 包含 nonce.
// expected nonce 需要和授权请求发出的 nonce 一致.
idTokenVerifier.verify("token", "expected-nonce");

// 验证令牌的 签名 和 声明, 包含 nonce 和 auth_time 声明.
// maxAuthenticationAge 参数表示 IDToken 在 用户最后一次认证后的有效时间,
// 这个值和授权请求的 max_age 参数一致.
idTokenVerifier.verify("token", "expected-nonce", 24 * 60 * 60); // maximum authentication age of 24 hours
```

## 文档

更多信息可参考 [Authok](http://authok.cn) 的 [文档](http://docs.authok.cn/).

## Authok 是什么?

Authok 帮助您:

* 使用多个认证身份源进行认证 [多认证身份源](https://docs.authok.cn/identityproviders), 社交身份源如 **Google, Facebook, Microsoft Account, LinkedIn, GitHub, Twitter, Box, Salesforce, among others**, 或企业身份系统例如 **Windows Azure AD, Google Apps, Active Directory, ADFS 或任何 SAML 身份提供者**.
* 通过传统 **[username/password databases](https://docs.authok.cn/mysql-connection-tutorial)** 进行认证.
*  **[关联不同账号](https://docs.authok.cn/link-accounts)**.
* 支持生成签名的 [Json Web Tokens](https://docs.authok.cn/jwt) 用于调用API, 以及安全的 **传递身份**.
* 分析用户活跃数据.
* 通过其它身份源获取数据并加入用户档案, 通过 [JavaScript rules](https://docs.authok.cn/rules).

## 创建一个免费的 Authok 账户

1. 跳转到 [Authok](https://authok.cn) 并点击注册.
2. 采用 Google, GitHub  或者 Microsoft Account, 微信, 企业微信，抖音, 微博 等 登录.

## 问题baogao

如果您找到bug 或者新功能请求, 请在本仓库的issues 部分剔除. 不要在公开的 issue tracker 中暴露安全漏洞.

## 作者

[Authok](https://authok.cn)

## 许可

本项目基于 MIT 许可. 更多请参考 [LICENSE](LICENSE).


<!-- Vars -->

[circle-ci-badge]: https://img.shields.io/circleci/project/github/authok/authok-java.svg?style=flat
[circle-ci-url]: https://circleci.com/gh/authok/authok-java/tree/master
[mit-badge]: http://img.shields.io/:license-mit-blue.svg?style=flat
[mit-url]: https://raw.githubusercontent.com/authok/authok-java/master/LICENSE
[maven-badge]: https://img.shields.io/maven-central/v/com.authok/authok.svg
[maven-url]: http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.authok%22%20AND%20a%3A%22authok%22
[jcenter-badge]: https://api.bintray.com/packages/authok/java/authok/images/download.svg
[jcenter-url]: https://bintray.com/authok/java/authok/_latestVersion
[codecov-badge]: https://codecov.io/gh/authok/authok-java/branch/master/graph/badge.svg
[codecov-url]: https://codecov.io/gh/authok/authok-java
