package com.gewuyou.blog.common.aspect;

import com.gewuyou.blog.common.annotation.Idempotent;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.exception.GlobalException;
import com.gewuyou.blog.common.service.IRedisService;
import com.gewuyou.blog.common.utils.IpUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * 幂等切面
 *
 * @author gewuyou
 * @since 2024-07-20 上午12:00:39
 */
@Aspect
@Component
@Slf4j
public class IdempotentAspect {
    private final IRedisService redisService;
    private final HttpServletRequest request;

    @Autowired
    public IdempotentAspect(IRedisService redisService, HttpServletRequest request) {
        this.redisService = redisService;
        this.request = request;
    }

    @Around("@annotation(idempotent)")
    public Object around(ProceedingJoinPoint joinPoint, Idempotent idempotent) throws Throwable {
        String key = getIdempotentKey(joinPoint, request);

        // 尝试在 Redis 中设置唯一键，设置成功表示首次请求，否则是重复请求
        Boolean isFirstRequest = redisService.setIfAbsent(key, true, Duration.ofSeconds(idempotent.delay()));

        if (Boolean.FALSE.equals(isFirstRequest)) {
            // 如果键已存在，表示重复请求
            throw new GlobalException(ResponseInformation.REPEAT_REQUEST);
        }

        try {
            return joinPoint.proceed();
        } finally {
            // 请求处理完成后，延迟 5 秒后清除 Redis 中的键
            redisService.delayedDelete(key, idempotent.delay(), TimeUnit.SECONDS);
        }
    }

    private String getIdempotentKey(ProceedingJoinPoint joinPoint, HttpServletRequest request) {
        // 根据请求参数生成唯一键，可以根据实际情况调整
        // 使用了mac地址、uri方法名和参数的哈希值作为键
        var ip = IpUtil.getIpAddress(request);
        var mac = IpUtil.getMACAddress(ip);
        var methodName = joinPoint.getSignature().getName();
        var params = Arrays.toString(joinPoint.getArgs());
        log.info("mac: {}, method: {}, args: {}", mac, params, methodName);
        return "idempotent:" + mac + ":" + methodName + ":" + params.hashCode();
    }
}
