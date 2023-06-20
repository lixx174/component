package com.component.wechat.exception;

/**
 * 业务异常 通常指请求成功了 但是业务失败
 *
 * @author jinx
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}
