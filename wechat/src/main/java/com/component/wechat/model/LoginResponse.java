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
public class LoginResponse implements WechatResponse {
    @JsonProperty("session_key")
    private String sessionKey;
    @JsonProperty("unionid")
    private String unionId;
    @JsonProperty("openid")
    private String openId;
    @JsonProperty("errcode")
    private Integer errCode;
    @JsonProperty("errmsg")
    private String errMsg;

    public boolean success() {
        return errCode != null && 0 == errCode;
    }

    @Override
    public String errorTips() {
        return "[code:" + errCode + "] " + "[msg:" + errMsg + "]";
    }
}
