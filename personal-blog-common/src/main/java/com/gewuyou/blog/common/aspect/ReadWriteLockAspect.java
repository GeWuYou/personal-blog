package com.gewuyou.blog.common.aspect;

import com.gewuyou.blog.common.annotation.ReadLock;
import com.gewuyou.blog.common.annotation.WriteLock;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 读写锁切面
 *
 * @author gewuyou
 * @since 2024-08-08 19:51:02
 */
@Aspect
@Component
@Slf4j
public class ReadWriteLockAspect {
    private final RedissonClient redissonClient;

    @Autowired
    public ReadWriteLockAspect(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Around("@annotation(readLock)")
    public Object aroundReadLock(ProceedingJoinPoint joinPoint, ReadLock readLock) throws Throwable {
        RLock rLock = redissonClient.getReadWriteLock(readLock.value()).readLock();
        getLock(rLock, readLock.leaseTime(), readLock.unit());
        try {
            return joinPoint.proceed();
        } finally {
            if (rLock.isHeldByCurrentThread()) {
                rLock.unlock();
                log.info("Released read lock for: {}", readLock.value());
            }
        }
    }

    @Around("@annotation(writeLock)")
    public Object aroundWriteLock(ProceedingJoinPoint joinPoint, WriteLock writeLock) throws Throwable {
        RLock rLock = redissonClient.getReadWriteLock(writeLock.value()).writeLock();
        getLock(rLock, writeLock.leaseTime(), writeLock.unit());
        try {
            return joinPoint.proceed();
        } finally {
            if (rLock.isHeldByCurrentThread()) {
                rLock.unlock();
                log.info("Released write lock for: {}", writeLock.value());
            }
        }
    }

    private void getLock(RLock rLock, long leaseTime, TimeUnit unit) {
        if (leaseTime > 0) {
            rLock.lock(leaseTime, unit);
        } else {
            rLock.lock();
        }
        log.info("Acquired lock: {}", rLock.getName());
    }
}
