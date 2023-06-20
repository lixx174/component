package com.component.wechat.model;

import com.component.wechat.core.WechatRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.SneakyThrows;
import org.apache.http.client.utils.URIBuilder;

import java.net.URI;

/**
 * @author jinx
 */
public record MobileRequest(@JsonIgnore String accessToken, String code) implements WechatRequest {

    @Override
    @SneakyThrows
    public URI constructUri() {
        final String baseUri = "https://api.weixin.qq.com/wxa/business/getuserphonenumber";

        return new URIBuilder(baseUri)
                .addParameter("access_token", accessToken)
                .build();
    }

    @Override
    public String toString() {
        return "getuserphonenumber";
    }
}
