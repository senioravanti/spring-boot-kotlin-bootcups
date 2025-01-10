package ru.manannikov.bootcupsbackend.repos

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import ru.manannikov.bootcupsbackend.entities.MenuItemEntity

@ActiveProfiles("test")
@TestPropertySource("classpath:application.yaml")
@DataJpaTest
abstract class BaseRepoTest {
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
        protected fun listMenuItems(menuItems: List<MenuItemEntity>): String {
            val logMessage = StringBuilder("Результат запроса\n---\n\n")
            menuItems.forEach {
                logMessage.append(it).append("\n")
            }
            return logMessage.append("\n---").toString()
        }
    }
}