package ru.manannikov.bootcupsbackend.services.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import ru.manannikov.bootcupsbackend.entities.OrderItemEntity
import ru.manannikov.bootcupsbackend.repos.OrderItemRepo
import ru.manannikov.bootcupsbackend.services.OrderItemService

@Transactional(readOnly = true)
@Service("orderItemService")
class OrderItemServiceImpl(
    private val orderItemRepo: OrderItemRepo
) : OrderItemService {
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    override fun countByOrderId(orderId: Int): Int = orderItemRepo.countByOrderId(orderId)

    override fun findByOrderId(orderId: Int): List<OrderItemEntity> = orderItemRepo.findByOrderId(orderId)
}