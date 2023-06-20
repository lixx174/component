package com.component.wechat.exception;

/**
 * 远程调用异常
 *
 * @author jinx
 */
public class RestException extends RuntimeException {

    public RestException(String message) {
        super(message);
    }
}
