package com.example.practicespringdataredis.domain.service

import com.example.practicespringdataredis.domain.repository.ReportedCountRepository
import com.example.practicespringdataredis.domain.repository.VCodeRepository
import org.springframework.stereotype.Service

@Service
class VCodeService(
    private val reportedCountRepository: ReportedCountRepository,
    private val vCodeRepository: VCodeRepository
) {
    fun use(vCode: String, userId: String) {
        check(!isBlocked(userId)) { "blocked user" }

        runCatching { vCodeRepository.use(vCode) }
            .onFailure {
                reportedCountRepository.report(userId)
                throw it
            }
    }

    fun isBlocked(userId: String): Boolean {
        return reportedCountRepository.getReportedCount(userId) >= 5
    }
}
