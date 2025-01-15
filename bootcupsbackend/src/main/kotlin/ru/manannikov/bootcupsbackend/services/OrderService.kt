package ru.manannikov.bootcupsbackend.services

import ru.manannikov.bootcupsbackend.entities.OrderEntity
import ru.manannikov.bootcupsbackend.enums.OrderStatus

interface OrderService : CrudService<OrderEntity, Int> {
    fun findAll(orderStatus: OrderStatus): List<OrderEntity>

    /**
     * Названия параметров запросов на выборку, относящихся к пагинации, фильтрации и сортировке заказов
     * order_amount_max -> максимальная стоимость заказа с учетом скидки (totalAmount-discountAmount)
     * order_amount_min -> минимальная стоимость заказа с учетом скидки
     *
     * order_created_before -> Момент создания заказа (unixtime) (в секундах)
     * order_created_after -> Момент создания заказа (unixtime) (в секундах)
     */
    companion object {
        const val ORDER_AMOUNT_MAX = "order_amount_max"
        const val ORDER_AMOUNT_MIN = "order_amount_min"

        const val ORDER_CREATED_BEFORE = "order_created_before"
        const val ORDER_CREATED_AFTER = "order_created_after"
    }
}