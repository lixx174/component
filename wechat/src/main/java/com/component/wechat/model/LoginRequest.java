package com.component.wechat.model;

import com.component.wechat.core.WechatRequest;
import lombok.SneakyThrows;
import org.apache.http.client.utils.URIBuilder;

import java.net.URI;

/**
 * @author jinx
 */
public record LoginRequest(String appId, String appSecret, String code) implements WechatRequest {

    @SneakyThrows
    public URI constructUri() {
        final String baseUri = "https://api.weixin.qq.com/sns/jscode2session";

        return new URIBuilder(baseUri)
                .addParameter("appid", appId)
                .addParameter("secret", appSecret)
                .addParameter("js_code", code)
                .addParameter("grant_type", "authorization_code")
                .build();
    }

    @Override
    public String toString() {
        return "jscode2session";
    }
}
