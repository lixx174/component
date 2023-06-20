package com.component.wechat.model;

import com.component.wechat.core.WechatRequest;
import lombok.SneakyThrows;
import org.apache.http.client.utils.URIBuilder;

import java.net.URI;

/**
 * @author jinx
 */
public record AccessRequest(String appId, String appSecret) implements WechatRequest {

    @Override
    @SneakyThrows
    public URI constructUri() {
        final String baseUri = "https://api.weixin.qq.com/cgi-bin/token";

        return new URIBuilder(baseUri)
                .addParameter("grant_type", "client_credential")
                .addParameter("appid", appId)
                .addParameter("secret", appSecret)
                .build();
    }

    @Override
    public String toString() {
        return "token";
    }
}
