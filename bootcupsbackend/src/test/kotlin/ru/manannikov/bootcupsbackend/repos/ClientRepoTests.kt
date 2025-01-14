package ru.manannikov.bootcupsbackend.repos

import org.apache.logging.log4j.LogManager
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import ru.manannikov.bootcupsbackend.TestcontainersTest
import ru.manannikov.bootcupsbackend.enums.RegistrationState

@DataJpaTest
class ClientRepoTests : TestcontainersTest() {
    @Autowired
    lateinit var clientRepo: ClientRepo

    @Test
    fun testFindByRegistrationState() {
        val clients = clientRepo.findByRegistrationState(RegistrationState.REGISTERED)

        logger.info("clients:\n{}", clients)
        assertEquals(4, clients.size)
    }

    companion object {
        private val logger = LogManager.getLogger(ClientRepoTests::class.java)
    }
}