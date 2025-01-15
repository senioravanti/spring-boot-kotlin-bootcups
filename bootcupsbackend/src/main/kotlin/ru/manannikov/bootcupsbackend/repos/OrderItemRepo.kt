package ru.manannikov.bootcupsbackend.repos

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Meta
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.ListCrudRepository
import org.springframework.data.repository.query.Param
import ru.manannikov.bootcupsbackend.entities.OrderItemEntity

interface OrderItemRepo : ListCrudRepository<OrderItemEntity, Long> {
    @Query(COUNT_QUERY)
    fun countByOrderId(@Param("orderId") orderId: Int): Int

    @Query(
        value = "SELECT oi FROM OrderItemEntity oi WHERE oi.parentOrder.id = :orderId",
        countQuery = COUNT_QUERY
    )
    fun findByOrderId(@Param("orderId") orderId: Int, pageRequest: Pageable): Page<OrderItemEntity>

    companion object {
        const val COUNT_QUERY = "SELECT COUNT(oi) FROM OrderItemEntity oi WHERE oi.parentOrder.id = :orderId"
    }
}