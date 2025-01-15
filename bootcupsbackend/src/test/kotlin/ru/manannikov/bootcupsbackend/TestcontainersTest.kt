package ru.manannikov.bootcupsbackend

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import org.testcontainers.containers.PostgreSQLContainer

@ActiveProfiles("test")
@TestPropertySource("classpath:application.yaml")
abstract class TestcontainersTest {
    companion object {
        private val POSTGRES_CONTAINER: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:17.2").withDatabaseName("repo_tests").withReuse(true)

        @JvmStatic
        @BeforeAll
        fun init() {
            POSTGRES_CONTAINER.start()
        }

        @JvmStatic
        @AfterAll
        fun destroy() {
            POSTGRES_CONTAINER.stop()
        }

        @JvmStatic
        protected fun listEntities(menuItems: List<Any>, message: String = "Результат запроса"): String {
            val logMessage = StringBuilder("$message\n---\n\n")
            menuItems.forEach {
                logMessage.append(it).append("\n")
            }
            return logMessage.append("\n---").toString()
        }
    }
}