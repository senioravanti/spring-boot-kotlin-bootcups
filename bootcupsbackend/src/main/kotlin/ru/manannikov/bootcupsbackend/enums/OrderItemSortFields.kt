package ru.manannikov.bootcupsbackend.enums

enum class OrderItemSortFields(
    override val fieldName: String
)
    : FieldEnum
{
    QUANTITY("order-item-entity.sort-fields.quantity"),
    PURCHASE("order-item-entity.sort-fields.purchase")
    ;
    override val fieldKey: String
        get() = this.name.lowercase()
}