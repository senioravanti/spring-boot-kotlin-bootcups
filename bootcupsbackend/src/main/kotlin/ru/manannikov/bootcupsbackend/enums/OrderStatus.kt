package ru.manannikov.bootcupsbackend.enums

enum class OrderStatus(
    override val fieldName: String
)
    : FieldEnum
{
    RAISED("order-entity.status.raised"),
    CANCELLED("order-entity.status.cancelled"),
    COMPLETED("order-entity.status.completed")
    ;

    override val fieldKey: String
        get() = this.name.lowercase()
}