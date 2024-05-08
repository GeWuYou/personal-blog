package com.gewuyou.blog.common.entity;

import com.gewuyou.blog.common.enums.ResponseInformation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 返回结果包装类
 *
 * @author gewuyou
 * @since 2024-04-13 上午12:24:04
 */
@Schema(description = "返回结果包装类")
@Data
public class Result<T> implements Serializable {
    /**
     * 结果代码
     */
    @Schema(description = "结果代码", example = "200")
    private Integer code;
    /**
     * 是否成功
     */
    @Schema(description = "是否成功")
    private boolean success;
    /**
     * 响应信息
     */
    @Schema(description = "响应信息")
    private String message;
    /**
     * 结果数据
     */
    @Schema(description = "结果数据")
    private transient T data;

    private Result(ResponseInformation responseInformation, boolean success, T data) {
        this.code = responseInformation.getCode();
        this.success = success;
        this.message = responseInformation.getMessage();
        this.data = data;
    }

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.success = false;
    }

    /**
     * 无数据成功返回
     *
     * @return 成功结果
     */
    public static <T> Result<T> success() {
        return new Result<>(ResponseInformation.SUCCESS, true, null);
    }

    /**
     * 设置响应信息成功返回
     *
     * @param responseInformation 响应代码枚举
     * @param <T>                 泛型
     * @return 成功结果
     */
    public static <T> Result<T> success(ResponseInformation responseInformation) {
        return new Result<>(responseInformation, true, null);
    }

    /**
     * 有数据成功返回
     *
     * @param data 数据
     * @param <T>  泛型
     * @return 成功结果
     */

    public static <T> Result<T> success(T data) {
        return new Result<>(ResponseInformation.SUCCESS, true, data);
    }

    /**
     * 有数据设置响应信息成功返回
     *
     * @param responseInformation 响应代码枚举
     * @param data                数据
     * @param <T>                 泛型
     * @return 成功结果
     */

    public static <T> Result<T> success(ResponseInformation responseInformation, T data) {
        return new Result<>(responseInformation, true, data);
    }

    /**
     * 失败返回
     *
     * @return 失败结果
     */
    public static Result<String> failure() {
        return new Result<>(ResponseInformation.FAIL, false, null);
    }

    /**
     * 失败返回
     *
     * @return 失败结果
     */
    public static Result<String> failure(Integer code, String message) {
        return new Result<>(code, message);
    }


    /**
     * 设置响应信息失败返回
     *
     * @param responseInformation 响应代码枚举
     * @return 失败结果
     */
    public static Result<String> failure(ResponseInformation responseInformation) {
        return new Result<>(responseInformation, false, null);
    }
}



