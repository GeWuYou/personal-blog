package com.gewuyou.blog.common.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gewuyou.blog.common.annotation.GlobalLogging;
import com.gewuyou.blog.common.enums.LoggingLevel;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.exception.GlobalException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 全局日志切面类
 *
 * @author gewuyou
 * @since 2024-04-13 下午4:39:30
 */
@Aspect// 用于证明当前类是一个切面类
@Component
public class GlobalLoggingAspect {
    /**
     * 正常返回后通知
     *
     * @param joinPoint     切入点
     * @param globalLogging 切入点的注解信息
     * @param result        返回值
     * @apiNote 记录日志，日志内容为方法名称、方法的参数和返回值
     * @since 2023/3/29 18:47
     */
    @AfterReturning(pointcut = "@annotation(globalLogging)", returning = "result")
    public void afterReturning(JoinPoint joinPoint, GlobalLogging globalLogging, Object result) {
        logBuild(joinPoint, globalLogging, result);
    }

    /**
     * 日志构建方法
     *
     * @param joinPoint     切入点
     * @param globalLogging 切入点注解信息
     * @param info          返回结果或者异常
     * @apiNote 解析结果或者异常构建日志
     * @since 2023/3/29 18:59
     */
    private void logBuild(JoinPoint joinPoint, GlobalLogging globalLogging, Object info) {
        // 获取到logger
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        // 获取到方法名
        String methodName = joinPoint.getSignature().getName();
        // 获取到方法的参数
        Object[] args = joinPoint.getArgs();
        // 获取日志级别
        LoggingLevel level = globalLogging.level();
        // 拼接日志内容
        String s = buildLogMessage(globalLogging, info, methodName, args);
        // 根据日志级别记录日志
        switch (level) {
            case TRACE -> logger.trace(s);
            case DEBUG -> logger.debug(s);
            case INFO -> logger.info(s);
            case WARN -> logger.warn(s);
            case ERROR -> logger.error(s);
        }
    }

    /**
     * 日志构建方法
     *
     * @param globalLogging 日志注解
     * @param info          异常或者返回结果
     * @param methodName    方法名称
     * @param args          参数数组
     * @return java.lang.String
     * @apiNote 通过解析注解中的内容构建相应的日志
     * @since 2023/3/29 18:50
     */
    private static String buildLogMessage(GlobalLogging globalLogging, Object info, String methodName, Object[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append("调用方法：").append(methodName);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // 是否记录参数
            if (globalLogging.logParams()) {
                // 将参数转为JSON字符串
                String argsJson = objectMapper.writeValueAsString(args);
                sb.append("，参数：").append(argsJson);
            }
            // 是否记录返回值或者异常信息
            if (globalLogging.logResult()) {
                // 将Result转为JSON字符串
                String resultJson = objectMapper.writeValueAsString(info);
                sb.append("，异常信息或者返回值：").append(resultJson);
            }
        } catch (JsonProcessingException e) {
            throw new GlobalException(ResponseInformation.LOG_BUILD_FAILED, e);
        }
        return sb.toString();
    }

    /**
     * 抛出异常后通知
     *
     * @param joinPoint     切入点
     * @param globalLogging 日志注解
     * @param e             异常
     * @apiNote 记录日志，日志内容为方法名称、方法的参数和异常信息
     * @since 2023/3/29 18:52
     */
    @AfterThrowing(pointcut = "@annotation(globalLogging)", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, GlobalLogging globalLogging, Exception e) {
        logBuild(joinPoint, globalLogging, e);
    }
}
