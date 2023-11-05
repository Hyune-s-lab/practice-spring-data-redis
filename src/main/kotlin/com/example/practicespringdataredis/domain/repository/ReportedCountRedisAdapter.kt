package com.example.practicespringdataredis.domain.repository

import org.springframework.context.annotation.Primary
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Primary
@Component
class ReportedCountRedisAdapter(
    private val reportedCountRedisTemplate: RedisTemplate<String, Int>
) : ReportedCountRepository {
    override fun report(userId: String) {
        reportedCountRedisTemplate.opsForValue().increment(userId.toKey())
    }

    override fun getReportedCount(userId: String): Int {
        return reportedCountRedisTemplate.opsForValue()[userId.toKey()] ?: 0
    }

    // key space 가 많아진다면 key 생성 책임을 분리할 수도 있습니다.
    private fun String.toKey(): String = "reported_count:$this"
}
