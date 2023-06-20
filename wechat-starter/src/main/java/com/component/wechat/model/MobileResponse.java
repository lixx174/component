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
public class MobileResponse implements WechatResponse {
    @JsonProperty("errcode")
    private Integer errCode;
    @JsonProperty("errmsg")
    private String errMsg;
    @JsonProperty("phone_info")
    private PhoneInfo phoneInfo;

    public boolean success() {
        return errCode != null && 0 == errCode;
    }

    @Override
    public String errorTips() {
        return "[code:" + errCode + "] " + "[msg:" + errMsg + "]";
    }

    @Getter
    @Setter
    public static class PhoneInfo {
        /**
         * 用户绑定的手机号（国外手机号会有区号）
         */
        private String phoneNumber;
        /**
         * 没有区号的手机号
         */
        private String purePhoneNumber;
        /**
         * 区号
         */
        private Integer countryCode;
    }
}
