package com.example.practicespringdataredis.domain.repository

import org.springframework.context.annotation.Primary
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration

@Primary
@Component
class ReportedCountRedisAdapter(
    private val reportedCountRedisTemplate: RedisTemplate<String, Int>
) : ReportedCountRepository {
    override fun report(userId: String) {
        reportedCountRedisTemplate.opsForValue().increment(userId.toKey())
        reportedCountRedisTemplate.expire(userId.toKey(), DEFAULT_TIMEOUT)
    }

    override fun getReportedCount(userId: String): Int {
        return reportedCountRedisTemplate.opsForValue()[userId.toKey()] ?: 0
    }

    override fun getExpire(userId: String): Long {
        return reportedCountRedisTemplate.getExpire(userId.toKey())
    }

    companion object {
        // key space 가 많아진다면 key 생성 책임을 분리할 수도 있습니다.
        private fun String.toKey(): String = "reported_count:$this"
        private val DEFAULT_TIMEOUT = Duration.ofMinutes(1)
    }
}
