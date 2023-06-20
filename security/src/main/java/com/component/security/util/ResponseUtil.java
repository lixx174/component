package com.component.security.util;

import com.component.security.SecurityContext;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;

/**
 * @author jinx
 */
public class ResponseUtil {

    @SneakyThrows
    public static void response(HttpStatus httpStatus) {
        HttpServletResponse response = SecurityContext.getResponse();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(httpStatus.value());

        response.getWriter().write(httpStatus.getReasonPhrase());

        response.getWriter().flush();
        response.getWriter().close();
    }

}
