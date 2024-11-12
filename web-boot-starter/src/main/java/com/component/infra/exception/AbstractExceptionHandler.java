package com.component.infra.exception;

import com.component.application.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.Assert;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Exception handle globally.
 *
 * @author Jinx
 */
@Slf4j
public abstract class AbstractExceptionHandler {

    public AbstractExceptionHandler(Environment environment) {
        // FIXME do something about environment
        Assert.notNull(environment, "...");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> validException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getFieldErrors().getFirst();

        return Result.fail(
                HttpStatus.BAD_REQUEST.value(),
                fieldError.getDefaultMessage() + " [%s]".formatted(fieldError.getRejectedValue())
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result<Void> badRequestException(HttpMessageNotReadableException e) {
        Throwable rootCause = e.getRootCause();

        return Result.fail(
                HttpStatus.BAD_REQUEST.value(),
                rootCause == null ? e.getMessage() : rootCause.getMessage()
        );
    }

    @ExceptionHandler({
            MissingServletRequestParameterException.class,
            HttpRequestMethodNotSupportedException.class,
            IllegalArgumentException.class
    })
    public Result<Void> badRequestException(Exception e) {
        return Result.fail(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public Result<Void> unprocessableException(UnsupportedOperationException e) {
        return Result.fail(HttpStatus.UNPROCESSABLE_ENTITY.value(), e.getMessage());
    }

    @ExceptionHandler(NestedRuntimeException.class)
    public Result<Void> restException(NestedRuntimeException e) {
        return Result.fail(e.getCode(), e.getMessage());
    }


    @ExceptionHandler(Exception.class)
    public Result<Void> exception(Exception e) {
        log.error(e.getMessage(), e);

        return Result.fail(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()
        );
    }
}
