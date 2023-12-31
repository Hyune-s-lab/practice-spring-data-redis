package com.example.practicespringdataredis.domain.service

import com.example.practicespringdataredis.domain.repository.ReportedCountRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.Spec
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.RedisTemplate
import java.util.*

@SpringBootTest
class VCodeServiceTest(
    private val reportedCountRepository: ReportedCountRepository,
    private val vCodeService: VCodeService,

    private val reportedCountRedisTemplate: RedisTemplate<String, Int>
) : DescribeSpec({
    describe("VCodeService 기본 테스트") {
        val vCode = UUID.randomUUID().toString().uppercase().replace("-", "")
        val userId = "testUser12"

        describe("V 코드를 사용하면 실패한다.") {
            shouldThrow<IllegalStateException> {
                vCodeService.use(vCode, userId)
            }.message shouldBe "always fail to use vCode"

            it("사용 시도한 userId 의 reported count 가 증가한다.") {
                reportedCountRepository.getReportedCount(userId) shouldBe 1
            }

            it("reported count 의 expire time 이 존재한다.") {
                reportedCountRepository.getExpire(userId) shouldBeGreaterThan 0
            }
        }

        describe("V 코드를 4회 더 사용해도 실패한다.") {
            repeat(4) {
                shouldThrow<IllegalStateException> {
                    vCodeService.use(vCode, userId)
                }.message shouldBe "always fail to use vCode"
            }

            it("5회 사용 실패한 userId 는 blocked 된다.") {
                reportedCountRepository.getReportedCount(userId) shouldBe 5
                vCodeService.isBlocked(userId) shouldBe true
            }
        }

        it("blocked user 는 사용 시도를 하기 전에 검증된다.") {
            repeat(5) {
                shouldThrow<IllegalStateException> {
                    vCodeService.use(vCode, userId)
                }.message shouldBe "blocked user"
            }
        }
    }
}) {
    override suspend fun beforeSpec(spec: Spec) {
        super.beforeSpec(spec)

        reportedCountRedisTemplate.keys("*").forEach(reportedCountRedisTemplate::delete)
    }
}
