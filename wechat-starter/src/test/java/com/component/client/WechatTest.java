package com.component.client;

import com.component.wechat.WechatAutoConfiguration;
import com.component.wechat.WechatProperties;
import com.component.wechat.core.Client;
import com.component.wechat.core.DefaultClient;
import com.component.wechat.exception.BusinessException;
import com.component.wechat.exception.RestException;
import com.component.wechat.model.AccessAndMobileRequest;
import com.component.wechat.model.AccessRequest;
import com.component.wechat.model.AccessResponse;
import com.component.wechat.model.LoginRequest;
import com.component.wechat.model.LoginResponse;
import com.component.wechat.model.MobileRequest;
import com.component.wechat.model.MobileResponse;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.component.client.FileReader.getContentFromFile;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author jinx
 */
public class WechatTest {

    @Test
    public void proxy() {
        WechatProperties properties = new WechatProperties();
        properties.setBusinessCheckEnable(true);
        Client client = new WechatAutoConfiguration().client(properties);
        System.out.println(client.toString());
    }

    @Test
    @DisplayName("微信授权")
    public void access() throws Exception {
        AccessRequest request = new AccessRequest(null, null);
        doTest(
                "access/response_success.json",
                "access/response_error.json",
                client -> {
                    AccessResponse response = client.access(request);
                    assertTrue(response.success());
                    assertEquals(response.getExpiresIn(), 7200);
                },
                client -> client.access(request)
        );
    }

    @Test
    @DisplayName("微信登录")
    public void login() throws Exception {
        LoginRequest request = new LoginRequest(null, null, null);
        doTest(
                "login/response_success.json",
                "login/response_error.json",
                client -> {
                    LoginResponse response = client.login(request);
                    assertTrue(response.success());
                    assertEquals(response.getErrCode(), 0);
                    assertEquals(response.getSessionKey(), "1");
                    assertEquals(response.getUnionId(), "1");
                    assertEquals(response.getOpenId(), "1");
                    assertEquals(response.getErrMsg(), "1");
                },
                client -> client.login(request)
        );
    }


    @Test
    @DisplayName("查询手机号")
    public void queryMobile() throws Exception {
        MobileRequest request = new MobileRequest(null, null);
        doTest(
                "mobile/response_success.json",
                "mobile/response_error.json",
                client -> {
                    MobileResponse response = client.queryMobile(request);
                    assertTrue(response.success());
                    assertEquals(response.getPhoneInfo().getPhoneNumber(), "123");
                    assertEquals(response.getErrCode(), 0);
                    assertEquals(response.getErrMsg(), "ok");
                    assertEquals(response.getPhoneInfo().getPurePhoneNumber(), "321");
                    assertEquals(response.getPhoneInfo().getCountryCode(), 86);
                },
                client -> client.queryMobile(request)
        );
    }


    @Test
    @DisplayName("查询手机号增强")
    public void queryMobileEnhancer() throws Exception {
        AccessAndMobileRequest request = new AccessAndMobileRequest(null, null, null);

        String accessSuccessResponse = getContentFromFile("access/response_success.json");
        String accessErrorResponse = getContentFromFile("access/response_structure_error.json");
        String mobileSuccessResponse = getContentFromFile("mobile/response_success.json");
        String mobileErrorResponse = getContentFromFile("mobile/response_error.json");

        CloseableHttpClient httpClient = mock(CloseableHttpClient.class);
        WechatProperties properties = new WechatProperties();
        DefaultClient client = new DefaultClient(httpClient, properties);

        CloseableHttpResponse accessResponse = mock(CloseableHttpResponse.class);
        CloseableHttpResponse mobileResponse = mock(CloseableHttpResponse.class);
        StatusLine accessStatusLine = mock(StatusLine.class);
        StatusLine mobileStatusLine = mock(StatusLine.class);

        when(httpClient.execute(any())).then(invocation -> {
            Object argument = invocation.getArgument(0);
            if (argument instanceof HttpGet) {
                return accessResponse;
            } else if (argument instanceof HttpPost) {
                return mobileResponse;
            } else {
                return null;
            }
        });
        when(accessResponse.getStatusLine()).thenReturn(accessStatusLine);
        when(mobileResponse.getStatusLine()).thenReturn(mobileStatusLine);
        when(accessStatusLine.getStatusCode()).thenReturn(200);
        when(mobileStatusLine.getStatusCode()).thenReturn(200);
        when(accessResponse.getEntity()).thenReturn(new StringEntity(accessSuccessResponse));
        when(mobileResponse.getEntity()).thenReturn(new StringEntity(mobileSuccessResponse));

        MobileResponse response = client.queryMobile(request);
        assertTrue(response.success());

        // mobile
        when(mobileResponse.getEntity()).thenReturn(new StringEntity(mobileErrorResponse));
        assertThrows(BusinessException.class, () -> client.queryMobile(request));

        when(mobileResponse.getEntity()).thenReturn(null);
        assertThrows(ParseException.class, () -> client.queryMobile(request));

        when(mobileStatusLine.getStatusCode()).thenReturn(500);
        assertThrows(RestException.class, () -> client.queryMobile(request));

        when(httpClient.execute(any())).then(invocation -> {
            if (invocation.getArgument(0) instanceof HttpPost) {
                return null;
            } else {
                return accessResponse;
            }
        });
        assertThrows(RestException.class, () -> client.queryMobile(request));


        // access
        when(accessResponse.getEntity()).thenReturn(new StringEntity(accessErrorResponse));
        assertThrows(Exception.class, () -> client.queryMobile(request));

        when(accessResponse.getEntity()).thenReturn(null);
        assertThrows(ParseException.class, () -> client.queryMobile(request));

        when(accessStatusLine.getStatusCode()).thenReturn(500);
        assertThrows(RestException.class, () -> client.queryMobile(request));

        when(httpClient.execute(any())).thenReturn(null);
        assertThrows(RestException.class, () -> client.queryMobile(request));
    }


    private void doTest(String successResponseFilename,
                        String errorResponseFilename,
                        Executable normalInvokeAndVerifyExecutable,
                        Executable onlyInvokeExecutable)
            throws Exception {
        String successResponse = getContentFromFile(successResponseFilename);
        String errorResponse = getContentFromFile(errorResponseFilename);

        CloseableHttpClient httpClient = mock(CloseableHttpClient.class);

        WechatProperties properties = new WechatProperties();
        DefaultClient client = new DefaultClient(httpClient, properties);

        CloseableHttpResponse response = mock(CloseableHttpResponse.class);
        StatusLine statusLine = mock(StatusLine.class);

        when(httpClient.execute(any())).thenReturn(response);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getEntity()).thenReturn(new StringEntity(successResponse));

        normalInvokeAndVerifyExecutable.execute(client);

        when(response.getEntity()).thenReturn(new StringEntity(errorResponse));
        assertThrows(BusinessException.class, () -> onlyInvokeExecutable.execute(client));

        when(response.getEntity()).thenReturn(null);
        assertThrows(ParseException.class, () -> onlyInvokeExecutable.execute(client));

        when(statusLine.getStatusCode()).thenReturn(500);
        assertThrows(RestException.class, () -> onlyInvokeExecutable.execute(client));

        when(httpClient.execute(any())).thenReturn(null);
        assertThrows(RestException.class, () -> onlyInvokeExecutable.execute(client));

        when(httpClient.execute(any())).thenThrow(new RuntimeException());
        assertThrows(RestException.class, () -> onlyInvokeExecutable.execute(client));
    }


    interface Executable {
        void execute(Client client);
    }
}
