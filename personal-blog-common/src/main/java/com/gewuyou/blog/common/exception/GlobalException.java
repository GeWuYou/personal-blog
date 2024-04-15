package com.gewuyou.blog.common.exception;

import java.io.Serial;

/**
 * 全局异常
 *
 * @author gewuyou
 * @since 2024-04-13 下午1:44:14
 */
public class GlobalException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 错误码
     */
    private final int errorCode;

    /**
     * 错误信息
     */
    private final String errorMessage;

    public GlobalException(int errorCode, String errorMessage) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }


    public GlobalException(int errorCode, String errorMessage, Throwable cause) {
        super(cause);
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    /**
     * Fills in the execution stack trace. This method records within this
     * {@code Throwable} object information about the current state of
     * the stack frames for the current thread.
     *
     * <p>If the stack trace of this {@code Throwable} {@linkplain
     * Throwable#Throwable(String, Throwable, boolean, boolean) is not
     * writable}, calling this method has no effect.
     *
     * @return a reference to this {@code Throwable} instance.
     * @see Throwable#printStackTrace()
     */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

    public int getErrorCode() {
        return errorCode;
    }


    public String getErrorMessage() {
        return errorMessage;
    }
}
