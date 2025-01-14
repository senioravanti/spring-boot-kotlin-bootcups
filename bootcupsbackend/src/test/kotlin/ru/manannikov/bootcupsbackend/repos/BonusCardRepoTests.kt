package ru.manannikov.bootcupsbackend.repos

import org.apache.logging.log4j.LogManager
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import ru.manannikov.bootcupsbackend.TestcontainersTest

@DataJpaTest
class BonusCardRepoTests : TestcontainersTest() {
    @Autowired
    lateinit var clientRepo: ClientRepo
    @Autowired
    lateinit var bonusCardRepo: BonusCardRepo

    @Test
    fun testFindByClientId() {
        val client = clientRepo.findById(2L).orElse(null)
        val bonusCard = bonusCardRepo.findByClientId(2L)

        assertAll(
            { assertNotNull(client) },
            { assertNotNull(bonusCard) }
        )

        logger.info("client: {}, bonusCard: {}", client, bonusCard)
    }

    companion object {
        private val logger = LogManager.getLogger(BonusCardRepoTests::class.java)
    }
}