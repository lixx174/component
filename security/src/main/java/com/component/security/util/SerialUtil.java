package com.component.security.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.SneakyThrows;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author jinx
 */
public class SerialUtil {
    private final static ObjectMapper OM;
    public static DateTimeFormatter STANDER_DATETIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static DateTimeFormatter STANDER_DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    static {
        OM = new ObjectMapper();
        OM.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        OM.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(STANDER_DATETIME_FORMATTER));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(STANDER_DATETIME_FORMATTER));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(STANDER_DATE_FORMATTER));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(STANDER_DATE_FORMATTER));
        OM.registerModule(javaTimeModule);
    }

    @SneakyThrows
    public static String serial(Object source) {
        return OM.writeValueAsString(source);
    }

    @SneakyThrows
    public static <T> T deSerial(String source, Class<T> clazz) {
        if (StringUtils.hasText(source)) {
            return OM.readValue(source, clazz);
        }

        return clazz.getConstructor().newInstance();
    }

}
