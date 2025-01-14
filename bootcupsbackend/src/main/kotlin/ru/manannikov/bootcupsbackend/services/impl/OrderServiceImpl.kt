package ru.manannikov.bootcupsbackend.services.impl

import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import ru.manannikov.bootcupsbackend.entities.OrderEntity
import ru.manannikov.bootcupsbackend.enums.OrderStatus
import ru.manannikov.bootcupsbackend.exceptions.NotFoundException
import ru.manannikov.bootcupsbackend.repos.OrderRepo
import ru.manannikov.bootcupsbackend.services.OrderService

@Transactional(readOnly = true)
@Service("orderService")
class OrderServiceImpl(
    private val orderRepo: OrderRepo
)
    : OrderService
{
    override fun findAll(orderStatus: OrderStatus): List<OrderEntity> {
        TODO("Not yet implemented")
    }

    override fun findById(id: Int): OrderEntity {
        if (orderRepo.count() == 0L) throw NotFoundException(OrderEntity::class.java)

        return orderRepo.findById(id).orElseThrow {
            NotFoundException(id.toString(), OrderEntity::class.java)
        }
    }

    @Transactional
    override fun save(entity: OrderEntity) {
        TODO("Not yet implemented")
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun update(id: Int, entity: OrderEntity) {
        TODO("Not yet implemented")
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun deleteById(id: Int) {
        TODO("Not yet implemented")
    }

    companion object {
        private val logger = LogManager.getLogger(OrderServiceImpl::class.java)
    }
}