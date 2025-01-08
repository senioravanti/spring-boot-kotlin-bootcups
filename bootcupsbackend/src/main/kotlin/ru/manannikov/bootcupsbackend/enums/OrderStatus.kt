package ru.manannikov.bootcupsbackend.enums

enum class OrderStatus {
    /**
     1. Оформлен;
     2. Отменен;
     3. Выполнен
     */
    RAISED,
    CANCELLED,
    COMPLETED;
}