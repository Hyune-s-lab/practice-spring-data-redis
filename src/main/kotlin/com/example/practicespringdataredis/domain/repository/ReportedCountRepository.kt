package com.example.practicespringdataredis.domain.repository

interface ReportedCountRepository {
    fun report(userId: String)
    fun getReportedCount(userId: String): Int
}
