package com.example.practicespringdataredis.domain.repository

import org.springframework.context.annotation.Primary
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Primary
@Component
class BlockedUserRedisAdapter(
    private val redisTemplate: RedisTemplate<String, Int>
) : BlockedUserRepository {
    override fun report(userId: String) {
        redisTemplate.opsForValue().increment(userId)
    }

    override fun getReportedCount(userId: String): Int {
        return redisTemplate.opsForValue()[userId] ?: 0
    }

    override fun isBlocked(userId: String): Boolean {
        return (redisTemplate.opsForValue()[userId] ?: 0) >= 5
    }
}
