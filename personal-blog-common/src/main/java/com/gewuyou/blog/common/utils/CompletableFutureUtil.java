package com.gewuyou.blog.common.utils;

import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * CompletableFuture工具类
 *
 * @author gewuyou
 * @since 2024-08-22 09:30:44
 */
@Slf4j
public class CompletableFutureUtil {
    private CompletableFutureUtil() {

    }

    /**
     * 异步执行Supplier，并返回CompletableFuture对象
     *
     * @param supplier 异步执行的Supplier
     * @param executor 执行的线程池
     * @param <T>      返回值类型
     * @return CompletableFuture对象
     * @apiNote 这个和CompletableFuture.supplyAsync()没什么区别就是加了个exceptionally处理异常
     */
    public static <T> CompletableFuture<T> supplyAsyncWithExceptionAlly(Supplier<T> supplier, Executor executor) {
        return CompletableFuture
                .supplyAsync(supplier, executor)
                .exceptionally(e -> {
                    throw new GlobalException(ResponseInformation.ASYNC_EXCEPTION);
                });
    }

    /**
     * 异步执行Runnable，并返回CompletableFuture对象
     *
     * @param runnable 异步执行的Runnable
     * @param executor 执行的线程池
     * @return CompletableFuture对象
     */
    public static CompletableFuture<Void> runAsyncWithExceptionAlly(Runnable runnable, Executor executor) {
        return CompletableFuture
                .runAsync(runnable, executor)
                .exceptionally(e -> {
                    throw new GlobalException(ResponseInformation.ASYNC_EXCEPTION);
                });
    }

    /**
     * 异步执行Runnable，并返回CompletableFuture对象
     *
     * @param runnable 异步执行的Runnable
     * @param executor 执行的线程池
     * @return CompletableFuture对象
     */
    public static CompletableFuture<Void> runAsyncWithExceptionAlly(Runnable runnable, Executor executor, Function<Throwable, ? extends Void> fn) {
        return CompletableFuture
                .runAsync(runnable, executor)
                .exceptionally(fn);
    }

    /**
     * 异步执行Supplier，并返回CompletableFuture对象
     *
     * @param supplier 异步执行的Supplier
     * @param executor 执行的线程池
     * @param <T>      返回值类型
     * @return CompletableFuture对象
     * @apiNote 这个和CompletableFuture.supplyAsync()没什么区别就是加了个exceptionally处理异常
     */
    public static <T> CompletableFuture<T> supplyAsyncWithExceptionAlly(Supplier<T> supplier, Executor executor, Function<Throwable, ? extends T> fn) {
        return CompletableFuture
                .supplyAsync(supplier, executor)
                .exceptionally(fn);
    }
}
