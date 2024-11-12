package com.component.infra.config;


import com.component.infra.exception.AbstractExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * FIXME auto config condition fine-grained or on switch？
 *
 * @author Jinx
 */
@Configuration
@RequiredArgsConstructor
@ConditionalOnMissingBean(AbstractExceptionHandler.class)
public class ExceptionHandlerAutoConfiguration {

    private final Environment environment;

    /**
     * The default global exception handler when the conditions are met.
     * <p>
     * TODO However, the common handler method can't cover all scene, so need provide hook to extend.
     */
    @RestControllerAdvice
    public class DelegateExceptionHandler extends AbstractExceptionHandler {
        public DelegateExceptionHandler() {
            super(environment);
        }
    }
}
