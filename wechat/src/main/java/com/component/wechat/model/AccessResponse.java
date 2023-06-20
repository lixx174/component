package com.component.wechat.model;

import com.component.wechat.core.WechatResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author jinx
 */
@Getter
@Setter
public class AccessResponse implements WechatResponse {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("expires_in")
    private Long expiresIn;

    public boolean success() {
        return accessToken != null && !accessToken.isEmpty();
    }

    @Override
    public String errorTips() {
        return "[code:500] [msg: 未知]";
    }
}
