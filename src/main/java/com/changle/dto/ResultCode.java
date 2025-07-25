package com.changle.dto;

import lombok.Getter;

/**
 * @author : 长乐
 * @package : com.changle.dto
 * @project : changLeBotServer
 * @name : ResultCode
 * @Date : 2025/7/23
 * @Description : 响应码枚举
 */
@Getter
public enum ResultCode {

    SUCCESS(200, "成功"),
    FAIL(500, "失败"),
    UNAUTHORIZED(401, "未认证"),
    NOT_FOUND(404, "未找到"),
    FORBIDDEN(403, "禁止访问"),
    SERVER_ERROR(500, "服务器错误");

    private final Integer code;
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getMessage(Integer code) {
        for (ResultCode value : values()) {
            if (value.getCode().equals(code)) {
                return value.getMessage();
            }
        }
        return null;
    }

}
