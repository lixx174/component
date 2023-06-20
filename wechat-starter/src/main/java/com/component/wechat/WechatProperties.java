package com.component.wechat;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author jinx
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "component.wechat")
public class WechatProperties {

    /**
     * 响应结果校验
     * 如果开启 会对{@link com.component.wechat.core.WechatResponse} 进行业务校验
     */
    private boolean businessCheckEnable = true;
}
