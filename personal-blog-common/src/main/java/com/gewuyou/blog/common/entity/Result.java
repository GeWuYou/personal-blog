package com.gewuyou.blog.common.entity;

import lombok.Data;

/**
 * 返回结果包装类
 *
 * @author gewuyou
 * @since 2024-04-13 上午12:24:04
 */
@Data
public class Result<T> {
    /**
     * 结果代码
     */
    private Integer code;
    /**
     * 响应信息
     */

    private String message;
    /**
     * 结果数据
     */
    private T data;

    private Result() {
    }

    public static Result<Void> ok() {
        Result<Void> result = new Result<>();
        result.setCode(200);
        result.setMessage("操作成功");
        return result;
    }

    public static <T> Result<T> ok(String message) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage(message);
        return result;
    }

    public static <T> Result<T> ok(String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public static Result<Void> error() {
        Result<Void> result = new Result<>();
        result.setCode(500);
        result.setMessage("操作失败");
        return result;
    }

    public static Result<Void> error(String message) {
        Result<Void> result = new Result<>();
        result.setCode(500);
        result.setMessage(message);
        return result;
    }

    public static Result<Void> error(Integer code, String message) {
        Result<Void> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
}
