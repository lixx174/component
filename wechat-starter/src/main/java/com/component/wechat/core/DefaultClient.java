package com.component.wechat.core;

import com.component.wechat.WechatProperties;
import com.component.wechat.exception.BusinessException;
import com.component.wechat.exception.RestException;
import com.component.wechat.model.AccessAndMobileRequest;
import com.component.wechat.model.AccessRequest;
import com.component.wechat.model.AccessResponse;
import com.component.wechat.model.LoginRequest;
import com.component.wechat.model.LoginResponse;
import com.component.wechat.model.MobileRequest;
import com.component.wechat.model.MobileResponse;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.net.URI;

/**
 * @author jinx
 */
@Slf4j
@RequiredArgsConstructor
public class DefaultClient implements Client {

    private final CloseableHttpClient httpClient;
    private final WechatProperties properties;
    private final JacksonSupport jacksonSupport = new JacksonSupport();


    @Override
    public LoginResponse login(LoginRequest request) {
        return doGet(request, LoginResponse.class);
    }

    @Override
    public AccessResponse access(AccessRequest request) {
        return doGet(request, AccessResponse.class);
    }

    @Override
    public MobileResponse queryMobile(MobileRequest request) {
        return doPost(request, MobileResponse.class);
    }

    @Override
    public MobileResponse queryMobile(AccessAndMobileRequest request) {
        AccessResponse response = access(new AccessRequest(request.appId(), request.appSecret()));

        return queryMobile(new MobileRequest(response.getAccessToken(), request.code()));
    }


    private <T extends WechatResponse> T doGet(WechatRequest request, Class<T> exceptResponse) {
        URI uri = request.constructUri();

        log.info("===>  wechat-{} 请求地址:{}", request, uri);
        String response = doExecute(new HttpGet(uri));
        log.info("===>  wechat-{} 响应参数:{}", request, response);

        return toBean(response, exceptResponse);
    }

    private <T extends WechatResponse> T doPost(WechatRequest request, Class<T> exceptResponse) {
        URI uri = request.constructUri();
        String body = jacksonSupport.toJsonStr(request);

        HttpPost httpPost = new HttpPost(uri);
        try {
            httpPost.setEntity(new StringEntity(body));
        } catch (Exception e) {
            throw new ParseException(e.getMessage());
        }

        log.info("===>  wechat-{} 请求地址:{}  请求参数:{}", request, uri, body);
        String response = doExecute(httpPost);
        log.info("===>  wechat-{} 响应参数:{}", request, response);

        return toBean(response, exceptResponse);
    }

    private <T extends WechatResponse> T toBean(String response, Class<T> exceptResponse) {
        T t = jacksonSupport.toBean(response, exceptResponse);

        if (properties.isBusinessCheckEnable() && !t.success()) {
            throw new BusinessException(t.errorTips());
        }

        return t;
    }

    private String doExecute(HttpUriRequest httpUriRequest) {
        CloseableHttpResponse response;
        try {
            response = httpClient.execute(httpUriRequest);
        } catch (Exception e) {
            throw new RestException(e.getMessage());
        }

        if (response == null) {
            throw new RestException("httpclient has no response");
        }

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 200) {
            throw new RestException("httpclient execute failed, the response code is %s".formatted(statusCode));
        }

        try {
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            throw new ParseException("the response entity can't be parsed");
        }
    }


    private class JacksonSupport {
        final ObjectMapper om;

        {
            // om init
            om = new ObjectMapper();

            // 忽略多余字段
            om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }

        @SneakyThrows
        String toJsonStr(Object obj) {
            return om.writeValueAsString(obj);
        }

        @SneakyThrows
        <T> T toBean(String jsonStr, Class<T> exceptResponse) {
            return om.readValue(jsonStr, exceptResponse);
        }
    }
}
