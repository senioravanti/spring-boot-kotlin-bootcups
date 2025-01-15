package ru.manannikov.bootcupsbackend.repos

import org.apache.logging.log4j.LogManager
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.manannikov.bootcupsbackend.TestcontainersTest
import ru.manannikov.bootcupsbackend.mappers.EmployeeMapper
import ru.manannikov.bootcupsbackend.repos.specs.Specifications
import ru.manannikov.bootcupsbackend.utils.EMPLOYEE_FIRST_NAME
import ru.manannikov.bootcupsbackend.utils.EMPLOYEE_LAST_NAME
import ru.manannikov.bootcupsbackend.utils.EMPLOYEE_MIDDLE_NAME

@SpringBootTest
class EmployeeRepoTests : TestcontainersTest() {
    @Autowired
    lateinit var employeeRepo: EmployeeRepo

    @Autowired
    lateinit var employeeMapper: EmployeeMapper

    @Test
    fun testFindAllByFullName() {
        val employee1 = employeeRepo.findAll(
            Specifications.employeeFilter(
                mapOf(
                    EMPLOYEE_LAST_NAME to "Мананников",
                    EMPLOYEE_FIRST_NAME to "Антон",
                    EMPLOYEE_MIDDLE_NAME to "Олегович"
                )
            )
        ).first()
        val employee2 = employeeRepo.findByEmail("senioravanti@vk.com")

        logger.info("\n---\nemployee1:\n{}\nemployee2:\n{}\n***", employee1, employee2)
        assertEquals(employee1, employee2)
        logger.info("employeeDto:\n{}\n---", employeeMapper.toDto(employee1))
    }

    companion object {
        private val logger = LogManager.getLogger(EmployeeRepoTests::class.java)
    }
}