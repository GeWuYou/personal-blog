package com.gewuyou.blog.common.exception;

import com.gewuyou.blog.common.enums.ResponseInformation;
import lombok.Getter;

import java.io.Serial;

/**
 * 全局异常
 *
 * @author gewuyou
 * @since 2024-04-13 下午1:44:14
 */
@Getter
public class GlobalException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    private final ResponseInformation responseInformation;

    public GlobalException(ResponseInformation responseInformation) {
        this.responseInformation = responseInformation;
    }

    public GlobalException(ResponseInformation responseInformation, Throwable cause) {
        super(cause);
        this.responseInformation = responseInformation;
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

    public String getErrorMessage() {
        return responseInformation.getMessage();
    }


    public int getErrorCode() {
        return responseInformation.getCode();
    }
}
