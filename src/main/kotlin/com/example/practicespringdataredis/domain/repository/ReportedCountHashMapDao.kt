package com.example.practicespringdataredis.domain.repository

import org.springframework.stereotype.Repository

@Repository
class ReportedCountHashMapDao : ReportedCountRepository {
    private val users = HashMap<String, Int>()

    override fun report(userId: String) {
        users[userId]?.let {
            users[userId] = it + 1
        } ?: run {
            users[userId] = 1
        }
    }

    override fun getReportedCount(userId: String): Int {
        return users[userId] ?: 0
    }

    override fun getExpire(userId: String): Long {
        TODO("Not yet implemented")
    }
}
