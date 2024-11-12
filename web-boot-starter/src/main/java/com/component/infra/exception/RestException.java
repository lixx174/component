package com.component.infra.exception;

import org.springframework.http.HttpStatus;

/**
 * Invoke exception, such as feign, http.
 *
 * @author Jinx
 */
public class RestException extends NestedRuntimeException {

    public RestException(String message) {
        super(HttpStatus.SERVICE_UNAVAILABLE, message);
    }
}
