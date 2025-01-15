package ru.manannikov.bootcupsbackend.services.impl

import org.apache.logging.log4j.LogManager
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import ru.manannikov.bootcupsbackend.entities.EmployeeEntity
import ru.manannikov.bootcupsbackend.entities.MenuItemEntity
import ru.manannikov.bootcupsbackend.entities.OrderEntity
import ru.manannikov.bootcupsbackend.enums.OrderStatus
import ru.manannikov.bootcupsbackend.exceptions.EntityAlreadyExistsException
import ru.manannikov.bootcupsbackend.exceptions.NotFoundException
import ru.manannikov.bootcupsbackend.repos.OrderRepo
import ru.manannikov.bootcupsbackend.repos.specs.Specifications
import ru.manannikov.bootcupsbackend.services.OrderService
import ru.manannikov.bootcupsbackend.services.impl.MenuItemServiceImpl.Companion
import ru.manannikov.bootcupsbackend.utils.ServiceUtils.tableNameFromEntity

@Transactional(readOnly = true)
@Service("orderService")
class OrderServiceImpl(
    private val orderRepo: OrderRepo
)
    : OrderService
{
    override fun findAll(
        pageRequest: Pageable,
        filter: Map<String, String>?
    )
        : Page<OrderEntity>
    {
        logger.debug("Получены:\npageRequest: {},\nfilter: {}", pageRequest, filter)
        val orders = if (filter != null) {
            orderRepo.findAll(
                Specifications.orderFilter(filter),
                pageRequest
            )
        } else {
            orderRepo.findAll(pageRequest)
        }
        logger.debug("Из таблицы orders получены следующие записи:\n{}", orders)
        return orders
    }

    override fun findById(id: Int): OrderEntity {
        if (orderRepo.count() == 0L) throw NotFoundException(OrderEntity::class.java)

        return orderRepo.findById(id).orElseThrow {
            NotFoundException(id.toString(), OrderEntity::class.java)
        }
    }

    @Transactional
    override fun save(entity: OrderEntity): OrderEntity {
        if (entity.id != null) throw EntityAlreadyExistsException(
            tableNameFromEntity(OrderEntity::class)
        )

        val savedOrder = orderRepo.save(entity)
        logger.debug("Заказ успешно создан:\n{}", savedOrder)
        return savedOrder
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun update(id: Int, employee: EmployeeEntity, status: OrderStatus) {
        var existingOrder = findById(id)
        if (existingOrder.status != OrderStatus.RAISED) throw IllegalArgumentException("exception.illegal-argument.order.status")

        existingOrder.apply {
            this.status = status
            this.employee = employee
        }

        existingOrder = orderRepo.save(existingOrder)
        logger.debug("Информация о заказе с идентификатором {} успешно обновлена:\n{}", id, existingOrder)
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun deleteById(id: Int) {
        findById(id)
        orderRepo.deleteById(id)
        logger.debug("Информация о заказе с идентификатором {}, и все связанные с ним пункты -> успешно удалены", id)
    }

    companion object {
        private val logger = LogManager.getLogger(OrderServiceImpl::class.java)
    }
}