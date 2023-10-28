package com.example.practicespringdataredis.domain.repository

interface BlockedUserRepository {
    fun report(userId: String)
    fun getReportedCount(userId: String): Int
    fun isBlocked(userId: String): Boolean
}
