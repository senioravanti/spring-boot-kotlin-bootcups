package ru.manannikov.bootcupsbackend.utils

import jakarta.persistence.Table
import org.apache.logging.log4j.LogManager
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Order
import ru.manannikov.bootcupsbackend.enums.FieldEnum
import ru.manannikov.bootcupsbackend.enums.OrderSortFields
import ru.manannikov.bootcupsbackend.enums.OrderStatus
import kotlin.enums.EnumEntries
import kotlin.reflect.KClass

object ServiceUtils {
    private val logger = LogManager.getLogger(ServiceUtils::class.java)

    private val preflightRegex = Regex("^_")
    private val snakeRegex = Regex("_[a-zA-Z]")

    fun tableNameFromEntity(entityClass: KClass<*>): String = (entityClass.annotations.find { it.annotationClass == Table::class } as? Table) ?. name ?: "unknowns"

    fun snakeToCamelCase(snakeCase: String) = snakeRegex.replace(preflightRegex.replace(snakeCase, "")) {
        it.value.replace("_", "").uppercase()
    }

    fun stringToOrderStatus(statusString: String) = OrderStatus.valueOf(statusString.uppercase())

    /**
     * Названия ключей, передаваемых в контроллер -> в snake case;
     * Множество допустимых ключей -> в snake case;
     * Названия полей, передаваемых Order -> в camelCase
     */
    fun sortFromSortCriteria(sortCriteria: List<String>, validKeys: List<String>): Sort {
        logger.debug("validKeys = {}", validKeys)
        val sortDirectionRegex = Regex("asc|desc")
        val sortOrders = sortCriteria.map { criteria ->

            val index = criteria.lastIndexOf('_')

            val sortField = criteria.substring(0, index)
            val sortDirection = criteria.substring(index+1)

            if (!validKeys.any { it == sortField } || !sortDirectionRegex.matches(sortDirection)) {
                logger.debug("criteria = {}, sortField = {}, sortDirection = {}", criteria, sortField, sortDirection)
                throw IllegalArgumentException("exception.illegal-argument.sort-criteria")
            }
            Order(
                Sort.Direction.fromString(sortDirection),
                snakeToCamelCase(sortField)
            )
        }
        return Sort.by(sortOrders)
    }
    fun sortCriteriaToPageRequest(
        sortCriteria: List<String>,
        pageRequest: PageRequest,
        fieldEnumEntries: List<FieldEnum>
    ): PageRequest = pageRequest.withSort(
        sortFromSortCriteria(
            sortCriteria,
            fieldEnumEntries.map { it.fieldKey }
        )
    )

}