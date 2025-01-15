package ru.manannikov.bootcupsbackend.services.impl

import org.apache.logging.log4j.LogManager
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import ru.manannikov.bootcupsbackend.entities.OrderEntity
import ru.manannikov.bootcupsbackend.entities.OrderItemEntity
import ru.manannikov.bootcupsbackend.exceptions.EntityAlreadyExistsException
import ru.manannikov.bootcupsbackend.exceptions.NotFoundException
import ru.manannikov.bootcupsbackend.repos.OrderItemRepo
import ru.manannikov.bootcupsbackend.repos.OrderRepo
import ru.manannikov.bootcupsbackend.services.OrderItemService
import ru.manannikov.bootcupsbackend.utils.ServiceUtils.tableNameFromEntity
import java.math.BigDecimal

@Transactional(readOnly = true)
@Service("orderItemService")
class OrderItemServiceImpl(
    private val orderItemRepo: OrderItemRepo,
    private val orderRepo: OrderRepo
)
    : OrderItemService
{
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    override fun countByOrderId(orderId: Int): Int = orderItemRepo.countByOrderId(orderId)

    override fun findById(id: Long): OrderItemEntity = orderItemRepo.findById(id).orElseThrow { NotFoundException(id.toString(), OrderItemEntity::class.java) }

    override fun findByOrderId(
        orderId: Int,
        pageRequest: Pageable
    ): Page<OrderItemEntity> = orderItemRepo.findByOrderId(orderId, pageRequest)

    @Transactional
    override fun save(parentOrder: OrderEntity, entity: OrderItemEntity) {
        if (parentOrder.id == null) throw IllegalArgumentException("exception.illegal-argument.order-item.parent-order")

        if (entity.id != null) throw EntityAlreadyExistsException(
            tableNameFromEntity(OrderItemEntity::class)
        )

        entity.parentOrder = parentOrder

        val savedOrderItem = orderItemRepo.save(entity)
        orderRepo.updateOrderTotalAmount(parentOrder.id!!)

        logger.debug("Пункт заказа успешно добавлен к заказу\nparentOrder: {}\nentity: {}", parentOrder, entity)
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun update(id: Long, quantity: Short) {
        var existingOrderItem = findById(id).apply {
            this.quantity = quantity
            purchase = menuItem.price.multiply(BigDecimal(quantity.toString()))
        }

        existingOrderItem = orderItemRepo.save(existingOrderItem)
        orderRepo.updateOrderTotalAmount(existingOrderItem.parentOrder.id!!)

        logger.debug("Количество порций и стоимость пункта заказа с идентификатором {} успешно обновлены:\n{}", id, existingOrderItem)
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun deleteById(id: Long) {
        val parentOrderId = findById(id).parentOrder.id!!

        orderItemRepo.deleteById(id)
        orderRepo.updateOrderTotalAmount(parentOrderId)

        logger.debug("Пункт заказа с идентификатором {} успешно удален", id)
    }

    companion object {
        private val logger = LogManager.getLogger(OrderItemServiceImpl::class.java)
    }
}