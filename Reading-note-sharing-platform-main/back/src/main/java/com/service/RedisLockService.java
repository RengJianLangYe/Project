package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisLockService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 获取编辑锁
     * @param resourceType 资源类型
     * @param resourceId 资源ID
     * @param userId 用户ID
     * @param ttlSeconds 锁超时时间（秒）
     * @return lockToken 成功返回token，失败返回null
     */
    public String acquireEditLock(String resourceType, String resourceId, String userId, int ttlSeconds) {
        String lockKey = "edit_lock:" + resourceType + ":" + resourceId;
        String lockToken = userId + ":" + System.currentTimeMillis();

        // SET NX EX 原子操作：不存在则设置，并设置过期时间
        Boolean acquired = redisTemplate.opsForValue()
                .setIfAbsent(lockKey, lockToken, ttlSeconds, TimeUnit.SECONDS);

        return Boolean.TRUE.equals(acquired) ? lockToken : null;
    }

    /**
     * 释放编辑锁（验证token）
     */
    public boolean releaseEditLock(String resourceType, String resourceId, String lockToken) {
        String lockKey = "edit_lock:" + resourceType + ":" + resourceId;
        String currentToken = redisTemplate.opsForValue().get(lockKey);

        if (lockToken.equals(currentToken)) {
            return Boolean.TRUE.equals(redisTemplate.delete(lockKey));
        }
        return false;
    }

    /**
     * 续期锁（延长过期时间）
     */
    public boolean renewEditLock(String resourceType, String resourceId, String lockToken, int ttlSeconds) {
        String lockKey = "edit_lock:" + resourceType + ":" + resourceId;
        String currentToken = redisTemplate.opsForValue().get(lockKey);

        if (lockToken.equals(currentToken)) {
            return Boolean.TRUE.equals(redisTemplate.expire(lockKey, ttlSeconds, TimeUnit.SECONDS));
        }
        return false;
    }

    /**
     * 获取当前持有锁的用户
     */
    public String getCurrentEditor(String resourceType, String resourceId) {
        String lockKey = "edit_lock:" + resourceType + ":" + resourceId;
        String lockToken = redisTemplate.opsForValue().get(lockKey);
        if (lockToken != null && lockToken.contains(":")) {
            return lockToken.substring(0, lockToken.indexOf(":"));
        }
        return null;
    }
}