package com.component.infra.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Wrapper of runtimeException.
 *
 * @author Jinx
 */
@Getter
@RequiredArgsConstructor
public class NestedRuntimeException extends RuntimeException {

    private final int code;

    public NestedRuntimeException(HttpStatus code, String message) {
        super(message);

        this.code = code.value();
    }
}
