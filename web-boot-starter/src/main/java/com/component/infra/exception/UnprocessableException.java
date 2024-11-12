package com.component.infra.exception;

import org.springframework.http.HttpStatus;

/**
 * Parameter model that can't be processed in business but checked successfully.
 *
 * @author Jinx
 */
public class UnprocessableException extends NestedRuntimeException {

    public UnprocessableException(String message) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, message);
    }
}
