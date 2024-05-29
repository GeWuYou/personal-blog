package com.gewuyou.blog.common.exception;

import lombok.Getter;

import java.io.Serial;

/**
 * 任务异常
 *
 * @author gewuyou
 * @since 2024-05-29 下午3:59:34
 */
@Getter
public class TaskException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    private final Code code;

    public TaskException(String msg, Code code) {
        this(msg, code, null);
    }

    public TaskException(String msg, Code code, Exception exception) {
        super(msg, exception);
        this.code = code;
    }

    public enum Code {
        TASK_EXISTS, NO_TASK_EXISTS, TASK_ALREADY_STARTED, UNKNOWN, CONFIG_ERROR, TASK_NODE_NOT_AVAILABLE
    }
}
