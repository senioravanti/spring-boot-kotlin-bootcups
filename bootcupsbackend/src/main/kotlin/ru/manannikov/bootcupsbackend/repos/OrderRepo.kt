package ru.manannikov.bootcupsbackend.repos

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.ListCrudRepository
import org.springframework.data.repository.ListPagingAndSortingRepository
import org.springframework.data.repository.query.Param
import ru.manannikov.bootcupsbackend.entities.OrderEntity
import ru.manannikov.bootcupsbackend.enums.OrderStatus

interface OrderRepo : ListCrudRepository<OrderEntity, Int>, ListPagingAndSortingRepository<OrderEntity, Int> {
    @Query("SELECT o FROM OrderEntity o WHERE o.status = :status")
    fun findByStatus(@Param("status") status: OrderStatus): List<OrderEntity>
}