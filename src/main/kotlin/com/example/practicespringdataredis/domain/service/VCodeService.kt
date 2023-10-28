package com.example.practicespringdataredis.domain.service

import com.example.practicespringdataredis.domain.repository.BlockedUserRepository
import com.example.practicespringdataredis.domain.repository.VCodeRepository
import org.springframework.stereotype.Service

@Service
class VCodeService(
    private val blockedUserRepository: BlockedUserRepository,
    private val vCodeRepository: VCodeRepository
) {
    fun use(vCode: String, userId: String) {
        check(!blockedUserRepository.isBlocked(userId)) { "blocked user" }

        runCatching { vCodeRepository.use(vCode) }
            .onFailure {
                blockedUserRepository.report(userId)
                throw it
            }
    }
}
