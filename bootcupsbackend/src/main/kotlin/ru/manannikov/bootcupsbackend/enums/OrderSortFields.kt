package ru.manannikov.bootcupsbackend.enums

enum class OrderSortFields(
    override val fieldName: String
)
    : FieldEnum
{
    TOTAL_AMOUNT("order-entity.sort-fields.total-amount"),
    DISCOUNT_AMOUNT("order-entity.sort-fields.discount-amount"),
    CREATED_AT("order-entity.sort-fields.created-at")
    ;

    override val fieldKey: String
        get() = this.name.lowercase()
}