package com.gewuyou.blog.common.handler;

import com.gewuyou.blog.common.entity.Result;
import com.gewuyou.blog.common.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 全局异常处理类
 *
 * @author gewuyou
 * @since 2024-04-13 上午12:22:18
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 异常处理器
     *
     * @param e 异常
     * @return com.gewuyou.blog.common.entity.Result<java.lang.Void>
     * @apiNote
     * @since 2024/4/13 上午12:29
     */
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return Result.error(e.getMessage());
    }

    /**
     * 全局异常处理器
     *
     * @param e 异常
     * @return com.gewuyou.blog.common.entity.Result<java.lang.Void>
     * @apiNote
     * @since 2024/4/13 下午1:56
     */
    @ExceptionHandler(GlobalException.class)
    public Result<Void> handleGlobalException(GlobalException e) {
        log.error(e.getErrorMessage(), e);
        return Result.error(e.getErrorCode(), e.getErrorMessage());
    }


}
