package com.techzealot.spring.playground.controller;

import java.io.Serializable;
import lombok.Data;

@Data
public class ApiResult<T> implements Serializable {

    private static final long serialVersionUID = 796464608076507729L;
    private int code = 0;
    private String msg;
    private T data;

    private ApiResult() {
    }

    private ApiResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private ApiResult(BaseEnum constant, T data) {
        this(constant.getCode(), constant.getMessage(), data);
    }

    private ApiResult(BaseEnum constant, String message, T data) {
        this(constant.getCode(), message, data);
    }

    public static <T> ApiResult<T> success() {
        return new ApiResult(BaseResultEnum.SUCCESS, null);
    }

    public static <T> ApiResult<T> success(String msg) {
        return new ApiResult(BaseResultEnum.SUCCESS.code, msg, null);
    }

    public static <T> ApiResult<T> success(T data) {
        return new ApiResult(BaseResultEnum.SUCCESS, data);
    }

    public static <T> ApiResult success(String msg, T data) {
        return new ApiResult(BaseResultEnum.SUCCESS.code, msg, data);
    }

    public static <T> ApiResult<T> error() {
        return new ApiResult(BaseResultEnum.FAILED, null);
    }

    public static <T> ApiResult<T> error(String msg) {
        return new ApiResult(BaseResultEnum.FAILED.code, msg, null);
    }

    public static <T> ApiResult<T> error(T data) {
        return new ApiResult(BaseResultEnum.FAILED, data);
    }

    public static <T> ApiResult<T> error(String msg, T data) {
        return new ApiResult(BaseResultEnum.FAILED.code, msg, data);
    }

    public static <T> ApiResult<T> error(BaseEnum constant) {
        return new ApiResult(constant.getCode(), constant.getMessage(), null);
    }

    public static <T> ApiResult<T> error(BaseEnum constant, String message) {
        return new ApiResult(constant.getCode(), message, null);
    }

    public static ApiResult error(int status, String message) {
        return new ApiResult(status, message, null);
    }

    public static <T> ApiResult<T> error(BaseEnum constant, T data) {
        return new ApiResult(constant, data);
    }
}
