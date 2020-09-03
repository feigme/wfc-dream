package com.wfc.starter.web;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 飞影
 * @create by 2019-09-03 22:24
 */
@Data
public class RestResult<T> implements Serializable {

    private int code;
    private boolean success;
    private String message;
    private T data;
    private Integer totalCount;
    private Integer pageSize;

    public RestResult() {
    }

    public static <T> RestResult newInstance(int code, String message, T data, Integer totalCount, Integer pageSize) {
        RestResult result = new RestResult();
        result.code = code;
        result.success = code == 200;
        result.message = message;
        result.data = data;
        result.totalCount = totalCount;
        result.pageSize = pageSize;
        return result;
    }

    public static <T> RestResult newInstance(int code, String message, T data) {
        return newInstance(code, message, data, null, null);
    }

    public static RestResult success() {
        return newInstance(200, null, null);
    }

    public static <T> RestResult success(T data) {
        return newInstance(200, null, data);
    }

    public static <T> RestResult success(T data, Integer totalCount, Integer pageSize) {
        return newInstance(200, null, data, totalCount, pageSize);
    }

    public static RestResult invalidParams(String message) {
        return newInstance(400, message, null);
    }

    public static RestResult failure(String message) {
        return newInstance(500, message, null);
    }

}
