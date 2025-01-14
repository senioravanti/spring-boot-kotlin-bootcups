package ru.manannikov.bootcupsbackend.repos

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.ListCrudRepository
import org.springframework.data.repository.query.Param
import ru.manannikov.bootcupsbackend.entities.OrderItemEntity

interface OrderItemRepo : ListCrudRepository<OrderItemEntity, Long> {
    @Query("SELECT COUNT(oi) FROM OrderItemEntity oi WHERE oi.parentOrder.id = :orderId")
    fun countByOrderId(@Param("orderId") orderId: Int): Int

    @Query("SELECT oi FROM OrderItemEntity oi WHERE oi.parentOrder.id = :orderId")
    fun findByOrderId(@Param("orderId") orderId: Int): List<OrderItemEntity>
}