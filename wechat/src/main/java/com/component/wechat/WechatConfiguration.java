package com.component.wechat;

import lombok.Builder;
import lombok.Getter;

/**
 * @author jinx
 */
@Getter
@Builder
public class WechatConfiguration {

    /**
     * 响应结果校验
     * 如果开启 会对{@link com.component.wechat.core.WechatResponse} 进行业务校验
     */
    private boolean businessCheckEnable;
}
