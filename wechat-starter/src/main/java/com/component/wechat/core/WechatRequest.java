package com.component.wechat.core;

import java.net.URI;

/**
 * @author jinx
 */
public interface WechatRequest {

    /**
     * 请求uri
     *
     * @return 包含请求地址，url参数
     */
    URI constructUri();

}
