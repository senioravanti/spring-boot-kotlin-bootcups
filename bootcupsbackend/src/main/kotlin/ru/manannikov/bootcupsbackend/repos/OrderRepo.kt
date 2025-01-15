package ru.manannikov.bootcupsbackend.repos

import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Meta
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.ListCrudRepository
import org.springframework.data.repository.ListPagingAndSortingRepository
import org.springframework.data.repository.query.Param
import ru.manannikov.bootcupsbackend.entities.OrderEntity

interface OrderRepo
:
    ListCrudRepository<OrderEntity, Int>,
    ListPagingAndSortingRepository<OrderEntity, Int>,
    JpaSpecificationExecutor<OrderEntity>
{
    @Meta(comment = "Обновляет стоимость заказа при добавлении или удалении пунктов заказа")
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE OrderEntity o SET o.totalAmount = (SELECT COALESCE(SUM(oi.purchase), 0.0) FROM OrderItemEntity oi WHERE oi.parentOrder.id = :orderId)")
    fun updateOrderTotalAmount(@Param("orderId") orderId: Int)
}