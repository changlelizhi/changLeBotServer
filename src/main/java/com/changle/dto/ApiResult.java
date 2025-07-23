package com.changle.dto;

import lombok.Setter;

/**
 * @author : 长乐
 * @package : com.changle.dto
 * @project : changLeBotServer
 * @name : ApiResult
 * @Date : 2025/7/23
 * @Description : api返回结果
 */
@Setter
public class ApiResult<T> {

    private int code;

    private String message;

    private T data;

    private long timestamp;

    public ApiResult() {
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * 成功响应（无数据）
     * @return
     * @param <T>
     */
    public static <T> ApiResult<T> success() {
        ApiResult<T> result = new ApiResult<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(ResultCode.SUCCESS.getMessage());
        return result;
    }

    /**
     * 成功响应（有数据）
     * @param data
     * @return
     * @param <T>
     */
    public static <T> ApiResult<T> success(T data) {
        ApiResult<T> result = new ApiResult<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(ResultCode.SUCCESS.getMessage());
        result.setData(data);
        return result;
    }

    /**
     * 失败响应
     * @return
     */
    public static <T> ApiResult<T> fail() {
        ApiResult<T> result = new ApiResult<>();
        result.setCode(ResultCode.FAIL.getCode());
        result.setMessage(ResultCode.FAIL.getMessage());
        return result;
    }

    /**
     * 失败响应
     * @param message
     * @return
     */
    public static <T> ApiResult<T> fail(String message) {
        ApiResult<T> result = new ApiResult<>();
        result.setCode(ResultCode.FAIL.getCode());
        result.setMessage(message);
        return result;
    }



}
