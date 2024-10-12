package com.jinx.component.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 统一响应模型
 *
 * @author Jinx
 **/
@Getter
@Setter
@AllArgsConstructor
public class Result<T> {

    private int code;
    private String msg;
    private T data;


    /**
     * FIXME 不序列化该方法
     */
    public boolean isSuccess() {
        return code == 200;
    }

    public static <T> Result<T> success() {
        return success((T) null);
    }

    public static <T> Result<T> success(T data) {
        return of(200, "success", data);
    }

    public static <T> Result<T> success(Runnable task) {
        task.run();
        return success();
    }

    public static <T> Result<T> fail(int code, String msg) {
        return of(code, msg, null);
    }

    public static <T> Result<T> of(int code, String msg, T data) {
        return new Result<>(code, msg, data);
    }
}