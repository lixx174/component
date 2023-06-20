package com.component.wechat.core;

/**
 * @author jinx
 */
public interface WechatResponse {

    /**
     * 是否成功响应
     *
     * @return true：成功
     */
    boolean success();

    /**
     * 错误信息
     *
     * @return 响应提示
     */
    String errorTips();
}
