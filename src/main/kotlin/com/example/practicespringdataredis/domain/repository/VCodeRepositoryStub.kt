package com.example.practicespringdataredis.domain.repository

import org.springframework.stereotype.Repository

@Repository
class VCodeRepositoryStub : VCodeRepository {
    /**
     * 항상 사용 실패
     */
    override fun use(vCode: String) {
        throw IllegalStateException("always fail to use vCode")
    }
}
