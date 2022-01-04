package com.authok;

import com.authok.client.auth.AuthAPI;
import com.authok.client.mgmt.ManagementAPI;
import com.authok.exception.AuthokException;
import com.authok.json.auth.TokenHolder;
import com.authok.json.mgmt.client.Client;
import com.authok.net.Request;
import com.authok.net.TokenRequest;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collections;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

public class ClientExample {
    private AuthAPI authAPI;
    private ManagementAPI managementAPI;

    static class MyX509TrustManager implements javax.net.ssl.X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            return;
        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            return;
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        try {
            ClientExample e = new ClientExample();
            e.run();
        } catch(Throwable e) {
            e.printStackTrace();
        }
    }

    public void run() throws Throwable {
        authenticate();
        testClient();
    }

    private void authenticate() throws Exception {
        this.authAPI = new AuthAPI("https://wsz.cn.authok.cn", "_f8riekVm23IjMsIsqEFjdhKH6i7tilr", "gJXi9XSC5AsXu4FAJLi5R1W__TisCIlzDNGhN-P6zA65WPRcvNqdqUuRtkRQdQ5E");

        TokenRequest req = authAPI.requestToken("https://wsz.com/api/v1/");//.setScope("openid contacts");
        TokenHolder holder = req.execute();

        Request<Void> req1 = authAPI.resetPasswordByEmail("1@126.com", "c1");
        Void r = req1.execute();

        this.managementAPI = new ManagementAPI("https://brucke.authok.cn", holder.getAccessToken());
    }

    private void testClient() {
        Client c = new Client("app5");
        c.setCallbacks(Collections.singletonList("http://localhost:3000"));

        try {
            Client client = this.managementAPI.clients().create(c).execute();
            System.out.print(client);
        } catch (AuthokException e) {
            e.printStackTrace();
        }
    }
}
