package com.gewuyou.blog.common.decorator;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.task.TaskDecorator;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 安全上下文装饰器
 *
 * @author gewuyou
 * @since 2024-09-07 17:08:44
 */
public class SecurityContextDecorator implements TaskDecorator {
    /**
     * Decorate the given {@code Runnable}, returning a potentially wrapped
     * {@code Runnable} for actual execution, internally delegating to the
     * original {@link Runnable#run()} implementation.
     *
     * @param runnable the original {@code Runnable}
     * @return the decorated {@code Runnable}
     */
    @NotNull
    @Override
    public Runnable decorate(@NotNull Runnable runnable) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return () -> {
            SecurityContextHolder.setContext(securityContext);
            try {
                runnable.run();
            } finally {
                SecurityContextHolder.clearContext();
            }
        };
    }
}
