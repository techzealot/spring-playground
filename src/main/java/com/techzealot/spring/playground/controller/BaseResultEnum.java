package com.techzealot.spring.playground.controller;

public enum BaseResultEnum implements BaseEnum {


    FAILED(0, "Fail"),
    SUCCESS(1, "Success"),
    INTERNAL_ERROR(100001, "系统繁忙，请稍后"),
    INVALID_PARAMETER(100002, "无效参数"),
    PERMISSION_DENIED(100003, "权限不足"),
    RPC_ERROR(100007, "服务暂时不可用"),
    SIGNATURE_ERROR(100008, "签名错误"),
    ;


    public final int code;

    public final String message;

    BaseResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
